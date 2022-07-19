package de.fhws.biedermann.webshop.api.services;

import de.fhws.biedermann.webshop.api.states.CouponState;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import de.fhws.biedermann.webshop.utils.handler.FlawHandler;
import de.fhws.biedermann.webshop.utils.SecurityBreachDetection;
import de.fhws.biedermann.webshop.models.Coupon;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("coupons") public class CouponService
{
	@Path("{name}") @GET @Produces(MediaType.APPLICATION_JSON) public Response getCoupon(
		@PathParam("name") final String name,
		@HeaderParam( "uuid" ) final String uuid,
		@Context HttpServletRequest request
	)
	{
		return new CouponState.Builder()
			.withUuid( uuid )
			.withModel( name )
			.defineResponseBody( DataHandler.getCoupon(name) )
			.build( )
			.ok( );
	}
}
