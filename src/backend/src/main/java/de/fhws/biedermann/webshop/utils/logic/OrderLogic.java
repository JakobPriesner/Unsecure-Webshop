package de.fhws.biedermann.webshop.utils.logic;

import de.fhws.biedermann.webshop.models.Order;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderLogic
{
	public static URI createOrder( boolean cleanup, String session, Order order, UriInfo uriInfo ) {
		if ( StringUtils.isEmpty( order.getDate( ) ) ) order.setDate( LocalDateTime.now( ).format( DateTimeFormatter.ISO_LOCAL_DATE ) );
		int orderNumber = DataHandler.createOrder(order, session, cleanup);
		return uriInfo.getAbsolutePathBuilder().path("id").path(String.valueOf(orderNumber)).build();
	}
}
