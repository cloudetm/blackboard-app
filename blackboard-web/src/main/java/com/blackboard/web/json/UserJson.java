package com.blackboard.web.json;

import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.service.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

/**
 * Created by ChristopherLicata on 11/30/15.
 */
public class UserJson
{
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int schoolId;


    public UserJson()
    {
    }


    public UserJson(User user)
    {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.schoolId = user.getSchoolId();
    }


    public Optional<User> toUser(UserService userService)
    {
        return userService.getUser(this.email);
    }


    @JsonProperty
    public String getFirstName()
    {
        return firstName;
    }


    @JsonProperty
    public String getLastName()
    {
        return lastName;
    }


    @JsonProperty
    public String getEmail()
    {
        return email;
    }


    @JsonProperty
    public String getPassword()
    {
        return password;
    }


    @JsonProperty
    public int getSchoolId()
    {
        return schoolId;
    }

}
