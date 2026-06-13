package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSource

interface ActivitiesProvider {
    fun listActivities(): Multi<Activity>
    fun getOriginalSource(activityId: String): Uni<FitSource>
}