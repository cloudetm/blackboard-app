package com.blackboard.api.dao.service;

import com.blackboard.api.core.Season;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Transcript;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public interface TranscriptService
{
    public List<Transcript> getStudentTranscripts(String studentEmail);

    public Optional<Transcript> getTranscriptById(int transcriptId);

    public Transcript createTranscript(String studentEmail, Season semester, int year, Course course);

    public Transcript updateTranscript(
            int transcriptId, String studentEmail, Season season, int year,
            Course course, double grade);

    public Optional<Transcript> deleteTranscript(int transcriptId);
}
