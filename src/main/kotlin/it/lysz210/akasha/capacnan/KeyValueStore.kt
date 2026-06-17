package it.lysz210.akasha.capacnan

import com.google.protobuf.MessageLite
import com.google.protobuf.Parser
import io.nats.client.KeyValue
import io.smallrye.mutiny.Uni

interface KeyValueStore<T : MessageLite> {
    val keyValueBucket: Uni<KeyValue>
    val payloadParser: Parser<T>
    fun  put(key: String, value: T): Uni<Long> =
        keyValueBucket.map { it.put(key, value.toByteArray()) }
    fun get(key: String): Uni<T> =
        keyValueBucket.map { it.get(key) }
            .map { payloadParser.parseFrom(it.value) }
}