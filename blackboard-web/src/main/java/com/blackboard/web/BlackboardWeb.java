package com.blackboard.web;

import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.BlackboardApi;
import com.blackboard.api.dao.util.MySQLDao;
import com.blackboard.web.auth.BasicAuthenticator;
import com.blackboard.web.auth.BasicAuthorizer;
import com.blackboard.web.filter.CORSFilter;
import com.blackboard.web.health.BlackboardHealthCheck;
import com.blackboard.web.resource.UserResource;
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
        final BlackboardApi api = new BlackboardApi(dao);
        final BlackboardHealthCheck healthCheck = new BlackboardHealthCheck(dao);
        environment.healthChecks().register("blackboard", healthCheck);

        final UserResource resource = new UserResource(api);
        environment.jersey().setUrlPattern("/api/v1/*");
        environment.jersey().register(resource);
        environment.jersey().register(
                new AuthDynamicFeature(
                        new BasicCredentialAuthFilter.Builder<User>()
                                .setAuthenticator(new BasicAuthenticator(api))
                                .setAuthorizer(new BasicAuthorizer())
                                .setRealm("as532zb^!FyzhD8!Xy98x92oyUBQWBfmdAJHMWJWUw6RRkkKTzVrXJT4&Y32ebV*")
                                .buildAuthFilter())
        );
        environment.jersey().register(CORSFilter.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }
}

