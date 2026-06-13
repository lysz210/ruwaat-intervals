package it.lysz210.akasha.alidrisi.ruwaat.intervals.api

import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.ActivitiesService
import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
class ActivitiesResource(
    private val activitiesService: ActivitiesService
) {
    @GET
    fun list(): Uni<List<Activity>> =
        activitiesService.list().collect().asList()

    @GET
    @Path("/{activityId}")
    fun getActivity(@PathParam("activityId") activityId: String): Uni<Activity> =
        activitiesService.activity(Activity.ActivityId(activityId))
}