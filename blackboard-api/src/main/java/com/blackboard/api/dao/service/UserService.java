package com.blackboard.api.dao.service;

import com.blackboard.api.core.model.User;

import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface UserService
{
    public User updateInstructorAccount(
            String firstName, String lastName, String email, String password,
            int schoolId);

    public User updateStudentAccount(
            String firstName, String lastName, String email, String password,
            double gpa, int schoolId);

    public Optional<User> deleteUserAccount(String userEmail);

    public Optional<User> getUser(String email);
}
