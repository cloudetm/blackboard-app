package com.blackboard.web.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;

/**
 * http://software.dzhuvinov.com/cors-filter.html
 * <p>
 * Created by ChristopherLicata on 11/30/15.
 */
public class CORSFilter
        implements ContainerResponseFilter
{

    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws
            IOException
    {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        headers.add("Access-Control-Allow-Credentials", "true");
    }

}