package com.blackboard.api.core.model;

import java.sql.Timestamp;

/**
 * The Submission Model Object that corresponds to the submissions table in the database.
 * <p>
 * Created by ChristopherLicata on 11/17/15.
 */
public class Submission
{
    int submissionId;

    private Grade grade;

    private Assignment assignment;

    private Timestamp currentTimeStamp;

    private String studentEmail;

    private String submissionFileName;


    /**
     * The constructor for the Submission Model Object that corresponds with the situation, during which, a
     * student submits an assignment that has yet to be graded.
     *
     * @param submissionId       The id associated with the submission in the DB.
     * @param assignment         The assignment to which this submission corresponds.
     * @param studentEmail       The email of the student whom submitted the work
     * @param submissionFileName The filename associated with this submission.
     */
    public Submission(
            int submissionId, Grade grade, Assignment assignment, String
            studentEmail, String submissionFileName, Timestamp currentTimeStamp)
    {
        this.submissionId = submissionId;
        this.grade = grade;
        this.assignment = assignment;
        this.studentEmail = studentEmail;
        this.submissionFileName = submissionFileName;
        this.currentTimeStamp = currentTimeStamp;
    }


    public Submission(
            int submissionId, Assignment assignment, String studentEmail, String submissionFileName,
            Timestamp currentTimeStamp)
    {
        this.assignment = assignment;
        this.studentEmail = studentEmail;
        this.submissionFileName = submissionFileName;
        this.currentTimeStamp = currentTimeStamp;
    }


    public Submission(
            Assignment assignment, String studentEmail, String submissionFileName,
            Timestamp currentTimeStamp)
    {
        this.assignment = assignment;
        this.studentEmail = studentEmail;
        this.submissionFileName = submissionFileName;
        this.currentTimeStamp = currentTimeStamp;
    }


    public int getSubmissionId()
    {
        return submissionId;
    }


    public void setSubmissionId(int submissionId)
    {
        this.submissionId = submissionId;
    }


    public Grade getGrade()
    {
        return grade;
    }


    public void setGrade(Grade grade)
    {
        this.grade = grade;
    }


    public Assignment getAssignment()
    {
        return assignment;
    }


    public void setAssignment(Assignment assignment)
    {
        this.assignment = assignment;
    }


    public String getStudentEmail()
    {
        return studentEmail;
    }


    public void setStudentEmail(String studentEmail)
    {
        this.studentEmail = studentEmail;
    }


    public String getSubmissionFileName()
    {
        return submissionFileName;
    }


    public void setSubmissionFileName(String submissionFileName)
    {
        this.submissionFileName = submissionFileName;
    }


    public Timestamp getCurrentTimeStamp()
    {
        return currentTimeStamp;
    }


    public void setCurrentTimeStamp(Timestamp currentTimeStamp)
    {
        this.currentTimeStamp = currentTimeStamp;
    }
}
