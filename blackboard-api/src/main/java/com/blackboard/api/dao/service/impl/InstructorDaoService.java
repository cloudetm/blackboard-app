package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.dao.impl.InstructorMySQLDao;
import com.blackboard.api.dao.impl.interfaces.InstructorDao;
import com.blackboard.api.dao.service.InstructorService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class InstructorDaoService
        implements InstructorService
{
    private MySQLDao dao;

    final InstructorDao instructorDao;


    public InstructorDaoService(MySQLDao dao)
    {
        this.dao = dao;
        instructorDao = new InstructorMySQLDao(dao);
    }


    @Override
    public List<Instructor> getAllInstructors()
    {
        return instructorDao.findAllInstructors();
    }


    @Override
    public Optional<Instructor> getInstructorAccountByEmail(String instructorEmail)
    {
        return instructorDao.findInstructorByEmail(instructorEmail);
    }


    @Override
    public Instructor createInstructor(
            String firstName, String lastName, String email, String pw, int
            schoolId)
    {
        if (instructorDao.findInstructorByEmail(email).isPresent())
        {
            throw new IllegalArgumentException("User with the email " + email + " is already in the system");
        }

        return instructorDao.createInstructor(Instructor.createInstructor(firstName, lastName, email, pw,
                                                                          schoolId));
    }

}
