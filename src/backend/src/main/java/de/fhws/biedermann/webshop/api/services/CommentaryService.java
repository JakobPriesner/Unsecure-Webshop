package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.CommentaryState;
import de.fhws.biedermann.webshop.database.DataAccessShopDatabase;
import de.fhws.biedermann.webshop.models.Commentary;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("comments")
public class CommentaryService {


	@POST public Response postCommentary(
		final Commentary commentary,
		@QueryParam( "articleId" ) final int articleId,
		@HeaderParam( "sessionId" ) final String sessionId,
		@HeaderParam( "uuid" ) final String uuid
	) {
		DataAccessShopDatabase daap = new DataAccessShopDatabase();

		return new CommentaryState.Builder()
			.withUuid( uuid )
			.withModel( commentary )
			.defineResponseBody( daap.postCommentary(commentary, articleId, sessionId) )
			.build( )
			.noContent( );
	}

}
