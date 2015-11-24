package com.blackboard.api.dao;

import com.blackboard.api.core.model.Assignment;

import java.util.List;
import java.util.Optional;

/**
 * The internal API for the persistence layer operations. These operations listed only pertain to the
 * Creation, Retrieval, Updating, and Deletion of Assignment objects.
 * <p/>
 *
 * @author ChristopherLicata <Chris@bizmerlin.com> Created by ChristopherLicata on 11/18/15.
 */

public interface AssignmentDao
{
    Optional<Assignment> findAssignmentById(int assignmentId);

    Optional<Assignment> deleteAssignmentById(int assignmentId);

    Assignment updateAssignment(Assignment assignment);

    Optional<List<Assignment>> findAllAssignmentsByCourseId(int courseId);
}
