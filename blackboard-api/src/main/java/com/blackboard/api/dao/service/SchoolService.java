package com.blackboard.api.dao.service;

import com.blackboard.api.core.model.School;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface SchoolService
{
    public Optional<School> getSchool(int schoolId);

    public List<School> getAllSchools();
}
