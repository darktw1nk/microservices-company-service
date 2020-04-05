package com.winter.service.rest;

import com.winter.model.User;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Traced
@Path("/api/user")
@RegisterRestClient
public interface UserService {

    @GET
    @Path("/hash/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUserByHash(@PathParam String id);

}
