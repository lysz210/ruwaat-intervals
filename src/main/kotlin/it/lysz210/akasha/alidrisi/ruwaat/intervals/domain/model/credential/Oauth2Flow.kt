package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.credential

import java.time.Instant

@JvmRecord
data class Oauth2Flow(
    val accessToken: String,
    val refreshToken: String? = null,
    val expiresAt: Instant? = null,
    val tokenType: String,
    val scope: String,
) {
    val expired: Boolean get() = expiresAt?.isBefore(Instant.now()) ?: true
}