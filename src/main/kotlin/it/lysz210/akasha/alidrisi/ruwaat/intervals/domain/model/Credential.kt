package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

@JvmRecord
data class Credential(
    val clientId: ClientId,
    val authentication: Authorization,
    val athlete: Athlete? = null
) {
    @JvmInline
    value class ClientId(val value: String)
}