/**
 * Copyright © 2014-2021 The SiteWhere Authors
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
package com.sitewhere.grpc.stream;

import java.util.ArrayList;
import java.util.List;

import com.sitewhere.grpc.common.CommonModelConverter;
import com.sitewhere.grpc.model.CommonModel.GOptionalString;
import com.sitewhere.grpc.model.StreamModel.GDeviceStream;
import com.sitewhere.grpc.model.StreamModel.GDeviceStreamCreateRequest;
import com.sitewhere.grpc.model.StreamModel.GDeviceStreamSearchCriteria;
import com.sitewhere.grpc.model.StreamModel.GDeviceStreamSearchResults;
import com.sitewhere.rest.model.device.request.DeviceStreamCreateRequest;
import com.sitewhere.rest.model.device.streaming.DeviceStream;
import com.sitewhere.rest.model.search.SearchResults;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.event.request.IDeviceStreamCreateRequest;
import com.sitewhere.spi.device.streaming.IDeviceStream;
import com.sitewhere.spi.search.ISearchCriteria;
import com.sitewhere.spi.search.ISearchResults;

/**
 * Convert device stream entities between SiteWhere API model and GRPC model.
 */
public class DeviceStreamModelConverter {

    /**
     * Convert a device stream create request from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceStreamCreateRequest asApiDeviceStreamCreateRequest(GDeviceStreamCreateRequest grpc)
	    throws SiteWhereException {
	DeviceStreamCreateRequest api = new DeviceStreamCreateRequest();
	api.setToken(grpc.hasToken() ? grpc.getToken().getValue() : null);
	api.setContentType(grpc.hasContentType() ? grpc.getContentType().getValue() : null);
	api.setMetadata(grpc.getMetadataMap());
	return api;
    }

    /**
     * Convert a device stream create request from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStreamCreateRequest asGrpcDeviceStreamCreateRequest(IDeviceStreamCreateRequest api)
	    throws SiteWhereException {
	GDeviceStreamCreateRequest.Builder grpc = GDeviceStreamCreateRequest.newBuilder();
	if (api.getToken() != null) {
	    grpc.setToken(GOptionalString.newBuilder().setValue(api.getToken()));
	}
	if (api.getContentType() != null) {
	    grpc.setContentType(GOptionalString.newBuilder().setValue(api.getContentType()));
	}
	if (api.getMetadata() != null) {
	    grpc.putAllMetadata(api.getMetadata());
	}
	return grpc.build();
    }

    /**
     * Convert device stream search criteria from API to GRPC.
     * 
     * @param criteria
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStreamSearchCriteria asApiDeviceStreamSearchCriteria(ISearchCriteria criteria)
	    throws SiteWhereException {
	GDeviceStreamSearchCriteria.Builder gcriteria = GDeviceStreamSearchCriteria.newBuilder();
	gcriteria.setPaging(CommonModelConverter.asGrpcPaging(criteria));
	return gcriteria.build();
    }

    /**
     * Convert device stream search results from GRPC to API.
     * 
     * @param response
     * @return
     * @throws SiteWhereException
     */
    public static ISearchResults<IDeviceStream> asApiDeviceStreamSearchResults(GDeviceStreamSearchResults response)
	    throws SiteWhereException {
	List<IDeviceStream> results = new ArrayList<IDeviceStream>();
	for (GDeviceStream grpc : response.getStreamsList()) {
	    results.add(DeviceStreamModelConverter.asApiDeviceStream(grpc));
	}
	return new SearchResults<IDeviceStream>(results, response.getCount());
    }

    /**
     * Convert a device stream from GRPC to API.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static DeviceStream asApiDeviceStream(GDeviceStream grpc) throws SiteWhereException {
	DeviceStream api = new DeviceStream();
	api.setAssignmentId(CommonModelConverter.asApiUuid(grpc.getDeviceAssignmentId()));
	api.setContentType(grpc.getContentType());
	CommonModelConverter.setEntityInformation(api, grpc.getEntityInformation());
	return api;
    }

    /**
     * Convert a device stream from API to GRPC.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceStream asGrpcDeviceStream(IDeviceStream api) throws SiteWhereException {
	GDeviceStream.Builder grpc = GDeviceStream.newBuilder();
	grpc.setDeviceAssignmentId(CommonModelConverter.asGrpcUuid(api.getAssignmentId()));
	grpc.setContentType(api.getContentType());
	grpc.setEntityInformation(CommonModelConverter.asGrpcEntityInformation(api));
	return grpc.build();
    }
}
