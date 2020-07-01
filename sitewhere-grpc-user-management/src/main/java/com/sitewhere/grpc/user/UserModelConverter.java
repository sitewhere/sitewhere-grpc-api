/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.user;

import java.util.ArrayList;
import java.util.List;

import com.sitewhere.grpc.common.CommonModelConverter;
import com.sitewhere.grpc.model.CommonModel.GGrantedAuthorityReference;
import com.sitewhere.grpc.model.CommonModel.GOptionalString;
import com.sitewhere.grpc.model.CommonModel.GUserAccountStatus;
import com.sitewhere.grpc.model.UserModel;
import com.sitewhere.grpc.model.UserModel.GGrantedAuthority;
import com.sitewhere.grpc.model.UserModel.GGrantedAuthorityCreateRequest;
import com.sitewhere.grpc.model.UserModel.GGrantedAuthoritySearchCriteria;
import com.sitewhere.grpc.model.UserModel.GGrantedAuthoritySearchResults;
import com.sitewhere.grpc.model.UserModel.GUser;
import com.sitewhere.grpc.model.UserModel.GUserCreateRequest;
import com.sitewhere.grpc.model.UserModel.GUserSearchCriteria;
import com.sitewhere.grpc.model.UserModel.GUserSearchResults;
import com.sitewhere.grpc.model.UserModel.GRole;
import com.sitewhere.grpc.model.UserModel.GRoleCreateRequest;
import com.sitewhere.grpc.model.UserModel.GRoleSearchResults;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.rest.model.user.GrantedAuthority;
import com.sitewhere.rest.model.user.Role;
import com.sitewhere.rest.model.user.User;
import com.sitewhere.rest.model.user.request.GrantedAuthorityCreateRequest;
import com.sitewhere.rest.model.user.request.UserCreateRequest;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.user.*;
import com.sitewhere.spi.user.request.IGrantedAuthorityCreateRequest;
import com.sitewhere.spi.user.request.IRoleCreateRequest;
import com.sitewhere.spi.user.request.IUserCreateRequest;

/**
 * Convert user entities between SiteWhere API model and GRPC model.
 */
public class UserModelConverter {

