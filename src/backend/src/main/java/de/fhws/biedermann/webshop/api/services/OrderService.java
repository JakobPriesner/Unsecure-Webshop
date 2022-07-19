package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import de.fhws.biedermann.webshop.models.Order;
import de.fhws.biedermann.webshop.api.states.OrderState;
import de.fhws.biedermann.webshop.utils.logic.OrderLogic;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("orders") public class OrderService
{
	@Context UriInfo uriInfo;

	@GET @Produces(MediaType.APPLICATION_JSON) public Response getOrders(
		@HeaderParam("sessionid") String session
	)
	{
		return new OrderState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getOrders( session ) )
			.build()
			.ok();
	}

	@GET @Produces(MediaType.APPLICATION_JSON) @Path("{id}") public Response getOrderByID(
		@PathParam("id") final int id
	)
	{
		return new OrderState.Builder()
			.withId( id )
			.defineResponseBody( DataHandler.getOrder( id ) )
			.build()
			.ok();
	}

	@POST @Consumes(MediaType.APPLICATION_JSON) public Response createOrder(
		@Context UriInfo uriInfo,
		@DefaultValue( "false" ) @QueryParam("cleanUpWishlist") final boolean cleanup,
		@HeaderParam("sessionid") String session,
		@HeaderParam( "uuid" ) final String uuid,
		@Context HttpServletRequest request,
		final Order order
	)
	{
		return new OrderState.Builder()
			.withSession( session )
			.withUuid( uuid )
			.withModel( order )
			.withUriInfo( OrderLogic.createOrder( cleanup, session, order, uriInfo ) )
			.build()
			.create();
	}
}
