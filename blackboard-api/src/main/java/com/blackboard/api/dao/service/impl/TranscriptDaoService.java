package com.blackboard.api.dao.service.impl;

import com.blackboard.api.core.Season;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Transcript;
import com.blackboard.api.dao.impl.CourseMySQLDao;
import com.blackboard.api.dao.impl.GradeMySQLDao;
import com.blackboard.api.dao.impl.TranscriptMySQLDao;
import com.blackboard.api.dao.impl.interfaces.CourseDao;
import com.blackboard.api.dao.impl.interfaces.GradeDao;
import com.blackboard.api.dao.impl.interfaces.TranscriptDao;
import com.blackboard.api.dao.service.TranscriptService;
import com.blackboard.api.dao.util.MySQLDao;

import java.util.List;
import java.util.Optional;

/**
 * Created by ChristopherLicata on 12/2/15.
 */
public class TranscriptDaoService
        implements TranscriptService
{
    private MySQLDao dao;

    final TranscriptDao transcriptDao;

    final CourseDao courseDao;

    final GradeDao gradeDao;


    public TranscriptDaoService(MySQLDao dao)
    {
        this.dao = dao;
        transcriptDao = new TranscriptMySQLDao(dao);
        courseDao = new CourseMySQLDao(dao);
        gradeDao = new GradeMySQLDao(dao);
    }


    @Override
    public List<Transcript> getStudentTranscripts(String studentEmail)
    {
        return transcriptDao.findTranscriptsByStudentEmail(studentEmail);
    }


    public Optional<Transcript> getTranscriptById(int transcriptId)
    {
        return transcriptDao.findTranscriptByTranscriptId(transcriptId);
    }


    @Override
    public Transcript createTranscript(String studentEmail, Season semester, int year, Course course)
    {
        Transcript transcript = new Transcript(studentEmail, semester, year, course);

        return transcriptDao.createTranscript(transcript);
    }


    @Override
    public Transcript updateTranscript(
            int transcriptId, String studentEmail, Season season, int year,
            Course course, double grade)
    {
        Transcript transcript = new Transcript(transcriptId, studentEmail, season, year, course, grade);
        return transcriptDao.updateTranscript(transcript);
    }


    @Override
    public Optional<Transcript> deleteTranscript(int transcriptId)
    {

        if (!transcriptDao.findTranscriptByTranscriptId(transcriptId).isPresent())
        {
            throw new IllegalArgumentException(
                    "There is no Transcript that matches that id in the system.");
        }
        return transcriptDao.deleteTranscript(transcriptId);
    }

}
