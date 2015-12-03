package com.blackboard.web.resource;

import com.blackboard.api.core.model.Student;
import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.service.StudentService;
import com.blackboard.api.dao.service.UserService;
import com.blackboard.web.json.StudentJson;
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
 * Created by ChristopherLicata on 12/1/15.
 */
@Path("/api/v1/students")
public class StudentResource
{
    private StudentService studentService;
    private UserService userService;

    public StudentResource(StudentService studentService, UserService userService)
    {
        this.userService = userService;
        this.studentService = studentService;
    }


    @GET
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudentUsers()
    {
        List<Student> students = studentService.getAllStudents();
        Stream<StudentJson> jsons = students.parallelStream().map(student -> {
            return new StudentJson(student);
        });
        return Response.ok(jsons.toArray()).build();
    }


    @GET
    @Path("/courses/{courseid}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentsInCourse(@PathParam("courseid") int courseId)
    {
        List<Student> students = studentService.getStudentsInCourse(courseId);
        Stream<StudentJson> jsons = students.parallelStream().map(student -> {
            return new StudentJson(student);
        });
        return Response.ok(jsons.toArray()).build();
    }


    @GET
    @Path("/{email}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getStudent(@PathParam("email") String email)
    {
        Optional<Student> result = studentService.getStudentAccountByEmail(email);
        if (result.isPresent())
        {
            Student student = result.get();
            return Response.ok(new StudentJson(student)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


    @PUT
    @Path("/{email}")
    @RolesAllowed("USER")
    @Timed
    public Response updateStudent(@PathParam("email") String email, StudentJson json, @Auth User user)
    {
        if (user.getEmail().equals(email))
        {

            Optional<Student> result = studentService.getStudentAccountByEmail(email);

            if (result.isPresent())
            {

                userService.updateStudentAccount(json.getFirstName(), json.getLastName(), json.getEmail(),
                                                 json
                        .getPassword(), json.getGpa(), json.getSchoolId());
                return getStudent(email);
            }
            else
            {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        else
        {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

}
