package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Athlete
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Authorization
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Credential
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Key
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Oauth2Flow
import it.lysz210.akasha.capacnan.quipus.credentials.CredentialQuipu
import it.lysz210.akasha.capacnan.quipus.credentials.Quipucamayoc
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant

@ApplicationScoped
class ClavigerQuipucamayoc(
    private val camayoc: Quipucamayoc,
) {

    fun untie(quipu: CredentialQuipu): Credential {
        val data = camayoc.untie(quipu)
        if (data.hasOauth2Flow()) {
            val oauth2Flow = data.oauth2Flow
            return Credential(
                key = Key(
                    group = quipu.userId,
                    id = data.serviceId,
                ),
                authentication = Authorization(
                    oauth2Flow = Oauth2Flow(
                        tokenType = oauth2Flow.tokenType,
                        accessToken = oauth2Flow.accessToken,
                        refreshToken = oauth2Flow.refreshToken,
                        expiresAt = oauth2Flow.takeIf { it.hasExpiresAt() }
                            ?.expiresAt?.let {
                                Instant.ofEpochSecond(it.seconds, it.nanos.toLong())
                            },
                        scope = oauth2Flow.scope,
                    )
                ),
                athlete = oauth2Flow.takeIf { it.hasIntervalsAthlete() }?.intervalsAthlete?.let {
                    Athlete(it.id, it.name)
                }
            )
        }
        throw IllegalArgumentException("unsupported.")
    }
}