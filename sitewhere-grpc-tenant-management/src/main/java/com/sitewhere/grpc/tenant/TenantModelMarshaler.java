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
package com.sitewhere.grpc.tenant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sitewhere.grpc.common.CommonModelConverter;
import com.sitewhere.grpc.model.TenantModel.GTenantModelUpdate;
import com.sitewhere.grpc.model.TenantModel.GTenantModelUpdateType;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.tenant.ITenant;

public class TenantModelMarshaler {

    /**
     * Build message that reflects a tenant model update.
     * 
     * @param type
     * @param tenant
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildTenantModelUpdateMessage(GTenantModelUpdateType type, ITenant tenant)
	    throws SiteWhereException {
	GTenantModelUpdate.Builder update = GTenantModelUpdate.newBuilder();
	update.setType(type);
	update.setTenant(TenantModelConverter.asGrpcTenant(tenant));
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	try {
	    update.build().writeTo(output);
	    return output.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException("Unable to build tenant update message.", e);
	} finally {
	    CommonModelConverter.closeQuietly(output);
	}
    }

    /**
     * Parse message that reflects a tenant model update.
     * 
     * @param payload
     * @return
     * @throws SiteWhereException
     */
    public static GTenantModelUpdate parseTenantModelUpdateMessage(byte[] payload) throws SiteWhereException {
	try {
	    return GTenantModelUpdate.parseFrom(payload);
	} catch (InvalidProtocolBufferException e) {
	    throw new SiteWhereException("Unable to parse tenant update message.", e);
	}
    }
}
