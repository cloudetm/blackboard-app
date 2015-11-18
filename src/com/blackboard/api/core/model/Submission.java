package com.blackboard.api.core.model;

/**
 * The Submission Model Object that corresponds to the submissions table in the database.
 * <p/>
 * Created by ChristopherLicata on 11/17/15.
 */
public class Submission
{
	private Grade grade;

	private Assignment assignment;

	private double weight;

	private String studentEmail;

	private String fileName;


	/**
	 * The default constructor for the Submission object.
	 */
	public Submission()
	{
	}


	/**
	 * The constructor for the Submission Model Object that corresponds with the
	 * situation, during which, a student submits an assignment that has yet to be graded.
	 *
	 * @param assignment   The assignment to which this submission corresponds.
	 * @param weight       The percentage of your overall course grade that is made up by said Submission.
	 * @param studentEmail The email of the student whom submitted the work
	 * @param fileName     The filename associated with this submission.
	 */
	public Submission(Assignment assignment, double weight, String studentEmail, String fileName)
	{
		this.assignment = assignment;
		this.weight = weight;
		this.studentEmail = studentEmail;
		this.fileName = fileName;
	}


	/**
	 * The full constructor for the Submission Model Object.
	 *
	 * @param grade        The grade earned on the work submitted
	 * @param assignment   The assignment to which this submission corresponds.
	 * @param weight       The percentage of your overall course grade that is made up by said Submission.
	 * @param studentEmail The email of the student whom submitted the work
	 * @param fileName     The filename associated with this submission.
	 */
	public Submission(Grade grade, Assignment assignment, double weight, String studentEmail, String fileName)
	{
		this.grade = grade;
		this.assignment = assignment;
		this.weight = weight;
		this.studentEmail = studentEmail;
		this.fileName = fileName;
	}


	public Grade getGrade()
	{
		return grade;
	}


	public void setGrade(Grade grade)
	{
		this.grade = grade;
	}


	public Assignment getAssignment()
	{
		return assignment;
	}


	public void setAssignment(Assignment assignment)
	{
		this.assignment = assignment;
	}


	public double getWeight()
	{
		return weight;
	}


	public void setWeight(double weight)
	{
		this.weight = weight;
	}


	public String getStudentEmail()
	{
		return studentEmail;
	}


	public void setStudentEmail(String studentEmail)
	{
		this.studentEmail = studentEmail;
	}


	public String getFileName()
	{
		return fileName;
	}


	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}
}
