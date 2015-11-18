package com.blackboard.api.core.model;

/**
 * This is the School Model Class that maps to the schools table in the database.
 * <p/>
 * Created by ChristopherLicata on 11/17/15.
 */
public class School
{
	private String name;


	public School(String name)
	{
		this.name = name;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}
}
