package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.dao.InstructorDao;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * The MySQL implementation of Create and Retrieve functions on the InstructorDao Objects.
 * <p/>
 * Created by ChristopherLicata on 11/22/15.
 */
public class InstructorMySQLDao
        implements InstructorDao
{
    private MySQLDao dao;


    public InstructorMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    /**
     * Creates Instructor in database without the Student's gpa.
     *
     * @param instructor The instructor object that will be created in the DB
     *
     * @return instructor  The model object representation of instructor that was created in the DB
     */
    @Override
    public Instructor createInstructor(Instructor instructor)
    {
        String query = new StringBuilder()
                .append("INSERT INTO users(fname, lname, email, password, school_id, is_student) VALUES")
                .append("(?, ?, ?, ?, ?, ?)").toString();
        String fname = instructor.getFirstName();
        String lname = instructor.getLastName();
        String email = instructor.getEmail();
        String pw = instructor.getPassword();
        int school_id = instructor.getSchoolId();
        Optional<ResultSet> instructorId = dao.update(query, fname, lname, email, pw, school_id, 0);
        try
        {
            // Assign the row id to the assignmentId for easy access
            instructor.setUserId(instructorId.get().getInt(1));
            // Closing result set for good form.
            instructorId.get().close();

        }
        catch (SQLException e)
        {
            printSQLException(e);
        }

        return instructor;
    }


    /**
     * Finds individual Instructor in database whose email matches that of the argument passed to the WHERE
     * clause of the MySQL query.
     *
     * @param instructorEmail The email of the instructor for whom it is searching.
     *
     * @return new Instructor(...) The model object representation of the instructor that was found in the DB
     */
    @Override
    public Optional<Instructor> findInstructorByEmail(String instructorEmail)
    {
        String q = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();
        return dao.query(q, instructorEmail, 0).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    int userId = result.getInt("userID");
                    String fname = result.getString("fname");
                    String lname = result.getString("lname");
                    String email = result.getString("email");
                    String pw = result.getString("password");
                    int schoolId = result.getInt("school_id");
                    return Optional.of(new Instructor(userId, fname, lname, email, pw, schoolId));
                }
                else
                    return Optional.empty();
            }
            catch (SQLException e)
            {
                printSQLException(e);
                return Optional.empty();
            }
        });
    }


    /**
     * Find all Instructors for a particular school in the database.
     *
     * @return instructors  List of instructors that are present in the users table of the database.
     */
    @Override
    public List<Instructor> findAllInstructors()
    {
        String q = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE is_student = ?")
                .toString();
        try
        {
            ResultSet result = dao.query(q, 0).get();

            ArrayList<Instructor> instructors = new ArrayList<>();
            while (result.next())
            {
                int userId = result.getInt("userID");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String email = result.getString("email");
                String pw = result.getString("password");
                int schoolId = result.getInt("school_id");
                instructors.add(new Instructor(userId, fname, lname, email, pw, schoolId));
            }
            return instructors;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }
}

