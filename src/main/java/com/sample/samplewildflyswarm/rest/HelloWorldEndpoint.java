package com.sample.samplewildflyswarm.rest;


import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import com.sample.samplewildflyswarm.dao.HelloDao;


@Path("/hello")
public class HelloWorldEndpoint {

	@Inject
	HelloDao dao;

	@GET
	@Produces("text/plain")
	public Response doGet() {
		return Response.ok("Hello from WildFly Swarm! ("+dao.getFirstTenant()+")").build();
	}
}