package com.blackboard.api.dao.impl;

import com.blackboard.api.core.model.*;
import com.blackboard.api.dao.BlackboardDAO;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of DB DAO operations using MySQL Databases.
 *
 * Created by ChristopherLicata on 11/14/15.
 */
public class MySQLDAO
		implements BlackboardDAO
{
	@Override
	public Optional<User> getUser(String email)
	{
		return null;
	}


	@Override
	public User updateUser(User User)
	{
		return null;
	}


	@Override
	public Optional<User> deleteUser(String email)
	{
		return null;
	}


	@Override
	public List<User> allUsers()
	{
		return null;
	}


	@Override
	public Student createStudent(Student student)
	{
		return null;
	}


	@Override
	public Optional<Student> getStudent(String email)
	{
		return null;
	}


	@Override
	public List<Student> allStudents()
	{
		return null;
	}


	@Override
	public Instructor createInstructor(Instructor instructor)
	{
		return null;
	}


	@Override
	public Optional<Instructor> getInstructor(String email)
	{
		return null;
	}


	@Override
	public List<Instructor> allInstructors()
	{
		return null;
	}


	@Override
	public List<School> allSchools()
	{
		return null;
	}


	@Override
	public Optional<Course> getCoursesOffered(School school)
	{
		return null;
	}


	@Override
	public Course createCourse(School school, Course course)
	{
		return null;
	}


	@Override
	public Optional<Course> deleteCourse(Course course)
	{
		return null;
	}


	@Override
	public Grade createGrade(Grade grade)
	{
		return null;
	}


	@Override
	public Optional<Grade> deleteGrade(Grade grade)
	{
		return null;
	}


	@Override
	public Optional<Grade> getGrade(Submission submission)
	{
		return null;
	}


	@Override
	public Grade updateGrade(Grade grade)
	{
		return null;
	}


	@Override
	public Optional<Transcript> getTranscript(String email)
	{
		return null;
	}


	@Override
	public Transcript updateTranscript(Transcript transcript)
	{
		return null;
	}


	@Override
	public Transcript createTranscript(Transcript transcript)
	{
		return null;
	}


	@Override
	public Optional<Submission> getSubmission(int studentId, int assignmentId)
	{
		return null;
	}


	@Override
	public Optional<Submission> deleteSubmission(Submission submission)
	{
		return null;
	}


	@Override
	public Submission createSubmission(Submission submission)
	{
		return null;
	}


	@Override
	public Optional<List<Submission>> getSubmissions(Assignment assignment)
	{
		return null;
	}


	@Override
	public Optional<Assignment> getAssignment(int assignmentId)
	{
		return null;
	}


	@Override
	public Optional<Assignment> deleteAssignment(int assignmentId)
	{
		return null;
	}


	@Override
	public Assignment updateAssignment(Assignment assignment)
	{
		return null;
	}


	@Override
	public Optional<List<Assignment>> allAssignments(int courseId)
	{
		return null;
	}
}