package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan

import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSourceInfo
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import it.lysz210.akasha.capacnan.quipus.maps.ActivityFitSource
import it.lysz210.akasha.capacnan.quipus.maps.activityFitSource
import it.lysz210.akasha.capacnan.quipus.maps.activityId
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class FitsQuipucamayoc {
    fun tie(info: FitSourceInfo): ActivityFitSource =
        activityFitSource {
            activityId = activityId {
                provider = INTERVALS_PROVIDER_NAME
                id = info.activitiId.value
            }
            uri = info.uri.toString()
        }
}