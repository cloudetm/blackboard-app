package com.blackboard.api.core.impl;

import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Grade;
import com.blackboard.api.core.model.Assignment;
import com.blackboard.api.core.model.Transcript;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BlackboardEngineTest
{

    @Test
    public void testCalculateAverageEmpty()
    {
        List<Grade> grades = new ArrayList<>();
        double average;

        average = BlackboardEngine.calculateAverage(grades);
        assertEquals("Average not zero", average, 0d, 0d);
    }


    @Test
    public void testCalculateAverage()
    {
        List<Grade> grades = new ArrayList<>();
        Grade g1 = mock(Grade.class);
        Grade g2 = mock(Grade.class);
        Grade g3 = mock(Grade.class);
        grades.add(g1);
        grades.add(g2);
        grades.add(g3);

        when(g1.getScore()).thenReturn(90);
        when(g2.getScore()).thenReturn(93);
        when(g3.getScore()).thenReturn(86);

        double average = BlackboardEngine.calculateAverage(grades);
        assertEquals("Incorrect average calculated", average, 89.67, 0.01);
    }


    @Test
    public void testCalculateWeightedAverageEmpty()
    {
        List<Grade> grades = new ArrayList<>();
        double average;

        average = BlackboardEngine.calculateWeightedAverage(grades);
        assertEquals("Average not zero", average, 0d, 0d);
    }


    @Test
    public void testCalculateWeightedAverage()
    {
        List<Grade> grades = new ArrayList<>();
        Grade g1 = mock(Grade.class);
        Grade g2 = mock(Grade.class);
        Grade g3 = mock(Grade.class);
        Assignment s1 = mock(Assignment.class);
        Assignment s2 = mock(Assignment.class);
        Assignment s3 = mock(Assignment.class);
        grades.add(g1);
        grades.add(g2);
        grades.add(g3);

        when(g1.getScore()).thenReturn(90);
        when(g2.getScore()).thenReturn(93);
        when(g3.getScore()).thenReturn(86);
        when(g1.getAssignment()).thenReturn(s1);
        when(g2.getAssignment()).thenReturn(s2);
        when(g3.getAssignment()).thenReturn(s3);
        when(s1.getWeight()).thenReturn(.3);
        when(s2.getWeight()).thenReturn(.5);
        when(s3.getWeight()).thenReturn(.2);

        double average = BlackboardEngine.calculateWeightedAverage(grades);
        assertEquals("Wrong weighted average", average, 90.7, 0.001);
    }


    @Test
    public void testCalculateGPAEmpty()
    {
        List<Transcript> transcripts = new ArrayList<>();
        double average;

        average = BlackboardEngine.calculateGPA(transcripts);
        assertEquals("GPA Not 0", average, 0d, 0d);
    }


    @Test
    public void testCalculateGPA()
    {
        List<Transcript> transcripts = new ArrayList<>();
        Transcript t1 = mock(Transcript.class);
        Transcript t2 = mock(Transcript.class);
        Transcript t3 = mock(Transcript.class);
        Course c1 = mock(Course.class);
        Course c2 = mock(Course.class);
        Course c3 = mock(Course.class);
        transcripts.add(t1);
        transcripts.add(t2);
        transcripts.add(t3);

        when(c1.getCredits()).thenReturn(3);
        when(c2.getCredits()).thenReturn(3);
        when(c3.getCredits()).thenReturn(4);
        when(t1.getGrade()).thenReturn(90.0);
        when(t1.getCourse()).thenReturn(c1);
        when(t2.getGrade()).thenReturn(93.0);
        when(t2.getCourse()).thenReturn(c2);
        when(t3.getGrade()).thenReturn(86.0);
        when(t3.getCourse()).thenReturn(c3);

        double average = BlackboardEngine.calculateGPA(transcripts);
        assertEquals("Incorrect GPA", average, 89.3, 0.01);
    }
}