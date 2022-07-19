package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.CartState;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import de.fhws.biedermann.webshop.models.ArticleVersion;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("cart") public class CartService
{
	@Context protected UriInfo uriInfo;

	@Path("items") @GET @Produces(MediaType.APPLICATION_JSON) public Response getCartItems(
		@HeaderParam("sessionid") String session,
		@Context HttpServletRequest request)
	{
		return new CartState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getCartItems(session) )
			.build( )
			.ok( );
	}

	@Path("items") @POST @Consumes(MediaType.APPLICATION_JSON) public Response createCartItem(
		@HeaderParam("sessionid") String session,
		final ArticleVersion articleVersion)
	{
		return new CartState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.createCartItem(articleVersion, session) )
			.build( )
			.noContent( );
	}

	@Path("items/{id}") @PUT @Consumes(MediaType.APPLICATION_JSON) public Response modifyCartItem(
		@PathParam("id") final int id, final ArticleVersion articleVersion,
		@HeaderParam("sessionid") String session)
	{
		return new CartState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.modifyCartItem(id, articleVersion) )
			.build( )
			.ok( );
	}

	@Path("items/{id}") @DELETE @Consumes(MediaType.APPLICATION_JSON) public Response deleteCartItem(
		@PathParam("id") final int id,
		@HeaderParam("sessionid") String session)
	{
		return new CartState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.deleteCartItem(session, id) )
			.build( )
			.noContent( );
	}
}
