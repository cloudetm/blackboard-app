package com.blackboard.api.core.model;

/**
 * The Student Model Class.
 * <p/>
 * To simplify DB structure this class extends the User class so that, using polymorphism, we can
 * <p/>
 * Created by ChristopherLicata on 11/14/15.
 */
public class Student
		extends User
{
	private double gpa;


	/**
	 * Default Constructor for Student
	 */
	public Student()
	{
	}


	/**
	 * The Full constructor of the Student model Class.
	 *
	 * @param firstName  The students's first name
	 * @param lastName   The students's last name
	 * @param email      The students's email/username
	 * @param password   The students's unencrypted password
	 * @param schoolName The students's home institution
	 * @implNote This constructor sets the isStudent variable to true in its parent class {@link com.blackboard.api.core.model.User}
	 */
	public Student(String firstName, String lastName, String email, String password, String schoolName, double gpa)
	{
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setSchoolName(schoolName);
		this.gpa = gpa;
	}


	public double getGpa()
	{
		return gpa;
	}


	public void setGpa(double gpa)
	{
		this.gpa = gpa;
	}

}
