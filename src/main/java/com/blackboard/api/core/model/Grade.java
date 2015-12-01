package com.blackboard.api.core.model;

import java.sql.Timestamp;

/**
 * This is the Grade Model Class that maps to the grades table in the database.
 * <p>
 * Created by ChristopherLicata on 11/14/15.
 */
public class Grade
{
    private int gradeId;

    private int score;

    private Assignment assignment;

    private int submissionId;

    private String studentEmail;

    private Timestamp timeSubmitted;


    /**
     * @param gradeId
     * @param score
     * @param assignment
     * @param submissionId
     * @param studentEmail
     */
    public Grade(
            int gradeId,
            int score,
            Assignment assignment,
            int submissionId,
            String studentEmail)
    {
        this.gradeId = gradeId;
        this.score = score;
        this.assignment = assignment;
        this.submissionId = submissionId;
        this.studentEmail = studentEmail;
        this.timeSubmitted = timeSubmitted;
    }


    /**
     * @param score
     * @param assignment
     * @param submissionId
     * @param studentEmail
     */
    public Grade(
            int score,
            Assignment assignment,
            int submissionId,
            String studentEmail)
    {
        this.score = score;
        this.assignment = assignment;
        this.submissionId = submissionId;
        this.studentEmail = studentEmail;

    }


    /**
     * @param score
     * @param assignment
     * @param submissionId
     * @param studentEmail
     * @param timeSubmitted
     */
    public Grade(
            int score,
            Assignment assignment,
            int submissionId,
            String studentEmail, Timestamp timeSubmitted)
    {
        this.score = score;
        this.assignment = assignment;
        this.submissionId = submissionId;
        this.studentEmail = studentEmail;
        this.timeSubmitted = timeSubmitted;
    }


    /**
     * @param gradeId
     * @param score
     * @param assignment
     * @param submissionId
     * @param studentEmail
     * @param timeSubmitted
     */
    public Grade(
            int gradeId,
            int score,
            Assignment assignment,
            int submissionId,
            String studentEmail,
            Timestamp timeSubmitted)
    {
        this.gradeId = gradeId;
        this.score = score;
        this.assignment = assignment;
        this.submissionId = submissionId;
        this.studentEmail = studentEmail;
        this.timeSubmitted = timeSubmitted;
    }


    public int getScore()
    {
        return score;
    }


    public void setScore(int score)
    {
        this.score = score;
    }


    public Timestamp getTimeSubmitted()
    {
        return timeSubmitted;
    }


    public void setTimeSubmitted(Timestamp timeSubmitted)
    {
        this.timeSubmitted = timeSubmitted;
    }


    public int getSubmissionId()
    {
        return submissionId;
    }


    public void setSubmissionId(int submissionId)
    {
        this.submissionId = submissionId;
    }


    public String getStudentEmail()
    {
        return studentEmail;
    }


    public void setStudentEmail(String studentEmail)
    {
        this.studentEmail = studentEmail;
    }


    public int getGradeId()
    {
        return gradeId;
    }


    public void setGradeId(int gradeId)
    {
        this.gradeId = gradeId;
    }


    public Assignment getAssignment()
    {
        return assignment;
    }


    public void setAssignment(Assignment assignment)
    {
        this.assignment = assignment;
    }
}
