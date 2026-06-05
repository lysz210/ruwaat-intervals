package icu.intervals.api.v1.dto

import jakarta.ws.rs.QueryParam
import java.time.LocalDate

@JvmRecord
data class ActivitiesRequest(
    @field:QueryParam("oldest")
    val startRange: LocalDate,
    @field:QueryParam("newest")
    val endRange: LocalDate? = null,
    @field:QueryParam("fields")
    val fields: String? = null,
) {
    constructor(
        startRange: LocalDate,
        fields: Set<String> = emptySet()
    ) : this(
        startRange = startRange,
        fields = fields.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = ",")
    )
}