package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Grade;
import com.blackboard.api.core.model.Submission;
import com.blackboard.api.dao.impl.AssignmentMySQLDao;
import com.blackboard.api.dao.impl.GradeMySQLDao;
import com.blackboard.api.dao.impl.SubmissionMySQLDao;
import com.blackboard.api.dao.impl.interfaces.AssignmentDao;
import com.blackboard.api.dao.impl.interfaces.GradeDao;
import com.blackboard.api.dao.impl.interfaces.SubmissionDao;
import com.blackboard.api.dao.service.SubmissionService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class SubmissionDaoService
        implements SubmissionService
{
    private MySQLDao dao;

    final SubmissionDao submissionDao;

    final AssignmentDao assignmentDao;

    final GradeDao gradeDao;


    public SubmissionDaoService(MySQLDao dao)
    {
        this.dao = dao;
        submissionDao = new SubmissionMySQLDao(dao);
        assignmentDao = new AssignmentMySQLDao(dao);
        gradeDao = new GradeMySQLDao(dao);
    }


    @Override
    public Submission updateSubmission(
            int submissionId, Grade grade, Assignment assignment, String
            studentEmail, String
                    submissionFileName)
    {
        if (!assignmentDao.findAssignmentById(assignment.getAssignmentId()).isPresent())
        {
            throw new IllegalArgumentException("There is no assignment with the id " + assignment
                    .getAssignmentId() + "in the System");
        }
        else if (!gradeDao.findGradeById(grade.getGradeId()).isPresent())
        {
            throw new IllegalArgumentException("There is no grade with the id " +
                                                       grade.getGradeId() + "in the System");
        }

        Submission submission = new Submission(submissionId, grade, assignment, studentEmail,
                                               submissionFileName, dao.generateTimeStamp());
        return submissionDao.updateSubmission(submission);

    }


    /**
     * In the current design, the choice was made to exclude update functionality from submissions, due to the
     * fact that it is easier to keep
     */
    @Override
    public Submission createSubmission(Assignment assignment, String studentEmail, String submissionFileName)
    {
        if (!assignmentDao.findAssignmentById(assignment.getAssignmentId()).isPresent())
        {
            throw new IllegalArgumentException("There is no assignment with the id " + assignment
                    .getAssignmentId() + "in the System");
        }

        Submission submission = new Submission(assignment, studentEmail, submissionFileName, dao
                .generateTimeStamp());

        return submissionDao.createSubmission(submission);
    }


    @Override
    public List<Submission> getSubmissionsForAssignment(Assignment assignment)
    {

        if (!assignmentDao.findAssignmentById(assignment.getAssignmentId()).isPresent())
        {
            throw new IllegalArgumentException("There is no assignment with the id " + assignment
                    .getAssignmentId() + "in the System");
        }
        return submissionDao.findSubmissionsByAssignment(assignment);
    }


    @Override
    public Optional<Submission> getStudentSubmission(String studentEmail, int assignmentId)
    {
        if (!assignmentDao.findAssignmentById(assignmentId).isPresent())
        {
            throw new IllegalArgumentException(
                    "There is no assignment with the id " + assignmentId + "in the System");
        }
        return submissionDao.findStudentSubmission(studentEmail, assignmentId);
    }


    @Override
    public Optional<Submission> deleteStudentSubmission(String studentEmail, int submissionId)
    {
        if (!submissionDao.findStudentSubmission(studentEmail, submissionId).isPresent())
        {
            throw new IllegalArgumentException(
                    "There is no Submission for that student in the system.");
        }
        return submissionDao.deleteStudentSubmission(studentEmail, submissionId);
    }

}
