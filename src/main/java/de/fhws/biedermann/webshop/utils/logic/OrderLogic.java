package de.fhws.biedermann.webshop.utils.logic;

import de.fhws.biedermann.webshop.models.Order;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.BadRequestException;
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

	public static int getShippingCosts( int deviceCount, String destinationCountry ) {
		if (destinationCountry.isEmpty()){
			throw new BadRequestException("You have to specify a destination country");
		}
		if (deviceCount < 1){
			throw new BadRequestException("Invalid device count!");
		}

		int phoneShippingCostInEuros;
		if (deviceCount > 20){
			phoneShippingCostInEuros = 2195;
		} else if (deviceCount > 10) {
			phoneShippingCostInEuros = 1195;
		} else {
			phoneShippingCostInEuros = 845;
		}

		return destinationCountry.equals("Deutschland") ? phoneShippingCostInEuros : phoneShippingCostInEuros + 1000;
	}
}
