package com.blackboard.web.resource;

import com.blackboard.api.core.Subject;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.School;
import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.service.CourseService;
import com.blackboard.api.dao.service.InstructorService;
import com.blackboard.api.dao.service.SchoolService;
import com.blackboard.web.json.CourseJson;
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
 * Created by ChristopherLicata on 12/2/15.
 */
@Path("/api/v1/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource
{
    private CourseService courseService;

    private InstructorService instructorService;

    private SchoolService schoolService;


    public CourseResource(
            CourseService courseService,
            InstructorService instructorService,
            SchoolService schoolService)
    {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.schoolService = schoolService;
    }


    @GET
    @Path("/instructors/{email}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCoursesByInstructor(@PathParam("email") String instructorEmail)
    {
        List<Course> courses = courseService.getAllInstructorCourses(instructorEmail);
        Stream<CourseJson> jsons = courses.parallelStream().map(course -> {
            return new CourseJson(course);
        });
        return Response.ok(jsons.toArray()).build();
    }


    @GET
    @Path("/{courseId}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getCourse(@PathParam("courseId") int courseId)
    {
        Optional<Course> result = courseService.getCourse(courseId);
        if (result.isPresent()) {
            Course course = result.get();
            return Response.ok(new CourseJson(course)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @GET
    @Path("/schools/{schoolId}")
    @RolesAllowed("USER")
    @Timed
    public Response getCoursesBySchool(@PathParam("schoolId") int schoolId)
    {

        Optional<School> school = schoolService.getSchool(schoolId);
        if (school.isPresent()) {
            List<Course> courses = courseService.getAllCoursesOffered(school.get());
            Stream<CourseJson> courseStream = courses.parallelStream().map(course -> {
                return new CourseJson(course);
            });
            return Response.ok(courseStream.toArray()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @DELETE
    @Path("/{courseId}")
    @RolesAllowed("USER")
    @Timed
    public Response deleteCourse(@PathParam("courseId") int courseId)
    {
        Optional<Course> result = courseService.getCourse(courseId);
        if (result.isPresent()) {
            return Response.ok(new CourseJson(courseService.deleteCourse(result.get()).get())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCourse(CourseJson json, @Auth User user)
    {

        if (instructorService.getInstructorAccountByEmail(user.getEmail()).isPresent()) {
            Optional<Instructor> instructor = instructorService
                    .getInstructorAccountByEmail(json.getInstructor().getEmail());

            Optional<School> school = schoolService.getSchool(json.getSchool().getSchoolId());

            if (instructor.isPresent() && school.isPresent()) {
                Course course = courseService.createCourse(school.get(), instructor.get(), json.getCourseName(),
                                                           Subject.valueOf(json.getSubject()), json.getCourseNumber(),
                                                           json.getCredits(), json.getSyllabusFileName(), json
                                                                   .getMaxCapacity());

                return Response.ok(new CourseJson(course)).build();

            } else {

                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }
}