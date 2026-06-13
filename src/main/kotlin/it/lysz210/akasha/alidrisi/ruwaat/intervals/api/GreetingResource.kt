package it.lysz210.akasha.alidrisi.ruwaat.intervals.api

import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.port.ActivitiesProvider
import it.lysz210.akasha.alidrisi.ruwaat.intervals.infrastructure.capacnan.ClavigerChasqui
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/hello")
class GreetingResource(
    private val clavigerChasqui: ClavigerChasqui,
    chasqui: ClavigerChasqui,
    private val activitiesProvider: ActivitiesProvider
) {

    @GET
//    @Produces(MediaType.TEXT_PLAIN)
    fun hello() = activitiesProvider.listActivities()

    @GET
    @Path("/{activityId}")
    fun download(@PathParam("activityId") activityId: String) = activitiesProvider.getOriginalSource(activityId)
}