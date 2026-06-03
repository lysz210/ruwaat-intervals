package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.credential

@JvmRecord
data class Credential(
    val key: Key,
    val authentication: Authorization,
)