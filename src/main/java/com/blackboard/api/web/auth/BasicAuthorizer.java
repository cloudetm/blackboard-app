package com.blackboard.api.web.auth;

import com.blackboard.api.core.model.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by ChristopherLicata on 11/29/15.
 */
public class BasicAuthorizer
        implements Authorizer<User>
{
    @Override
    public boolean authorize(User principal, String role)
    {
        return role.equals(principal.getRole());
    }
}
