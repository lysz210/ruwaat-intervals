package icu.intervals.api.v1.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.Instant

@JvmRecord
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ActivitiesResponse(
    val id: String,
    val icuAthleteId: String,
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
