package com.blackboard.api.dao.impl;

import com.blackboard.api.core.Subject;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.interfaces.CourseDao;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * The MySQL implementation of Create, Retrieve, and Delete functions on the CourseDao Objects.
 * <p>
 * Created by ChristopherLicata on 11/23/15.
 */
public class CourseMySQLDao
        implements CourseDao
{

    private MySQLDao dao;


    public CourseMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    /**
     * Find courses offered at a particular institution, using the school_id to locate the record in the
     * database.
     *
     * @param school The particular institution for which we are searching courses.
     *
     * @return courses The courses offered at that particular institution
     */
    @Override
    public List<Course> findCoursesOffered(School school)
    {
        String q = "SELECT * FROM courses WHERE school_id = ?";
        try
        {
            ResultSet result = dao.query(q, school.getSchoolId()).get();

            ArrayList<Course> courses = new ArrayList<>();
            while (result.next())
            {
                int courseId = result.getInt("course_id");
                String subject = result.getString("subject");
                int courseNumber = result.getInt("course_number");
                int credits = result.getInt("credits");
                int schoolId = result.getInt("school_id");
                String instructorEmail = result.getString("instructor_email");
                String courseName = result.getString("course_name");
                String syllabusFileName = result.getString("syllabus_filename");
                int maxCapacity = result.getInt("max_capacity");

                // Build School Object
                SchoolMySQLDao schoolDao = new SchoolMySQLDao(dao);
                School schoolRes = schoolDao.findSchoolById(schoolId).get();

                // Build Instructor Object
                InstructorMySQLDao instructorDao = new InstructorMySQLDao(dao);
                Instructor instructor = instructorDao.findInstructorByEmail(instructorEmail).get();

                courses.add(new Course(courseId, schoolRes, instructor, courseName, Subject.valueOf(subject),
                                       courseNumber, credits, syllabusFileName, maxCapacity));
            }
            return courses;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }


    /**
     * Find a course offered at a particular institution, using the course_id to locate the record in the
     * database.
     *
     * @param courseId The uniquely identifying course_id of the course that is being searched
     *
     * @return course    The course that corresponds to the course_id supplied as an argument to the method
     */

    @Override
    public Optional<Course> findCourseById(int courseId)
    {
        String q = "SELECT * FROM courses WHERE course_id = ? LIMIT 1";

        return dao.query(q, courseId).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    String subject = result.getString("subject");
                    int courseNumber = result.getInt("course_number");
                    int credits = result.getInt("credits");
                    int schoolId = result.getInt("school_id");
                    String instructorEmail = result.getString("instructor_email");
                    String courseName = result.getString("course_name");
                    String syllabusFileName = result.getString("syllabus_filename");
                    int maxCapacity = result.getInt("max_capacity");

                    // Build School Object
                    SchoolMySQLDao schoolDao = new SchoolMySQLDao(dao);
                    School schoolRes = schoolDao.findSchoolById(schoolId).get();

                    // Build Instructor Object
                    InstructorMySQLDao instructorDao = new InstructorMySQLDao(dao);
                    Instructor instructor = instructorDao.findInstructorByEmail(instructorEmail).get();

                    return Optional.of(new Course(courseId, schoolRes, instructor, courseName,
                                                  Subject.valueOf(subject), courseNumber, credits,
                                                  syllabusFileName, maxCapacity));
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
     * Creates Course in database.
     *
     * @param school The school in which the class will be registered.
     * @param course The course that is being stored in the DB.
     *
     * @return student  The course model object representation of the course that was created in the DB.
     */
    @Override
    public Course createCourse(School school, Course course)
    {
        String query = new StringBuilder()
                .append("INSERT INTO courses(subject, course_number, school_id, instructor_email, course_name,")
                .append(" syllabus_filename, max_capacity, credits) VALUES")
                .append("(?, ?, ?, ?, ?, ?, ?, ?)").toString();

        String subject = course.getSubjectAsString();
        int courseNumber = course.getCourseNumber();
        int schoolId = school.getSchoolId();
        String instructorEmail = course.getInstructor().getEmail();
        String courseName = course.getCourseName();
        String syllabusFileName = course.getSyllabusFileName();
        int maxCapacity = course.getMaxCapacity();
        int credits = course.getCredits();
        Optional<ResultSet> courseId = dao.update(query, subject, courseNumber, schoolId, instructorEmail,
                                                  courseName, syllabusFileName, maxCapacity, credits);

        try
        {
            if (courseId.get().next())
            {
                course.setCourseId(courseId.get().getInt(1));
            }
        }
        catch (SQLException e)
        {
            printSQLException(e);
        }
        return course;
    }


    /**
     * Delete a course from the database, using the courseId to uniquely identify it
     *
     * @param courseId The id of the course that we are trying to delete from the database.
     *
     * @return course The course that was just deleted from the database
     */
    @Override
    public Optional<Course> deleteCourseById(int courseId)
    {
        return findCourseById(courseId).map(course -> {
            dao.update("DELETE FROM courses WHERE course_id = ?", course.getCourseId());
            return course;
        });
    }


    @Override
    public List<Course> findCoursesByInstructor(String instructorEmail)
    {
        String q = "SELECT * FROM courses WHERE instructor_email = ?";
        try
        {
            ResultSet result = dao.query(q, instructorEmail).get();

            ArrayList<Course> courses = new ArrayList<>();
            while (result.next())
            {
                int courseId = result.getInt("course_id");
                String subject = result.getString("subject");
                int courseNumber = result.getInt("course_number");
                int credits = result.getInt("credits");
                int schoolId = result.getInt("school_id");
                String courseName = result.getString("course_name");
                String syllabusFileName = result.getString("syllabus_filename");
                int maxCapacity = result.getInt("max_capacity");

                // Build School Object
                SchoolMySQLDao schoolDao = new SchoolMySQLDao(dao);
                School schoolRes = schoolDao.findSchoolById(schoolId).get();

                // Build Instructor Object
                InstructorMySQLDao instructorDao = new InstructorMySQLDao(dao);
                Instructor instructor = instructorDao.findInstructorByEmail(instructorEmail).get();

                courses.add(new Course(courseId, schoolRes, instructor, courseName, Subject.valueOf(subject),
                                       courseNumber, credits, syllabusFileName, maxCapacity));
            }
            return courses;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }
}

