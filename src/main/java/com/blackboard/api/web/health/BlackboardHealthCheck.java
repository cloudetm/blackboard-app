package com.blackboard.api.web.health;

import com.blackboard.api.dao.util.MySQLDao;
import com.codahale.metrics.health.HealthCheck;

/**
 * Checks Health of Application by verifying connection to the Database
 * <p>
 * Created by ChristopherLicata on 11/30/15.
 */
public class BlackboardHealthCheck
        extends HealthCheck
{

    private MySQLDao database;


    public BlackboardHealthCheck(MySQLDao database)
    {
        this.database = database;
    }


    // TODO: Verify that this function works
    @Override
    protected Result check()
            throws Exception
    {
        if (this.database.isConnected())
        {
            return Result.healthy("Connection to RDS instance successful!");
        }
        else
        {
            return Result.unhealthy(
                    "Connection cannot be made to the RDS instance containing the MySQL Database.");
        }
    }

}
