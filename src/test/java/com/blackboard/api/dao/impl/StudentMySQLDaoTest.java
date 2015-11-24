package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Student;
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
 * Created by ChristopherLicata on 11/19/15.
 */
public class StudentMySQLDaoTest
{
    StudentMySQLDao studentDao;

    MySQLDao dao;

    ResultSetMocker resultSet;


    @Before
    public void setUp()
    {
        dao = mock(MySQLDao.class);
        studentDao = new StudentMySQLDao(dao);
        resultSet = new ResultSetMocker();
    }


    /**
     * Testing for the creation of students -- expecting Success.
     */
    @Test
    public void testCreateStudent()
    {
        String query = new StringBuilder()
                .append("INSERT INTO users(fname, lname, email, password, school_id, is_student) VALUES")
                .append("(?, ?, ?, ?, ?, ?)").toString();

        Student student = mock(Student.class);
        when(student.getFirstName()).thenReturn("Chrisy");
        when(student.getLastName()).thenReturn("Licatas");
        when(student.getEmail()).thenReturn("foo@bar.com");
        when(student.getPassword()).thenReturn("6guyhijokl");
        when(student.getSchoolId()).thenReturn(12);

        studentDao.createStudent(student);
        verify(dao).update(query, "Chrisy", "Licatas", "foo@bar.com", "6guyhijokl", 12, 1);
    }


    /**
     * Testing for correct parameters in call to StudentMySQLDao -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindStudentByEmailParameters()
            throws SQLException
    {
        String q = new StringBuilder()
                .append("SELECT fname, lname, email, password, school_id, gpa FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();
        Optional<ResultSet> result = resultSet.mockStudentResultSet("Radcliff", "Jones", "rjones@gmail.com",
                                                                    "ghf4euw9g", 324, 3.4);

        int isStudent = 1;
        String email = "john@thesmiths.com";
        when(dao.query(q, email, isStudent)).thenReturn(result);
        studentDao.findStudentByEmail(email);

        verify(dao).query(q, email, isStudent);
    }


    /**
     * Test for finding Student by email in DB when the email input is valid and exists in the DB. Not only
     * tests for whether or not the values of the object returned are the same, but also that the result set
     * is not empty -- expecting success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindValidEmail()
            throws SQLException
    {
        String q = new StringBuilder()
                .append("SELECT fname, lname, email, password, school_id, gpa FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();

        String email = "foo@bar.com";
        int isStudent = 1;

        Optional<ResultSet> result = resultSet.mockStudentResultSet("Chris", "Licata", "foo@bar.com",
                                                                    "fyghvbjknlm", 1, 4.0);
        when(dao.query(q, email, isStudent)).thenReturn(result);

        Optional<Student> student = studentDao.findStudentByEmail("foo@bar.com");

        Assert.that(student.isPresent(), "Student is not being returned as a result of the DB query");
        Assert.that(student.get().getFirstName()
                            .equals("Chris"), "Student first name did not match expected value");
        Assert.that(student.get().getLastName()
                            .equals("Licata"), "Student last name did not match expected value");
        Assert.that(student.get().getEmail()
                            .equals("foo@bar.com"), "Student email name did not match expected value");
        Assert.that(student.get().getPassword()
                            .equals("fyghvbjknlm"), "Student password did not match expected value");
        Assert.that(student.get().getSchoolId() == 1, "Student schoolId did not match expected value");

        Assert.that(student.get().getGpa() == 4.0, "Student GPA did not match expected value");
    }


    /**
     * Test for finding Student by email in DB when the email input is Invalid and does not exist in the DB.
     * -- expecting failure.
     *
     * @throws SQLException
     */
    @Test
    public void testFindInvalidEmail()
            throws SQLException
    {
        Optional<ResultSet> result = Optional.empty();

        when(dao.query(anyString(), anyString(), anyInt())).thenReturn(result);

        Optional<Student> actualResult = studentDao.findStudentByEmail("foo@bar.com");

        Assert.that(
                actualResult.equals(Optional.empty()),
                "Students are being returned despite invalid email input to the query");
    }


    /**
     * Test for finding all Students in DB. Not only tests for whether or not the values of the object
     * returned are the same, but also that the result set is not empty -- expecting success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindAllStudents()
            throws SQLException
    {
        String q = new StringBuilder()
                .append("SELECT fname, lname, email, password, school_id, gpa FROM users ")
                .append("WHERE email = ? AND is_student = ?")
                .toString();
        int isStudent = 1;
        Optional<ResultSet> result = resultSet.mockStudentMultiRowResultSet();
        when(dao.query(q, isStudent)).thenReturn(result);

        List<Student> students = studentDao.findAllStudents();

        //First Row of Result Set
        Assert.that(students.size() == 2, "Students are not being returned as a result of the DB query");

        Assert.that(students.get(0).getFirstName()
                            .equals("Monica"), "Student first name did not match expected value");
        Assert.that(students.get(0).getLastName()
                            .equals("Geller"), "Student last name did not match expected value");
        Assert.that(students.get(0).getEmail()
                            .equals("chefmonica@gmail.com"), "Student email name did not match expected value");
        Assert.that(students.get(0).getPassword()
                            .equals("9382hcioh8"), "Student password did not match expected value");
        Assert.that(students.get(0).getSchoolId() == 78, "Student schoolId did not match expected value");

        Assert.that(students.get(0).getGpa() == 3.1, "Student GPA did not match expected value");

        // Second Row of Result Set
        Assert.that(students.get(1).getFirstName()
                            .equals("Ross"), "Student first name did not match expected value");
        Assert.that(students.get(1).getLastName()
                            .equals("Geller"), "Student last name did not match expected value");
        Assert.that(students.get(1).getEmail()
                            .equals("dinosaurman93@yahoo.com"), "Student email name did not match expected " +
                            "value");
        Assert.that(students.get(1).getPassword()
                            .equals("f8j342nfb"), "Student password did not match expected value");
        Assert.that(students.get(1).getSchoolId() == 7, "Student schoolId did not match expected value");

        Assert.that(students.get(1).getGpa() == 4.3, "Student GPA did not match expected value");
    }
}