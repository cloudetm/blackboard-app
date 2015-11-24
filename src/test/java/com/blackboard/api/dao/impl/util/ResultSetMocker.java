package com.blackboard.api.dao.impl.util;

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
        when(resultSetMock.getInt("school_id")).thenReturn(12, 2);
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
}
