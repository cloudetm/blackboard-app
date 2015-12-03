package com.blackboard.web.resource;

import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.Student;
import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.service.InstructorService;
import com.blackboard.api.dao.service.StudentService;
import com.blackboard.api.dao.service.UserService;
import com.blackboard.web.json.UserCreationJson;
import com.blackboard.web.json.UserJson;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Path("/api/v1/users")
public class UserResource
{
    private UserService userService;
    private StudentService studentService;
    private InstructorService instructorService;

    public UserResource(UserService userService, StudentService studentService, InstructorService instructorService)
    {
        this.userService = userService;
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    @GET
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInstructorUsers()
    {
        List<Instructor> users = instructorService.getAllInstructors();
        Stream<UserJson> jsons = users.parallelStream().map(u -> {
            return new UserJson(u);
        });
        return Response.ok(jsons.toArray()).build();
    }


    @GET
    @Path("/{email}")
    @RolesAllowed("USER")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed
    public Response getUser(@PathParam("email") String email)
    {
        Optional<User> result = userService.getUser(email);
        if (result.isPresent())
        {
            User user = result.get();
            return Response.ok(new UserJson(user)).build();
        }
        else
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/login/{email}")


    @POST
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(UserCreationJson json)
    {
        if (json.isStudent() == 1)
        {
            Student student = studentService.createStudent(json.getFirstName(), json.getLastName(), json
                .getEmail(),
                                                json.getPassword(), json.getSchoolId());
            return Response.ok(new UserJson(student)).build();
        }
        else
        {
            Instructor instructor = instructorService
                    .createInstructor(json.getFirstName(), json.getLastName(), json.getEmail(),
                                      json.getPassword(), json.getSchoolId());
            return Response.ok(new UserJson(instructor)).build();
        }
    }


    @DELETE
    @Path("/{email}")
    @RolesAllowed("ADMIN")
    @Timed
    public Response deleteUser(@PathParam("email") String email, @Auth User user)
    {
        if (user.getRole().equals("ADMIN"))
        {
            Optional<User> result = userService.getUser(email);
            if (result.isPresent())
            {
                return Response.ok(new UserJson(userService.deleteUserAccount(email).get())).build();
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


    @PUT
    @Path("/{email}")
    @RolesAllowed("USER")
    @Timed
    public Response updateInstructor(@PathParam("email") String email, UserJson json, @Auth User user)
    {
        if (user.getEmail().equals(email))
        {

            Optional<User> result = userService.getUser(email);

            if (result.isPresent())
            {

                userService.updateInstructorAccount(json.getFirstName(), json.getLastName(), json.getEmail(), json
                        .getPassword(), json.getSchoolId());
                return getUser(email);
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
