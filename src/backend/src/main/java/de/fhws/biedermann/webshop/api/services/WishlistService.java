package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.WishlistState;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import de.fhws.biedermann.webshop.models.ArticleVersion;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

@Path("wishlist") public class WishlistService
{
	@GET @Path("items") public Response getWishlist(@HeaderParam("sessionid") String session)
	{
		return new WishlistState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getWishlist(session) )
			.build()
			.ok();
	}

	@POST @Consumes(MediaType.APPLICATION_JSON) @Path("items") public Response createWishlistItem(
		final ArticleVersion articleVersion, @HeaderParam("sessionid") String session) throws URISyntaxException
	{
		return new WishlistState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.createWishlistItem(articleVersion, session) )
			.build()
			.create();
	}

	@PUT @Consumes(MediaType.APPLICATION_JSON) @Path("items/{id}") public Response modifyWishlistItem(
		@PathParam("id") final int id, final ArticleVersion articleVersion,
		@HeaderParam("sessionid") String session)
	{
		return new WishlistState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.modifyWhishlistItem(articleVersion, session, id) )
			.build()
			.ok();
	}

	@Path("items") @DELETE public Response deleteAllItems(@HeaderParam("sessionid") String session)
	{
		return new WishlistState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.deleteWishlist(session) )
			.build()
			.noContent();
	}

	@DELETE @Path("items/{id}") public Response deleteWishlistItem(@PathParam("id") final int id,
		@HeaderParam("sessionid") String session)
	{
		return new WishlistState.Builder()
			.withSession( session )
			.withId( id )
			.defineResponseBody( DataHandler.deleteWishlistItem(id) )
			.build()
			.noContent();
	}
}
