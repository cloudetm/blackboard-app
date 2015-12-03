package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.dao.impl.AssignmentMySQLDao;
import com.blackboard.api.dao.impl.interfaces.AssignmentDao;
import com.blackboard.api.dao.service.AssignmentService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class AssignmentDaoService
        implements AssignmentService
{
    private MySQLDao dao;

    final AssignmentDao assignmentDao;


    public AssignmentDaoService(MySQLDao dao)
    {
        this.dao = dao;
        assignmentDao = new AssignmentMySQLDao(dao);
    }

    /*
     * Assignment API
     */


    @Override
    public Assignment createAssignment(
            Course course, String assignmentName, String assignmentFileName,
            String instructions, double weight, int totalPoints, Date
                    dateAssigned, Date dueDate)
    {
        Assignment assignment = new Assignment(course, assignmentName, assignmentFileName,
                                               instructions, weight, totalPoints, dateAssigned,
                                               dueDate);

        return assignmentDao.createAssignment(assignment);
    }


    @Override
    public Optional<Assignment> getAssignmentById(int assignmentId)
    {
        return assignmentDao.findAssignmentById(assignmentId);
    }


    @Override
    public Optional<Assignment> deleteAssignmentById(int assignmentId)
    {
        if (!assignmentDao.findAssignmentById(assignmentId).isPresent())
        {
            throw new IllegalArgumentException(
                    "No Course with the id " + assignmentId + " could be found");
        }
        return assignmentDao.deleteAssignmentById(assignmentId);
    }


    @Override
    public Assignment updateAssignment(
            int assignmentId, Course course, String assignmentName, String
            assignmentFileName, String instructions, double weight, int totalPoints, Date
                    dateAssigned, Date dueDate)
    {
        Assignment assignment = new Assignment(assignmentId, course, assignmentName, assignmentFileName,
                                               instructions, weight, totalPoints, dateAssigned,
                                               dueDate);
        return assignmentDao.updateAssignment(assignment);
    }


    @Override
    public List<Assignment> getAllCourseAssignmentsById(int courseId)
    {
        return assignmentDao.findAllAssignmentsByCourseId(courseId);
    }

}
