/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.devicestate;

import java.util.ArrayList;
import java.util.List;

import com.sitewhere.grpc.common.CommonModelConverter;
import com.sitewhere.grpc.event.EventModelConverter;
import com.sitewhere.grpc.model.CommonModel.GOptionalString;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceState;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceStateCreateRequest;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceStateSearchCriteria;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceStateSearchResults;
import com.sitewhere.grpc.model.DeviceStateModel.GRecentStateEvent;
import com.sitewhere.grpc.model.DeviceStateModel.GRecentStateEventCreateRequest;
import com.sitewhere.grpc.model.DeviceStateModel.GRecentStateEventSearchCriteria;
import com.sitewhere.grpc.model.DeviceStateModel.GRecentStateEventSearchResults;
import com.sitewhere.rest.model.device.state.DeviceState;
import com.sitewhere.rest.model.device.state.RecentStateEvent;
import com.sitewhere.rest.model.device.state.request.DeviceStateCreateRequest;
import com.sitewhere.rest.model.device.state.request.RecentStateEventCreateRequest;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.rest.model.search.device.DeviceStateSearchCriteria;
import com.sitewhere.rest.model.search.device.RecentStateEventSearchCriteria;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.state.IDeviceState;
import com.sitewhere.spi.device.state.IRecentStateEvent;
import com.sitewhere.spi.device.state.request.IDeviceStateCreateRequest;
import com.sitewhere.spi.device.state.request.IRecentStateEventCreateRequest;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.search.device.IDeviceStateSearchCriteria;
import com.sitewhere.spi.search.device.IRecentStateEventSearchCriteria;

/**
 * Convert device state entities between SiteWhere API model and GRPC model.
 */
public class DeviceStateModelConverter {