    /**
     * Convert account status from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static AccountStatus asApiAccountStatus(GUserAccountStatus grpc) throws SiteWhereException {
	switch (grpc) {
	case USER_STATUS_ACTIVE:
	    return AccountStatus.Active;
	case USER_STATUS_EXPIRED:
	    return AccountStatus.Expired;
	case USER_STATUS_LOCKED:
	    return AccountStatus.Locked;
	case UNRECOGNIZED:
	    throw new SiteWhereException("Unknown account status: " + grpc.name());
	}
	return null;
    }

    /**
     * Convert account status from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GUserAccountStatus asGrpcAccountStatus(AccountStatus api) throws SiteWhereException {
	switch (api) {
	case Active:
	    return GUserAccountStatus.USER_STATUS_ACTIVE;
	case Expired:
	    return GUserAccountStatus.USER_STATUS_EXPIRED;
	case Locked:
	    return GUserAccountStatus.USER_STATUS_LOCKED;
	}
	throw new SiteWhereException("Unknown account status: " + api.name());
    }

    /**
     * Convert user create request from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IUserCreateRequest asApiUserCreateRequest(GUserCreateRequest grpc) throws SiteWhereException {
	UserCreateRequest api = new UserCreateRequest();
	api.setUsername(grpc.hasUsername() ? grpc.getUsername().getValue() : null);
	api.setPassword(grpc.hasPassword() ? grpc.getPassword().getValue() : null);
	api.setFirstName(grpc.hasFirstName() ? grpc.getFirstName().getValue() : null);
	api.setLastName(grpc.hasLastName() ? grpc.getLastName().getValue() : null);
	api.setStatus(UserModelConverter.asApiAccountStatus(grpc.getStatus()));
	if (grpc.getAuthoritiesList().size() > 0) {
	    List<String> authorities = new ArrayList<>();
	    authorities.addAll(grpc.getAuthoritiesList());
	    api.setAuthorities(authorities);
	}
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert user create request from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GUserCreateRequest asGrpcUserCreateRequest(IUserCreateRequest api) throws SiteWhereException {
	GUserCreateRequest.Builder builder = GUserCreateRequest.newBuilder();
	if (api.getUsername() != null) {
	    builder.setUsername(GOptionalString.newBuilder().setValue(api.getUsername()));
	}
	if (api.getPassword() != null) {
	    builder.setPassword(GOptionalString.newBuilder().setValue(api.getPassword()));
	}
	if (api.getFirstName() != null) {
	    builder.setFirstName(GOptionalString.newBuilder().setValue(api.getFirstName()));
	}
	if (api.getLastName() != null) {
	    builder.setLastName(GOptionalString.newBuilder().setValue(api.getLastName()));
	}
	if (api.getStatus() != null) {
	    builder.setStatus(UserModelConverter.asGrpcAccountStatus(api.getStatus()));
	}
	if (api.getAuthorities() != null) {
	    builder.addAllAuthorities(api.getAuthorities());
	}
	if (api.getMetadata() != null) {
	    builder.putAllMetadata(api.getMetadata());
	}
	return builder.build();
    }

    /**
     * Convert user from API to GRPC.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IUser asApiUser(GUser grpc) throws SiteWhereException {
	User api = new User();
	api.setUsername(grpc.getUsername());
	api.setHashedPassword(grpc.getHashedPassword());
	api.setFirstName(grpc.getFirstName());
	api.setLastName(grpc.getLastName());
	api.setStatus(UserModelConverter.asApiAccountStatus(grpc.getStatus()));
	api.setLastLogin(CommonModelConverter.asApiDate(grpc.getLastLogin()));
	api.getAuthorities().addAll(grpc.getAuthoritiesList());
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	return api;
    }

    /**
     * Convert user from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GUser asGrpcUser(IUser api) throws SiteWhereException {
	GUser.Builder builder = UserModel.GUser.newBuilder();
	builder.setUsername(api.getUsername());
	builder.setHashedPassword(api.getHashedPassword());
	builder.setFirstName(api.getFirstName());
	builder.setLastName(api.getLastName());
	builder.setStatus(UserModelConverter.asGrpcAccountStatus(api.getStatus()));
	builder.setLastLogin(CommonModelConverter.asGrpcDate(api.getLastLogin()));
	if (api.getAuthorities() != null) {
	    builder.addAllAuthorities(api.getAuthorities());
	}
	builder.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	return builder.build();
    }

    /**
     * Convert user search criteria from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GUserSearchCriteria asGrpcUserSearchCriteria(IUserSearchCriteria api) throws SiteWhereException {
	GUserSearchCriteria.Builder builder = GUserSearchCriteria.newBuilder();
	return builder.build();
    }

    /**
     * Convert user search results from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IUser> asApiUserSearchResults(GUserSearchResults grpc) throws SiteWhereException {
	List<IUser> users = UserModelConverter.asApiUsers(grpc.getUsersList());
	return new SearchResults<IUser>(users, grpc.getCount());
    }

    /**
     * Convert user search results from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GUserSearchResults asGrpcUserSearchResults(ISearchResults<IUser> api) throws SiteWhereException {
	GUserSearchResults.Builder grpc = GUserSearchResults.newBuilder();
	grpc.setCount(api.getNumResults());
	grpc.addAllUsers(UserModelConverter.asGrpcUsers(api.getResults()));
	return grpc.build();
    }

    /**
     * Convert a list of users from GRPC to API.
     *
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<IUser> asApiUsers(List<GUser> grpcs) throws SiteWhereException {
	List<IUser> api = new ArrayList<IUser>();
	for (GUser guser : grpcs) {
	    api.add(UserModelConverter.asApiUser(guser));
	}
	return api;
    }

    /**
     * Convert a list of users from API to GRPC.
     *
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GUser> asGrpcUsers(List<IUser> apis) throws SiteWhereException {
	List<GUser> grpcs = new ArrayList<GUser>();
	for (IUser apiUser : apis) {
	    grpcs.add(UserModelConverter.asGrpcUser(apiUser));
	}
	return grpcs;
    }

    /**
     * Convert granted authority create request from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IGrantedAuthorityCreateRequest asApiGrantedAuthorityCreateRequest(GGrantedAuthorityCreateRequest grpc)
	    throws SiteWhereException {
	GrantedAuthorityCreateRequest api = new GrantedAuthorityCreateRequest();
	api.setAuthority(grpc.getAuthority());
	api.setDescription(grpc.getDescription());
	api.setParent(grpc.hasParent() ? grpc.getParent().getAuthority() : null);
	api.setGroup(grpc.getGroup());
	return api;
    }

    /**
     * Convert granted authority create request from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GGrantedAuthorityCreateRequest asGrpcGrantedAuthorityCreateRequest(IGrantedAuthorityCreateRequest api)
	    throws SiteWhereException {
	GGrantedAuthorityCreateRequest.Builder builder = GGrantedAuthorityCreateRequest.newBuilder();
	builder.setAuthority(api.getAuthority());
	builder.setDescription(api.getDescription());
	if (api.getParent() != null) {
	    GGrantedAuthorityReference.Builder parent = GGrantedAuthorityReference.newBuilder();
	    parent.setAuthority(api.getParent());
	    builder.setParent(parent);
	}
	builder.setGroup(api.isGroup());
	return builder.build();
    }

    /**
     * Convert granted authority search criteria from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GGrantedAuthoritySearchCriteria asGrpcGrantedAuthoritySearchCriteria(
	    IGrantedAuthoritySearchCriteria api) throws SiteWhereException {
	GGrantedAuthoritySearchCriteria.Builder builder = GGrantedAuthoritySearchCriteria.newBuilder();
	return builder.build();
    }

    /**
     * Convert granted authority search results from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IGrantedAuthority> asApiGrantedAuthoritySearchResults(
	    GGrantedAuthoritySearchResults grpc) throws SiteWhereException {
	List<IGrantedAuthority> auths = UserModelConverter.asApiGrantedAuthorities(grpc.getAuthoritiesList());
	return new SearchResults<IGrantedAuthority>(auths, grpc.getCount());
    }

    /**
     * Convert granted authority search results from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GGrantedAuthoritySearchResults asGrpcGrantedAuthoritySearchResults(
	    ISearchResults<IGrantedAuthority> api) throws SiteWhereException {
	GGrantedAuthoritySearchResults.Builder grpc = GGrantedAuthoritySearchResults.newBuilder();
	grpc.setCount(api.getNumResults());
	grpc.addAllAuthorities(UserModelConverter.asGrpcGrantedAuthorities(api.getResults()));
	return grpc.build();
    }

    /**
     * Convert granted authority from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IGrantedAuthority asApiGrantedAuthority(GGrantedAuthority grpc) throws SiteWhereException {
	GrantedAuthority api = new GrantedAuthority();
	api.setAuthority(grpc.getAuthority());
	api.setDescription(grpc.getDescription());
	api.setParent(grpc.hasParent() ? grpc.getParent().getAuthority() : null);
	api.setGroup(grpc.getGroup());
	return api;
    }

    /**
     * Convert a list of granted authorities from GRPC to API.
     *
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<IGrantedAuthority> asApiGrantedAuthorities(List<GGrantedAuthority> grpcs)
	    throws SiteWhereException {
	List<IGrantedAuthority> api = new ArrayList<IGrantedAuthority>();
	for (GGrantedAuthority gauth : grpcs) {
	    api.add(UserModelConverter.asApiGrantedAuthority(gauth));
	}
	return api;
    }

    /**
     * Convert a granted authority from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GGrantedAuthority asGrpcGrantedAuthority(IGrantedAuthority api) throws SiteWhereException {
	GGrantedAuthority.Builder builder = UserModel.GGrantedAuthority.newBuilder();
	builder.setAuthority(api.getAuthority());
	builder.setDescription(api.getDescription());
	if (api.getParent() != null) {
	    GGrantedAuthorityReference.Builder parent = GGrantedAuthorityReference.newBuilder();
	    parent.setAuthority(api.getParent());
	    builder.setParent(parent);
	}
	builder.setGroup(api.isGroup());
	return builder.build();
    }

    /**
     * Convert a list of granted authorities from API to GRPC.
     *
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GGrantedAuthority> asGrpcGrantedAuthorities(List<IGrantedAuthority> apis)
	    throws SiteWhereException {
	List<GGrantedAuthority> grpcs = new ArrayList<GGrantedAuthority>();
	for (IGrantedAuthority api : apis) {
	    grpcs.add(UserModelConverter.asGrpcGrantedAuthority(api));
	}
	return grpcs;
    }

    //*******************************inicio********************************
    /**
     * Convert a list of roles from GRPC to API.
     *
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<IRole> asApiRole(List<GRole> grpcs)
		    throws SiteWhereException {
	List<IRole> api = new ArrayList<IRole>();
	for (GRole gauth : grpcs) {
	    api.add(UserModelConverter.asApiRole(gauth));
	}
	return api;
    }

    /**
     * Convert role from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IRole asApiRole(GRole grpc) throws SiteWhereException {
	Role api = new Role();
	api.setRole(grpc.getRole());
	api.setDescription(grpc.getDescription());
	api.setAuthorities(grpc.getAuthorities());
	return api;
    }

    /**
     * Convert role create request from API to GRPC.
     *
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GRoleCreateRequest asGrpcRoleCreateRequest(IRoleCreateRequest api)
		    throws SiteWhereException {
	GRoleCreateRequest.Builder builder = GRoleCreateRequest.newBuilder();
	builder.setRole(api.getRole());
	builder.setDescription(api.getDescription());
	builder.setAuthorities(api.getAuthorities());
	return builder.build();
    }

    /**
     * Convert role search results from GRPC to API.
     *
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IRole> asApiRoleSearchResults(GRoleSearchResults grpc) throws SiteWhereException {
	List<IRole> roles = UserModelConverter.asApiRole(grpc.getRolesList());
	return new SearchResults<IRole>(roles, grpc.getCount());
    }
}