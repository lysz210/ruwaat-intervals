package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals

import com.fasterxml.jackson.databind.ObjectMapper
import icu.intervals.api.v1.IntervalsRestClient
import icu.intervals.api.v1.dto.ActivitiesRequest
import icu.intervals.api.v1.dto.ActivitiesResponse
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSource
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivitiesProvider
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan.ClavigerChasqui
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.time.LocalDate

@ApplicationScoped
class IntervalsClientWrapper(
    private val clavigerChasqui: ClavigerChasqui,
    @param:RestClient private val intervalsRestClient: IntervalsRestClient,
    private val intervalsClientMapper: IntervalsClientMapper,
    objectMapper: ObjectMapper,
) : ActivitiesProvider {
    private val fields: Set<String>
    init {
        val config = objectMapper.serializationConfig

        val beanDesc = config.introspect(
            objectMapper.constructType(ActivitiesResponse::class.java)
        )
        fields = beanDesc.findProperties().map {
            prop -> prop.name
        }.toSet()
    }
    override fun listActivities(): Multi<Activity> =
        this.clavigerChasqui.intervalsAthlete.map { it.id }
            .onItem().transformToUni { athleteId ->
                intervalsRestClient.listActivities(
                    athleteId.value,
                    ActivitiesRequest(
                        LocalDate.of(2026, 1, 1),
                        this.fields
                    )
                )
            }
            .onItem().transformToMulti { Multi.createFrom().iterable(it) }
            .map { intervalsClientMapper.toDoamin(it) }

    override fun getOriginalSource(activityId: Activity.ActivityId): Uni<FitSource> =
        this.intervalsRestClient.downloadActivityOriginalSource(activityId.value)
            .emitOn(Infrastructure.getDefaultWorkerPool() )
            .map { data -> FitSource(
                activityId = activityId,
                data = data.readAllBytes()
            ) }
}