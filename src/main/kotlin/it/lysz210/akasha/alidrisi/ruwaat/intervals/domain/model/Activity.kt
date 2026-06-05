package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

@JvmRecord
data class Activity(
    val id: String,
    val externalId: String,
    val athlete: Athlete? = null,
)
