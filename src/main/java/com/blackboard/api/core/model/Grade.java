package com.blackboard.api.core.model;

/**
 * This is the Grade Model Class that maps to the grades table in the database.
 * <p/>
 * Created by ChristopherLicata on 11/14/15.
 */
public class Grade
{
    private int score;

    private Submission submission;


    /**
     * Full constructor for the Grade Model Object.
     *
     * @param score      The actual numerical value calculated based on the student's performance on the assessment in question
     * @param submission The weight of that grade as it pertains to the course as a whole.
     */

    public Grade(int score, Submission submission)
    {
        this.score = score;
        this.submission = submission;
    }


    public int getScore()
    {
        return score;
    }


    public void setScore(int score)
    {
        this.score = score;
    }


    public Submission getSubmission()
    {
        return submission;
    }


    public void setSubmission(Submission submission)
    {
        this.submission = submission;
    }
}
