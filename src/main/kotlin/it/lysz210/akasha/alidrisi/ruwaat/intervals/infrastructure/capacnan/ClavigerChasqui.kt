package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.quarkiverse.reactive.messaging.nats.jetstream.client.store.KeyValueStoreAware
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.exception.CredentialNotFoundException
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.credential.Credential
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.credential.Key
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanProperties
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.IntervalsProperties
import it.lysz210.akasha.capacnan.quipus.credentials.CredentialQuipu
import jakarta.enterprise.context.ApplicationScoped


@ApplicationScoped
class ClavigerChasqui(
    private val kvStore: KeyValueStoreAware,
    private val clavigerQuipucamayoc: ClavigerQuipucamayoc,
    capacnanProperties: CapacnanProperties,
    properties: IntervalsProperties,
) {
    private val intervalsKey = Key("intervals", properties.clientId())
    private val intervalsBucket = capacnanProperties.buckets().credentials()

    val intervals: Uni<Credential>
        get() = kvStore.getValue(intervalsBucket, intervalsKey.qualifiedId, CredentialQuipu::class.java)
            .onItem().transform {clavigerQuipucamayoc.untie(it) }
            .onFailure().transform { failure -> CredentialNotFoundException(this.intervalsKey, failure) }

    val intervalsAccessToken: Uni<String>
        get() = this.intervals.onItem().transformToUni {
            val oauth2 = it.authentication.oauth2Flow
            if (oauth2 == null || oauth2.expired) {
                Uni.createFrom().failure(CredentialNotFoundException(this.intervalsKey))
            } else {
                Uni.createFrom().item(oauth2.accessToken)
            }
        }
}