package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain

import io.quarkus.logging.Log
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
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

    fun list(): Multi<Activity> = activitiesProvider.listActivities()
        .call { activity ->
            activitiesResponsitory.save(activity)
                .invoke { recordId ->
                    Log.info("Activity ${activity.id.value} with tracking activityId $recordId")
                }
        }

    fun activity(activityId: Activity.ActivityId): Uni<Activity> =
        activitiesResponsitory.findById(activityId)
            .call { _ ->
                activitiesProvider.getOriginalSource(activityId)
                    .call { source -> fitFilesStore.put(source) }
            }
}