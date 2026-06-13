package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port

import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity

interface ActivityRepository {
    fun save(activity: Activity): Uni<Long>
    fun findById(id: Activity.ActivityId): Uni<Activity>
    fun exists(id: Activity.ActivityId): Boolean
}