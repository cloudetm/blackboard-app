package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.School;
import com.blackboard.api.dao.impl.util.ResultSetMocker;
import com.blackboard.api.dao.util.MySQLDao;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * * Test class for the MySQL-based {@link School} Operations
 * <p>
 * Created by ChristopherLicata on 11/23/15.
 */
public class SchoolMySQLDaoTest
{
    private MySQLDao dao;

    private SchoolMySQLDao schoolDao;

    private ResultSetMocker resultSet;


    @Before
    public void setUp()
            throws Exception
    {
        dao = mock(MySQLDao.class);
        schoolDao = new SchoolMySQLDao(dao);
        resultSet = new ResultSetMocker();
    }


    /**
     * Test for finding School by id in DB when the id input is valid and exists in the DB.  -- expecting
     * success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindSchoolById()
            throws SQLException
    {
        String q = "SELECT * FROM schools WHERE school_id = ?";
        int schoolId = 6;

        Optional<ResultSet> result = resultSet
                .mockSchoolResultSet(6, "University of California - Los Angeles");

        when(dao.query(q, schoolId)).thenReturn(result);

        Optional<School> school = schoolDao.findSchoolById(6);

        Assert.that(school.isPresent(), "School is not being returned as a result of the DB query");
        Assert.that(school.get().getName()
                            .equals("University of California - Los Angeles"), "School name did not match expected value");
        Assert.that(school.get().getSchoolId() == 6, "School schoolId did not match expected value");
    }


    /**
     * Test for finding all Schools in DB. Not only tests for whether or not the values of the object returned
     * are the same, but also that the result set is not empty -- expecting success.
     *
     * @throws SQLException
     */
    @Test
    public void testFindAllSchools()
            throws SQLException
    {
        String q = "SELECT * FROM schools";

        Optional<ResultSet> result = resultSet.mockSchoolMultiRowResultSet();

        when(dao.query(q)).thenReturn(result);

        List<School> schools = schoolDao.findAllSchools();

        //First Row of Result Set
        Assert.that(
                schools.size() == 2, "Schools are not being returned as a result of the DB query");

        Assert.that(
                schools.get(0).getName()
                        .equals("The George Washington University"),
                "School first name did not match expected value");
        Assert.that(
                schools.get(0).getSchoolId() == 15, "School schoolId did not match expected value");

        // Second Row of Result Set
        Assert.that(schools.get(1).getName()
                            .equals("University of Maryland"), "School name did not match expected value");
        Assert.that(
                schools.get(1).getSchoolId() == 14, "School schoolId did not match expected value");

    }
}