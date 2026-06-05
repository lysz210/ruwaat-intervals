package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

@JvmRecord
data class Credential(
    val key: Key,
    val authentication: Authorization,
    val athlete: Athlete? = null
)