package de.fhws.biedermann.webshop.api.states;

import de.fhws.biedermann.webshop.database.DataAccessShopDatabase;
import de.fhws.biedermann.webshop.models.Commentary;
import de.fhws.biedermann.webshop.utils.VulnerabilityCheck;
import de.fhws.biedermann.webshop.utils.handler.FlawHandler;

public class CommentaryState extends AbstractState
{
	private CommentaryState( AbstractStateBuilder builder )
	{
		super( builder );
	}

	@Override void lookForFlaw( )
	{
		VulnerabilityCheck vc = new VulnerabilityCheck();

		DataAccessShopDatabase daap = new DataAccessShopDatabase();

		// todo articleId


		if( this.modelToWorkWith instanceof Commentary && vc.checkXSS( ( ( Commentary ) this.modelToWorkWith ).getCommentText())){
			FlawHandler.commentXSS(uuid);
		}
	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new CommentaryState( this );
		}
	}
}
