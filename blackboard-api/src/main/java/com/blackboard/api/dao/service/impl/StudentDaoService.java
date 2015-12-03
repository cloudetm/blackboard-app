package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.model.Student;
import com.blackboard.api.dao.impl.CourseMySQLDao;
import com.blackboard.api.dao.impl.StudentMySQLDao;
import com.blackboard.api.dao.impl.interfaces.CourseDao;
import com.blackboard.api.dao.impl.interfaces.StudentDao;
import com.blackboard.api.dao.service.StudentService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class StudentDaoService
        implements StudentService
{
    private MySQLDao dao;

    final StudentDao studentDao;

    final CourseDao courseDao;


    public StudentDaoService(MySQLDao dao)
    {
        studentDao = new StudentMySQLDao(dao);
        courseDao = new CourseMySQLDao(dao);
        this.dao = dao;
    }


    /*
     * STUDENT API
     */
    @Override
    public Student createStudent(
            String firstName, String lastName, String email, String pw, int
            schoolId)
    {
        if (studentDao.findStudentByEmail(email).isPresent())
        {
            throw new IllegalArgumentException("User with the email " + email + " is already in the system");
        }

        return studentDao.createStudent(Student.createStudent(firstName, lastName, email, pw,
                                                              schoolId));

    }


    @Override
    public Optional<Student> getStudentAccountByEmail(String email)
    {
        return studentDao.findStudentByEmail(email);
    }


    @Override
    public List<Student> getAllStudents()
    {
        return studentDao.findAllStudents();
    }


    @Override
    public List<Student> getStudentsInCourse(int courseId)
    {

        if (!courseDao.findCourseById(courseId).isPresent())
        {
            throw new IllegalArgumentException("No course could be found with the id of " + courseId);
        }

        return studentDao.findStudentsByCourseId(courseId);
    }

}
