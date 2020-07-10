/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.kafka.serdes;

import org.apache.kafka.common.serialization.Serde;

import com.sitewhere.grpc.model.DeviceEventModel.GDecodedEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GEnrichedEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GPreprocessedEventPayload;
import com.sitewhere.grpc.model.DeviceModel.GDeviceRegistationPayload;

/**
 * Kafka {@link Serde} implementations for standard payloads.
 */
public class SiteWhereSerdes {

    public static Serde<GDecodedEventPayload> forDecodedEventPayload() {
	return new DecodedEventPayloadSerde();
    }

    public static Serde<GPreprocessedEventPayload> forPreprocessedEventPayload() {
	return new PreprocessedEventPayloadSerde();
    }

    public static Serde<GEnrichedEventPayload> forEnrichedEventPayload() {
	return new EnrichedEventPayloadSerde();
    }

    public static Serde<GDeviceRegistationPayload> forDeviceRegistrationPayload() {
	return new DeviceRegistrationPayloadSerde();
    }
}
