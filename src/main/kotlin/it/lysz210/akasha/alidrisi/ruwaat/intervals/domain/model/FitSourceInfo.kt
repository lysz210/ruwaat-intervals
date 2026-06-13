package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

import java.net.URI

@JvmRecord
data class FitSourceInfo(
    val activitiId: Activity.ActivityId,
    val uri: URI
)