package com.blackboard.web.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by ChristopherLicata on 11/30/15.
 */
public class BlackboardHealthCheck
        extends HealthCheck
{
    @Override
    protected Result check()
            throws Exception
    {
        return Result.healthy();
    }
}
