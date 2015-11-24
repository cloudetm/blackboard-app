package com.blackboard.api.dao;

import com.blackboard.api.core.model.School;

import java.util.List;

/**
 * The internal API for the persistence layer operations. These operations listed only pertain to the
 * Retrieval of School objects.
 * <p/>
 *
 * @author ChristopherLicata <Chris@bizmerlin.com> Created by ChristopherLicata on 11/18/15.
 */

public interface SchoolDao
{
    List<School> findAllSchools();
}
