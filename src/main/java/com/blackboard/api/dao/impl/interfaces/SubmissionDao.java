package com.blackboard.api.dao.impl.interfaces;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Submission;

import java.util.List;
import java.util.Optional;

/**
 * The internal API for the persistence layer operations. These operations listed only pertain to the
 * Creation, Retrieval, Updating, and Deletion of Submission objects.
 * <p>
 *
 * @author ChristopherLicata <Chris@bizmerlin.com> Created by ChristopherLicata on 11/18/15.
 */

public interface SubmissionDao
{
    Optional<Submission> findStudentSubmission(String studentEmail, int assignmentId);

    Optional<Submission> deleteStudentSubmission(String studentEmail, int submissionId);

    Submission createSubmission(Submission submission);

    List<Submission> findSubmissionsByAssignment(Assignment assignment);

    Submission updateSubmission(Submission submission);

}
