package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.User;

import java.util.Optional;

/**
 * The internal API for the persistence layer operations. These operations listed only pertain to the
 * Creation, Retrieval, Updating, and Deletion of User objects.
 * <p/>
 *
 * @author ChristopherLicata <Chris@bizmerlin.com> Created by ChristopherLicata on 11/18/15.
 */

public interface UserDao
{
    Optional<User> findUserByEmail(String email);

    User updateUser(User User);

    Optional<User> deleteUser(String email);

}
