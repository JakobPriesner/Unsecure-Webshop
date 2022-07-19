package de.fhws.biedermann.webshop.models;

import java.time.LocalDateTime;

public class LoginData
{

	final String uuid;
	final String email;
	final String password;
	LocalDateTime timestamp;

	public LoginData( final String uuid, final String email, final String password )
	{
		this.uuid = uuid;
		this.email = email;
		this.password = password;
	}

	public String getUuid( )
	{
		return uuid;
	}

	public String getEmail( )
	{
		return email;
	}

	public String getPassword( )
	{
		return password;
	}

	public LocalDateTime getTimestamp( )
	{
		return timestamp;
	}

	public LoginData setTimestamp( LocalDateTime timestamp )
	{
		this.timestamp = timestamp;
		return this;
	}

	public boolean compareTo( LoginData o )
	{
		return this.email.equals( o.getEmail() ) || this.password.equals( o.getPassword() );
	}
}
