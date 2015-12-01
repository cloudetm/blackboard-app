package com.blackboard.api.web.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ChristopherLicata on 12/1/15.
 */
public class UserCreationJson
{

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int schoolId;

    private boolean isStudent;


    public UserCreationJson()
    {
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


    @JsonProperty
    public boolean isStudent()
    {
        return isStudent;
    }
}
