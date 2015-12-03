package com.blackboard.api.dao.service;

import com.blackboard.api.core.model.Student;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface StudentService
{
    public Student createStudent(
            String firstName, String lastName, String email, String pw, int
            schoolId);

    public Optional<Student> getStudentAccountByEmail(String email);

    public List<Student> getAllStudents();

    public List<Student> getStudentsInCourse(int courseId);
}
