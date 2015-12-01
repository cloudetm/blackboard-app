package com.blackboard.api.web.json;

import com.blackboard.api.core.model.Student;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ChristopherLicata on 12/1/15.
 */
public class StudentJson
        extends UserJson
{
    private double gpa;


    public StudentJson()
    {
    }


    public StudentJson(Student student)
    {
        super(student);
        if (student.getGpa() != 0)
        {
            gpa = student.getGpa();
        }
    }


    @JsonProperty
    public double getGpa()
    {
        return gpa;
    }
}
