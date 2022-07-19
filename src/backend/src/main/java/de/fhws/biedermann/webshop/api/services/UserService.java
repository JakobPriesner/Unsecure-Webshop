package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.UserState;
import de.fhws.biedermann.webshop.models.*;
import de.fhws.biedermann.webshop.utils.*;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import de.fhws.biedermann.webshop.utils.logic.AuthenticationLogic;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static de.fhws.biedermann.webshop.api.states.UserState.createNewUser;

@Path("users") public class UserService
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(
		@HeaderParam("sessionid") String session)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getUser(session) )
			.build()
			.ok();
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(
		@PathParam("id") final int id)
	{
		return new UserState.Builder()
			.defineResponseBody( DataHandler.getUserById(id) )
			.build()
			.ok();
	}

	@POST
	@Path("register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(
		@Context UriInfo uriInfo,
		final User user,
		@Context HttpServletRequest request,
		@HeaderParam( "uuid" ) final String uuid
	)
	{
		return new UserState.Builder()
			.withUuid( uuid )
			.defineResponseBody( AuthenticationLogic.register( user, uuid ) )
			.build( )
			.ok( );
	}

	@GET
	@Path("login")
	@Produces(MediaType.TEXT_PLAIN)
	public Response checkLogin(
		@DefaultValue("") @QueryParam("mail") String mail,
		@DefaultValue("") @QueryParam("password") String password,
		@HeaderParam( "uuid" ) final String uuid
	) {
		return new UserState.Builder()
			.withUuid( uuid )
			.defineResponseBody( AuthenticationLogic.login( mail, password, uuid ) )
			.build( )
			.ok( );
	}

	@POST
	@Path("logout")
	public Response logout(
		@HeaderParam("sessionid") final String session
	) {
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( AuthenticationLogic.logout( session ) )
			.build( )
			.ok( );
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifyUser(
		@HeaderParam("sessionid") String session,
		@HeaderParam( "uuid" ) final String uuid,
		final User user,
		@Context HttpServletRequest request)
	{
		final UserVulnerability userVul = VulnerabilityCheck.checkSqlInjection( user.getDescription() );

		return new UserState.Builder()
			.withSession( session )
			.withUuid( uuid )
			.withModel( user )
			.defineResponseBody( userVul != null ? userVul : DataHandler.modifyUser(session, user) )
			.build()
			.ok();

	}

	@DELETE
	public Response deleteUser(
		@HeaderParam("sessionid") String session)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.deleteUser(session) )
			.build()
			.noContent();
	}

	@Path( "{id:\\d+}" )
	@DELETE
	public Response deleteUserById(
		@DefaultValue( "" ) @HeaderParam("sessionid") String session,
		@PathParam( "id" ) final int id,
		@Context HttpServletRequest req
	)
	{
		return new UserState.Builder()
			.withSession( session )
			.withId( id )
			.defineResponseBody( DataHandler.deleteUserById(session, id, req.getRemoteAddr()) )
			.build()
			.noContent();
	}

	@Path("payment")
	@GET @Produces(MediaType.APPLICATION_JSON)
	public Response getUserPayment(
		@HeaderParam("sessionid") String session)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getUserPayment(session) )
			.build()
			.ok();
	}

	@Path("payment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUserPayment(
		@HeaderParam("sessionid") String session,
		@Context UriInfo uriInfo,
		final Payment payment)
	{
		return new UserState.Builder()
			.withSession( session )
			.withModel( payment )
			.withUriInfo( uriInfo.getAbsolutePathBuilder().build(  ) )
			.defineResponseBody( DataHandler.createUserPayment(session, payment) )
			.build()
			.create();
	}

	@Path("payment")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifyUserPayment(
		@HeaderParam("sessionid") String session,
		final Payment payment)
	{
		// DataHandler.modifyUserPayment(session, payment);
		return Response.status( 501 ).build();
	}

	@Path("payment")
	@DELETE
	public Response deleteUserPayment(
		@HeaderParam("sessionid") String session)
	{
		// DataHandler.deleteUserPayment(session);
		return Response.status( 501 ).build();
	}

	@GET
	@Path("addresses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUserAddresses(
		@HeaderParam("sessionid") String session)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getAllUserAddresses(session) )
			.build()
			.ok();
	}

	@GET @Path("/addresses/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserAddress(
		@HeaderParam("sessionid") String session,
		@PathParam("id") final int id)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getUserAddress(session, id) )
			.build()
			.ok();
	}

	@Path("addresses")
	@POST @Produces(MediaType.APPLICATION_JSON)
	public Response createUserAddress(
		@HeaderParam("sessionid") String session,
		@Context UriInfo uriInfo,
		final Address address)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.createAddress( session, address ) )
			.build()
			.ok();
	}

	@Path("addresses/{id}")
	@PUT @Consumes(MediaType.APPLICATION_JSON)
	public Response modifyUserAddress(
		@HeaderParam("sessionid") final String session,
		@PathParam("id") final int id,
		final Address  address)
	{
		if (id != address.getId()){ //todo überprüfen
			return Response.status( 422 ).build();
		}
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.modifyAddress( address, session ) )
			.build()
			.ok();
	}

	@Path("addresses/{id}")
	@DELETE
	public Response deleteUserAddress(
		@HeaderParam("sessionid") String session,
		@PathParam("id") final int id)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.deleteAddress( session, id ) )
			.build()
			.noContent();
	}

	@Path("mail")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserMail(
		@HeaderParam("sessionid") String session)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.getUserMail(session) )
			.build()
			.ok();
	}

	@Path("mail")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUserMail(
		@HeaderParam("sessionid") String session,
		@Context UriInfo uriInfo,
		final String mail)
	{
		return new UserState.Builder()
			.withSession( session )
			.withUriInfo( uriInfo.getAbsolutePathBuilder().build(  ) )
			.defineResponseBody( DataHandler.createUserMail(session, mail) )
			.build()
			.create();
	}

	@Path("newsletter")
	@GET
	public Response checkNewsletter(
		@HeaderParam("sessionid") String session
	)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.checkNewsletter(session) )
			.build()
			.ok();
	}

	@Path("newsletter")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response turnOnNewsletter(
		@HeaderParam("sessionid") String session,
		@HeaderParam( "uuid" ) final String uuid,
		@Context HttpServletRequest request,
		final Nletter nletter
	)
	{
		return new UserState.Builder()
			.withSession( session )
			.withUuid( uuid )
			.withModel( nletter )
			.defineResponseBody( DataHandler.turnOnNewsletter(session) )
			.build()
			.noContent();
	}

	@Path("newsletter")
	@DELETE
	public Response turnOffNewsletter(
		@HeaderParam("sessionid") String session
	)
	{
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( DataHandler.turnOffNewsletter(session) )
			.build()
			.noContent();
	}

	@Path("password")
	@Consumes(MediaType.TEXT_PLAIN)
	@PUT
	public Response modifyPassword(
		@HeaderParam("sessionid") String session,
		final String password)
	{
		// TODO something with password here
		//toDO get user with sessionid
		// modify in database
		return new UserState.Builder()
			.withSession( session )
			.defineResponseBody( password )
			.build()
			.ok();
	}

	@Path( "me" )
	@GET
	public Response getNewUuid() {
		return new UserState.Builder()
			.defineResponseBody( createNewUser() )
			.build()
			.ok();
	}

}
