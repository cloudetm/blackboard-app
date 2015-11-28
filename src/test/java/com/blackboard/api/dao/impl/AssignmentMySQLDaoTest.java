package com.blackboard.api.dao.impl;

import com.blackboard.api.core.Subject;
import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.util.ResultSetMocker;
import com.blackboard.api.dao.util.MySQLDao;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Created by ChristopherLicata on 11/25/15.
 */
public class AssignmentMySQLDaoTest
{
    private MySQLDao dao;

    private ResultSetMocker resultSet;

    private AssignmentMySQLDao assignmentDao;

    private java.sql.Date assignedDate;

    private java.sql.Date dueDate;


    @Before
    public void setUp()
            throws ParseException
    {
        dao = mock(MySQLDao.class);
        resultSet = new ResultSetMocker();
        assignmentDao = new AssignmentMySQLDao(dao);

        // Assigned Date Handling
        String assignedDateString = "2014-01-28";
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(assignedDateString);
        // because PreparedStatement#setDate(..) expects a java.sql.Date argument
        assignedDate = new java.sql.Date(utilDate.getTime());

        //Due Date Handling
        String dueDateString = "2014-01-31";
        java.util.Date javaDate = new SimpleDateFormat("yyyy-MM-dd").parse(dueDateString);
        // because PreparedStatement#setDate(..) expects a java.sql.Date argument
        dueDate = new java.sql.Date(javaDate.getTime());
    }


    @Test
    public void testCreateAssignment()
            throws SQLException, ParseException
    {
        String query = new StringBuilder()
                .append("INSERT INTO assignments(course_id, assigned_date, due_date, ")
                .append("assignment_filename, assignment_name,totalPoints, instructions")
                .append("(?, ?, ?, ?, ?, ?, ?)").toString();

        // Mock Course Object Handling
        Course mockCourse = mock(Course.class);
        when(mockCourse.getCourseId()).thenReturn(1);

        Optional<ResultSet> assignmentId = resultSet.mockAutoGenerateKeyResultSet(23);
        Assignment assignment = mock(Assignment.class);
        when(assignment.getCourse()).thenReturn(mockCourse);
        when(assignment.getAssignmentName()).thenReturn("Zombies Assignment");
        when(assignment.getAssignmentFileName()).thenReturn("zombies.jar");
        when(assignment.getDateAssigned()).thenReturn(assignedDate);
        when(assignment.getDueDate()).thenReturn(dueDate);
        when(assignment.getTotalPoints()).thenReturn(100);
        when(assignment.getInstructions()).thenReturn("You are all going to fail this test");
        when(assignmentId.get().getInt(1)).thenReturn(23);

        when(dao.update(query, 1, assignedDate, dueDate, "zombies.jar", "Zombies Assignment", 100,
                        "You are all going to fail this test")).thenReturn(assignmentId);

        assignmentDao.createAssignment(assignment);
        verify(dao).update(query, 1, assignedDate, dueDate, "zombies.jar", "Zombies Assignment", 100,
                           "You are all going to fail this test");
    }


    @Test
    public void testFindAssignmentById()
            throws SQLException, ParseException
    {
        String assignmentsQuery = "SELECT * FROM assignments WHERE assignment_id = ? LIMIT 1";

        // School Argument Mock Call Handler
        School mockSchool = mock(School.class);
        when(mockSchool.getSchoolId()).thenReturn(1);

        // Schools Mock Call Handler
        String schoolsQuery = "SELECT * FROM schools WHERE school_id = ?";

        ResultSet schoolResult = resultSet.mockSchoolResultSet(1, "University of California - Los Angeles")
                .get();

        when(schoolResult.next()).thenReturn(true).thenReturn(false);
        when(dao.query(schoolsQuery, 1)).thenReturn(Optional.of(schoolResult));

        //Instructor Mock DB Call Handling
        String instructorQuery = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();

        Optional<ResultSet> instructorOneResult = resultSet.mockInstructorResultSet(1, "Jim", "Bean",
                                                                                    "jordip@gmu.edu",
                                                                                    "iond3t83h", 1);
        when(dao.query(instructorQuery, "jordip@gmu.edu", 0)).thenReturn(instructorOneResult);

        String coursesQuery = "SELECT * FROM courses WHERE course_id = ? LIMIT 1";

        Optional<ResultSet> courseResult = resultSet.mockCourseResultSet(123, Subject.ENGL, 3456, 1,
                                                                         "jordip@gmu.edu",
                                                                         "Foundations of Literature",
                                                                         "FoLsyllabus.pdf", 69, 3);
        when(courseResult.get().next()).thenReturn(true).thenReturn(false);
        when(dao.query(coursesQuery, 123)).thenReturn(courseResult);

        Optional<ResultSet> assignmentResult = resultSet.mockAssignmentResultSet(13, 123,
                                                                                 "This is going to be good",
                                                                                 "Tough Assignment",
                                                                                 "tassignment.pdf",
                                                                                 assignedDate,
                                                                                 dueDate,
                                                                                 100);

        when(dao.query(assignmentsQuery, 13)).thenReturn(assignmentResult);

        Optional<Assignment> assignment = assignmentDao.findAssignmentById(13);

        Assert.that(assignment.isPresent(), "Assignment returned isPresent()");

        Assert.that(assignment.get().getAssignmentId() == 13, "Assignment id did not match expected value");

        Assert.that(
                assignment.get().getCourse().getCourseId() == 123, "Course id did not match expected value");

        Assert.that(
                assignment.get().getDueDate().equals(dueDate),
                "Assignment dueDate " + assignment.get().getDueDate().toString() +
                        " did not match expected value");

        Assert.that(
                assignment.get().getDateAssigned().equals(assignedDate),
                "Assignment date assigned did not match expected value");

        Assert.that(
                assignment.get().getInstructions().equals("This is going to be good"),
                "Assignment instructions did not match expected value");

        Assert.that(
                assignment.get().getAssignmentName().equals("Tough Assignment"),
                "Assignment name did not match expected value");

        Assert.that(
                assignment.get().getAssignmentFileName().equals("tassignment.pdf"),
                "Assignment filename did not match expected value");
    }


