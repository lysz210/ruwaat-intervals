package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model

@JvmRecord
data class Key(
    val provider: String,
    val id: String,
){
    /**
     * Returns the dot-separated representation (provider.id) used for storage and indexing.
     */
    val qualifiedId: String
        get() = "$provider.$id"
}