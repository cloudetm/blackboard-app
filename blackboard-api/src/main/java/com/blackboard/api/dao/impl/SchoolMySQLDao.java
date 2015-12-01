package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.interfaces.SchoolDao;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

/**
 * Class that provides the MySQL implementation of retrieval of schools from the DB.
 * <p>
 * Created by ChristopherLicata on 11/23/15.
 */
public class SchoolMySQLDao
        implements SchoolDao
{
    private MySQLDao dao;


    public SchoolMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    /**
     * Finds all Schools in the database.
     *
     * @return schools  List of Schools that are present in the schools table of the database.
     */
    @Override
    public List<School> findAllSchools()
    {
        String q = "SELECT * FROM schools";
        try
        {
            ResultSet result = dao.query(q).get();

            ArrayList<School> schools = new ArrayList<>();
            while (result.next())
            {
                int schoolId = result.getInt("school_id");
                String name = result.getString("name");
                schools.add(new School(schoolId, name));
            }
            return schools;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }


    /**
     * Find School by the school's school_id number
     *
     * @param id The school_id of the school for which we are trying to search.
     *
     * @return new School(...) The model object representation of the school whose id matched that of the
     * arguments to the method.
     */
    @Override
    public Optional<School> findSchoolById(int id)
    {
        String q = "SELECT * FROM schools WHERE school_id = ?";
        return dao.query(q, id).flatMap(result -> {
            try
            {
                if (result.next())
                {
                    int schoolId = result.getInt("school_id");
                    String schoolName = result.getString("name");
                    return Optional.of(new School(schoolId, schoolName));
                }
                else
                    return Optional.empty();
            }
            catch (SQLException e)
            {
                printSQLException(e);
                return Optional.empty();
            }
        });
    }

}
