package com.blackboard.web.json;

import com.blackboard.api.core.model.School;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ChristopherLicata on 12/1/15.
 */
public class SchoolJson
{
    private String name;

    private int schoolId;


    public SchoolJson(School school)
    {
        name = school.getName();
        schoolId = school.getSchoolId();
    }


    @JsonProperty
    public String getName()
    {
        return name;
    }


    @JsonProperty
    public int getSchoolId()
    {
        return schoolId;
    }
}
