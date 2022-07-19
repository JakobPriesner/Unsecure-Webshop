package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.AdminState;
import de.fhws.biedermann.webshop.utils.logic.AdminLogic;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("admin")
public class AdminService {

    @GET
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response checkLogin(@DefaultValue("") @QueryParam("username") String username,
                               @DefaultValue("") @QueryParam("password") String password){

        return new AdminState.Builder()
            .defineResponseBody( AdminLogic.getInstance().login(username, password) )
            .build()
            .ok();
    }

    @GET
    @Path("interface")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRanking(@HeaderParam("sessionid") String session){
        return new AdminState.Builder()
            .withSession( session )
            .defineResponseBody( AdminLogic.getInstance().getRanking(session) )
            .build()
            .ok();
    }

    @GET
    @Path( "level" )
    public Response getLevel(@HeaderParam( "sessionid" ) String session) {
        return new AdminState.Builder()
            .defineResponseBody( AdminLogic.getInstance().getLevel() )
            .build( )
            .ok( );
    }

    @PUT
    @Path( "interface" )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response resetDatabaseShop( @HeaderParam("sessionid") String session ){
        return new AdminState.Builder()
            .withSession( session )
            .defineResponseBody( AdminLogic.getInstance( ).resetDatabaseShop( session ) )
            .build( )
            .ok( );
    }

    @POST
    @Path( "logout" )
    @Consumes( MediaType.APPLICATION_JSON )
    public Response logout(
        @HeaderParam( "sessionid" ) String session
    ){
        return new AdminState.Builder( )
            .withSession( session )
            .defineResponseBody( AdminLogic.getInstance().logout( session ) )
            .build( )
            .noContent( );
    }

    @POST
    @Path("interface")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setLevel(
        @HeaderParam("sessionid") String session,
        @DefaultValue("1") @QueryParam("level") int level
    ){
        return new AdminState.Builder()
            .withSession( session )
            .defineResponseBody( AdminLogic.getInstance().setLevel(level, session) )
            .build( )
            .noContent( );
    }

    @DELETE
    @Path("interface")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response resetDatabaseRanking(@HeaderParam("sessionid") String session){
        return new AdminState.Builder()
            .withSession( session )
            .defineResponseBody( AdminLogic.getInstance().resetDatabaseRanking(session) )
            .build( )
            .noContent( );
    }
}
