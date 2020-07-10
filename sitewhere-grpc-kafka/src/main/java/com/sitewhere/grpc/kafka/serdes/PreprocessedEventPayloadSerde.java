package com.sitewhere.grpc.kafka.serdes;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes.WrapperSerde;
import org.apache.kafka.common.serialization.Serializer;

import com.sitewhere.grpc.event.EventModelMarshaler;
import com.sitewhere.grpc.model.DeviceEventModel.GPreprocessedEventPayload;
import com.sitewhere.spi.SiteWhereException;

/**
 * Kafka {@link Serde} implementation for gRPC preprocessed event payload
 * message.
 */
public class PreprocessedEventPayloadSerde extends WrapperSerde<GPreprocessedEventPayload> {

    public PreprocessedEventPayloadSerde() {
	super(new PreprocessedEventPayloadSerializer(), new PreprocessedEventPayloadDeserializer());
    }

    /**
     * Serializer for gRPC preprocessed event payload message.
     */
    public static class PreprocessedEventPayloadSerializer implements Serializer<GPreprocessedEventPayload> {

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
	public byte[] serialize(String topic, GPreprocessedEventPayload data) {
	    try {
		return EventModelMarshaler.buildPreprocessedEventPayloadMessage(data);
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
     * Deserializer for gRPC preprocessed event payload message.
     */
    public static class PreprocessedEventPayloadDeserializer implements Deserializer<GPreprocessedEventPayload> {

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
	public GPreprocessedEventPayload deserialize(String topic, byte[] data) {
	    try {
		return EventModelMarshaler.parsePreprocessedEventPayloadMessage(data);
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
