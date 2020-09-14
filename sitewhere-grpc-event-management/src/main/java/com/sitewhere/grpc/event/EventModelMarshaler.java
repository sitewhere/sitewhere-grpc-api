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
package com.sitewhere.grpc.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.sitewhere.grpc.common.CommonModelConverter;
import com.sitewhere.grpc.model.DeviceEventModel.GDecodedEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GPreprocessedEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GProcessedEventPayload;
import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.device.event.kafka.IDecodedEventPayload;
import com.sitewhere.spi.device.event.kafka.IPreprocessedEventPayload;
import com.sitewhere.spi.device.event.kafka.IProcessedEventPayload;

/**
 * Methods that support marshaling/unmarshaling event model payloads.
 */
public class EventModelMarshaler {

    /**
     * Build binary message for API decoded event payload.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildDecodedEventPayloadMessage(IDecodedEventPayload api) throws SiteWhereException {
	GDecodedEventPayload grpc = EventModelConverter.asGrpcDecodedEventPayload(api);
	return buildDecodedEventPayloadMessage(grpc);
    }

    /**
     * Build binary message for GRPC decoded event payload.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildDecodedEventPayloadMessage(GDecodedEventPayload grpc) throws SiteWhereException {
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	try {
	    grpc.writeTo(output);
	    return output.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException("Unable to build decoded event payload message.", e);
	} finally {
	    CommonModelConverter.closeQuietly(output);
	}
    }

    /**
     * Parse message that contains a decoded event payload.
     * 
     * @param payload
     * @return
     * @throws SiteWhereException
     */
    public static GDecodedEventPayload parseDecodedEventPayloadMessage(byte[] payload) throws SiteWhereException {
	try {
	    return GDecodedEventPayload.parseFrom(payload);
	} catch (InvalidProtocolBufferException e) {
	    throw new SiteWhereException("Unable to parse decoded event payload message.", e);
	}
    }

    /**
     * Build binary message for API preprocessed event payload.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildPreprocessedEventPayloadMessage(IPreprocessedEventPayload api) throws SiteWhereException {
	GPreprocessedEventPayload grpc = EventModelConverter.asGrpcPreprocessedEventPayload(api);
	return buildPreprocessedEventPayloadMessage(grpc);
    }

    /**
     * Build binary message for GRPC preprocessed event payload.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildPreprocessedEventPayloadMessage(GPreprocessedEventPayload grpc)
	    throws SiteWhereException {
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	try {
	    grpc.writeTo(output);
	    return output.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException("Unable to build preprocessed event payload message.", e);
	} finally {
	    CommonModelConverter.closeQuietly(output);
	}
    }

    /**
     * Parse message that contains a preprocessed event payload.
     * 
     * @param payload
     * @return
     * @throws SiteWhereException
     */
    public static GPreprocessedEventPayload parsePreprocessedEventPayloadMessage(byte[] payload)
	    throws SiteWhereException {
	try {
	    return GPreprocessedEventPayload.parseFrom(payload);
	} catch (InvalidProtocolBufferException e) {
	    throw new SiteWhereException("Unable to parse preprocessed event payload message.", e);
	}
    }

    /**
     * Build binary message for GRPC processed event payload.
     * 
     * @param grpc
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildProcessedEventPayloadMessage(GProcessedEventPayload grpc) throws SiteWhereException {
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	try {
	    grpc.writeTo(output);
	    return output.toByteArray();
	} catch (IOException e) {
	    throw new SiteWhereException("Unable to build enriched event payload message.", e);
	} finally {
	    CommonModelConverter.closeQuietly(output);
	}
    }

    /**
     * Build binary message for API processed event payload.
     * 
     * @param api
     * @return
     * @throws SiteWhereException
     */
    public static byte[] buildProcessedEventPayloadMessage(IProcessedEventPayload api) throws SiteWhereException {
	GProcessedEventPayload grpc = EventModelConverter.asGrpcProcessedEventPayload(api);
	return buildProcessedEventPayloadMessage(grpc);
    }

    /**
     * Parse message that contains a processed event payload.
     * 
     * @param payload
     * @return
     * @throws SiteWhereException
     */
    public static GProcessedEventPayload parseProcessedEventPayloadMessage(byte[] payload) throws SiteWhereException {
	try {
	    return GProcessedEventPayload.parseFrom(payload);
	} catch (InvalidProtocolBufferException e) {
	    throw new SiteWhereException("Unable to parse processed event payload message.", e);
	}
    }
}