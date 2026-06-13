package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.quarkiverse.reactive.messaging.nats.jetstream.client.store.KeyValueStoreAware
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.exception.ActivityNotFoundException
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivityRepository
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanBlueprint
import jakarta.enterprise.context.ApplicationScoped
import it.lysz210.akasha.capacnan.quipus.maps.Activity as ActivityQuipu

@ApplicationScoped
class ActivitiesChasqui(
    private val kvStore: KeyValueStoreAware,
    private val activityQuipucamayoc: ActivitiesQuipucamayoc,
    blueprint: CapacnanBlueprint,
) : ActivityRepository {
    private val geoBucketName = blueprint.geo().kv().bucket()

    private fun getKeyFor(activityId: Activity.ActivityId) =
        "${INTERVALS_PROVIDER_NAME}.${activityId.value}"

    override fun save(activity: Activity): Uni<Long> {
        val activityQuipu = activityQuipucamayoc.tie(activity)
        return kvStore.putValue(this.geoBucketName, getKeyFor(activity.id), activityQuipu)
    }

    override fun findById(id: Activity.ActivityId): Uni<Activity> =
        kvStore.getValue(this.geoBucketName, getKeyFor(id), ActivityQuipu::class.java)
            .onItem().ifNotNull().transform { activityQuipucamayoc.untie(it) }
            .onItem().ifNull().failWith { ActivityNotFoundException(id) }
            .onFailure().transform { failure -> ActivityNotFoundException(id, failure) }

    override fun exists(id: Activity.ActivityId): Boolean {
        TODO("Not yet implemented")
    }
}