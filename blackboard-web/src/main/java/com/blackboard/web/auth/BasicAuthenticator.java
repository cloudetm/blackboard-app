package com.blackboard.web.auth;

import com.blackboard.api.core.model.User;
import com.blackboard.api.dao.service.UserService;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Created by ChristopherLicata on 11/29/15.
 */
public class BasicAuthenticator
        implements Authenticator<BasicCredentials, User>
{
    private UserService userService;


    public BasicAuthenticator(UserService userService)
    {
        this.userService = userService;
    }


    @Override
    public Optional<User> authenticate(BasicCredentials credentials)
            throws AuthenticationException
    {
        java.util.Optional<User> result = userService.getUser(credentials.getUsername());
        if (result.isPresent())
        {
            User u = result.get();
            if (User.validatePassword(credentials.getPassword(), u))
            {
                return Optional.of(u);
            }
            else
            {
                return Optional.absent();
            }
        }
        else
        {
            return Optional.absent();
        }
    }
}
