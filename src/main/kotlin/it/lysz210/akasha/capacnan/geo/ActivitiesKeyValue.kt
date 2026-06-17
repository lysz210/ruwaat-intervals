package it.lysz210.akasha.capacnan.geo

import com.google.protobuf.Parser
import io.nats.client.KeyValue
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.capacnan.CapacnaGeo
import it.lysz210.akasha.capacnan.KeyValueStore
import it.lysz210.akasha.capacnan.quipus.maps.Activity
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class ActivitiesKeyValue(
    @CapacnaGeo geoKv: Uni<KeyValue>
) : KeyValueStore<Activity> {
    override val keyValueBucket = geoKv
    override val payloadParser: Parser<Activity> = Activity.parser()
}