package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.dao.AssignmentDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 11/18/15.
 */
public class AssignmentMySQLDao
        implements AssignmentDao
{
    @Override
    public Optional<Assignment> findAssignmentById(int assignmentId)
    {
        return null;
    }


    @Override
    public Optional<Assignment> deleteAssignmentById(int assignmentId)
    {
        return null;
    }


    @Override
    public Assignment updateAssignment(Assignment assignment)
    {
        return null;
    }


    @Override
    public Optional<List<Assignment>> findAllAssignmentsByCourseId(int courseId)
    {
        return null;
    }
}
