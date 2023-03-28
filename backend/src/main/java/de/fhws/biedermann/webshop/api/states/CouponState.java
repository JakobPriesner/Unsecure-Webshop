package de.fhws.biedermann.webshop.api.states;

import de.fhws.biedermann.webshop.models.Coupon;
import de.fhws.biedermann.webshop.utils.SecurityBreachDetection;
import de.fhws.biedermann.webshop.utils.handler.FlawHandler;

import javax.ws.rs.BadRequestException;

public class CouponState extends AbstractState
{
	private CouponState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	@Override void lookForFlaw( )
	{
		if ( !(this.modelToWorkWith instanceof String modelToWorkWith) ){
			throw new BadRequestException( "Invalid model was given!" );
		}
		if ( SecurityBreachDetection.guessCoupon( modelToWorkWith ) ) {
			FlawHandler.guessCoupon( uuid );
			this.responseBody = new Coupon( modelToWorkWith, 40, true );
		}
	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new CouponState( this );
		}
	}
}
