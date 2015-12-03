package com.blackboard.web.resource;

import com.blackboard.api.core.Season;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Transcript;
import com.blackboard.api.dao.service.CourseService;
import com.blackboard.api.dao.service.TranscriptService;
import com.blackboard.web.json.TranscriptJson;
import com.codahale.metrics.annotation.Timed;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by ChristopherLicata on 12/3/15.
 */

@Path("/api/v1/transcripts")
@Produces(MediaType.APPLICATION_JSON)
public class TranscriptResource
{
    private TranscriptService transcriptService;

    private CourseService courseService;


    public TranscriptResource(
            TranscriptService transcriptService, CourseService courseService)
    {
        this.transcriptService = transcriptService;
        this.courseService = courseService;
    }


    @GET
    @Path("/students/{email}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTranscriptsForStudent(@PathParam("email") String studentEmail)
    {
        List<Transcript> transcripts = transcriptService.getStudentTranscripts(studentEmail);
        Stream<
                TranscriptJson> jsons = transcripts.parallelStream().map(TranscriptJson::new);
        return Response.ok(jsons.toArray()).build();
    }


    @GET
    @Path("/{transcriptId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getTranscript(@PathParam("transcriptId") int transcriptId)
    {
        Optional<Transcript> result = transcriptService.getTranscriptById(transcriptId);
        if (result.isPresent()) {
            Transcript transcript = result.get();
            return Response.ok(new TranscriptJson(transcript)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Path("/{transcriptId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed
    public Response updateTranscript(
            @PathParam("transcriptId") int transcriptId, TranscriptJson json, @QueryParam
            ("userEmail") String
            userEmail)
    {
        Optional<Transcript> result = transcriptService.getTranscriptById(transcriptId);
        String instructorEmail = result.get().getCourse().getInstructor().getEmail();

        if (result.isPresent() && instructorEmail.equals(userEmail)) {
            Optional<Course> course = courseService.getCourse(json.getCourse().getCourseId());

            if (course.isPresent()) {
                return Response.ok(transcriptService.updateTranscript(transcriptId, json.getStudentEmail(),
                                                                      Season.valueOf(json.getSemester()), json
                                                                              .getYear(), course.get(), json
                                                                              .getGrade())).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }

        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }


    @POST
    @RolesAllowed("USER")
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTranscript(TranscriptJson json, @QueryParam("userEmail") String email)
    {

        if (json.getStudentEmail().equals(email)) {
            Optional<Course> course = courseService.getCourse(json.getCourse().getCourseId());

            if (course.isPresent()) {
                Transcript transcript = transcriptService.createTranscript(json.getStudentEmail(), Season.valueOf(json
                                                                                                                          .getSemester()), json
                                                                                   .getYear(), course.get());
                return Response.ok(new TranscriptJson(transcript)).build();

            } else {

                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }


    @DELETE
    @Path("/{transcriptId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response deleteTranscript(@PathParam("transcriptId") int transcriptId)
    {
        Optional<Transcript> result = transcriptService.getTranscriptById(transcriptId);
        if (result.isPresent()) {
            return Response.ok(new TranscriptJson(transcriptService.deleteTranscript(result.get().getTranscriptId())
                                                          .get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
