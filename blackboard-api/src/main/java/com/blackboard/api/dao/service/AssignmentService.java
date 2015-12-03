package com.blackboard.api.dao.service;

import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Course;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface AssignmentService
{
    public Assignment createAssignment(
            Course course, String assignmentName, String assignmentFileName,
            String instructions, double weight, int totalPoints, Date
                    dateAssigned, Date dueDate);

    public Optional<Assignment> getAssignmentById(int assignmentId);

    public Optional<Assignment> deleteAssignmentById(int assignmentId);

    public Assignment updateAssignment(
            int assignmentId, Course course, String assignmentName, String
            assignmentFileName, String instructions, double weight, int totalPoints, Date
                    dateAssigned, Date dueDate);

    public List<Assignment> getAllCourseAssignmentsById(int courseId);
}
