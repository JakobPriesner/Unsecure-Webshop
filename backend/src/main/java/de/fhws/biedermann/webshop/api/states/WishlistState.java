package de.fhws.biedermann.webshop.api.states;

public class WishlistState extends AbstractState
{
	private WishlistState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	@Override void lookForFlaw( )
	{

	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new WishlistState( this );
		}
	}
}
