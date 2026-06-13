package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port

import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSource

interface FitFilesStore {
    fun put(fitSource: FitSource): Uni<Void>
}