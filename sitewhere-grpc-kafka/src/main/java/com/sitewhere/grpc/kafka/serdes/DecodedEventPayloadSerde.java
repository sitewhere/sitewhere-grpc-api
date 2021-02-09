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

import com.sitewhere.grpc.event.EventModelMarshaler;
import com.sitewhere.grpc.model.DeviceEventModel.GDecodedEventPayload;
import com.sitewhere.spi.SiteWhereException;

/**
 * Kafka {@link Serde} implementation for gRPC decoded event payload message.
 */
public class DecodedEventPayloadSerde extends WrapperSerde<GDecodedEventPayload> {

    public DecodedEventPayloadSerde() {
	super(new DecodedEventPayloadSerializer(), new DecodedEventPayloadDeserializer());
    }

    /**
     * Serializer for gRPC decoded event payload message.
     */
    public static class DecodedEventPayloadSerializer implements Serializer<GDecodedEventPayload> {

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
	public byte[] serialize(String topic, GDecodedEventPayload data) {
	    try {
		return EventModelMarshaler.buildDecodedEventPayloadMessage(data);
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
     * Deserializer for gRPC decoded event payload message.
     */
    public static class DecodedEventPayloadDeserializer implements Deserializer<GDecodedEventPayload> {

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
	public GDecodedEventPayload deserialize(String topic, byte[] data) {
	    try {
		return EventModelMarshaler.parseDecodedEventPayloadMessage(data);
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
