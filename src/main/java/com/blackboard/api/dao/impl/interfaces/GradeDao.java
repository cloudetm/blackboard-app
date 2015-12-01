package com.blackboard.api.dao.impl.interfaces;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Grade;

import java.util.List;
import java.util.Optional;

/**
 * The internal API for the persistence layer operations. These operations listed only pertain to the
 * Creation, Retrieval, Updating, and Deletion of Grade objects.
 * <p>
 *
 * @author ChristopherLicata <Chris@bizmerlin.com> Created by ChristopherLicata on 11/18/15.
 */

public interface GradeDao
{
    Grade createGrade(Grade grade);

    Optional<Grade> deleteGradeById(int gradeId);

    Optional<Grade> findGradeById(int gradeId);

    List<Grade> findGradesByAssignment(Assignment assignment);

    Optional<Grade> findGradeBySubmission(int submissionId);

    Grade updateGrade(Grade grade);
}
