package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.quarkiverse.reactive.messaging.nats.jetstream.client.store.KeyValueStoreAware
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Key
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivityRepository
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
    override fun save(activity: Activity): Uni<Long> {
        val activityQuipu = activityQuipucamayoc.tie(activity)
        return kvStore.putValue(this.geoBucketName, activity.id.qualifiedId, activityQuipu)
    }

    override fun findByKey(key: Key): Uni<Activity?> =
        kvStore.getValue(this.geoBucketName, key.qualifiedId, ActivityQuipu::class.java)
            .map { activityQuipucamayoc.untie(it) }
            .onFailure().recoverWithItem { _ -> null }

    override fun exists(key: Key): Boolean {
        TODO("Not yet implemented")
    }
}