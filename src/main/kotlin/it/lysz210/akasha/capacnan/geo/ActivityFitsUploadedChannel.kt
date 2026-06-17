package it.lysz210.akasha.capacnan.geo

import io.nats.client.JetStream
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.config.CapacnanBlueprint
import it.lysz210.akasha.capacnan.CapacnaGeo
import it.lysz210.akasha.capacnan.OutgoingChannel
import it.lysz210.akasha.capacnan.quipus.maps.ActivityFitSource
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ActivityFitsUploadedChannel(
    @CapacnaGeo stream_: Uni<JetStream>,
    capacnanBlueprint: CapacnanBlueprint
) : OutgoingChannel<ActivityFitSource> {
    override val stream: Uni<JetStream> = stream_
    override val subject: OutgoingChannel.Subject =
        OutgoingChannel.Subject("${capacnanBlueprint.geo().keyPrefix()}.activities.fits.uploaded")
}