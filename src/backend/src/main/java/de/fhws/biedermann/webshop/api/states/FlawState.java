package de.fhws.biedermann.webshop.api.states;

public class FlawState extends AbstractState
{
	private FlawState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	@Override void lookForFlaw( )
	{

	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new FlawState( this );
		}
	}
}
