package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.database.DataAccessAdminPanel;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("flaws") public class FlawService
{
	@GET @Produces(MediaType.APPLICATION_JSON) public Response checkFlaws(
		@HeaderParam( "uuid" ) final String uuid,
		@HeaderParam("sessionid") final String session,
		@Context HttpServletRequest request)
	{
		DataAccessAdminPanel daap = new DataAccessAdminPanel();
		List<String> findings = daap.checkForNewFindings(uuid);
		return Response.ok(findings).build();
	}
}
