package icu.intervals.api.v1.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JvmRecord
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ActivitiesResponse(
    val id: String,
    val externalId: String,
    val icuAthleteId: String,
)
