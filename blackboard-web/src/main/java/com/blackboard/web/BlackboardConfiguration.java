package com.blackboard.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Parses config.yml file in JSON format, retrieving all information necessary for establishing a connection
 * to the databas, configuring a server, etc.
 * <p>
 * Created by ChristopherLicata on 11/30/15.
 */
public class BlackboardConfiguration
        extends Configuration
{
    @NotEmpty
    private String db;

    @NotEmpty
    private String driver;


    @JsonProperty
    public String getDb()
    {
        return db;
    }


    @JsonProperty
    public void setDb(String db)
    {
        this.db = db;
    }


    @JsonProperty
    public String getDriver()
    {
        return driver;
    }


    @JsonProperty
    public void setDriver(String driver)
    {
        this.driver = driver;
    }
}
