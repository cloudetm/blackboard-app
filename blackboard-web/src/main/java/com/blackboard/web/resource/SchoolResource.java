package com.blackboard.web.resource;

import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.service.SchoolService;
import com.blackboard.web.json.SchoolJson;
import com.codahale.metrics.annotation.Timed;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by ChristopherLicata on 12/1/15.
 */
@Path("api/v1/schools")
@Produces(MediaType.APPLICATION_JSON)
public class SchoolResource
{

    private SchoolService schoolService;


    public SchoolResource(SchoolService schoolService)
    {
        this.schoolService = schoolService;
    }


    @GET
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSchools()
    {
        List<School> schools = schoolService.getAllSchools();
        Stream<SchoolJson> jsons = schools.parallelStream().map(s -> {
            return new SchoolJson(s);
        });
        return Response.ok(jsons.toArray()).build();
    }


    @GET
    @Path("/{id}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getSchool(@PathParam("id") int id)
    {
        Optional<School> result = schoolService.getSchool(id);
        if (result.isPresent())
        {
            School school = result.get();
            return Response.ok(new SchoolJson(school)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
