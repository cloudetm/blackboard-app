package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.Subject;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.CourseMySQLDao;
import com.blackboard.api.dao.impl.SchoolMySQLDao;
import com.blackboard.api.dao.impl.interfaces.CourseDao;
import com.blackboard.api.dao.impl.interfaces.SchoolDao;
import com.blackboard.api.dao.service.CourseService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class CourseDaoService
        implements CourseService
{
    private MySQLDao dao;

    final CourseDao courseDao;

    final SchoolDao schoolDao;


    public CourseDaoService(MySQLDao dao)
    {
        this.dao = dao;
        courseDao = new CourseMySQLDao(dao);
        schoolDao = new SchoolMySQLDao(dao);
    }


    /*
       * COURSE API
       */
    public List<Course> getAllInstructorCourses(String instructorEmail)
    {

        return courseDao.findCoursesByInstructor(instructorEmail);
    }


    public List<Course> getAllCoursesOffered(School school)
    {
        if (!schoolDao.findSchoolById(school.getSchoolId()).isPresent())
        {
            throw new IllegalArgumentException("No school with  could be found with the id " + school
                    .getSchoolId() + " and name " + school.getName());
        }
        return courseDao.findCoursesOffered(school);
    }


    public Optional<Course> getCourse(int courseId)
    {

        return courseDao.findCourseById(courseId);
    }


    public Optional<Course> deleteCourse(Course course)
    {
        if (!courseDao.findCourseById(course.getCourseId()).isPresent())
        {
            throw new IllegalArgumentException(
                    "No Course with the id " + course.getCourseId() + " could be found");
        }
        return courseDao.deleteCourseById(course.getCourseId());
    }


    public Course createCourse(
            School school, Instructor instructor, String courseName, Subject subject,
            int courseNumber, int credits, String syllabusFileName, int maxCapacity)
    {
        Course course = new Course(school, instructor, courseName, subject, courseNumber, credits,
                                   syllabusFileName, maxCapacity);

        return courseDao.createCourse(course.getSchool(), course);
    }

}
