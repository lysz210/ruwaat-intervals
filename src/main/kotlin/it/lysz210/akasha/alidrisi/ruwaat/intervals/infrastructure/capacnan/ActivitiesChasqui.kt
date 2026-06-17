package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivityRepository
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import it.lysz210.akasha.capacnan.geo.ActivitiesKeyValue
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ActivitiesChasqui(
    private val activitiesKv: ActivitiesKeyValue,
    private val activityQuipucamayoc: ActivitiesQuipucamayoc,
) : ActivityRepository {

    private fun getKeyFor(activityId: Activity.ActivityId) =
        "${INTERVALS_PROVIDER_NAME}.${activityId.value}"

    override fun save(activity: Activity): Uni<Long> {
        val activityQuipu = activityQuipucamayoc.tie(activity)
        return activitiesKv.put(getKeyFor(activity.id), activityQuipu)
    }

    override fun findById(id: Activity.ActivityId): Uni<Activity> =
        activitiesKv.get(getKeyFor(id))
            .map { activityQuipucamayoc.untie(it) }

    override fun exists(id: Activity.ActivityId): Boolean {
        TODO("Not yet implemented")
    }
}