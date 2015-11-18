package com.blackboard.api.dao;

import com.blackboard.api.core.model.*;

import java.util.List;
import java.util.Optional;

/**
 * The internal API for the persistence layer operations.
 * These operations listed only pertain to the Creation,
 * Retrieval, Updating, and Deletion of objects.
 * <p/>
 * Created by ChristopherLicata on 11/17/15.
 */
public interface BlackboardDAO
{

	// User Functions
	Optional<User> getUser(String email);

	User updateUser(User User);

	Optional<User> deleteUser(String email);

	List<User> allUsers();

	// Student Functions
	Student createStudent(Student student);

	Optional<Student> getStudent(String email);

	List<Student> allStudents();

	// Instructor Functions
	Instructor createInstructor(Instructor instructor);

	Optional<Instructor> getInstructor(String email);

	List<Instructor> allInstructors();

	// Schools Functions
	List<School> allSchools();

	// Courses Functions
	Optional<Course> getCoursesOffered(School school);

	Course createCourse(School school, Course course);

	Optional<Course> deleteCourse(Course course);

	//Grades
	Grade createGrade(Grade grade);

	Optional<Grade> deleteGrade(Grade grade);

	Optional<Grade> getGrade(Submission submission);

	Grade updateGrade(Grade grade);

	// Transcript Functions
	Optional<Transcript> getTranscript(String email);

	Transcript updateTranscript(Transcript transcript);

	Transcript createTranscript(Transcript transcript);

	// Submission Functions
	Optional<Submission> getSubmission(int studentId, int assignmentId);

	Optional<Submission> deleteSubmission(Submission submission);

	Submission createSubmission(Submission submission);

	Optional<List<Submission>> getSubmissions(Assignment assignment);

	//Assignment Functions
	Optional<Assignment> getAssignment(int assignmentId);

	Optional<Assignment> deleteAssignment(int assignmentId);

	Assignment updateAssignment(Assignment assignment);

	Optional<List<Assignment>> allAssignments(int courseId);
}