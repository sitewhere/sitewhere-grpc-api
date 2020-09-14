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
package com.sitewhere.grpc.device;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sitewhere.grpc.common.CommonModelConverter;
import com.sitewhere.grpc.model.DeviceModel.GDeviceRegistationPayload;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.event.kafka.IDeviceRegistrationPayload;

/**
 * Methods that support marshaling/unmarshaling device model payloads.
 */
public class DeviceModelMarshaler {

    /**
     * Build binary message for API device registration payload.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildDeviceRegistrationPayloadMessage(IDeviceRegistrationPayload api)
	    throws SiteWhereException {
	GDeviceRegistationPayload grpc = DeviceModelConverter.asGrpcDeviceRegistrationPayload(api);
	return buildDeviceRegistrationPayloadMessage(grpc);
    }

    /**
     * Build binary message for GRPC device registration payload.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildDeviceRegistrationPayloadMessage(GDeviceRegistationPayload grpc)
	    throws SiteWhereException {
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	try {
	    grpc.writeTo(output);
	    return output.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException("Unable to build device registration payload message.", e);
	} finally {
	    CommonModelConverter.closeQuietly(output);
	}
    }

    /**
     * Parse message that contains an device registration payload.
     * 
     * @param payload
     * @return
     * @throws SiteWhereException
     */
    public static GDeviceRegistationPayload parseDeviceRegistrationPayloadMessage(byte[] payload)
	    throws SiteWhereException {
	try {
	    return GDeviceRegistationPayload.parseFrom(payload);
	} catch (InvalidProtocolBufferException e) {
	    throw new SiteWhereException("Unable to parse device registration payload message.", e);
	}
    }
}
