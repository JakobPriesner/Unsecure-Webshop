package de.fhws.biedermann.webshop.models;

public class Contact implements IModel
{
	private String firstName;
	private String lastName;
	private String mail;
	private String message;

	public Contact()
	{}

	public Contact(String firstName, String lastName, String mail, String message)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.message = message;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
