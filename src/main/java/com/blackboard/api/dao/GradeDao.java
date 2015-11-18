package com.blackboard.api.dao;

import com.blackboard.api.core.model.Grade;
import com.blackboard.api.core.model.Submission;

import java.util.Optional;

/**
 * The internal API for the persistence layer operations.
 * These operations listed only pertain to the Creation,
 * Retrieval, Updating, and Deletion of Grade objects.
 * <p/>
 *
 * @author ChristopherLicata <Chris@bizmerlin.com>
 *         Created by ChristopherLicata on 11/18/15.
 */

public interface GradeDao
{
    Grade createGrade(Grade grade);

    Optional<Grade> deleteGradeById(int gradeId);

    Optional<Grade> deleteGrade(Grade grade);

    Optional<Grade> findGradeBySubmission(Submission submission);

    Grade updateGrade(Grade grade);
}
