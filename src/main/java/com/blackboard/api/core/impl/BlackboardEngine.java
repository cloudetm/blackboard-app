package com.blackboard.api.core.impl;

import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Grade;
import com.blackboard.api.core.model.Submission;
import com.blackboard.api.core.model.Transcript;

import java.util.List;

/**
 * This utility class implements all of the core logic that will be utilized throughout the application, more
 * specifically, the calculations that will have to be made frequently.
 * <p/>
 * Created by ChristopherLicata on 11/14/15.
 *
 * @author Phil Lopreiato <plopreiato@gwmail.gwu.edu>
 */
public class BlackboardEngine
{

    /**
     * Calculate the averages of a list of {@link Grade} objects
     *
     * @param gradeList Grades to average
     *
     * @return Resulting average
     */
    public static double calculateAverage(List<Grade> gradeList)
    {
        return gradeList.stream()
                .mapToInt(Grade::getScore)
                .average()
                .orElse(0);
    }


    /**
     * Compute a weighted average of {@link Grade} objects by the weight of their associated {@link
     * Submission}
     *
     * @param gradeList Grades to average
     *
     * @return Resulting weighted average
     */
    public static double calculateWeightedAverage(List<Grade> gradeList)
    {
        return gradeList.stream()
                .mapToDouble((grade -> grade.getScore() * grade.getAssignment().getWeight()))
                .sum();
    }


    /**
     * Compute a student's GPA from a list of their completed courses Uses the transcript's associated {@link
     * Course} and {@link Grade} for credit weight / result
     * <p/>
     * Returns a percent of total points earned (100 * number of credits per course)
     *
     * @param transcripts Transcripts to average
     *
     * @return Resulting weighted average
     */
    public static double calculateGPA(List<Transcript> transcripts)
    {
        double totalPoints = 0;
        double earnedPoints = 0;
        for (Transcript transcript : transcripts)
        {
            int credits = transcript.getCourse().getCredits();
            int grade = transcript.getGrade().getScore();
            totalPoints += (credits * 100);
            earnedPoints += (credits * grade);
        }
        return transcripts.size() > 0 ? (earnedPoints / totalPoints) * 100 : 0;
    }

}
