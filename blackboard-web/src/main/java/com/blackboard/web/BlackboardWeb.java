package com.blackboard.web;

import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.service.*;
import com.blackboard.api.dao.service.impl.*;
import com.blackboard.api.dao.util.MySQLDao;
import com.blackboard.web.auth.BasicAuthenticator;
import com.blackboard.web.auth.BasicAuthorizer;
import com.blackboard.web.filter.CORSFilter;
import com.blackboard.web.health.BlackboardHealthCheck;
import com.blackboard.web.resource.*;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by ChristopherLicata on 12/1/15.
 */
public class BlackboardWeb
        extends Application<BlackboardConfiguration>
{
    public static void main(String[] args)
            throws Exception
    {
        new BlackboardWeb().run(args);
    }


    @Override
    public void initialize(Bootstrap<BlackboardConfiguration> config)
    {
        //        config.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }


    @Override
    public void run(BlackboardConfiguration config, Environment environment)
            throws Exception
    {

        final MySQLDao dao = new MySQLDao(config.getDb(), config.getDriver());
        final AssignmentService assignmentService = new AssignmentDaoService(dao);
        final CourseService courseService = new CourseDaoService(dao);
        final GradeService gradeService = new GradeDaoService(dao);
        final InstructorService instructorService = new InstructorDaoService(dao);
        final SchoolService schoolService = new SchoolDaoService(dao);
        final StudentService studentService = new StudentDaoService(dao);
        final SubmissionService submissionService = new SubmissionDaoService(dao);
        final TranscriptService transcriptService = new TranscriptDaoService(dao);
        final UserService userService = new UserDaoService(dao);
        final BlackboardHealthCheck healthCheck = new BlackboardHealthCheck(dao);
        environment.healthChecks().register("blackboard", healthCheck);

        final UserResource userResource = new UserResource(userService, studentService, instructorService);
        environment.jersey().setUrlPattern("/api/v1/*");
        environment.jersey().register(userResource);
        environment.jersey().register(
                new AuthDynamicFeature(
                        new BasicCredentialAuthFilter.Builder<User>()
                                .setAuthenticator(new BasicAuthenticator(userService))
                                .setAuthorizer(new BasicAuthorizer())
                                .setRealm("Enter the login information of the user.")
                                .buildAuthFilter())
        );
        environment.jersey().register(CORSFilter.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        final SchoolResource schoolResource = new SchoolResource(schoolService);
        environment.jersey().register(schoolResource);

        final StudentResource studentResource = new StudentResource(studentService, userService);
        environment.jersey().register(studentResource);

        final CourseResource courseResource = new CourseResource(courseService, instructorService, schoolService);
        environment.jersey().register(courseResource);

        final TranscriptResource transcriptResource = new TranscriptResource(transcriptService, courseService);
        environment.jersey().register(transcriptResource);

        final AssignmentResource assignmentResource = new AssignmentResource(assignmentService, courseService,
                                                                             instructorService);
        environment.jersey().register(assignmentResource);
    }
}

