package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config

import io.smallrye.config.ConfigMapping

@ConfigMapping(prefix = "capacnan")
interface CapacnanProperties {
    fun buckets(): Buckets
    fun streams(): Streams
    interface Buckets {
        fun credentials(): String
        fun mapsActivities(): String
    }
    interface Streams {
        fun maps(): Stream
    }
    interface Stream {
        fun name(): String
    }
}