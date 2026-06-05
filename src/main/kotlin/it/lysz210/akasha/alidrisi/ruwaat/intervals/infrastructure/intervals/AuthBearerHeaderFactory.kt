package it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals

import io.quarkus.rest.client.reactive.ReactiveClientHeadersFactory
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan.ClavigerChasqui
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.core.MultivaluedHashMap
import jakarta.ws.rs.core.MultivaluedMap

@ApplicationScoped
class AuthBearerHeaderFactory(
    private val clavigerChasqui: ClavigerChasqui,
) : ReactiveClientHeadersFactory() {
    override fun getHeaders(
        incomingHeaders: MultivaluedMap<String?, String?>?,
        clientOutgoingHeaders: MultivaluedMap<String?, String?>?
    ): Uni<MultivaluedMap<String?, String?>?>? {
        return clavigerChasqui.intervalsAccessToken
            .map { token ->
                val headers = MultivaluedHashMap<String, String>()
                headers.add("Authorization", "Bearer $token")
                headers.add("Accept", "*/*")
                headers
            }
    }

}