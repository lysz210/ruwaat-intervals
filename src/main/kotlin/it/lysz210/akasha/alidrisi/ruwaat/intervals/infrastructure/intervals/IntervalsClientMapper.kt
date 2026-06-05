package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals

import icu.intervals.api.v1.dto.ActivitiesResponse
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Athlete
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class IntervalsClientMapper {

    fun toDoamin(response: ActivitiesResponse) =
        Activity(
            id = response.id,
            externalId = response.externalId,
            athlete = Athlete(response.icuAthleteId)
        )
}