package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.Student;
import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.impl.util.ResultSetMocker;
import com.blackboard.api.dao.util.MySQLDao;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Test class for the MySQL-based {@link UserMySQLDao} Operations
 * <p>
 * Created by ChristopherLicata on 11/22/15.
 */
public class UserMySQLDaoTest
{
    private UserMySQLDao userDao;

    private MySQLDao dao;

    private ResultSetMocker resultSet;


    @Before
    public void setUp()
            throws Exception
    {
        dao = mock(MySQLDao.class);
        userDao = new UserMySQLDao(dao);
        resultSet = new ResultSetMocker();

    }


    /**
     * Test for finding User by email in DB when the email input is valid, exists in the DB, and the Entiry it
     * is associated with is an Instructor. Not only tests for whether or not the values of the object
     * returned are the same, but also that the result set is not empty -- expecting success.
     *
     * @throws Exception
     */
    @Test
    public void testFindUserByEmailInstructor()
            throws Exception
    {
        String q = "SELECT * FROM users WHERE email = ? LIMIT 1";
        String email = "fooman@bar.com";
        int isStudent = 0;

        Optional<ResultSet> result = resultSet.mockInstructorResultSet(1, "Gregory", "Manchester",
                                                                       "fooman@bar.com",
                                                                       "234567jfe", 12);

        when(dao.query(q, email)).thenReturn(result);

        Optional<User> instructor = userDao.findUserByEmail("fooman@bar.com");

        Assert.that(instructor.isPresent(), "Instructor is not being returned as a result of the DB query");

        Assert.that(instructor.get().getUserId() == 1, "Instructor userID did not match expected value");
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
     * Test for finding User by email in DB when the email input is valid, exists in the DB, and the Entity it
     * is associated with is a Student. Not only tests for whether or not the values of the object returned
     * are the same, but also that the result set is not empty -- expecting success.
     *
     * @throws Exception
     */
    @Test
    public void testFindUserByEmailStudent()
            throws Exception
    {

        String q = "SELECT * FROM users WHERE email = ? LIMIT 1";

        Optional<ResultSet> result = resultSet.mockStudentResultSet(1, "Chris", "Licata", "foo@bar.com",
                                                                    "fyghvbjknlm", 1, 4.0);
        when(dao.query(q, "foo@bar.com")).thenReturn(result);

        Optional<User> student = userDao.findUserByEmail("foo@bar.com");

        Assert.that(student.get().getUserId() == 1, "Student userID did not match expected value");
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

        //        Assert.that(((Student)student.get()).getGpa() == 4.0, "Student GPA did not match expected value");
    }


    /**
     * Testing for correct parameters in call to updateUser() -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateUserStudent()
            throws SQLException
    {
        String q = "UPDATE users SET fname=?, lname=?, password=?, school_id=?, gpa=? WHERE email = ?";
        Student student = Student.createStudent("Charlie", "Murphey", "cmurphey@gmail.com", "hq239gfh23", 123,
                                                4.0);
        userDao.updateUser(student);
        verify(dao).update(q, student.getFirstName(), student.getLastName(), student
                .getPassword(), student.getSchoolId(), student.getGpa(), student.getEmail());
    }


    /**
     * Testing for correct parameters in call to updateUser() -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testUpdateUserInstructor()
            throws SQLException
    {
        String q = "UPDATE users SET fname=?, lname=?, password=?, school_id=?, gpa=? WHERE email = ?";
        Instructor instructor = Instructor.createInstructor("Charlie", "Runkle", "crunkle@gmail.com",
                                                            "b789ov4", 48);
        userDao.updateUser(instructor);
        verify(dao).update(q, instructor.getFirstName(), instructor.getLastName(), instructor
                .getPassword(), instructor.getSchoolId(), null, instructor.getEmail());
    }


    /**
     * Testing for correct parameters in call to deleteUser() -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testDeleteUserStudent()
            throws SQLException
    {
        String selectQ = "SELECT * FROM users WHERE email = ? LIMIT 1";
        String deleteQ = "DELETE FROM users WHERE email=?";

        String email = "bobbylight@gmail.com";
        Optional<ResultSet> result = resultSet
                .mockStudentResultSet(1, "Robbie", "Thomson", "bobbylight@gmail.com",
                                      "fyghvbjknlm", 1, 4.0);
        when(dao.query(selectQ, email)).thenReturn(
                (result));
        userDao.deleteUser(email);
        verify(dao).update(deleteQ, email);
    }


    /**
     * Testing for correct parameters in call to deleteUser() -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testDeleteUserInstructor()
            throws SQLException
    {
        String selectQ = "SELECT * FROM users WHERE email = ? LIMIT 1";
        String deleteQ = "DELETE FROM users WHERE email=?";
        String email = "boobookitty@gmail.com";
        Optional<ResultSet> result = resultSet.mockInstructorResultSet(1, "Richard", "McCarther",
                                                                       "boobookitty@gmail.com",
                                                                       "bc85bpqa23v", 1);
        when(dao.query(selectQ, email)).thenReturn(
                (result));
        userDao.deleteUser(email);
        verify(dao).update(deleteQ, email);
    }
}