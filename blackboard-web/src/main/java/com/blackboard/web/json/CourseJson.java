package com.blackboard.web.json;

import com.blackboard.api.core.model.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class CourseJson
{
    private int courseId;

    private UserJson instructor;

    private SchoolJson school;

    private String courseName;

    private String subject;

    private int courseNumber;

    private int credits;

    private String syllabusFileName;

    private int maxCapacity;


    public CourseJson(Course course)
    {
        courseId = course.getCourseId();
        instructor = new UserJson(course.getInstructor());
        school = new SchoolJson(course.getSchool());
        courseName = course.getCourseName();
        subject = course.getSubjectAsString();
        courseNumber = course.getCourseNumber();
        credits = course.getCredits();
        syllabusFileName = course.getSyllabusFileName();
        this.maxCapacity = course.getMaxCapacity();
    }

    @JsonProperty
    public int getCourseId()
    {
        return courseId;
    }

    @JsonProperty
    public UserJson getInstructor()
    {
        return instructor;
    }

    @JsonProperty
    public SchoolJson getSchool()
    {
        return school;
    }

    @JsonProperty
    public String getCourseName()
    {
        return courseName;
    }

    @JsonProperty
    public String getSubject()
    {
        return subject;
    }

    @JsonProperty
    public int getCourseNumber()
    {
        return courseNumber;
    }

    @JsonProperty
    public int getCredits()
    {
        return credits;
    }

    @JsonProperty
    public String getSyllabusFileName()
    {
        return syllabusFileName;
    }

    @JsonProperty
    public int getMaxCapacity()
    {
        return maxCapacity;
    }

}
