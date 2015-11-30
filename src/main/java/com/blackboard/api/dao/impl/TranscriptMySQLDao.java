package com.blackboard.api.dao.impl;

import com.blackboard.api.core.Season;
import com.blackboard.api.core.model.Course;
import com.blackboard.api.core.model.Transcript;
import com.blackboard.api.dao.util.MySQLDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.blackboard.api.dao.util.MySQLDao.printSQLException;

public class TranscriptMySQLDao
        implements TranscriptDao
{
    private MySQLDao dao;


    public TranscriptMySQLDao(MySQLDao dao)
    {
        this.dao = dao;
    }


    @Override
    public List<Transcript> findTranscriptsByStudentEmail(String studentEmail)
    {
        String q = "SELECT * FROM transcripts WHERE student_email = ?";
        try
        {
            ResultSet result = dao.query(q, studentEmail).get();

            ArrayList<Transcript> transcripts = new ArrayList<>();
            while (result.next())
            {
                int transcriptId = result.getInt("transcript_id");
                int courseId = result.getInt("course_id");
                Season semester = Season.valueOf(result.getString("semester"));
                int year = result.getInt("year");
                int schoolId = result.getInt("school_id");
                double grade = result.getDouble("grade");

                CourseMySQLDao courseDao = new CourseMySQLDao(dao);
                Course course = courseDao.findCourseById(courseId).get();

                if (grade != -1.0)
                {
                    transcripts
                            .add(new Transcript(transcriptId, studentEmail, semester, year, course, grade));
                }
                else
                {
                    transcripts
                            .add(new Transcript(transcriptId, studentEmail, semester, year, course));
                }
            }
            return transcripts;
        }
        catch (SQLException e)
        {
            printSQLException(e);
            return new ArrayList<>();
        }
    }


    @Override
    public Optional<Transcript> findTranscriptByTranscriptId(int transcriptId)
    {
        String q = "SELECT * FROM transcripts WHERE transcriptId = ? LIMIT 1";
        return dao.query(q, transcriptId).flatMap(r -> {
            try
            {
                if (r.next())
                {
                    String studentEmail = r.getString("student_email");
                    String semester = r.getString("semester");
                    int courseId = r.getInt("course_id");
                    double grade = r.getDouble("grade");
                    int year = r.getInt("year");

                    CourseMySQLDao courseDao = new CourseMySQLDao(dao);
                    Course course = courseDao.findCourseById(courseId).get();

                    return Optional.of(new Transcript(transcriptId, studentEmail, Season.valueOf(semester),
                                                      year, course, grade));
                }
                else
                    return Optional.empty();
            }
            catch (SQLException e)
            {
                printSQLException(e);
                return Optional.empty();
            }
        });

    }


    @Override
    public Transcript updateTranscript(Transcript transcript)
    {
        String query = new StringBuilder("UPDATE transcript SET student_email = ?, course_id = ?, semester = ?")
                .append("school_id = ?, grade = ?, year = ? WHERE transcript_id = ?").toString();

        int transcriptId = transcript.getTranscriptId();
        String studentEmail = transcript.getStudentEmail();
        String semester = transcript.getSemester().toString();
        int year = transcript.getYear();
        int schoolId = transcript.getCourse().getSchool().getSchoolId();
        int courseId = transcript.getCourse().getCourseId();

        if (transcript.getGrade() != 0.0d)
        {
            double grade = transcript.getGrade();
            dao.update(query, studentEmail, courseId, schoolId, grade, year, semester, year);
        }
        else
        {
            dao.update(query, studentEmail, courseId, schoolId, -1.0, year, semester, year);
        }
        return transcript;

    }


    @Override
    public Transcript createTranscript(Transcript transcript)
    {
        String query = new StringBuilder()
                .append("INSERT INTO transcripts(student_email, course_id, school_id, semester, year")
                .append("(?, ?, ?, ?, ?)").toString();
        String studentEmail = transcript.getStudentEmail();
        int courseId = transcript.getCourse().getCourseId();
        int schoolId = transcript.getCourse().getSchool().getSchoolId();
        String semester = transcript.getSemester().toString();
        int year = transcript.getYear();

        Optional<ResultSet> transcriptId = dao
                .update(query, studentEmail, courseId, schoolId, semester, year);

        try
        {
            transcript.setTranscriptId(transcriptId.get().getInt(1));
        }
        catch (SQLException e)
        {
            printSQLException(e);
        }

        return transcript;
    }


    @Override
    public Optional<Transcript> deleteTranscript(int transcriptId)
    {
        return findTranscriptByTranscriptId(transcriptId).map(transcript -> {
            dao.update("DELETE FROM transcripts WHERE transcriptId = ?", transcript.getTranscriptId());
            return transcript;
        });
    }
}
