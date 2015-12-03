package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.Student;
import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.impl.UserMySQLDao;
import com.blackboard.api.dao.impl.interfaces.UserDao;
import com.blackboard.api.dao.service.UserService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class UserDaoService
        implements UserService
{

    private MySQLDao dao;

    final UserDao userDao;


    public UserDaoService(MySQLDao dao)
    {
        this.dao = dao;
        userDao = new UserMySQLDao(dao);
    }


    @Override
    public User updateInstructorAccount(
            String firstName, String lastName, String email, String password,
            int schoolId)
    {

        User user = Instructor.createInstructor(firstName, lastName, email, password, schoolId);
        return userDao.updateUser(user);
    }


    @Override
    public User updateStudentAccount(
            String firstName, String lastName, String email, String password,
            double gpa, int schoolId)
    {

        User user = Student.createStudent(firstName, lastName, email, password, schoolId, gpa);
        return userDao.updateUser(user);
    }


    @Override
    public Optional<User> deleteUserAccount(String userEmail)
    {
        return userDao.deleteUser(userEmail);
    }


    @Override
    public Optional<User> getUser(String email)
    {
        return userDao.findUserByEmail(email);
    }

}
