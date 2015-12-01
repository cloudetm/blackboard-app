package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.util.ResultSetMocker;
import com.blackboard.api.dao.util.MySQLDao;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.core.Subject.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the MySQL-based {@link CourseMySQLDao} Operations
 * <p>
 * Created by ChristopherLicata on 11/24/15.
 */
public class CourseMySQLDaoTest
{
    private MySQLDao dao;

    private ResultSetMocker resultSet;

    private CourseMySQLDao courseDao;


    @Before
    public void setUp()
    {
        dao = mock(MySQLDao.class);
        resultSet = new ResultSetMocker();
        courseDao = new CourseMySQLDao(dao);
    }


    /**
     * Test for finding all courses offered by a particular school in DB. Not only tests for whether or not
     * the values of the object returned are the same, but also that the result set is not empty -- expecting
     * success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindSchoolCourses()
            throws SQLException
    {
        String coursesQuery = "SELECT * FROM courses WHERE school_id = ?";

        // School Argument Mock Call Handler
        School mockSchool = mock(School.class);
        when(mockSchool.getSchoolId()).thenReturn(14);

        // Schools Mock Call Handler
        String schoolsQuery = "SELECT * FROM schools WHERE school_id = ?";
        ResultSet schoolResult = resultSet.mockSchoolResultSet(14, "University of California - Los Angeles")
                .get();
        when(schoolResult.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(dao.query(schoolsQuery, 14)).thenReturn(Optional.of(schoolResult)).thenReturn
                (Optional.of(schoolResult));

        //Instructor Mock DB Call Handling
        String instructorQuery = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();

        Optional<ResultSet> instructorOneResult = resultSet.mockInstructorResultSet(1, "Jim", "Bean",
                                                                                    "jsuarez@gwu.edu",
                                                                                    "iond3t83h", 14);

        Optional<ResultSet> instructorTwoResult = resultSet.mockInstructorResultSet(2, "Jake", "Oberman",
                                                                                    "sudoman@caltech.edu",
                                                                                    "vb2389bv", 14);

        when(dao.query(instructorQuery, "jsuarez@gwu.edu", 0)).thenReturn(instructorOneResult);
        when(dao.query(instructorQuery, "sudoman@caltech.edu", 0)).thenReturn(instructorTwoResult);

        Optional<ResultSet> result = resultSet.mockCoursesMultiRowResultSet();
        when(dao.query(coursesQuery, 14)).thenReturn(result);

        List<Course> courses = courseDao.findCoursesOffered(mockSchool);

        // Unit Tests for First Row of Result Set
        Assert.that(
                courses.size() == 2, "Courses are not being returned as a result of the DB query");

        Assert.that(courses.get(0).getCourseId() == 34, "Course id did not match expected value");

        Assert.that(courses.get(0).getSubjectAsString()
                            .equals(MATH.toString()), "Course subject did not match expected value");

        Assert.that(
                courses.get(0).getCourseNumber() == 1001,
                "Course number did not match expected value");

        Assert.that(
                courses.get(0).getSchool().getSchoolId() == 14,
                "Course school id did not match expected value");

        Assert.that(
                courses.get(0).getInstructor().getEmail().equals("jsuarez@gwu.edu"),
                "Course instructor email did not match expected value");

        Assert.that(
                courses.get(0).getCourseName().equals("Calculus I"),
                "Course name did not match expected value");

        Assert.that(
                courses.get(0).getSyllabusFileName().equals("calc1_FALL_2015.docx"),
                "Course syllabus filename did not match expected value");

        Assert.that(
                courses.get(0).getMaxCapacity() == 101, "Course max capacity did not match expected value");

        Assert.that(courses.get(0).getCredits() == 3, "Course credits did not match expected value");

        // Unit Tests for Second Row of Result Set

        Assert.that(courses.get(1).getCourseId() == 198, "Course id did not match expected value");

        Assert.that(courses.get(1).getSubjectAsString()
                            .equals(CSCI.toString()), "Course subject did not match expected value");

        Assert.that(
                courses.get(1).getCourseNumber() == 2113,
                "Course number did not match expected value");

        Assert.that(
                courses.get(1).getSchool().getSchoolId() == 14,
                "Course school id did not match expected value");

        Assert.that(
                courses.get(1).getInstructor().getEmail().equals("sudoman@caltech.edu"),
                "Course instructor email did not match expected value");

        Assert.that(
                courses.get(1).getCourseName().equals("Software Engineering"),
                "Course name did not match expected value");

        Assert.that(
                courses.get(1).getSyllabusFileName().equals("software_engineering_FALL_2015.docx"),
                "Course syllabus filename did not match expected value");

        Assert.that(
                courses.get(1).getMaxCapacity() == 45, "Course max capacity did not match expected value");

        Assert.that(courses.get(1).getCredits() == 3, "Course credits did not match expected value");
    }


    /**
     * Testing for correct parameters in call to {@link CourseMySQLDao} -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindCourseByIdParameters()
            throws SQLException
    {
        String coursesQuery = "SELECT * FROM courses WHERE course_id = ? LIMIT 1";

        // School Argument Mock Call Handler
        School mockSchool = mock(School.class);
        when(mockSchool.getSchoolId()).thenReturn(123);

        // Schools Mock Call Handler
        String schoolsQuery = "SELECT * FROM schools WHERE school_id = ?";
        ResultSet schoolResult = resultSet.mockSchoolResultSet(123, "University of California - Los Angeles")
                .get();
        when(schoolResult.next()).thenReturn(true).thenReturn(false);
        when(dao.query(schoolsQuery, 123)).thenReturn(Optional.of(schoolResult));

        //Instructor Mock DB Call Handling
        String instructorQuery = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();

        Optional<ResultSet> instructorOneResult = resultSet.mockInstructorResultSet(1, "Jim", "Bean",
                                                                                    "jsuarez@gwu.edu",
                                                                                    "iond3t83h", 14);
        when(dao.query(instructorQuery, "jsuarez@gwu.edu", 0)).thenReturn(instructorOneResult);

        Optional<ResultSet> result = resultSet.mockCourseResultSet(14, CSCI, 3534, 123,
                                                                   "jsuarez@gwu.edu", "Software Testing", "gwu.docx", 35, 3);
        when(dao.query(coursesQuery, 14)).thenReturn(result);

        Optional<Course> course = courseDao.findCourseById(14);
        verify(dao).query(coursesQuery, 14);

    }


    /**
     * Test for finding Course by id in DB when the id input is valid and exists in the DB. Not only tests for
     * whether or not the values of the object returned are the same, but also that the result set is not
     * empty -- expecting success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindCourseByValidId()
            throws SQLException
    {
        String coursesQuery = "SELECT * FROM courses WHERE course_id = ? LIMIT 1";

        // School Argument Mock Call Handler
        School mockSchool = mock(School.class);
        when(mockSchool.getSchoolId()).thenReturn(123);

        // Schools Mock Call Handler
        String schoolsQuery = "SELECT * FROM schools WHERE school_id = ?";
        ResultSet schoolResult = resultSet.mockSchoolResultSet(123, "University of California - Los Angeles")
                .get();
        when(schoolResult.next()).thenReturn(true).thenReturn(false);
        when(dao.query(schoolsQuery, 123)).thenReturn(Optional.of(schoolResult));

        //Instructor Mock DB Call Handling
        String instructorQuery = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();

        Optional<ResultSet> instructorOneResult = resultSet.mockInstructorResultSet(1, "Jim", "Bean",
                                                                                    "jsuarez@gwu.edu",
                                                                                    "iond3t83h", 14);
        when(dao.query(instructorQuery, "jsuarez@gwu.edu", 0)).thenReturn(instructorOneResult);

        Optional<ResultSet> result = resultSet.mockCourseResultSet(14, CSCI, 3534, 123,
                                                                   "jsuarez@gwu.edu", "Software Testing", "gwu.docx", 35, 3);
        when(dao.query(coursesQuery, 14)).thenReturn(result);

        Optional<Course> course = courseDao.findCourseById(14);

        Assert.that(course.isPresent(), "Course returned isPresent()");

        Assert.that(course.get().getCourseId() == 14, "Course id did not match expected value");

        Assert.that(course.get().getSubjectAsString()
                            .equals(CSCI.toString()), "Course subject did not match expected value");

        Assert.that(
                course.get().getCourseNumber() == 3534,
                "Course number did not match expected value");

        Assert.that(
                course.get().getSchool().getSchoolId() == 123,
                "Course school id did not match expected value");

        Assert.that(
                course.get().getInstructor().getEmail().equals("jsuarez@gwu.edu"),
                "Course instructor email did not match expected value");

        Assert.that(
                course.get().getCourseName().equals("Software Testing"),
                "Course name did not match expected value");

        Assert.that(
                course.get().getSyllabusFileName().equals("gwu.docx"),
                "Course syllabus filename did not match expected value");

        Assert.that(
                course.get().getMaxCapacity() == 35, "Course max capacity did not match expected value");

        Assert.that(course.get().getCredits() == 3, "Course credits did not match expected value");

    }


    /**
     * Testing for the creation of Courses -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testCreateCourse()
            throws SQLException
    {
        String query = new StringBuilder()
                .append("INSERT INTO courses(subject, course_number, school_id, instructor_email, course_name,")
                .append(" syllabus_filename, max_capacity, credits) VALUES")
                .append("(?, ?, ?, ?, ?, ?, ?, ?)").toString();

        // School Argument Mock Call Handler
        School mockSchool = mock(School.class);
        when(mockSchool.getSchoolId()).thenReturn(14);

        // Instructor Argument Mock Call Handler
        Instructor mockInstructor = mock(Instructor.class);
        when(mockInstructor.getEmail()).thenReturn("googler@gmail.com");

        Course mockCourse = mock(Course.class);
        when(mockCourse.getCourseId()).thenReturn(1);
        when(mockCourse.getInstructor()).thenReturn(mockInstructor);
        when(mockCourse.getInstructor().getEmail()).thenReturn("googler@gmail.com");
        when(mockCourse.getCourseName()).thenReturn("AWS Studies");
        when(mockCourse.getSubjectAsString()).thenReturn("MATH");
        when(mockCourse.getCourseNumber()).thenReturn(3334);
        when(mockCourse.getCredits()).thenReturn(3);
        when(mockCourse.getSyllabusFileName()).thenReturn("aws_rtfm_guide.pdf");
        when(mockCourse.getMaxCapacity()).thenReturn(49);

        Optional<ResultSet> courseId = resultSet.mockAutoGenerateKeyResultSet(1);

        when(dao.update(query, "MATH", 3334, 14, "googler@gmail.com", "AWS Studies",
                        "aws_rtfm_guide.pdf", 49, 3)).thenReturn(courseId);
        when(courseId.get().next()).thenReturn(true);
        courseDao.createCourse(mockSchool, mockCourse);

        verify(dao).update(query, "MATH", 3334, 14, "googler@gmail.com", "AWS Studies",
                           "aws_rtfm_guide.pdf", 49, 3);

    }


    /**
     * Testing for correct parameters in call to deleteCourseById() -- expecting Success.
     *
     * @throws SQLException
     */
    @Test
    public void testDeleteCourseById()
            throws Exception
    {
        String selectQuery = "SELECT * FROM courses WHERE course_id = ? LIMIT 1";
        String deleteQuery = "DELETE FROM courses WHERE course_id = ?";

        int courseId = 1538;
        Optional<ResultSet> result = resultSet.mockCourseResultSet(1538, ACCT, 2358, 2,
                                                                   "bobbylight@gmail.com",
                                                                   "Advanced Accounting", "advancedacct.docx",
                                                                   67, 4);
        // Schools Mock Call Handler
        String schoolsQuery = "SELECT * FROM schools WHERE school_id = ?";

        Optional<ResultSet> schoolResult = resultSet
                .mockSchoolResultSet(2, "University of California - Los Angeles");

        when(dao.query(schoolsQuery, 2)).thenReturn(schoolResult);

        //Instructor Mock DB Call Handling
        String instructorQuery = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();
        Optional<ResultSet> instructorOneResult = resultSet.mockInstructorResultSet(1, "Jim", "Bean",
                                                                                    "bobbylight@gmail.com",
                                                                                    "iond3t83h", 14);

        when(dao.query(instructorQuery, "bobbylight@gmail.com", 0)).thenReturn(instructorOneResult);

        when(dao.query(selectQuery, courseId)).thenReturn(result);
        courseDao.deleteCourseById(courseId);
        verify(dao).update(deleteQuery, courseId);
    }
}
