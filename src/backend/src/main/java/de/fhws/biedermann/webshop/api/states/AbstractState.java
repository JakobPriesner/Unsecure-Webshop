package de.fhws.biedermann.webshop.api.states;

import okhttp3.internal.http2.Header;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import java.net.URI;

public abstract class AbstractState
{

	final String session;
	final String uuid;
	final Object modelToWorkWith;
	final long idToWorkWith;
	Object responseBody;
	final boolean validInputData;
	final String invalidInputDataMessage;
	final URI uri;

	final Header header;

	public AbstractState( final AbstractStateBuilder builder ){
		// toDo: in den Builder verlagern
		if ( !builder.validInputData ) throw new NotAuthorizedException( builder.invalidInputDataMessage );

		this.session = builder.session;
		this.uuid = builder.uuid;
		this.modelToWorkWith = builder.modelToWorkWith;
		this.idToWorkWith = builder.idToWorkWith;
		this.responseBody = builder.responseBody;
		this.validInputData = true;
		this.invalidInputDataMessage = builder.invalidInputDataMessage;
		this.uri = builder.uri;
		this.header = builder.header;

	}

	public Response ok(){
		this.execute();
//		if (this.responseBody == null) return Response.status( Response.Status.NOT_FOUND ).build();
		return addHeaderToResponse( Response.ok( this.responseBody ) );
	}

	public Response create(){
		this.execute();
		return handleUriInfo( );
	}

	public Response noContent(){
		this.execute();
		return addHeaderToResponse( Response.noContent( ) );
	}

	public Response statusCode( final int status ) {
		this.execute();
		return addHeaderToResponse( Response.status( status ) );
	}

	private Response handleUriInfo( ) {
		if ( uri == null ) {
			return addHeaderToResponse( Response.status( Response.Status.CREATED ) );
		} else {
			return addHeaderToResponse( Response.created( uri ) );
		}
	}

	private Response addHeaderToResponse ( Response.ResponseBuilder response ){
		if ( this.header != null ) {
			return response.header( header.name.base64(), header.value ).build();
		} else {
			return response.build();
		}
	}

	private void execute() {
		lookForFlaw();
	}

	abstract void lookForFlaw();

}
