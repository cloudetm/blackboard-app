package com.blackboard.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.apache.xpath.internal.operations.String;
import org.hibernate.validator.constraints.NotEmpty;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

/**
 * Created by ChristopherLicata on 11/30/15.
 */
public class BlackboardConfiguration
        extends Configuration
{
    @NotEmpty
    private String db;


    @JsonProperty
    public String getDb()
    {
        return db;
    }


    @JsonProperty
    public void setDb()
    {
        this.db = db;
    }


    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(java.lang.String name)
    {
        return new AppConfigurationEntry[0];
    }
}
