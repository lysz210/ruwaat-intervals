package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.nats.client.Connection
import io.nats.client.ObjectStore
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSource
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSourceInfo
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.FitFilesStore
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanBlueprint
import it.lysz210.akasha.capacnan.geo.ActivityFitsUploadedChannel
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.core.UriBuilder
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream

@ApplicationScoped
class FitFilesChasqui (
    private val connection: Connection,
    private val fitUploadedEmitter: ActivityFitsUploadedChannel,
    private val fitsQuipucamayoc: FitsQuipucamayoc,
    blueprint: CapacnanBlueprint,
) : FitFilesStore {
    private val storeName = blueprint.geo().objectStore().bucket()

    protected fun objectStore(): Uni<ObjectStore> =
        connection.objectStore(this.storeName).let {
            Uni.createFrom().item(it)
        }

    override fun put(fitSource: FitSource): Uni<FitSourceInfo> {
        val uri = UriBuilder.newInstance()
            .scheme("nats-object")
            .host(storeName)
            .path(INTERVALS_PROVIDER_NAME).path("${fitSource.activityId.value}.fit.gz")
            .build()
        return Uni.createFrom().item {
            val outputStream = ByteArrayOutputStream()
            GZIPOutputStream(outputStream).use { gzipOutputStream ->
                gzipOutputStream.write(fitSource.data)
            }
            outputStream.toByteArray()
        }
            .onItem().transformToUni { data ->
                objectStore().onItem().transform { store ->
                    store.put(uri.path, data)
                }
            }
            .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
            .invoke { response -> Log.info("Putting ${response.objectName} $response") }
            .map {
                FitSourceInfo(
                    activitiId = fitSource.activityId,
                    uri = uri
                )
            }
    }

    override fun notify(fitSourceInfo: FitSourceInfo): Uni<Long> =
        fitUploadedEmitter.send(
            fitsQuipucamayoc.tie(fitSourceInfo)
        )
            .invoke { seq -> Log.info("Event [$seq] => $fitSourceInfo") }
}