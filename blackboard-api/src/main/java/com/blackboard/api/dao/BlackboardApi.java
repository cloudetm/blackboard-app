package com.blackboard.api.dao;

import com.blackboard.api.dao.service.*;
import com.blackboard.api.dao.service.impl.*;
import com.blackboard.api.dao.util.MySQLDao;

/**
 * Created by ChristopherLicata on 11/28/15.
 */
public class BlackboardApi
{
    final AssignmentService assignmentService;

    final CourseService courseService;

    final GradeService gradeService;

    final InstructorService instructorService;

    final SchoolService schoolService;

    final StudentService studentService;

    final SubmissionService submissionService;

    final TranscriptService transcriptService;

    final UserService userService;

    final MySQLDao dao;


    public BlackboardApi(MySQLDao dao)
    {
        this.dao = dao;
        assignmentService = new AssignmentDaoService(dao);
        courseService = new CourseDaoService(dao);
        gradeService = new GradeDaoService(dao);
        instructorService = new InstructorDaoService(dao);
        schoolService = new SchoolDaoService(dao);
        studentService = new StudentDaoService(dao);
        submissionService = new SubmissionDaoService(dao);
        transcriptService = new TranscriptDaoService(dao);
        userService = new UserDaoService(dao);
    }


    public static void main(String[] args)
    {
        MySQLDao dao = new MySQLDao();
        BlackboardApi api = new BlackboardApi(dao);

    }
}
