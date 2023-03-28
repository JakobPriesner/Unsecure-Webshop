package de.fhws.biedermann.webshop.api.states;

public class ArticleState extends AbstractState
{
	private ArticleState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	@Override void lookForFlaw( )
	{

	}

	public static class Builder extends AbstractStateBuilder
	{
		public ArticleState build(){
			return new ArticleState( this );
		}
	}
}
