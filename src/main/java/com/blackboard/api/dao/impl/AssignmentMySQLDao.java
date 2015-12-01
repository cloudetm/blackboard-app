package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.dao.impl.interfaces.AssignmentDao;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * The MySQL implementation of Create, Retrieve, Update and Delete functions on the AssignmentDao Objects.
 * <p>
 * Created by ChristopherLicata on 11/24/15.
 */

public class AssignmentMySQLDao
        implements AssignmentDao
{
    private MySQLDao dao;


    public AssignmentMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    /**
     * Create Assignment in database.
     *
     * @param assignment The assignment being added to the course.
     *
     * @return assignment The assignment model object representation of the assignment that created in the DB.
     */
    @Override
    public Assignment createAssignment(Assignment assignment)
    {
        String query = new StringBuilder()
                .append("INSERT INTO assignments(course_id, assigned_date, due_date, ")
                .append("assignment_filename, assignment_name,total_points, instructions) VALUES ")
                .append("(?, ?, ?, ?, ?, ?, ?)").toString();

        int courseId = assignment.getCourse().getCourseId();
        String assignmentName = assignment.getAssignmentName();
        String assignmentFileName = assignment.getAssignmentFileName();
        String instructions = assignment.getInstructions();
        int totalPoints = assignment.getTotalPoints();
        Date dateAssigned = new Date(assignment.getDateAssigned().getTime());
        Date dueDate = new Date(assignment.getDueDate().getTime());
        Optional<ResultSet> assignmentId = dao
                .update(query, courseId, dateAssigned, dueDate, assignmentFileName, assignmentName,
                        totalPoints, instructions);

        try
        {
            if (assignmentId.get().next())
            {
                // Assign the row id to the assignmentId for easy access
                assignment.setAssignmentId(assignmentId.get().getInt(1));
                // Closing result set for good form.
                assignmentId.get().close();
            }

        }
        catch (SQLException e)
        {
            printSQLException(e);
        }
        return assignment;
    }


    /**
     * Find an assignment using the assignment to locate the record in the database.
     *
     * @param assignmentId The uniquely identifying course_id of the course that is being searched
     *
     * @return course    The course that corresponds to the course_id supplied as an argument to the method
     */

    @Override
    public Optional<Assignment> findAssignmentById(int assignmentId)
    {
        String q = "SELECT * FROM assignments WHERE assignment_id = ? LIMIT 1";

        return dao.query(q, assignmentId).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    int courseId = result.getInt("course_id");
                    String assignmentName = result.getString("assignment_name");
                    String instructions = result.getString("instructions");
                    int totalPoints = result.getInt("total_points");
                    java.util.Date dateAssigned = result.getDate("assigned_date");
                    java.util.Date dueDate = result.getDate("due_date");
                    String assignmentFileName = result.getString("assignment_filename");

                    // Find Course Object
                    Course course = getNewCourseMySQLDao(dao).findCourseById(courseId).get();

                    return Optional
                            .of(new Assignment(assignmentId, course, assignmentName, assignmentFileName,
                                               instructions, totalPoints, dateAssigned, dueDate));
                }
                else
                {
                    return Optional.empty();
                }
            }
            catch (SQLException e)
            {
                printSQLException(e);
                return Optional.empty();
            }
        });
    }


    public CourseMySQLDao getNewCourseMySQLDao(MySQLDao dao)
    {
        return new CourseMySQLDao(dao);
    }


    /**
     * Delete a course from the database, using the courseId to uniquely identify it
     *
     * @param assignmentId The id of the Assignment that we are trying to delete from the database.
     *
     * @return assignment The assignment that was just deleted from the database
     */
    @Override
    public Optional<Assignment> deleteAssignmentById(int assignmentId)
    {
        return findAssignmentById(assignmentId).map(assignment -> {
            dao.update("DELETE FROM assignments WHERE assignment_id = ?", assignment.getAssignmentId());
            return assignment;
        });
    }


    /**
     * Update the data for one assignment in the database.
     *
     * @param assignment Assignment model object, whose properties will be used to update the data in the
     *                   database
     */
    @Override
    public Assignment updateAssignment(Assignment assignment)
    {
        String query = new StringBuilder("UPDATE assignments SET course_id = ?, assigned_date = ?, due_date = ?")
                .append(", assignment_filename = ?, total_points = ?, instructions = ?, weight = ?, ")
                .append("assignment_name = ? WHERE assignment_id = ?")
                .toString();
        int assignmentId = assignment.getAssignmentId();
        String assignmentName = assignment.getAssignmentName();
        String assignmentFileName = assignment.getAssignmentFileName();
        String instructions = assignment.getInstructions();
        double weight = assignment.getWeight();
        Date dueDate = new java.sql.Date(assignment.getDueDate().getTime());
        Date dateAssigned = new java.sql.Date(assignment.getDateAssigned().getTime());
        int totalPoints = assignment.getTotalPoints();
        int courseId = assignment.getCourse().getCourseId();

        dao.update(query, courseId, dateAssigned, dueDate, assignmentFileName, totalPoints, instructions,
                   weight, assignmentName, assignmentId);

        return assignment;

    }


    /**
     * Find all assignments from a particular course at a university.
     *
     * @param courseId The id of the course from which we would like to receive the assignments.
     *
     * @return assignments list of assignments in a particular course
     */
    @Override
    public List<Assignment> findAllAssignmentsByCourseId(int courseId)
    {
        String query = "SELECT * FROM assignments WHERE course_id = ?";

        try
        {
            ResultSet result = dao.query(query, courseId).get();
            ArrayList<Assignment> assignments = new ArrayList<>();
            while (result.next())
            {
                int assignmentId = result.getInt("assignment_id");
                int resCourseId = result.getInt("course_id");
                int totalPoints = result.getInt("total_points");
                java.util.Date dueDate = result.getDate("due_date");
                java.util.Date dateAssigned = result.getDate("assigned_date");
                String instructions = result.getString("instructions");
                String assignmentName = result.getString("assignment_name");
                String assignmentFileName = result.getString("assignment_filename");

                CourseMySQLDao courseDao = getNewCourseMySQLDao(dao);
                Course course = courseDao.findCourseById(resCourseId).get();

                assignments.add(new Assignment(assignmentId, course, assignmentName, assignmentFileName,
                                               instructions, totalPoints, dateAssigned, dueDate));
            }
            return assignments;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }

    }

}
