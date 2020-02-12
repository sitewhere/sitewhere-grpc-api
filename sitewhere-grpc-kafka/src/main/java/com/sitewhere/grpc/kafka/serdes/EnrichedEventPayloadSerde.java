/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.grpc.kafka.serdes;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes.WrapperSerde;
import org.apache.kafka.common.serialization.Serializer;

import com.sitewhere.grpc.event.EventModelMarshaler;
import com.sitewhere.grpc.model.DeviceEventModel.GEnrichedEventPayload;
import com.sitewhere.spi.SiteWhereException;

/**
 * Kafka {@link Serde} implementation for gRPC enriched event payload message.
 */
public class EnrichedEventPayloadSerde extends WrapperSerde<GEnrichedEventPayload> {

    public EnrichedEventPayloadSerde() {
	super(new EnrichedEventPayloadSerializer(), new EnrichedEventPayloadDeserializer());
    }

    /**
     * Serializer for gRPC enriched event payload message.
     */
    public static class EnrichedEventPayloadSerializer implements Serializer<GEnrichedEventPayload> {

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
	public byte[] serialize(String topic, GEnrichedEventPayload data) {
	    try {
		return EventModelMarshaler.buildEnrichedEventPayloadMessage(data);
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
     * Deserializer for gRPC enriched event payload message.
     */
    public static class EnrichedEventPayloadDeserializer implements Deserializer<GEnrichedEventPayload> {

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
	public GEnrichedEventPayload deserialize(String topic, byte[] data) {
	    try {
		return EventModelMarshaler.parseEnrichedEventPayloadMessage(data);
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
