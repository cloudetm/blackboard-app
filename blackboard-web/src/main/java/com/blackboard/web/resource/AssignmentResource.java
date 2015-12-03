package com.blackboard.web.resource;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.service.AssignmentService;
import com.blackboard.api.dao.service.CourseService;
import com.blackboard.api.dao.service.InstructorService;
import com.blackboard.web.json.AssignmentJson;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;

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

@Path("api/v1/assignments")
public class AssignmentResource
{
    private AssignmentService assignmentService;

    private CourseService courseService;

    private InstructorService instructorService;


    public AssignmentResource(
            AssignmentService assignmentService,
            CourseService courseService,
            InstructorService instructorService)
    {
        this.assignmentService = assignmentService;
        this.courseService = courseService;
        this.instructorService = instructorService;
    }


    @GET
    @Path("/courses/{courseId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCourseAssignments(@PathParam("courseId") int courseId)
    {
        if (courseService.getCourse(courseId).isPresent()) {
            List<Assignment> assignments = assignmentService.getAllCourseAssignmentsById(courseId);
            Stream<AssignmentJson> jsons = assignments.parallelStream().map(AssignmentJson::new);
            return Response.ok(jsons.toArray()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }


    @GET
    @Path("/{assignmentId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getCourse(@PathParam("assignmentId") int assignmentId)
    {
        Optional<Assignment> result = assignmentService.getAssignmentById(assignmentId);
        if (result.isPresent()) {
            Assignment assignment = result.get();
            return Response.ok(new AssignmentJson(assignment)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Path("/{assignmentId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response updateAssignment(@PathParam("assignmentId") int assignmentId)
    {

        Optional<Assignment> assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignment.isPresent()) {
            Optional<Course> course = courseService.getCourse(assignment.get().getCourse().getCourseId());
            if (course.isPresent()) {
                Assignment updatedAssignment = assignmentService.updateAssignment(assignmentId, course.get(),
                                                                                  assignment.get().getAssignmentName(),
                                                                                  assignment.get()
                                                                                          .getAssignmentFileName(),
                                                                                  assignment
                                                                                          .get().getInstructions(),
                                                                                  assignment.get()
                                                                                          .getWeight(), assignment.get()
                                                                                          .getTotalPoints(),
                                                                                  assignment.get()
                                                                                          .getDateAssigned(), assignment
                                                                                          .get().getDueDate());
                return Response.ok(updatedAssignment).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @DELETE
    @Path("/{assignmentId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response deleteAssignment(@PathParam("assignmentId") int assignmentId)
    {
        Optional<Assignment> result = assignmentService.getAssignmentById(assignmentId);
        if (result.isPresent()) {
            return Response.ok(new AssignmentJson(assignmentService.deleteAssignmentById(result.get().getAssignmentId())
                                                          .get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAssignment(AssignmentJson json, @Auth User user)
    {

        if (instructorService.getInstructorAccountByEmail(user.getEmail()).isPresent()) {
            Optional<Course> course = courseService.getCourse(json.getCourse().getCourseId());
            if (course.isPresent()) {
                Assignment assignment = assignmentService.createAssignment(course.get(), json.getAssignmentName(),
                                                                           json.getAssignmentFileName(),
                                                                           json.getInstructions(), json.getWeight(),
                                                                           json.getTotalPoints(), json
                                                                                   .getDateAssigned(),
                                                                           json.getDueDate());

                return Response.ok(new AssignmentJson(assignment)).build();

            } else {

                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }
}
