package icu.intervals.api.v1

import icu.intervals.api.v1.dto.ActivitiesRequest
import icu.intervals.api.v1.dto.ActivitiesResponse
import io.smallrye.faulttolerance.api.RateLimit
import io.smallrye.faulttolerance.api.RateLimitException
import io.smallrye.faulttolerance.api.RateLimitType
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.intervals.AuthBearerHeaderFactory
import jakarta.ws.rs.BeanParam
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.faulttolerance.Retry
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import java.io.InputStream
import java.time.temporal.ChronoUnit

@RegisterRestClient(configKey = "intervals-v1")
@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
@RegisterClientHeaders(AuthBearerHeaderFactory::class)
@RateLimit(
    value = 15,
    window = 1,
    windowUnit = ChronoUnit.SECONDS,
    type = RateLimitType.SMOOTH,
)
@Retry(
    maxRetries = 10,                 // Give it plenty of attempts if processing a huge batch
    delay = 250,                     // Wait 250ms before trying again
    delayUnit = ChronoUnit.MILLIS,
    jitter = 50,                     // Add a little randomness so queued requests don't all retry at the identical millisecond
    retryOn = [RateLimitException::class] // ONLY retry if we hit our internal rate limit
)
interface IntervalsRestClient {

    @GET
    @Path("/athlete/{athleteId}/activities")
    fun listActivities(
        @PathParam("athleteId") athleteId: String,
        @BeanParam params: ActivitiesRequest)
    : Uni<List<ActivitiesResponse>>

    @GET
    @Path("/activity/{activityId}/file")
    fun downloadActivityOriginalSource(@PathParam("activityId") activityId: String): Uni<ByteArray>
}