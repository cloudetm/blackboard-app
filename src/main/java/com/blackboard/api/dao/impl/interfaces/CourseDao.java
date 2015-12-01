package com.blackboard.api.dao.impl.interfaces;

import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.School;

import java.util.List;
import java.util.Optional;

/**
 * The internal API for the persistence layer operations. These operations listed only pertain to the
 * Creation, Retrieval and Deletion of Course objects.
 * <p/>
 * Created by ChristopherLicata on 11/18/15.
 */
public interface CourseDao
{
    List<Course> findCoursesOffered(School school);

    Optional<Course> findCourseById(int courseId);

    Course createCourse(School school, Course course);

    Optional<Course> deleteCourseById(int courseId);

    List<Course> findCoursesByInstructor(String instructorEmail);

}
