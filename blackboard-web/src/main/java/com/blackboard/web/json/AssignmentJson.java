package com.blackboard.web.json;

import com.blackboard.api.core.model.Assignment;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by ChristopherLicata on 12/3/15.
 */
public class AssignmentJson
{
    private int assignmentId;

    private CourseJson course;

    private String assignmentName;

    private String assignmentFileName;

    private String instructions;

    private double weight;

    private int totalPoints;

    private long dateAssigned;

    private long dueDate;


    public AssignmentJson(Assignment assignment)
    {
        assignmentId = assignment.getAssignmentId();
        course = new CourseJson(assignment.getCourse());
        assignmentName = assignment.getAssignmentName();
        assignmentFileName = assignment.getAssignmentFileName();
        instructions = assignment.getInstructions();
        weight = assignment.getWeight();
        totalPoints = assignment.getTotalPoints();
        dateAssigned = assignment.getDateAssigned().getTime();
        dueDate = assignment.getDueDate().getTime();
    }


    @JsonProperty
    public int getAssignmentId()
    {
        return assignmentId;
    }


    @JsonProperty
    public CourseJson getCourse()
    {
        return course;
    }


    @JsonProperty
    public String getAssignmentName()
    {
        return assignmentName;
    }


    @JsonProperty
    public String getAssignmentFileName()
    {
        return assignmentFileName;
    }


    @JsonProperty
    public String getInstructions()
    {
        return instructions;
    }


    @JsonProperty
    public double getWeight()
    {
        return weight;
    }


    @JsonProperty
    public int getTotalPoints()
    {
        return totalPoints;
    }


    // Convert JavaScript Date to that of Java
    @JsonProperty
    public Date getDateAssigned()
    {
        return new Date(dateAssigned);
    }


    @JsonProperty
    public Date getDueDate()
    {
        return new Date(dueDate);
    }
}
