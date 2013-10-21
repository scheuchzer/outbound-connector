package com.ja.rsc.demo;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.ja.rsc.echo.api.EchoConnection;
import com.ja.rsc.echo.api.EchoConnectionFactory;
import com.ja.rsc.echo.api.EchoResponse;

@Path("echo2")
@Stateless
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class Echo2Resource {

	@Resource(name = "jca/echo2")
	private EchoConnectionFactory echo2;

	@Path("{text}")
	@GET
	public String echo(@PathParam("text") String text) {
		try (EchoConnection connection = echo2.getConnection()) {
			EchoResponse response = connection.echo(text);
			return response.toString();
		} catch (Exception e) {
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

}
