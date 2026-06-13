package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

@JvmRecord
data class Athlete(
    val id: AthleteId,
    val name: String? = null,
) {
    @JvmInline
    value class AthleteId(val value: String)
}
