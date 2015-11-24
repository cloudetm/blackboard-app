package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.dao.impl.util.ResultSetMocker;
import com.blackboard.api.dao.util.MySQLDao;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Created by ChristopherLicata on 11/22/15.
 */
public class InstructorMySQLDaoTest
{
    private MySQLDao dao;

    private InstructorMySQLDao instructorDao;

    private ResultSetMocker resultSet;


    @Before
    public void setUp()
    {
        dao = mock(MySQLDao.class);
        instructorDao = new InstructorMySQLDao(dao);
        resultSet = new ResultSetMocker();
    }


    /**
     * Testing for the creation of instructors -- expecting Success.
     */
    @Test
    public void testCreateInstructor()
    {
        String query = new StringBuilder()
                .append("INSERT INTO users(fname, lname, email, password, school_id, is_student) VALUES")
                .append("(?, ?, ?, ?, ?, ?)").toString();

        Instructor instructor = mock(Instructor.class);
        when(instructor.getFirstName()).thenReturn("Greg");
        when(instructor.getLastName()).thenReturn("House");
        when(instructor.getEmail()).thenReturn("foo@bar.com");
        when(instructor.getPassword()).thenReturn("hykhkbgku");
        when(instructor.getSchoolId()).thenReturn(3);

        instructorDao.createInstructor(instructor);
        verify(dao).update(query, "Greg", "House", "foo@bar.com", "hykhkbgku", 3, 0);
    }


    /**
     * Testing for correct parameters in call to InstructorMySQLDao -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindInstructorByEmailParameters()
            throws SQLException
    {
        String q = new StringBuilder()
                .append("SELECT fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();

        String email = "c2o151@yahoo.com";

        Optional<ResultSet> result = resultSet.mockStudentResultSet("Radcliff", "Garret", "c2o151@yahoo.com",
                                                                    "ytr83942ct3", 89, 2.1);
        when(dao.query(q, email, 0)).thenReturn(result);
        instructorDao.findInstructorByEmail(email);
        verify(dao).query(q, email, 0);
    }


    /**
     * Test for finding Instructor by email in DB when the email input is valid and exists in the DB. Not only
     * tests for whether or not the values of the object returned are the same, but also that the result set
     * is not empty -- expecting success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindInstructorByEmail()
            throws SQLException
    {
        String q = new StringBuilder()
                .append("SELECT fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();
        String email = "fooman@bar.com";
        int isStudent = 0;

        Optional<ResultSet> result = resultSet.mockInstructorResultSet("Gregory", "Manchester",
                                                                       "fooman@bar.com",
                                                                       "234567jfe", 12);
        when(dao.query(q, email, isStudent)).thenReturn(result);

        Optional<Instructor> instructor = instructorDao.findInstructorByEmail("fooman@bar.com");

        Assert.that(instructor.isPresent(), "Instructor is not being returned as a result of the DB query");
        Assert.that(instructor.get().getFirstName()
                            .equals("Gregory"), "Instructor first name did not match expected value");
        Assert.that(instructor.get().getLastName()
                            .equals("Manchester"), "Instructor last name did not match expected value");
        Assert.that(instructor.get().getEmail()
                            .equals("fooman@bar.com"), "Instructor email name did not match expected value");
        Assert.that(instructor.get().getPassword()
                            .equals("234567jfe"), "Instructor password did not match expected value");
        Assert.that(instructor.get().getSchoolId() == 12, "Instructor schoolId did not match expected value");
    }


    /**
     * Test for finding Instructor by email in DB when the email input is Invalid and does not exist in the
     * DB.  -- expecting failure.
     *
     * @throws SQLException
     */
    @Test
    public void testFindInvalidEmail()
            throws SQLException
    {
        Optional<ResultSet> result = Optional.empty();

        when(dao.query(anyString(), anyString(), anyInt())).thenReturn(result);

        Optional<Instructor> actualResult = instructorDao.findInstructorByEmail("foo@barnciewo.com");

        Assert.that(
                actualResult.equals(Optional.empty()),
                "Instructors are being returned despite invalid email input to the query");
    }


    /**
     * Test for finding all Instructors in DB. Not only tests for whether or not the values of the object
     * returned are the same, but also that the result set is not empty -- expecting success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindAllInstructors()
            throws Exception
    {
        String q = new StringBuilder()
                .append("SELECT fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ?")
                .toString();

        Optional<ResultSet> result = resultSet.mockInstructorMultiRowResultSet();
        when(dao.query(q, 0)).thenReturn(result);

        List<Instructor> instructors = instructorDao.findAllInstructors();

        //First Row of Result Set
        Assert.that(
                instructors.size() == 2, "Instructors are not being returned as a result of the DB query");

        Assert.that(instructors.get(0).getFirstName()
                            .equals("Phil"), "Instructor first name did not match expected value");
        Assert.that(instructors.get(0).getLastName()
                            .equals("Loopy"), "Instructor last name did not match expected value");
        Assert.that(instructors.get(0).getEmail()
                            .equals("loopy@gmail.com"), "Instructor email name did not match expected value");
        Assert.that(instructors.get(0).getPassword()
                            .equals("rdtcut27"), "Instructor password did not match expected value");
        Assert.that(
                instructors.get(0).getSchoolId() == 12, "Instructor schoolId did not match expected value");

        // Second Row of Result Set
        Assert.that(instructors.get(1).getFirstName()
                            .equals("Bobby"), "Instructor first name did not match expected value");
        Assert.that(instructors.get(1).getLastName()
                            .equals("Hayes"), "Instructor last name did not match expected value");
        Assert.that(instructors.get(1).getEmail()
                            .equals("heffer@yahoo.com"), "Instructor email name did not match expected " +
                            "value");
        Assert.that(instructors.get(1).getPassword()
                            .equals("gdby7c8e2"), "Instructor password did not match expected value");
        Assert.that(
                instructors.get(1).getSchoolId() == 2, "Instructor schoolId did not match expected value");

    }
}