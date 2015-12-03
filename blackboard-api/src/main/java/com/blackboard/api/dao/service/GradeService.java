package com.blackboard.api.dao.service;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Grade;
import com.blackboard.api.core.model.Submission;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface GradeService
{
    public Grade updateGrade(
            int gradeId, int score, Assignment assignment, int submissionId, String
            studentEmail);

    public List<Grade> getAllGradesForAssignment(Assignment assignment);

    public Optional<Grade> getGradeById(int gradeId);

    public Optional<Grade> deleteGrade(Grade grade);

    public Grade createGrade(int score, Assignment assignment, Submission submission, String studentEmail);
}
