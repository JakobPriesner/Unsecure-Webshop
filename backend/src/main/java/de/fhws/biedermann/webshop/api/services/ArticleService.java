package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.ArticleState;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("articles") public class ArticleService
{
	@GET @Produces(MediaType.APPLICATION_JSON) public Response getArticles(
		@DefaultValue("1") @QueryParam("page") final int page,
		@DefaultValue("false") @QueryParam("specifications") final boolean specifications,
		@DefaultValue("") @QueryParam("search") final String search

	)
	{
		return new ArticleState.Builder()
			.defineResponseBody( DataHandler.getArticles(page, search, specifications) )
			.build( )
			.ok( );
	}


	@GET @Path("{id}") @Produces(MediaType.APPLICATION_JSON) public Response getArticleByID(
		@PathParam("id") final int id
	)
	{
		return new ArticleState.Builder()
			.defineResponseBody( DataHandler.getArticle(id) )
			.build( )
			.ok( );
	}
}
