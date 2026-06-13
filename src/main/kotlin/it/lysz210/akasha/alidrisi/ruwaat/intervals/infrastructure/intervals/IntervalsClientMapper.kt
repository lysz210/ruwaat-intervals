package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals

import icu.intervals.api.v1.dto.ActivitiesResponse
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Athlete
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Key
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class IntervalsClientMapper {

    fun toDoamin(response: ActivitiesResponse) =
        Activity(
            id = Key(INTERVALS_PROVIDER_NAME, response.id),
            externalId = response.externalId,
            athlete = Athlete(response.icuAthleteId),
            name = response.name,
            distance = response.distance,
            movingTime = response.movingTime,
            elapsedTime = response.elapsedTime,
            type = response.type,
            startDate = response.startDate,
            averageSpeed = response.averageSpeed,
            maxSpeed = response.maxSpeed,
        )
}