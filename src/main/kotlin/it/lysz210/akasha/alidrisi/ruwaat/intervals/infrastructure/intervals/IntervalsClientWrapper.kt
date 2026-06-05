package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals

import icu.intervals.api.v1.IntervalsRestClient
import icu.intervals.api.v1.dto.ActivitiesRequest
import io.smallrye.common.annotation.Blocking
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
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@ApplicationScoped
class IntervalsClientWrapper(
    private val clavigerChasqui: ClavigerChasqui,
    @param:RestClient private val intervalsRestClient: IntervalsRestClient,
    private val intervalsClientMapper: IntervalsClientMapper,
) : ActivitiesPort {
    override fun listActivities(): Multi<Activity> =
        this.clavigerChasqui.intervalsAthlete.map { it.id }
            .onItem().transformToUni { athleteId ->
                intervalsRestClient.listActivities(
                    athleteId,
                    ActivitiesRequest(
                        LocalDate.of(2026, 1, 1),
                        setOf("id", "external_id", "icu_athlete_id")
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
                    ZipOutputStream(FileOutputStream(zipFile.toFile())).use { output ->
                        val zipEntry = ZipEntry("activity.fit")
                        output.putNextEntry(zipEntry)

                        input.copyTo(output, bufferSize = 4096)
                        output.closeEntry()
                    }
                }
                zipFile
            }
}