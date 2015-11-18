package com.blackboard.api.core.model;

/**
 * The Course model class that corresponds to the Course Table in the Database.
 * <p/>
 * Created by ChristopherLicata on 11/17/15.
 */
public class Course
{
    private Instructor instructor;

    private School school;

    private String courseName;

    private String subject;

    private int courseNumber;

    private int credits;

    private String syllabusFileName;

    private int maxCapacity;


    /**
     * The full constructor for the Course model object.
     *
     * @param school           The instution that offers the course
     * @param instructor       The instructor of the course.
     * @param courseName       The name of the course.
     * @param subject          The subject of the course.
     * @param courseNumber     The course number.
     * @param credits          The number of credits assigned to the course.
     * @param syllabusFileName The filename of the syllabus.
     * @param maxCapacity      The maximum number of students allowed to enroll in the course.
     */
    public Course(
            School school,
            Instructor instructor,
            String courseName,
            String subject,
            int courseNumber,
            int credits,
            String syllabusFileName,
            int maxCapacity)
    {
        this.school = school;
        this.instructor = instructor;
        this.courseName = courseName;
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.credits = credits;
        this.syllabusFileName = syllabusFileName;
        this.maxCapacity = maxCapacity;
    }


    public Instructor getInstructor()
    {
        return instructor;
    }


    public void setInstructor(Instructor instructor)
    {
        this.instructor = instructor;
    }


    public String getCourseName()
    {
        return courseName;
    }


    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }


    public String getSyllabusFileName()
    {
        return syllabusFileName;
    }


    public void setSyllabusFileName(String syllabusFileName)
    {
        this.syllabusFileName = syllabusFileName;
    }


    public int getMaxCapacity()
    {
        return maxCapacity;
    }


    public void setMaxCapacity(int maxCapacity)
    {
        this.maxCapacity = maxCapacity;
    }


    public String getSubject()
    {
        return subject;
    }


    public void setSubject(String subject)
    {
        this.subject = subject;
    }


    public int getCourseNumber()
    {
        return courseNumber;
    }


    public void setCourseNumber(int courseNumber)
    {
        this.courseNumber = courseNumber;
    }


    public int getCredits()
    {
        return credits;
    }


    public void setCredits(int credits)
    {
        this.credits = credits;
    }


    public School getSchool()
    {
        return school;
    }


    public void setSchool(School school)
    {
        this.school = school;
    }
}
