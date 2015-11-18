package com.blackboard.api.dao;

import com.blackboard.api.core.model.Student;

import java.util.List;
import java.util.Optional;

/**
 * The internal API for the persistence layer operations.
 * These operations listed only pertain to the Creation,
 * Retrieval, Updating, and Deletion of Student objects.
 * <p/>
 *
 * @author ChristopherLicata <Chris@bizmerlin.com>
 *         Created by ChristopherLicata on 11/18/15.
 */

public interface StudentDao
{
    Student createStudent(Student student);

    Optional<Student> findStudentByEmail(String email);

    List<Student> findAllStudents();

}