    @Test
    public void testDeleteAssignmentById()
            throws SQLException, ParseException
    {
        String assignmentsQuery = "SELECT * FROM assignments WHERE assignment_id = ? LIMIT 1";
        String assignmentsDeleteQuery = "DELETE FROM assignments WHERE assignment_id = ?";

        // School Argument Mock Call Handler
        School mockSchool = mock(School.class);
        when(mockSchool.getSchoolId()).thenReturn(1);

        // Schools Mock Call Handler
        String schoolsQuery = "SELECT * FROM schools WHERE school_id = ?";

        ResultSet schoolResult = resultSet.mockSchoolResultSet(1, "University of California - Los Angeles")
                .get();

        when(schoolResult.next()).thenReturn(true).thenReturn(false);
        when(dao.query(schoolsQuery, 1)).thenReturn(Optional.of(schoolResult));

        //Instructor Mock DB Call Handling
        String instructorQuery = new StringBuilder()
                .append("SELECT userID, fname, lname, email, password, school_id FROM users ")
                .append("WHERE email = ? AND is_student = ? LIMIT 1")
                .toString();

        Optional<ResultSet> instructorOneResult = resultSet.mockInstructorResultSet(1, "Jim", "Bean",
                                                                                    "jordip@gmu.edu",
                                                                                    "iond3t83h", 1);
        when(dao.query(instructorQuery, "jordip@gmu.edu", 0)).thenReturn(instructorOneResult);

        String coursesQuery = "SELECT * FROM courses WHERE course_id = ? LIMIT 1";

        Optional<ResultSet> courseResult = resultSet.mockCourseResultSet(123, Subject.ENGL, 3456, 1,
                                                                         "jordip@gmu.edu",
                                                                         "Foundations of Literature",
                                                                         "FoLsyllabus.pdf", 69, 3);
        when(courseResult.get().next()).thenReturn(true).thenReturn(false);
        when(dao.query(coursesQuery, 123)).thenReturn(courseResult);

        Optional<ResultSet> assignmentResult = resultSet.mockAssignmentResultSet(13, 123,
                                                                                 "This is going to be good",
                                                                                 "Tough Assignment",
                                                                                 "tassignment.pdf",
                                                                                 assignedDate,
                                                                                 dueDate,
                                                                                 100);

        when(dao.query(assignmentsQuery, 13)).thenReturn(assignmentResult);

        assignmentDao.deleteAssignmentById(13);

        verify(dao).update(assignmentsDeleteQuery, 13);

    }


    @Test
    public void testUpdateAssignment()
            throws SQLException
    {
        String query = new StringBuilder("UPDATE assignments SET course_id = ?, assigned_date = ?, due_date = ?")
                .append(", assignment_filename = ?, total_points = ?, instructions = ?, assignment_name = ?, ")
                .append("WHERE assignment_id = ?")
                .toString();

        // Mock Course Object Handling
        Course mockCourse = mock(Course.class);
        when(mockCourse.getCourseId()).thenReturn(1);

        Assignment assignment = new Assignment(12, mockCourse, "test1", "t1.doc", "Do this!", 100, assignedDate,
                                               dueDate);
        assignmentDao.updateAssignment(assignment);
        verify(dao).update(query, assignment.getCourse().getCourseId(), assignment.getDateAssigned(),
                           assignment.getDueDate(), assignment.getAssignmentFileName(), assignment
                                   .getTotalPoints(), assignment.getInstructions(), assignment
                                   .getAssignmentName(), assignment.getAssignmentId());
    }


    @Test
    public void testFindAllAssignmentsByCourseId()
            throws Exception
    {

    }
}