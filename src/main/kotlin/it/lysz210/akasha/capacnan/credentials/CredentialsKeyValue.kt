package it.lysz210.akasha.capacnan.credentials

import com.google.protobuf.Parser
import io.nats.client.KeyValue
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.capacnan.CapacnaCredentials
import it.lysz210.akasha.capacnan.KeyValueStore
import it.lysz210.akasha.capacnan.quipus.credentials.CredentialQuipu
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class CredentialsKeyValue(
    @CapacnaCredentials geoKv: Uni<KeyValue>
) : KeyValueStore<CredentialQuipu> {
    override val keyValueBucket = geoKv
    override val payloadParser: Parser<CredentialQuipu> = CredentialQuipu.parser()
}