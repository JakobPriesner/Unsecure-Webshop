package de.fhws.biedermann.webshop.utils.authentication;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

public class MyKeyGenerator
{
	public static Key getNewKey( )
	{
		// generate key for uuid
		try
		{
			final SecureRandom rand = new SecureRandom( );
			final KeyGenerator keyGen = KeyGenerator.getInstance( "AES" );
			keyGen.init( 256, rand );
			return keyGen.generateKey( );
		}
		catch ( final Exception e )
		{
			e.printStackTrace( );
			return null;
		}
	}
}
