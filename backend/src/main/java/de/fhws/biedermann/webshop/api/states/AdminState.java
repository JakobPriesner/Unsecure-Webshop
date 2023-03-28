package de.fhws.biedermann.webshop.api.states;


public class AdminState extends AbstractState
{

	private AdminState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	@Override void lookForFlaw( )
	{

	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new AdminState( this );
		}
	}
}
