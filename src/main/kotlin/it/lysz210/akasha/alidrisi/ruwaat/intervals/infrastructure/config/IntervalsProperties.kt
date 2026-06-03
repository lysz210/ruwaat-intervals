package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config

import io.smallrye.config.ConfigMapping
import java.net.URI

@ConfigMapping(prefix = "intervals")
interface IntervalsProperties {
    fun baseUrl(): URI
    fun clientId(): String
}