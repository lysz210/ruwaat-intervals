package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config

import io.smallrye.config.ConfigMapping
import it.lysz210.akasha.capacnan.blueprint.Blueprint

@ConfigMapping(prefix = "capacnan")
interface CapacnanBlueprint : Blueprint {

    fun credentials(): Credentials

    interface Credentials :
            Blueprint.Resources,
            Blueprint.WithKeyValue

    fun geo(): Geo

    interface Geo :
            Blueprint.Resources,
            Blueprint.WithKeyValue,
            Blueprint.WithObjectStore,
            Blueprint.WithStream
}