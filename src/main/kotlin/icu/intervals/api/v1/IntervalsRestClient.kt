package icu.intervals.api.v1

import icu.intervals.api.v1.dto.ActivitiesRequest
import icu.intervals.api.v1.dto.ActivitiesResponse
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals.AuthBearerHeaderFactory
import jakarta.ws.rs.BeanParam
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import java.io.InputStream

@RegisterRestClient(configKey = "intervals-v1")
@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@RegisterClientHeaders(AuthBearerHeaderFactory::class)
interface IntervalsRestClient {

    @GET
    @Path("/athlete/{athleteId}/activities")
    fun listActivities(
        @PathParam("athleteId") athleteId: String,
        @BeanParam params: ActivitiesRequest)
    : Uni<List<ActivitiesResponse>>

    @GET
    @Path("/activity/{activityId}/file")
    fun downloadActivityOriginalSource(@PathParam("activityId") activityId: String): Uni<InputStream>
}