package com.blackboard.web.json;

import com.blackboard.api.core.model.Transcript;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class TranscriptJson
{
    private int transcriptId;

    private String studentEmail;

    private String semester;

    private int year;

    private CourseJson course;

    private double grade;


    public TranscriptJson()
    {
    }


    public TranscriptJson(Transcript transcript)
    {
        transcriptId = transcript.getTranscriptId();
        studentEmail = transcript.getStudentEmail();
        semester = transcript.getSemester().toString();
        year = transcript.getYear();
        course = new CourseJson(transcript.getCourse());
        grade = transcript.getGrade();
    }


    @JsonProperty
    public int getTranscriptId()
    {
        return transcriptId;
    }


    @JsonProperty
    public String getStudentEmail()
    {
        return studentEmail;
    }


    @JsonProperty
    public String getSemester()
    {
        return semester;
    }


    @JsonProperty
    public int getYear()
    {
        return year;
    }


    @JsonProperty
    public CourseJson getCourse()
    {
        return course;
    }


    @JsonProperty
    public double getGrade()
    {
        return grade;
    }

}
