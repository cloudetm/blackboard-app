package com.blackboard.api.dao.impl;

import com.blackboard.api.core.Subject;
import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.util.ResultSetMocker;
import com.blackboard.api.dao.util.MySQLDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Created by ChristopherLicata on 11/25/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssignmentMySQLDaoTest {
    @Mock
    private MySQLDao dao;

    @Mock
    private Course mockCourse;

    private ResultSetMocker resultSet;

    private AssignmentMySQLDao assignmentDao;

    private java.sql.Date assignedDate;

    private java.sql.Date dueDate;

    private Date createSQLDate(String dateString) throws ParseException {
        return new Date(new SimpleDateFormat("yyy-MM-dd").parse(dateString).getTime());
    }


    @Before
    public void setUp()
            throws ParseException {
        resultSet = new ResultSetMocker();
        assignmentDao = new AssignmentMySQLDao(dao);

        // Assigned / Due Date Handling
        // because PreparedStatement#setDate(..) expects a java.sql.Date argument
        assignedDate = createSQLDate("2014-01-28");
        dueDate = createSQLDate("2014-01-31");
    }


    @Test
    public void testCreateAssignment()
            throws SQLException, ParseException {
        String query = new StringBuilder()
                .append("INSERT INTO assignments(course_id, assigned_date, due_date, ")
                .append("assignment_filename, assignment_name,totalPoints, instructions")
                .append("(?, ?, ?, ?, ?, ?, ?)").toString();

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
    public void testFindAssignmentByIDNoInternalDBCalls() throws SQLException {
        // Note that `findCourseById` is tested elsewhere
        // Note that `findSchoolByID` is tested elsewhere
        // Note that `findInstructorByEmail` is tested elsewhere
        // So we are concentrating on the assignment lookup logic only,
        //  presuming that the rest of the logic is correct

        /* ========================================================================
                Test mocking setup begins
           ======================================================================== */
        // we need to mock just one method on our assignmentMySQLDao
        // a newly added getNewCourseMySQLDao(MySQLDao dao) one, that creates
        // a courseDao for us. Separating it in a method might be good, as now
        // we can
        //      a) mock it, to encapsulate the testing logic
        //      b) refactor it later on not to create a new CourseMySQLDao every time a serach is invoked
        AssignmentMySQLDao spiedAssignmentDao = spy(assignmentDao);

        // used values have verbose names now. Just easier to read the code.
        int courseID = 123;
        int assignmentID = 13;
        String instructorsString = "This is going to be good";
        String assignmentName = "Tough Assignment";
        String assignmentFileName = "tassignment.pdf";
        int totalPoints = 100;


        // Mocking course value, that is searched for is tested method explicitly
        Course mockCourse = mock(Course.class);
        when(mockCourse.getCourseId()).thenReturn(courseID);
        // Mocking CourseMySQLDao for logic separation described above
        // Now nothing in the findCourseByID can stop us!
        CourseMySQLDao mockCourseMySQLDao = mock(CourseMySQLDao.class);
        when(mockCourseMySQLDao.findCourseById(courseID)).thenReturn(Optional.of(mockCourse));

        String assignmentsQuery = "SELECT * FROM assignments WHERE assignment_id = ? LIMIT 1";
        Optional<ResultSet> assignmentResult = resultSet.mockAssignmentResultSet(assignmentID, courseID,
                instructorsString,
                assignmentName,
                assignmentFileName,
                assignedDate,
                dueDate,
                totalPoints);
        when(dao.query(assignmentsQuery, assignmentID)).thenReturn(assignmentResult);

        // now the logic of course lookup can be mocked by substituting real CourseMySQLDao with our mocked one
        when(spiedAssignmentDao.getNewCourseMySQLDao(dao)).thenReturn(mockCourseMySQLDao);
        /* ========================================================================
                Test mocking setup ends
           ======================================================================== */

        Optional<Assignment> assignment = spiedAssignmentDao.findAssignmentById(assignmentID);

        Assert.that(assignment.isPresent(), "Assignment returned isPresent()");
        Assert.that(assignment.get().getAssignmentId() == assignmentID, "Assignment id did not match expected value");
        Assert.that(
                assignment.get().getCourse().getCourseId() == courseID, "Course id did not match expected value");
        Assert.that(assignment.get().getDueDate().equals(dueDate),
                "Assignment dueDate " + assignment.get().getDueDate().toString() +
                        " did not match expected value " + dueDate.toString());

        Assert.that(assignment.get().getDateAssigned().equals(assignedDate),
                "Assignment assignedDate" + assignment.get().getDateAssigned().toString() +
                        "did not match expected value " + assignedDate.toString());

        Assert.that(assignment.get().getInstructions().equals(instructorsString),
                "Assignment instructions did not match expected value " +
                        instructorsString
        );

        Assert.that(assignment.get().getAssignmentName().equals(assignmentName),
                "Assignment name did not match expected value " +
                        assignmentName
        );

        Assert.that(assignment.get().getAssignmentFileName().equals(assignmentFileName),
                "Assignment filename did not match expected value " +
                        assignmentFileName
        );
    }

    @Test
    public void testFindAssignmentById()
            throws SQLException, ParseException {
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
            throws SQLException, ParseException {
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
            throws SQLException {
        String query = new StringBuilder("UPDATE assignments SET course_id = ?, assigned_date = ?, due_date = ?")
                .append(", assignment_filename = ?, total_points = ?, instructions = ?, assignment_name = ?, ")
                .append("WHERE assignment_id = ?")
                .toString();

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
            throws Exception {

    }
}