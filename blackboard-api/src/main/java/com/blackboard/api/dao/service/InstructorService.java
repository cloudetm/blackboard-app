package com.blackboard.api.dao.service;

import com.blackboard.api.core.model.Instructor;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface InstructorService
{
    public List<Instructor> getAllInstructors();

    public Instructor createInstructor(
            String firstName, String lastName, String email, String pw, int
            schoolId);

    public Optional<Instructor> getInstructorAccountByEmail(String instructorEmail);

}
