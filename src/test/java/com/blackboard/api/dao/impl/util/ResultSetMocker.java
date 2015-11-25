package com.blackboard.api.dao.impl.util;

import com.blackboard.api.core.Subject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Utility class used to generate result sets for the different DAO model objects.
 * <p/>
 * Created by ChristopherLicata on 11/22/15.
 */
public class ResultSetMocker
{

    public Optional<ResultSet> mockStudentResultSet(
            String firstName, String lastName, String email, String
            password, int schoolId, double gpa)
            throws SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("fname")).thenReturn(firstName);
        when(resultSetMock.getString("lname")).thenReturn(lastName);
        when(resultSetMock.getString("email")).thenReturn(email);
        when(resultSetMock.getString("password")).thenReturn(password);
        when(resultSetMock.getInt("school_id")).thenReturn(schoolId);
        when(resultSetMock.getDouble("gpa")).thenReturn(gpa);

        return Optional.of(resultSetMock);
    }


    public Optional<ResultSet> mockStudentMultiRowResultSet()
            throws
            SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("fname")).thenReturn("Monica", "Ross");
        when(resultSetMock.getString("lname")).thenReturn("Geller", "Geller");
        when(resultSetMock.getString("email")).thenReturn("chefmonica@gmail.com", "dinosaurman93@yahoo.com");
        when(resultSetMock.getString("password")).thenReturn("9382hcioh8", "f8j342nfb");
        when(resultSetMock.getInt("school_id")).thenReturn(78, 7);
        when(resultSetMock.getDouble("gpa")).thenReturn(3.1, 4.3);
        return Optional.of(resultSetMock);
    }


    public Optional<ResultSet> mockInstructorResultSet(
            String firstName, String lastName, String email, String
            password, int schoolId)
            throws SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("fname")).thenReturn(firstName);
        when(resultSetMock.getString("lname")).thenReturn(lastName);
        when(resultSetMock.getString("email")).thenReturn(email);
        when(resultSetMock.getString("password")).thenReturn(password);
        when(resultSetMock.getInt("school_id")).thenReturn(schoolId);
        return Optional.of(resultSetMock);
    }


    public Optional<ResultSet> mockInstructorMultiRowResultSet()
            throws
            SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("fname")).thenReturn("Phil", "Bobby");
        when(resultSetMock.getString("lname")).thenReturn("Loopy", "Hayes");
        when(resultSetMock.getString("email")).thenReturn("loopy@gmail.com", "heffer@yahoo.com");
        when(resultSetMock.getString("password")).thenReturn("rdtcut27", "gdby7c8e2");
        when(resultSetMock.getInt("school_id")).thenReturn(14, 12);
        return Optional.of(resultSetMock);
    }


    public Optional<ResultSet> mockSchoolResultSet(int schoolId, String schoolName)
            throws SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("school_id")).thenReturn(schoolId);
        when(resultSetMock.getString("name")).thenReturn(schoolName);
        return Optional.of(resultSetMock);
    }


    public Optional<ResultSet> mockSchoolMultiRowResultSet()
            throws SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("school_id")).thenReturn(15, 14);
        when(resultSetMock.getString("name")).thenReturn(
                "The George Washington University",
                "University of Maryland");
        return Optional.of(resultSetMock);
    }


    public Optional<ResultSet> mockCourseResultSet(
            int course_id, Subject subject, int courseNumber, int school_id, String instructorEmail, String
            courseName, String syllabusFilename, int maxCapacity, int credits)
            throws SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("course_id")).thenReturn(course_id);
        when(resultSetMock.getString("subject")).thenReturn(subject.toString());
        when(resultSetMock.getInt("course_number")).thenReturn(courseNumber);
        when(resultSetMock.getInt("school_id")).thenReturn(school_id);
        when(resultSetMock.getString("instructor_email")).thenReturn(instructorEmail);
        when(resultSetMock.getString("course_name")).thenReturn(courseName);
        when(resultSetMock.getString("syllabus_filename")).thenReturn(syllabusFilename);
        when(resultSetMock.getInt("max_capacity")).thenReturn(maxCapacity);
        when(resultSetMock.getInt("credits")).thenReturn(credits);
        return Optional.of(resultSetMock);
    }


    public Optional<ResultSet> mockCoursesMultiRowResultSet()
            throws SQLException
    {
        ResultSet resultSetMock = mock(ResultSet.class);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getInt("course_id")).thenReturn(34, 198);
        when(resultSetMock.getString("subject")).thenReturn(Subject.MATH.toString(), Subject.CSCI.toString());
        when(resultSetMock.getInt("course_number")).thenReturn(1001, 2113);
        when(resultSetMock.getInt("school_id")).thenReturn(14, 14);
        when(resultSetMock.getString("instructor_email"))
                .thenReturn("jsuarez@gwu.edu", "sudoman@caltech.edu");
        when(resultSetMock.getString("course_name")).thenReturn("Calculus I", "Software Engineering");
        when(resultSetMock.getString("syllabus_filename")).thenReturn(
                "calc1_FALL_2015.docx",
                "software_engineering_FALL_2015.docx");
        when(resultSetMock.getInt("max_capacity")).thenReturn(101, 45);
        when(resultSetMock.getInt("credits")).thenReturn(3, 3);
        return Optional.of(resultSetMock);
    }

}
