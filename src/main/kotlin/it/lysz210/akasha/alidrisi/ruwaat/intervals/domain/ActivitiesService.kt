package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain

import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivitiesProvider
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivityRepository
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.FitFilesStore
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ActivitiesService(
    private val activitiesProvider: ActivitiesProvider,
    private val activitiesResponsitory: ActivityRepository,
    private val fitFilesStore: FitFilesStore,
) {
}