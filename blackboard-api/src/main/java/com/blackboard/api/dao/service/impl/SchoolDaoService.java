package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.SchoolMySQLDao;
import com.blackboard.api.dao.impl.interfaces.SchoolDao;
import com.blackboard.api.dao.service.SchoolService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class SchoolDaoService
        implements SchoolService
{
    private MySQLDao dao;

    final SchoolDao schoolDao;


    public SchoolDaoService(MySQLDao dao)
    {
        this.dao = dao;
        schoolDao = new SchoolMySQLDao(dao);
    }


    @Override
    public Optional<School> getSchool(int schoolId)
    {
        return schoolDao.findSchoolById(schoolId);
    }


    @Override
    public List<School> getAllSchools()
    {
        return schoolDao.findAllSchools();
    }

}
