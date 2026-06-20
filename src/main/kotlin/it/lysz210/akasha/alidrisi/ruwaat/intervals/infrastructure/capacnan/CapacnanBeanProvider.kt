package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.nats.client.Connection
import io.nats.client.JetStream
import io.nats.client.KeyValue
import io.nats.client.Nats
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.capacnan.CapacnanCredentials
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanBlueprint
import it.lysz210.akasha.capacnan.CapacnaGeo
import it.lysz210.akasha.capacnan.quipus.credentials.EncryptionStrategy
import it.lysz210.akasha.capacnan.quipus.credentials.NoopEncryptionStrategy
import it.lysz210.akasha.capacnan.quipus.credentials.Quipucamayoc
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class CapacnanBeanProvider {

    @Produces
    @ApplicationScoped
    fun natsConnection(@ConfigProperty(name = "nats.connection.url") natsUrl: String): Connection {
        return Nats.connect(natsUrl)
    }

    @Produces
    @ApplicationScoped
    fun encryptionStrategy(): EncryptionStrategy = NoopEncryptionStrategy()

    @Produces
    @ApplicationScoped
    fun camayoc(strategy: EncryptionStrategy): Quipucamayoc = Quipucamayoc(strategy)

    @Produces
    @ApplicationScoped
    @CapacnaGeo
    fun geoStream(nats: Connection): Uni<JetStream> =
        Uni.createFrom().item { nats.jetStream() }

    @Produces
    @ApplicationScoped
    @CapacnaGeo
    fun geoKeyValue(nats: Connection, blueprint: CapacnanBlueprint): Uni<KeyValue> =
        Uni.createFrom().item { nats.keyValue(blueprint.geo().kv().bucket()) }
    @Produces
    @ApplicationScoped
    @CapacnanCredentials
    fun credentialsKeyValue(nats: Connection, blueprint: CapacnanBlueprint): Uni<KeyValue> =
        Uni.createFrom().item { nats.keyValue(blueprint.credentials().kv().bucket()) }
}