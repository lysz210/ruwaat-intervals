package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.nats.client.Connection
import io.nats.client.ObjectStore
import io.quarkus.logging.Log
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.infrastructure.Infrastructure
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSource
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.FitFilesStore
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanBlueprint
import jakarta.enterprise.context.ApplicationScoped
import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream

@ApplicationScoped
class FitFilesChasqui (
    private val connection: Connection,
    blueprint: CapacnanBlueprint,
) : FitFilesStore {
    private val storeName = blueprint.geo().objectStore().bucket()

    protected fun objectStore(): Uni<ObjectStore> =
        connection.objectStore(this.storeName).let {
            Uni.createFrom().item(it)
        }

    override fun put(fitSource: FitSource): Uni<Void> =
        objectStore()
            .emitOn(Infrastructure.getDefaultWorkerPool() )
            .onItem().transform { store ->
                val outputStream = ByteArrayOutputStream()
                GZIPOutputStream(outputStream).use { gzipOutputStream ->
                    gzipOutputStream.write(fitSource.data)
                }

                store.put("${INTERVALS_PROVIDER_NAME}/${fitSource.activityId.value}.fit.gz", outputStream.toByteArray())
            }
            .invoke { response -> Log.info("Putting ${response.objectName} ${response}") }
            .replaceWithVoid()
}