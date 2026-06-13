package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import com.google.protobuf.timestamp
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Athlete
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Key
import it.lysz210.akasha.capacnan.quipus.maps.activityId
import it.lysz210.akasha.capacnan.quipus.maps.activity as activityQuipu
import jakarta.enterprise.context.ApplicationScoped
import java.time.Instant
import it.lysz210.akasha.capacnan.quipus.maps.Activity as ActivityQuipu

@ApplicationScoped
class ActivitiesQuipucamayoc {
    fun tie(activity: Activity): ActivityQuipu =
        activityQuipu {
            id = activityId {
                provider = activity.id.provider
                id = activity.id.id
            }
            name = activity.name.toString()
            athleteId = activity.athlete?.id.toString()
            externalId = activity.externalId.toString()
            distance = activity.distance ?: 0.0
            movingTime = activity.movingTime ?: 0
            elapsedTime = activity.elapsedTime ?: 0
            sportType = activity.type.toString()
            if (activity.startDate != null) {
                startDate = timestamp {
                    seconds = activity.startDate.epochSecond
                    nanos = activity.startDate.nano
                }
            }
            averageSpeed = activity.averageSpeed ?: 0.0
            maxSpeed = activity.maxSpeed ?: 0.0
        }

    fun untieAthlete(quipu: ActivityQuipu): Athlete? =
        quipu.athleteId.takeIf { quipu.hasAthleteId() }
            ?.let { Athlete(id = it) }

    fun untie(quipu: ActivityQuipu): Activity =
        Activity (
            id = quipu.id.let { Key(it.provider, it.id)},
            athlete = untieAthlete(quipu),
            name = quipu.name.takeIf { quipu.hasName() },
            externalId = quipu.externalId.takeIf { quipu.hasExternalId() },
            distance = quipu.distance,
            movingTime = quipu.movingTime,
            elapsedTime = quipu.elapsedTime,
            type = quipu.sportType.takeIf { quipu.hasSportType() },
            startDate = quipu.startDate.takeIf { quipu.hasStartDate() }
                ?.let{ Instant.ofEpochSecond(it.seconds, it.nanos.toLong()) },
            averageSpeed = quipu.averageSpeed,
            maxSpeed = quipu.maxSpeed,
        )
}