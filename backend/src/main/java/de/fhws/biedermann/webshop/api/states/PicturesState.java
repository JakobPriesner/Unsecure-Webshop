package de.fhws.biedermann.webshop.api.states;

public class PicturesState extends AbstractState
{
	private PicturesState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	@Override void lookForFlaw( )
	{

	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new PicturesState( this );
		}
	}
}
