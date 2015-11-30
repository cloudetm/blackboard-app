package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Student;
import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.SQLException;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * The MySQL implementation of Retrieve, Update and Delete functions on the UserDao Objects.
 * <p/>
 * Created by ChristopherLicata on 11/19/15.
 */
public class UserMySQLDao
        implements UserDao
{
    private MySQLDao dao;


    public UserMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    /**
     * Finds individual User in database whose email matches that of the argument passed to the WHERE clause
     * of the MySQL query.
     *
     * @param userEmail The email of the user for whom it is searching.
     *
     * @return new User(...) the User found in the database
     */
    @Override
    public Optional<User> findUserByEmail(String userEmail)
    {
        String q = "SELECT * FROM users WHERE email = ? LIMIT 1";
        return dao.query(q, userEmail).flatMap(r -> {
            try
            {
                if (r.next())
                {
                    int userId = r.getInt("userID");
                    String email = r.getString("email");
                    String fname = r.getString("fname");
                    String lname = r.getString("lname");
                    String encryptedPassword = r.getString("password");
                    int schoolId = r.getInt("school_id");

                    return Optional.of(new User(userId, fname, lname, email, encryptedPassword, schoolId));
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
     * Update the data for one users in the database.
     *
     * @param user User model object, whose properties will be used to update the data in the database
     */
    @Override
    public User updateUser(User user)
    {
        String query = "UPDATE users SET fname=?, lname=?, password=?, school_id=?, gpa=? WHERE email = ?";
        String fname = user.getFirstName();
        String lname = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        int schoolId = user.getSchoolId();

        if (user instanceof Student)
        {
            double gpa = ((Student) user).getGpa();
            dao.update(query, fname, lname, password, schoolId, gpa, email);
        }
        else
        {
            dao.update(query, fname, lname, password, schoolId, null, email);
        }

        return user;

    }


    /**
     * Delete a user from the database, using the user's email to uniquely identify him or her
     *
     * @param email The email of the user that we are trying to delete from the database.
     *
     * @return user The user that was just deleted from the database
     */

    @Override
    public Optional<User> deleteUser(String email)
    {
        return findUserByEmail(email).map(user -> {
            dao.update("DELETE FROM users WHERE email=?", user.getEmail());
            return user;
        });

    }
}
