package com.blackboard.api.core.model;

import com.blackboard.api.core.Season;

/**
 * The Transcript model class that corresponds to the Transcript Table in the Database.
 * <p/>
 * Created by ChristopherLicata on 11/17/15.
 */
public class Transcript
{
    private int transcriptId;

    private String studentEmail;

    private Season semester;

    private int year;

    private Course course;

    private double grade;


    /**
     * The full constructor for a Transcript model object.
     *
     * @param studentEmail The email of the student to whom this transcript references
     * @param semester     The semester in which the courses were taken
     * @param year         The year in which the courses were taken
     * @param course       The course that was taken
     * @param grade        The grade that the student received in the course.
     */

    public Transcript(
            int transcriptId, String studentEmail, Season semester, int year, Course course, double
            grade)
    {
        this.transcriptId = transcriptId;
        this.studentEmail = studentEmail;
        this.semester = semester;
        this.year = year;
        this.course = course;
        this.grade = grade;
    }


    /**
     * Transcript Constructor used for the initial creation of a Transcript in the DB
     *
     * @param studentEmail
     * @param semester
     * @param year
     * @param course
     */
    public Transcript(String studentEmail, Season semester, int year, Course course)
    {
        this.studentEmail = studentEmail;
        this.semester = semester;
        this.year = year;
        this.course = course;
    }


    /**
     * Transcript Constructor used to return the created Transcript in the DB after creation or during find
     *
     * @param transcriptId
     * @param studentEmail
     * @param semester
     * @param year
     * @param course
     */
    public Transcript(int transcriptId, String studentEmail, Season semester, int year, Course course)
    {
        this.transcriptId = transcriptId;
        this.studentEmail = studentEmail;
        this.semester = semester;
        this.year = year;
        this.course = course;
    }


    public String getStudentEmail()
    {
        return studentEmail;
    }


    public void setStudentEmail(String studentEmail)
    {
        this.studentEmail = studentEmail;
    }


    public Season getSemester()
    {
        return semester;
    }


    public void setSemester(Season semester)
    {
        this.semester = semester;
    }


    public int getYear()
    {
        return year;
    }


    public void setYear(int year)
    {
        this.year = year;
    }


    public Course getCourse()
    {
        return course;
    }


    public void setCourse(Course course)
    {
        this.course = course;
    }


    public double getGrade()
    {
        return grade;
    }


    public void setGrade(int grade)
    {
        this.grade = grade;
    }


    public int getTranscriptId()
    {
        return transcriptId;
    }


    public void setTranscriptId(int transcriptId)
    {
        this.transcriptId = transcriptId;
    }
}
