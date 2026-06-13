package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config

import io.smallrye.config.ConfigMapping
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.INTERVALS_PROVIDER_NAME
import java.net.URI

@ConfigMapping(prefix = INTERVALS_PROVIDER_NAME)
interface IntervalsProperties {
    fun baseUrl(): URI
    fun clientId(): String
}