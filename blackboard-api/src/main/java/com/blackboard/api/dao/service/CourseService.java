package com.blackboard.api.dao.service;

import com.blackboard.api.core.Subject;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Instructor;
import com.blackboard.api.core.model.School;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface CourseService
{
    public List<Course> getAllInstructorCourses(String instructorEmail);

    public List<Course> getAllCoursesOffered(School school);

    public Optional<Course> getCourse(int courseId);

    public Optional<Course> deleteCourse(Course course);

    public Course createCourse(
            School school, Instructor instructor, String courseName, Subject subject,
            int courseNumber, int credits, String syllabusFileName, int maxCapacity);

}
