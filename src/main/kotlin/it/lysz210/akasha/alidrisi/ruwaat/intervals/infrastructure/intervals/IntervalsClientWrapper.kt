package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals

import com.fasterxml.jackson.databind.ObjectMapper
import icu.intervals.api.v1.IntervalsRestClient
import icu.intervals.api.v1.dto.ActivitiesRequest
import icu.intervals.api.v1.dto.ActivitiesResponse
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.infrastructure.Infrastructure
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivitiesPort
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan.ClavigerChasqui
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.rest.client.inject.RestClient
import java.io.FileOutputStream
import java.nio.file.Files
import java.time.LocalDate
import java.util.zip.GZIPOutputStream

@ApplicationScoped
class IntervalsClientWrapper(
    private val clavigerChasqui: ClavigerChasqui,
    @param:RestClient private val intervalsRestClient: IntervalsRestClient,
    private val intervalsClientMapper: IntervalsClientMapper,
    objectMapper: ObjectMapper,
) : ActivitiesPort {
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
                    athleteId,
                    ActivitiesRequest(
                        LocalDate.of(2026, 1, 1),
                        this.fields
                    )
                )
            }
            .onItem().transformToMulti { Multi.createFrom().iterable(it) }
            .map { intervalsClientMapper.toDoamin(it) }
    fun getActivityOriginalSource(activityId: String) =
        this.intervalsRestClient.downloadActivityOriginalSource(activityId)
            .emitOn(Infrastructure.getDefaultWorkerPool() )
            .map { inputStream ->
                val zipFile = Files.createTempFile("activity_$activityId", ".zip")
                inputStream.use { input ->
                    GZIPOutputStream(FileOutputStream(zipFile.toFile())).use { output ->
                        input.copyTo(output, bufferSize = 4096)
                    }
                }
                zipFile
            }
}