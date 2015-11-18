package com.blackboard.api.dao;

import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.School;

import java.util.Optional;

/**
 * Created by ChristopherLicata on 11/18/15.
 */
public interface CourseDao
{
    Optional<Course> findCoursesOffered(School school);

    Course createCourse(School school, Course course);

    Optional<Course> deleteCourse(Course course);

}
