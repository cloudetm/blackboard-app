package com.blackboard.api.dao.service;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Grade;
import com.blackboard.api.core.model.Submission;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface SubmissionService
{
    public Submission updateSubmission(
            int submissionId, Grade grade, Assignment assignment, String
            studentEmail, String
                    submissionFileName);

    public Submission createSubmission(Assignment assignment, String studentEmail, String submissionFileName);

    public List<Submission> getSubmissionsForAssignment(Assignment assignment);

    public Optional<Submission> getStudentSubmission(String studentEmail, int assignmentId);

    public Optional<Submission> deleteStudentSubmission(String studentEmail, int submissionId);

}
