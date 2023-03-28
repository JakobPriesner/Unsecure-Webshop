package de.fhws.biedermann.webshop.api.services;


import de.fhws.biedermann.webshop.api.states.BackupState;
import org.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("backup") public class BackupService
{
	@GET
	public Response getBackup(
		@HeaderParam( "uuid" ) final String uuid
	){
		// toDo: move to data handler
		JSONObject response = new JSONObject(  );
		response.put( "backup_version", "1.0" );
		response.put( "current_version", "1.6" );
		//this.responseBody = response;

		return new BackupState.Builder()
			.withUuid( uuid )
			.build()
			.ok();
	}
}
