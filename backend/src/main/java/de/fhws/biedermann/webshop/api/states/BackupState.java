package de.fhws.biedermann.webshop.api.states;

import de.fhws.biedermann.webshop.utils.handler.FlawHandler;

public class BackupState extends AbstractState
{
	private BackupState( final AbstractStateBuilder builder ){
		super( builder );
	}

	@Override void lookForFlaw( )
	{
		FlawHandler.endpointScanning( this.uuid );
	}

	public static class Builder extends AbstractStateBuilder{
		@Override
		public AbstractState build(){
			return new BackupState( this );
		}
	}

}
