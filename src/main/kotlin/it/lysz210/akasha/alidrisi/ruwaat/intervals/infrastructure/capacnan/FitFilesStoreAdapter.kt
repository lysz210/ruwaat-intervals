package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.nats.client.Connection
import io.nats.client.ObjectStore
import io.nats.client.api.ObjectInfo
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.FitFilesStore
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanBlueprint
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FitFilesStoreAdapter (
    private val connection: Connection,
    blueprint: CapacnanBlueprint,
) : FitFilesStore {
    private val storeName = blueprint.geo().objectStore().bucket()

    protected fun objectStore(): Uni<ObjectStore> =
        connection.objectStore(this.storeName).let {
            Uni.createFrom().item(it)
        }

    fun put(key: String, value: ByteArray): Uni<ObjectInfo> {
        return objectStore().onItem()
            .transform { store ->
                store.put(key, value)
            }
    }
}