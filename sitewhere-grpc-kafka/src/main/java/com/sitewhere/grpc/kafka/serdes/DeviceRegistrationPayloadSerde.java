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

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes.WrapperSerde;
import org.apache.kafka.common.serialization.Serializer;

import com.sitewhere.grpc.device.DeviceModelMarshaler;
import com.sitewhere.grpc.model.DeviceModel.GDeviceRegistationPayload;
import com.sitewhere.spi.SiteWhereException;

/**
 * Kafka {@link Serde} implementation for gRPC device registration payload
 * message.
 */
public class DeviceRegistrationPayloadSerde extends WrapperSerde<GDeviceRegistationPayload> {

    public DeviceRegistrationPayloadSerde() {
	super(new DeviceRegistrationSerializer(), new DeviceRegistrationDeserializer());
    }

    /**
     * Serializer for gRPC device registration payload message.
     */
    public static class DeviceRegistrationSerializer implements Serializer<GDeviceRegistationPayload> {

	/*
	 * @see
	 * org.apache.kafka.common.serialization.Serializer#configure(java.util.Map,
	 * boolean)
	 */
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	/*
	 * @see
	 * org.apache.kafka.common.serialization.Serializer#serialize(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public byte[] serialize(String topic, GDeviceRegistationPayload data) {
	    try {
		return DeviceModelMarshaler.buildDeviceRegistrationPayloadMessage(data);
	    } catch (SiteWhereException e) {
		throw new RuntimeException("Unable to deserialize payload.", e);
	    }
	}

	/*
	 * @see org.apache.kafka.common.serialization.Serializer#close()
	 */
	@Override
	public void close() {
	}
    }

    /**
     * Deserializer for gRPC device registration payload message.
     */
    public static class DeviceRegistrationDeserializer implements Deserializer<GDeviceRegistationPayload> {

	/*
	 * @see
	 * org.apache.kafka.common.serialization.Deserializer#configure(java.util.Map,
	 * boolean)
	 */
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	/*
	 * @see
	 * org.apache.kafka.common.serialization.Deserializer#deserialize(java.lang.
	 * String, byte[])
	 */
	@Override
	public GDeviceRegistationPayload deserialize(String topic, byte[] data) {
	    try {
		return DeviceModelMarshaler.parseDeviceRegistrationPayloadMessage(data);
	    } catch (SiteWhereException e) {
		throw new RuntimeException("Unable to deserialize payload.", e);
	    }
	}

	/*
	 * @see org.apache.kafka.common.serialization.Deserializer#close()
	 */
	@Override
	public void close() {
	}
    }
}
