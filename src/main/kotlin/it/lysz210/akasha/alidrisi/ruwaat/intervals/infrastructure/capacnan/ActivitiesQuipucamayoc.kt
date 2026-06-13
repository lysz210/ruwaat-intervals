package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import com.google.protobuf.timestamp
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Athlete
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import it.lysz210.akasha.capacnan.quipus.maps.activityId
import jakarta.enterprise.context.ApplicationScoped
import java.net.URI
import java.time.Instant
import it.lysz210.akasha.capacnan.quipus.maps.Activity as ActivityQuipu
import it.lysz210.akasha.capacnan.quipus.maps.activity as activityQuipu

@ApplicationScoped
class ActivitiesQuipucamayoc {
    fun tie(activity: Activity): ActivityQuipu =
        activityQuipu {
            id = activityId {
                provider = INTERVALS_PROVIDER_NAME
                id = activity.id.value
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
            if (activity.fitFileUri != null) {
                fitFileUri = activity.fitFileUri.toString()
            }
        }

    fun untieAthlete(quipu: ActivityQuipu): Athlete? =
        quipu.athleteId.takeIf { quipu.hasAthleteId() }
            ?.let { Athlete(id = Athlete.AthleteId(it)) }

    fun untie(quipu: ActivityQuipu): Activity =
        Activity (
            id = quipu.id.id.let { Activity.ActivityId(it) },
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
            fitFileUri = quipu.fitFileUri.takeIf { quipu.hasFitFileUri() }?.let{ URI(it) },
        )
}