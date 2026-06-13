package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.quarkiverse.reactive.messaging.nats.jetstream.client.store.KeyValueStoreAware
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.exception.CredentialNotFoundException
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Athlete
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Credential
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanBlueprint
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.IntervalsProperties
import it.lysz210.akasha.capacnan.quipus.credentials.CredentialQuipu
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ClavigerChasqui(
    private val kvStore: KeyValueStoreAware,
    private val clavigerQuipucamayoc: ClavigerQuipucamayoc,
    capacnanBlueprint: CapacnanBlueprint,
    properties: IntervalsProperties,
) {
    private val clientId: Credential.ClientId = Credential.ClientId(properties.clientId())
    private val intervalsBucket = capacnanBlueprint.credentials().kv().bucket()

    val intervals: Uni<Credential>
        get() = kvStore.getValue(intervalsBucket, "${INTERVALS_PROVIDER_NAME}.${clientId.value}", CredentialQuipu::class.java)
            .onItem().transform {clavigerQuipucamayoc.untie(it) }
            .onFailure().transform { failure -> CredentialNotFoundException(this.clientId, failure) }

    val intervalsAccessToken: Uni<String>
        get() = this.intervals.onItem().transformToUni {
            val oauth2 = it.authentication.oauth2Flow
            if (oauth2 == null || oauth2.expired) {
                Uni.createFrom().failure(CredentialNotFoundException(this.clientId))
            } else {
                Uni.createFrom().item(oauth2.accessToken)
            }
        }

    val intervalsAthlete: Uni<Athlete>
        get() = this.intervals.map { it.athlete }
}