/**
 * Copyright © 2014-2020 The SiteWhere Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sitewhere.grpc.devicestate;

import java.util.ArrayList;
import java.util.List;

import com.sitewhere.grpc.common.CommonModelConverter;
import com.sitewhere.grpc.event.EventModelConverter;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceState;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceStateCreateRequest;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceStateEventMergeRequest;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceStateSearchCriteria;
import com.sitewhere.grpc.model.DeviceStateModel.GDeviceStateSearchResults;
import com.sitewhere.rest.model.device.state.DeviceState;
import com.sitewhere.rest.model.device.state.request.DeviceStateCreateRequest;
import com.sitewhere.rest.model.device.state.request.DeviceStateEventMergeRequest;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.rest.model.search.device.DeviceStateSearchCriteria;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.state.IDeviceState;
import com.sitewhere.spi.device.state.request.IDeviceStateCreateRequest;
import com.sitewhere.spi.device.state.request.IDeviceStateEventMergeRequest;
import com.sitewhere.spi.search.ISearchResults;
import com.sitewhere.spi.search.device.IDeviceStateSearchCriteria;

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
     * Convert device state event merge request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static IDeviceStateEventMergeRequest asApiDeviceStateEventMergeRequest(GDeviceStateEventMergeRequest grpc)
	    throws SiteWhereException {
	DeviceStateEventMergeRequest api = new DeviceStateEventMergeRequest();
	api.getLocations().addAll(EventModelConverter.asApiDeviceLocations(grpc.getLocationsList()));
	api.getMeasurements().addAll(EventModelConverter.asApiDeviceMeasurements(grpc.getMeasurementsList()));
	api.getAlerts().addAll(EventModelConverter.asApiDeviceAlerts(grpc.getAlertsList()));
	return api;
    }

    /**
     * Convert device state event merge request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStateEventMergeRequest asGrpcDeviceStateEventMergeRequest(IDeviceStateEventMergeRequest api)
	    throws SiteWhereException {
	GDeviceStateEventMergeRequest.Builder grpc = GDeviceStateEventMergeRequest.newBuilder();
	if (api.getLocations() != null) {
	    grpc.addAllLocations(EventModelConverter.asGrpcDeviceLocations(api.getLocations()));
	}
	if (api.getMeasurements() != null) {
	    grpc.addAllMeasurements(EventModelConverter.asGrpcDeviceMeasurements(api.getMeasurements()));
	}
	if (api.getAlerts() != null) {
	    grpc.addAllAlerts(EventModelConverter.asGrpcDeviceAlerts(api.getAlerts()));
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
    public static DeviceState asApiDeviceState(GDeviceState grpc) throws SiteWhereException {
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
     * Convert device states from GRPC to API.
     * 
     * @param grpcs
     * @return
     * @throws SiteWhereException
     */
    public static List<DeviceState> asApiDeviceStates(List<GDeviceState> grpcs) throws SiteWhereException {
	List<DeviceState> api = new ArrayList<DeviceState>();
	for (GDeviceState grpc : grpcs) {
	    api.add(asApiDeviceState(grpc));
	}
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
     * Convert device states from API to GRPC.
     * 
     * @param apis
     * @return
     * @throws SiteWhereException
     */
    public static List<GDeviceState> asGrpcDeviceStates(List<? extends IDeviceState> apis) throws SiteWhereException {
	List<GDeviceState> grpcs = new ArrayList<GDeviceState>();
	for (IDeviceState api : apis) {
	    grpcs.add(asGrpcDeviceState(api));
	}
	return grpcs;
    }
}
