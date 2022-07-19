package de.fhws.biedermann.webshop.api.states;

import de.fhws.biedermann.webshop.utils.ErrorMessages;
import okhttp3.internal.http2.Header;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractStateBuilder
{

	String session = "";
	String uuid = "";
	Object responseBody;
	Object modelToWorkWith;
	int idToWorkWith;
	boolean validInputData = true;
	String invalidInputDataMessage;
	URI uri;
	Header header;


	public AbstractState build(){
		throw new InternalServerErrorException(  );
	}

	public AbstractStateBuilder withSession( final String session ){
		this.session = session;
		if ( !this.validInputData ){
			return this;
		} else if ( StringUtils.isEmpty( session ) ) {
			this.invalidInputDataMessage = ErrorMessages.MISSING_SESSION;
			this.validInputData = false;
		}

		return this;
	}

	public AbstractStateBuilder withUuid( final String uuid ){
		this.uuid = uuid;
		if ( !this.validInputData ){
			return this;
		} else if ( StringUtils.isEmpty( uuid ) ) {
			this.invalidInputDataMessage = ErrorMessages.MISSING_UUID;
			this.validInputData = false;
		}

		return this;
	}

	public AbstractStateBuilder withModel( final Object model ){
		if (model == null) {
			throw new BadRequestException( "" );
		}
		this.modelToWorkWith = model;
		return this;
	}

	public AbstractStateBuilder withId( final int id ){
		this.idToWorkWith = id;
		return this;
	}

	public AbstractStateBuilder withCheckId( final int modelId, final int requestedId ) {
		if ( modelId != requestedId ) throw new BadRequestException( "Model id does not match the id from the request!" );
		return this;
	}

	public AbstractStateBuilder defineResponseBody( final Object responseBody ){
		// use this function as the last call before build() !!!
		this.responseBody = responseBody;
		return this;
	}

	public AbstractStateBuilder withUriInfo( final URI uriInfo ){
		this.uri = uriInfo;
		return this;
	}

	public AbstractStateBuilder withNotNull( final Object... objects ){
		if ( Arrays.stream( objects ).anyMatch( Objects::isNull ) ) throw new BadRequestException( "" );
		return this;
	}

	public AbstractStateBuilder withAuthorize( final boolean authorized ) {
		if ( !authorized ) throw new ForbiddenException( );
		return this;
	}

	public AbstractStateBuilder withHeader( final Header header ) {
		this.header = header;
		return this;
	}

}
