package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.ContactState;
import de.fhws.biedermann.webshop.models.Contact;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("contact") public class ContactService
{
	@Context protected UriInfo uriInfo;

	@POST @Consumes(MediaType.APPLICATION_JSON) public Response createContact(
		final Contact contact,
		@HeaderParam( "uuid" ) final String uuid,
		@Context UriInfo uriInfo,
		@Context HttpServletRequest request
	)
	{
		return new ContactState.Builder()
			.withUuid( uuid )
			.withModel( contact )
			.defineResponseBody( null )
			.build( )
			.noContent( );
	}
}