    /**
     * Convert device state create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IDeviceStateCreateRequest asApiDeviceStateCreateRequest(GDeviceStateCreateRequest grpc)
	    throws SiteWhereException {
	DeviceStateCreateRequest api = new DeviceStateCreateRequest();
	api.setDeviceId(CommonModelConverter.asApiUuid(grpc.getDeviceId()));
	api.setDeviceTypeId(CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()));
	api.setDeviceAssignmentId(CommonModelConverter.asApiUuid(grpc.getDeviceAssignmentId()));
	api.setCustomerId(CommonModelConverter.asApiUuid(grpc.getCustomerId()));
	api.setAreaId(CommonModelConverter.asApiUuid(grpc.getAreaId()));
	api.setAssetId(CommonModelConverter.asApiUuid(grpc.getAssetId()));
	api.setLastInteractionDate(CommonModelConverter.asApiDate(grpc.getLastInteractionDate()));
	api.setPresenceMissingDate(CommonModelConverter.asApiDate(grpc.getPresenceMissingDate()));
	return api;
    }

    /**
     * Convert device state create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStateCreateRequest asGrpcDeviceStateCreateRequest(IDeviceStateCreateRequest api)
	    throws SiteWhereException {
	GDeviceStateCreateRequest.Builder grpc = GDeviceStateCreateRequest.newBuilder();
	if (api.getDeviceId() != null) {
	    grpc.setDeviceId(CommonModelConverter.asGrpcUuid(api.getDeviceId()));
	}
	if (api.getDeviceTypeId() != null) {
	    grpc.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	}
	if (api.getDeviceAssignmentId() != null) {
	    grpc.setDeviceAssignmentId(CommonModelConverter.asGrpcUuid(api.getDeviceAssignmentId()));
	}
	if (api.getCustomerId() != null) {
	    grpc.setCustomerId(CommonModelConverter.asGrpcUuid(api.getCustomerId()));
	}
	if (api.getAreaId() != null) {
	    grpc.setAreaId(CommonModelConverter.asGrpcUuid(api.getAreaId()));
	}
	if (api.getAssetId() != null) {
	    grpc.setAssetId(CommonModelConverter.asGrpcUuid(api.getAssetId()));
	}
	if (api.getLastInteractionDate() != null) {
	    grpc.setLastInteractionDate(CommonModelConverter.asGrpcDate(api.getLastInteractionDate()));
	}
	if (api.getPresenceMissingDate() != null) {
	    grpc.setPresenceMissingDate(CommonModelConverter.asGrpcDate(api.getPresenceMissingDate()));
	}
	return grpc.build();
    }

    /**
     * Convert device state search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceStateSearchCriteria asApiDeviceStateSearchCriteria(GDeviceStateSearchCriteria grpc)
	    throws SiteWhereException {
	DeviceStateSearchCriteria api = new DeviceStateSearchCriteria();
	api.setLastInteractionDateBefore(CommonModelConverter.asApiDate(grpc.getLastInteractionDateBefore()));
	if (grpc.getDeviceTypeTokenCount() > 0) {
	    api.setDeviceTypeTokens(grpc.getDeviceTypeTokenList());
	}
	if (grpc.getCustomerTokenCount() > 0) {
	    api.setCustomerTokens(grpc.getCustomerTokenList());
	}
	if (grpc.getAreaTokenCount() > 0) {
	    api.setAreaTokens(grpc.getAreaTokenList());
	}
	if (grpc.getAssetTokenCount() > 0) {
	    api.setAssetTokens(grpc.getAssetTokenList());
	}
	api.setPageNumber(grpc.getPaging().getPageNumber());
	api.setPageSize(grpc.getPaging().getPageSize());
	return api;
    }

    /**
     * Convert device state search criteria from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStateSearchCriteria asGrpcDeviceStateSearchCriteria(IDeviceStateSearchCriteria api)
	    throws SiteWhereException {
	GDeviceStateSearchCriteria.Builder grpc = GDeviceStateSearchCriteria.newBuilder();
	grpc.setLastInteractionDateBefore(CommonModelConverter.asGrpcDate(api.getLastInteractionDateBefore()));
	if (api.getDeviceTypeTokens() != null) {
	    grpc.addAllDeviceTypeToken(api.getDeviceTypeTokens());
	}
	if (api.getCustomerTokens() != null) {
	    grpc.addAllCustomerToken(api.getCustomerTokens());
	}
	if (api.getAreaTokens() != null) {
	    grpc.addAllAreaToken(api.getAreaTokens());
	}
	if (api.getAssetTokens() != null) {
	    grpc.addAllAssetToken(api.getAssetTokens());
	}
	grpc.setPaging(CommonModelConverter.asGrpcPaging(api));
	return grpc.build();
    }

    /**
     * Convert device state search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceState> asApiDeviceStateSearchResults(GDeviceStateSearchResults response)
	    throws SiteWhereException {
	List<IDeviceState> results = new ArrayList<IDeviceState>();
	for (GDeviceState grpc : response.getDeviceStatesList()) {
	    results.add(DeviceStateModelConverter.asApiDeviceState(grpc));
	}
	return new SearchResults<IDeviceState>(results, response.getCount());
    }

    /**
     * Convert device state from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IDeviceState asApiDeviceState(GDeviceState grpc) throws SiteWhereException {
	DeviceState api = new DeviceState();
	api.setId(CommonModelConverter.asApiUuid(grpc.getId()));
	api.setDeviceId(CommonModelConverter.asApiUuid(grpc.getDeviceId()));
	api.setDeviceTypeId(CommonModelConverter.asApiUuid(grpc.getDeviceTypeId()));
	api.setDeviceAssignmentId(CommonModelConverter.asApiUuid(grpc.getDeviceAssignmentId()));
	api.setCustomerId(CommonModelConverter.asApiUuid(grpc.getCustomerId()));
	api.setAreaId(CommonModelConverter.asApiUuid(grpc.getAreaId()));
	api.setAssetId(CommonModelConverter.asApiUuid(grpc.getAssetId()));
	api.setLastInteractionDate(CommonModelConverter.asApiDate(grpc.getLastInteractionDate()));
	api.setPresenceMissingDate(CommonModelConverter.asApiDate(grpc.getPresenceMissingDate()));
	return api;
    }

    /**
     * Convert device state from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceState asGrpcDeviceState(IDeviceState api) throws SiteWhereException {
	GDeviceState.Builder grpc = GDeviceState.newBuilder();
	if (api.getId() != null) {
	    grpc.setId(CommonModelConverter.asGrpcUuid(api.getId()));
	}
	if (api.getDeviceId() != null) {
	    grpc.setDeviceId(CommonModelConverter.asGrpcUuid(api.getDeviceId()));
	}
	if (api.getDeviceTypeId() != null) {
	    grpc.setDeviceTypeId(CommonModelConverter.asGrpcUuid(api.getDeviceTypeId()));
	}
	if (api.getDeviceAssignmentId() != null) {
	    grpc.setDeviceAssignmentId(CommonModelConverter.asGrpcUuid(api.getDeviceAssignmentId()));
	}
	if (api.getCustomerId() != null) {
	    grpc.setCustomerId(CommonModelConverter.asGrpcUuid(api.getCustomerId()));
	}
	if (api.getAreaId() != null) {
	    grpc.setAreaId(CommonModelConverter.asGrpcUuid(api.getAreaId()));
	}
	if (api.getAssetId() != null) {
	    grpc.setAssetId(CommonModelConverter.asGrpcUuid(api.getAssetId()));
	}
	if (api.getLastInteractionDate() != null) {
	    grpc.setLastInteractionDate(CommonModelConverter.asGrpcDate(api.getLastInteractionDate()));
	}
	if (api.getPresenceMissingDate() != null) {
	    grpc.setPresenceMissingDate(CommonModelConverter.asGrpcDate(api.getPresenceMissingDate()));
	}
	return grpc.build();
    }

    /**
     * Convert recent state event create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IRecentStateEventCreateRequest asApiRecentStateEventCreateRequest(GRecentStateEventCreateRequest grpc)
	    throws SiteWhereException {
	RecentStateEventCreateRequest api = new RecentStateEventCreateRequest();
	api.setDeviceStateId(CommonModelConverter.asApiUuid(grpc.getDeviceStateId()));
	api.setEventType(EventModelConverter.asApiDeviceEventType(grpc.getEventType()));
	api.setClassifier(grpc.hasClassifier() ? grpc.getClassifier().getValue() : null);
	api.setValue(grpc.hasValue() ? grpc.getValue().getValue() : null);
	api.setEventId(CommonModelConverter.asApiUuid(grpc.getEventId()));
	api.setEventDate(CommonModelConverter.asApiDate(grpc.getEventDate()));
	return api;
    }

    /**
     * Convert recent state event create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GRecentStateEventCreateRequest asGrpcRecentStateEventCreateRequest(IRecentStateEventCreateRequest api)
	    throws SiteWhereException {
	GRecentStateEventCreateRequest.Builder grpc = GRecentStateEventCreateRequest.newBuilder();
	if (api.getDeviceStateId() != null) {
	    grpc.setDeviceStateId(CommonModelConverter.asGrpcUuid(api.getDeviceStateId()));
	}
	if (api.getEventType() != null) {
	    grpc.setEventType(EventModelConverter.asGrpcDeviceEventType(api.getEventType()));
	}
	if (api.getClassifier() != null) {
	    grpc.setClassifier(GOptionalString.newBuilder().setValue(api.getClassifier()));
	}
	if (api.getValue() != null) {
	    grpc.setValue(GOptionalString.newBuilder().setValue(api.getValue()));
	}
	if (api.getEventId() != null) {
	    grpc.setEventId(CommonModelConverter.asGrpcUuid(api.getEventId()));
	}
	if (api.getEventDate() != null) {
	    grpc.setEventDate(CommonModelConverter.asGrpcDate(api.getEventDate()));
	}
	return grpc.build();
    }

    /**
     * Convert recent state event search criteria from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static RecentStateEventSearchCriteria asApiRecentStateEventSearchCriteria(
	    GRecentStateEventSearchCriteria grpc) throws SiteWhereException {
	RecentStateEventSearchCriteria api = new RecentStateEventSearchCriteria();
	api.setDeviceStateId(CommonModelConverter.asApiUuid(grpc.getDeviceStateId()));
	api.setEventType(EventModelConverter.asApiDeviceEventType(grpc.getEventType()));
	api.setClassifier(grpc.hasClassifier() ? grpc.getClassifier().getValue() : null);
	api.setPageNumber(grpc.getPaging().getPageNumber());
	api.setPageSize(grpc.getPaging().getPageSize());
	return api;
    }

    /**
     * Convert recent state event search criteria from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GRecentStateEventSearchCriteria asGrpcRecentStateEventSearchCriteria(
	    IRecentStateEventSearchCriteria api) throws SiteWhereException {
	GRecentStateEventSearchCriteria.Builder grpc = GRecentStateEventSearchCriteria.newBuilder();
	if (api.getDeviceStateId() != null) {
	    grpc.setDeviceStateId(CommonModelConverter.asGrpcUuid(api.getDeviceStateId()));
	}
	if (api.getEventType() != null) {
	    grpc.setEventType(EventModelConverter.asGrpcDeviceEventType(api.getEventType()));
	}
	if (api.getClassifier() != null) {
	    grpc.setClassifier(GOptionalString.newBuilder().setValue(api.getClassifier()));
	}
	grpc.setPaging(CommonModelConverter.asGrpcPaging(api));
	return grpc.build();
    }

    /**
     * Convert recent state event search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IRecentStateEvent> asApiRecentStateEventSearchResults(
	    GRecentStateEventSearchResults response) throws SiteWhereException {
	List<IRecentStateEvent> results = new ArrayList<IRecentStateEvent>();
	for (GRecentStateEvent grpc : response.getRecentStateEventsList()) {
	    results.add(DeviceStateModelConverter.asApiRecentStateEvent(grpc));
	}
	return new SearchResults<IRecentStateEvent>(results, response.getCount());
    }

    /**
     * Convert recent state event from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IRecentStateEvent asApiRecentStateEvent(GRecentStateEvent grpc) throws SiteWhereException {
	RecentStateEvent api = new RecentStateEvent();
	api.setId(CommonModelConverter.asApiUuid(grpc.getId()));
	api.setDeviceStateId(CommonModelConverter.asApiUuid(grpc.getDeviceStateId()));
	api.setEventType(EventModelConverter.asApiDeviceEventType(grpc.getEventType()));
	api.setClassifier(grpc.getClassifier());
	api.setValue(grpc.getValue());
	api.setEventId(CommonModelConverter.asApiUuid(grpc.getEventId()));
	api.setEventDate(CommonModelConverter.asApiDate(grpc.getEventDate()));
	return api;
    }

    /**
     * Convert recent state event from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GRecentStateEvent asGrpcRecentStateEvent(IRecentStateEvent api) throws SiteWhereException {
	GRecentStateEvent.Builder grpc = GRecentStateEvent.newBuilder();
	if (api.getId() != null) {
	    grpc.setId(CommonModelConverter.asGrpcUuid(api.getId()));
	}
	if (api.getDeviceStateId() != null) {
	    grpc.setDeviceStateId(CommonModelConverter.asGrpcUuid(api.getDeviceStateId()));
	}
	if (api.getEventType() != null) {
	    grpc.setEventType(EventModelConverter.asGrpcDeviceEventType(api.getEventType()));
	}
	if (api.getClassifier() != null) {
	    grpc.setClassifier(api.getClassifier());
	}
	if (api.getValue() != null) {
	    grpc.setValue(api.getValue());
	}
	if (api.getEventId() != null) {
	    grpc.setEventId(CommonModelConverter.asGrpcUuid(api.getEventId()));
	}
	if (api.getEventDate() != null) {
	    grpc.setEventDate(CommonModelConverter.asGrpcDate(api.getEventDate()));
	}
	return grpc.build();
    }
}
