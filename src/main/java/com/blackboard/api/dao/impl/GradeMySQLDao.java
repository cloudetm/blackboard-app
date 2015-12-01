package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Grade;
import com.blackboard.api.dao.impl.interfaces.GradeDao;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * The MySQL implementation of Create, Retrieve, and Delete functions on the GradDao Objects.
 * <p>
 * Created by ChristopherLicata on 11/27/15.
 */
public class GradeMySQLDao
        implements GradeDao
{

    private MySQLDao dao;


    public GradeMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    @Override
    public Grade createGrade(Grade grade)
    {
        String query = new StringBuilder()
                .append("INSERT INTO grades (assignment_id, submission_id, student_email, ")
                .append("score, submitted_time) VALUES")
                .append("(?, ?, ?, ?, ?)").toString();

        int assignmentId = grade.getAssignment().getAssignmentId();
        int submissionId = grade.getSubmissionId();
        String studentEmail = grade.getStudentEmail();
        int score = grade.getScore();
        Timestamp createdTime = dao.generateTimeStamp();
        Optional<ResultSet> gradeId = dao
                .update(query, assignmentId, submissionId, studentEmail, score, createdTime);

        try
        {
            if (gradeId.get().next())
            {
                // Assign the row id to the assignmentId for easy access
                grade.setGradeId(gradeId.get().getInt(1));
                // Closing result set for good form.
                gradeId.get().close();
            }
        }
        catch (SQLException e)
        {
            printSQLException(e);
        }
        return grade;
    }


    @Override
    public Optional<Grade> deleteGradeById(int gradeId)
    {
        return findGradeById(gradeId).map(grade -> {
            dao.update("DELETE FROM grades WHERE grade_id = ?", gradeId);
            return grade;
        });
    }


    @Override
    public Optional<Grade> findGradeById(int gradeId)
    {
        String q = "SELECT * FROM grades WHERE grade_id = ? LIMIT 1";

        return dao.query(q, gradeId).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    int assignmentId = result.getInt("assignment_id");
                    int submissionId = result.getInt("submission_id");
                    String studentEmail = result.getString("student_email");
                    int score = result.getInt("score");
                    Timestamp lastUpdatedTime = result.getTimestamp("submitted_time");

                    // Find Assignment Object
                    AssignmentMySQLDao courseDao = new AssignmentMySQLDao(dao);
                    Assignment assignment = courseDao.findAssignmentById(assignmentId).get();

                    return Optional.of(new Grade(gradeId, score, assignment, submissionId, studentEmail,
                                                 lastUpdatedTime));
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


    @Override
    public List<Grade> findGradesByAssignment(Assignment assignment)
    {
        String query = "SELECT * FROM grades WHERE assignment_id = ?";

        try
        {
            ResultSet result = dao.query(query, assignment.getAssignmentId()).get();
            ArrayList<Grade> grades = new ArrayList<>();
            while (result.next())
            {
                int gradeId = result.getInt("grade_id");
                int submissionId = result.getInt("submission_id");
                int score = result.getInt("score");
                String studentEmail = result.getString("student_email");
                Timestamp dateAssigned = result.getTimestamp("submitted_time");

                grades.add(new Grade(gradeId, assignment, submissionId, studentEmail, dateAssigned));
            }
            return grades;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }


    @Override
    public Optional<Grade> findGradeBySubmission(int submissionId)
    {
        String q = "SELECT * FROM grades WHERE submission_id = ? LIMIT 1";

        return dao.query(q, submissionId).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    int gradeId = result.getInt("grade_id");
                    int assignmentId = result.getInt("assignment_id");
                    int score = result.getInt("score");
                    String studentEmail = result.getString("student_email");
                    Timestamp submittedTime = result.getTimestamp("submitted_time");

                    // Find Assignment Object
                    AssignmentMySQLDao courseDao = new AssignmentMySQLDao(dao);
                    Assignment assignment = courseDao.findAssignmentById(assignmentId).get();

                    return Optional
                            .of(new Grade(gradeId, score, assignment, submissionId, studentEmail,
                                          submittedTime));
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


    @Override
    public Grade updateGrade(Grade grade)
    {
        String query = new StringBuilder("UPDATE grades SET assignment_id = ?, submission_id = ?, ")
                .append("student_email = ?, score = ?, submitted_time = ? WHERE grade_id  = ?")
                .toString();
        int gradeId = grade.getGradeId();
        int assignmentId = grade.getAssignment().getAssignmentId();
        int submissionId = grade.getSubmissionId();
        String studentEmail = grade.getStudentEmail();
        int score = grade.getScore();
        Timestamp timestamp = dao.generateTimeStamp();

        dao.update(query, assignmentId, submissionId, studentEmail, score, timestamp, gradeId);

        return grade;

    }
}
