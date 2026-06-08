package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

import java.time.Instant

@JvmRecord
data class Activity(
    val id: String,
    val athlete: Athlete? = null,
    val name: String? = null,
    val externalId: String? = null,
    val distance: Double? = null,
    val movingTime: Long? = null,
    val elapsedTime: Long? = null,
    val type: String? = null,
    val startDate: Instant? = null,
    val averageSpeed: Double? = null,
    val maxSpeed: Double? = null,
)
