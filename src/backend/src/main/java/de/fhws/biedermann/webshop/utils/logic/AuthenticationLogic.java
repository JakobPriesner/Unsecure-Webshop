package de.fhws.biedermann.webshop.utils.logic;

import de.fhws.biedermann.webshop.database.AuthorizationType;
import de.fhws.biedermann.webshop.database.DataAccessShopDatabase;
import de.fhws.biedermann.webshop.models.LoginData;
import de.fhws.biedermann.webshop.models.User;
import de.fhws.biedermann.webshop.utils.SecurityBreachDetection;
import de.fhws.biedermann.webshop.utils.handler.DataHandler;
import de.fhws.biedermann.webshop.utils.handler.FlawHandler;

import javax.annotation.Nullable;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.security.SecureRandom;
import java.util.Base64;

public class AuthenticationLogic
{

	static DataAccessShopDatabase Database = new DataAccessShopDatabase();

	public static String register( final User user, final String uuid ) {
		DataHandler.createUser(user);
		return login( user.getMail(), user.getPassword(), uuid );
	}

	public static String login(final String mail, final String password, final String uuid)
	{
		SecurityBreachDetection.detectLoginSecurityBreach( new LoginData( uuid, mail, password ) );
		SecurityBreachDetection.detectHtmlCommentUser( mail, password, uuid );
		AuthorizationType session = Database.checkAuthData(mail, password);
		if ( session == AuthorizationType.FALSE_PASSWORD || session == AuthorizationType.FALSE_USER )
		{
			if ( AdminLogic.getInstance().getLevel() < 2 )
			{
				if ( session == AuthorizationType.FALSE_PASSWORD )
					throw new WebApplicationException( Response.status( Response.Status.UNAUTHORIZED ).entity( "Das " +
						"Passwort stimmt nicht mit dem Benutzer überein! Versuche es erneut." ).build() );
				else
					throw new WebApplicationException( Response.status( Response.Status.UNAUTHORIZED ).entity( "Der " +
						"Benutzer existiert nicht! Versuche es erneut." ).build() );
			}
			else
				throw new WebApplicationException( Response.status( Response.Status.UNAUTHORIZED ).entity( "Benutzer" +
					" oder Passwort falsch! Versuche es erneut." ).build() );
		} else if (session == AuthorizationType.AUTHORIZED_DUMMY_USER) {
			FlawHandler.guessUserLogin( uuid );
		}
		String sessionID = createSessionId();
		Database.postSession(sessionID, mail, uuid);
		return sessionID;
	}

	public static Nullable logout(String session) {
		Database.deleteSession(session);
		return null;
	}

	public static String createSessionId( )
	{
		SecureRandom GENERATOR = new SecureRandom();
		String session = "";
		do {
			byte[] token = new byte[32];
			GENERATOR.nextBytes(token);
			session = Base64.getEncoder().encodeToString(token);
		} while (Database.sessionExists(session));
		return session;
	}

}
