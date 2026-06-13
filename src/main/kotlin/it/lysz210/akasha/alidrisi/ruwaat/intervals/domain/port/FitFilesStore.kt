package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port

import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSource
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.FitSourceInfo

interface FitFilesStore {
    fun put(fitSource: FitSource): Uni<FitSourceInfo>
}