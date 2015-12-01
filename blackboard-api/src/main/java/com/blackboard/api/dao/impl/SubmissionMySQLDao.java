package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Grade;
import com.blackboard.api.core.model.Submission;
import com.blackboard.api.dao.impl.interfaces.SubmissionDao;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * The MySQL implementation of Create, Retrieve, and Delete functions on the SubmissionDao Objects.
 * <p>
 * Created by ChristopherLicata on 11/27/15.
 */
public class SubmissionMySQLDao
        implements SubmissionDao
{

    private MySQLDao dao;


    public SubmissionMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    /**
     * Create Submission in database.
     *
     * @param submission The submission being added to the course.
     *
     * @return submission The submission model object representation of the submission that created in the DB.
     */
    @Override
    public Submission createSubmission(Submission submission)
    {
        String query = new StringBuilder()
                .append("INSERT INTO submissions(assignment_id, student_email, date_time_submitted, ")
                .append("submission_filename) VALUES")
                .append("(?, ?, ?, ?)").toString();

        int assignmentId = submission.getAssignment().getAssignmentId();
        String submissionFileName = submission.getSubmissionFileName();
        String studentEmail = submission.getStudentEmail();
        Timestamp currentTimestamp = dao.generateTimeStamp();
        Optional<ResultSet> submissionId = dao.update(query, assignmentId, studentEmail, currentTimestamp,
                                                      submissionFileName);

        try
        {
            if (submissionId.get().next())
            {
                // Assign the row id to the assignmentId for easy access
                submission.setSubmissionId(submissionId.get().getInt(1));
                submission.setCurrentTimeStamp(currentTimestamp);
                // Closing result set for good form.
                submissionId.get().close();
            }

        }
        catch (SQLException e)
        {
            printSQLException(e);
        }
        return submission;
    }


    /**
     * Find a particular student's submission for an assignment.
     *
     * @param assignmentId The id of the assignment associated with said submission that we are trying to
     *                     delete from the database.
     * @param studentEmail The email of the student whose submission is being deleted from the database
     *
     * @return submission The submission that was found in the database
     */
    @Override
    public Optional<com.blackboard.api.core.model.Submission> findStudentSubmission(
            String studentEmail, int assignmentId)
    {
        String submissionQuery = "SELECT * FROM submissions WHERE student_email = ? AND assignment_id = ?";
        return dao.query(submissionQuery, studentEmail, assignmentId).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    int submissionId = result.getInt("submission_id");
                    int gradeId = result.getInt("grade_id");
                    Timestamp timestamp = result.getTimestamp("date_time_submitted");
                    String submissionFileName = result.getString("submission_filename");

                    // Find Associated Assignment Object
                    AssignmentMySQLDao assignmentDao = new AssignmentMySQLDao(dao);
                    Assignment assignment = assignmentDao.findAssignmentById(assignmentId).get();

                    //Find Associated Grade Object

                    GradeMySQLDao gradeDao = new GradeMySQLDao(dao);
                    Optional<Grade> grade = gradeDao.findGradeBySubmission(submissionId);

                    Optional<Submission> submission;

                    if (grade.isPresent())
                    {
                        submission = Optional.of(new Submission(submissionId, grade.get(), assignment,
                                                                studentEmail,
                                                                submissionFileName, dao.generateTimeStamp()));
                    }
                    else
                    {
                        submission = Optional.of(new Submission(submissionId, assignment,
                                                                studentEmail,
                                                                submissionFileName, dao.generateTimeStamp()));
                    }
                    return submission;

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


    /**
     * Delete a submission from the database, using the student's email and the assignment id to uniquely
     * identify it
     *
     * @param assignmentId The id of the assignment associated with said submission that we are trying to
     *                     delete from the database.
     * @param studentEmail The email of the student whose submission is being deleted from the database
     *
     * @return submission The submission that was just deleted from the database
     */
    @Override
    public Optional<com.blackboard.api.core.model.Submission> deleteStudentSubmission(
            String studentEmail,
            int assignmentId)
    {
        return findStudentSubmission(studentEmail, assignmentId).map(submission -> {
            dao.update("DELETE FROM submissions WHERE submission_id = ?", submission.getSubmissionId());
            return submission;
        });
    }


    /**
     * Finds all student submissions for a particular assignment.
     *
     * @param assignment The assignment associated with the submissions.
     *
     * @return submissions The list of submissions for that particular assignment in the DB.
     */

    @Override
    public List<Submission> findSubmissionsByAssignment(Assignment assignment)
    {
        String query = "SELECT * FROM submissions WHERE assignment_id = ?";

        try
        {
            ResultSet result = dao.query(query, assignment.getAssignmentId()).get();
            ArrayList<Submission> submissions = new ArrayList<>();

            while (result.next())
            {
                int submissionId = result.getInt("submission_id");
                int assignmentId = result.getInt("assignment_id");
                int gradeId = result.getInt("grade_id");
                String studentEmail = result.getString("student_email");
                Timestamp timestamp = result.getTimestamp("date_time_submitted");
                String submissionFileName = result.getString("submission_filename");

                //Find Associated Grade Object

                GradeMySQLDao gradeDao = new GradeMySQLDao(dao);
                Optional<Grade> grade = gradeDao.findGradeById(gradeId);

                Optional<Submission> submission;

                if (grade.isPresent())
                {
                    submission = Optional.of(new Submission(submissionId, grade.get(), assignment,
                                                            studentEmail,
                                                            submissionFileName, dao.generateTimeStamp()));
                }
                else
                {
                    submission = Optional.of(new Submission(submissionId, assignment,
                                                            studentEmail,
                                                            submissionFileName, dao.generateTimeStamp()));
                }
                submissions.add(submission.get());

            }
            return submissions;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }


    @Override
    public Submission updateSubmission(Submission submission)
    {
        java.lang.String query = new StringBuilder("UPDATE submissions SET assignment_id = ?, grade_id = ?, ")
                .append("student_email = ?, date_time_submitted = ?, submission_filename = ?")
                .append(" WHERE submission_id = ? LIMIT 1")
                .toString();

        int assignmentId = submission.getAssignment().getAssignmentId();
        int gradeId = submission.getGrade().getGradeId();
        String studentEmail = submission.getStudentEmail();
        Timestamp timestamp = dao.generateTimeStamp();
        String submissionFilename = submission.getSubmissionFileName();
        int submissionId = submission.getSubmissionId();

        dao.update(query, assignmentId, gradeId, studentEmail, timestamp, submissionFilename, submissionId);

        return submission;

    }
}

