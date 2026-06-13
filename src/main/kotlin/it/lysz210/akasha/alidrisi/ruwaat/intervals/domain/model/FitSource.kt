package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

@JvmRecord
data class FitSource(
    val activityId: Activity.ActivityId,
    val data: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FitSource

        if (activityId != other.activityId) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = activityId.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}