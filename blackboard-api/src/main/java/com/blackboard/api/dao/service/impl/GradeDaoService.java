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
import com.blackboard.api.dao.service.GradeService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class GradeDaoService
        implements GradeService
{
    private MySQLDao dao;

    final GradeDao gradeDao;

    final AssignmentDao assignmentDao;

    final SubmissionDao submissionDao;


    public GradeDaoService(MySQLDao dao)
    {
        this.dao = dao;
        gradeDao = new GradeMySQLDao(dao);
        assignmentDao = new AssignmentMySQLDao(dao);
        submissionDao = new SubmissionMySQLDao(dao);
    }


    @Override
    public Grade createGrade(int score, Assignment assignment, Submission submission, String studentEmail)
    {
        Grade grade = new Grade(score, assignment, submission.getSubmissionId(), studentEmail);
        return gradeDao.createGrade(grade);
    }


    @Override
    public Optional<Grade> deleteGrade(Grade grade)
    {

        if (!gradeDao.findGradeById(grade.getGradeId()).isPresent())
        {
            throw new IllegalArgumentException(
                    "No Grade with the id " + grade.getGradeId() + " could be found");
        }
        return gradeDao.deleteGradeById(grade.getGradeId());
    }


    @Override
    public Optional<Grade> getGradeById(int gradeId)
    {
        return gradeDao.findGradeById(gradeId);
    }


    @Override
    public List<Grade> getAllGradesForAssignment(Assignment assignment)
    {
        if (!assignmentDao.findAssignmentById(assignment.getAssignmentId()).isPresent())
        {
            throw new IllegalArgumentException("There is no assignment with the id " + assignment
                    .getAssignmentId() + "in the System");
        }
        return gradeDao.findGradesByAssignment(assignment);
    }


    @Override
    public Grade updateGrade(
            int gradeId, int score, Assignment assignment, int submissionId, String
            studentEmail)
    {
        if (!assignmentDao.findAssignmentById(assignment.getAssignmentId()).isPresent())
        {
            throw new IllegalArgumentException("There is no assignment with the id " + assignment
                    .getAssignmentId() + "in the System");
        }
        else if (!submissionDao.findStudentSubmission(studentEmail, assignment.getAssignmentId()).isPresent())
        {
            throw new IllegalArgumentException(
                    "There is no submission with the id " + submissionId + "in the System");
        }
        Grade grade = new Grade(gradeId, score, assignment, submissionId, studentEmail);
        return gradeDao.updateGrade(grade);
    }

}
