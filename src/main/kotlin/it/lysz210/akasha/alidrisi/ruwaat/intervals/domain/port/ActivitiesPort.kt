package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port

import io.smallrye.mutiny.Multi
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity

interface ActivitiesPort {
    fun listActivities(): Multi<Activity>
//    fun getOriginalSource(activity: Activity)
}