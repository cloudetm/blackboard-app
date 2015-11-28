package com.blackboard.api.dao.impl;


import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Submission;
import com.blackboard.api.dao.impl.util.ResultSetMocker;
import com.blackboard.api.dao.util.MySQLDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SubmissionMySQLDaoTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Mock
    Submission mockedSubmission;
    @Mock
    Assignment mockedAssignment;
    @Mock
    MySQLDao dao;

    private SubmissionMySQLDao submissionDao;
    private int assignmentId = 123;
    private String studentEmail = "cmlicata@gwu.edu";
    // don't we like this getTime().getTime() thing ??? Java...
    private Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTime().getTime());
    private String submissionFileName = "submission_file_name.txt";

    @Before
    public void setUp() {
        // with this you can test if anything is printed to standard output (like SQLException printing)
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        submissionDao = new SubmissionMySQLDao(dao);
        when(dao.generateTimeStamp()).thenReturn(timeStamp);
        when(mockedSubmission.getAssignment()).thenReturn(mockedAssignment);
        when(mockedSubmission.getStudentEmail()).thenReturn(studentEmail);
        when(mockedAssignment.getAssignmentId()).thenReturn(assignmentId);
        doNothing().when(mockedSubmission).setSubmissionId(anyInt());
        doNothing().when(mockedSubmission).setCurrentTimeStamp(any(Timestamp.class));
        when(mockedSubmission.getSubmissionFileName()).thenReturn(submissionFileName);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    }


    @Test
    public void testCreateSubmission() throws Exception {
        String submissionSQLQuery = new StringBuilder()
                .append("INSERT INTO submissions(assignment_id, student_email, date_time_submitted, ")
                .append("submission_filename")
                .append("(?, ?, ?, ?)").toString();
        Optional<ResultSet> submissionResultSet = new ResultSetMocker().mockSubmissionResultSet(
                assignmentId, studentEmail, timeStamp, submissionFileName
        );

        when(dao.update(submissionSQLQuery, assignmentId, studentEmail, timeStamp, submissionFileName)).thenReturn(submissionResultSet);

        Submission submission = submissionDao.createSubmission(mockedSubmission);

        verify(dao, times(1)).update(submissionSQLQuery, assignmentId, studentEmail, timeStamp, submissionFileName);
        verify(mockedSubmission, times(1)).setSubmissionId(anyInt());
        verify(mockedSubmission, times(1)).setCurrentTimeStamp(timeStamp);
        assertTrue(outContent.toString().length() == 0);
    }

    /*
    How to test that exceptions are actually thrown, even with an implementation where exceptions are
    printed to the standard output
     */
    @Test
    public void testFindStudentSubmissionThrowsAnSQLException() throws Exception {
        String submissionSQLQuery = "SELECT * FROM submissions WHERE student_email = ? AND assignment_id = ?";
        ResultSet hardResult = new ResultSetMocker().mockSubmissionResultSet(
                assignmentId, studentEmail, timeStamp, submissionFileName
        ).get();
        when(hardResult.getInt("submission_id")).thenThrow(new SQLException());
        Optional<ResultSet> result = Optional.of(hardResult);
        when(dao.query(submissionSQLQuery, studentEmail, assignmentId)).thenReturn(result);

        Optional<Submission> submission = submissionDao.findStudentSubmission(studentEmail, assignmentId);

        assertFalse(submission.isPresent());
        verify(dao, times(1)).query(submissionSQLQuery, studentEmail, assignmentId);
        assertTrue(outContent.toString().length() > 0);
    }
}
