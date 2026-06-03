package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.credential

@JvmRecord
data class Key(
    val group: String,
    val id: String,
){
    /**
     * Returns the dot-separated representation (group.id) used for storage and indexing.
     */
    val qualifiedId: String
        get() = "$group.$id"
}