package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port

import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Key

interface ActivityRepository {
    fun save(activity: Activity): Uni<Long>
    fun findByKey(key: Key): Uni<Activity?>
    fun exists(key: Key): Boolean
}