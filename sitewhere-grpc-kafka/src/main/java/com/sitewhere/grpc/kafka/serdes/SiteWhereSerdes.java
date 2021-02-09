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
package com.sitewhere.grpc.kafka.serdes;

import org.apache.kafka.common.serialization.Serde;

import com.sitewhere.grpc.model.DeviceEventModel.GDecodedEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GPreprocessedEventPayload;
import com.sitewhere.grpc.model.DeviceEventModel.GProcessedEventPayload;
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

    public static Serde<GProcessedEventPayload> forProcessedEventPayload() {
	return new ProcessedEventPayloadSerde();
    }

    public static Serde<GDeviceRegistationPayload> forDeviceRegistrationPayload() {
	return new DeviceRegistrationPayloadSerde();
    }
}
