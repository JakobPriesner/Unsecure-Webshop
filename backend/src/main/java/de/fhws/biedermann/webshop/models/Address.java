package de.fhws.biedermann.webshop.models;

public class Address implements IModel
{ //erledigt

	private int id;
	private String name;
	private String country;
	private String address;
	private String address2;
	private String zipCode;
	private String city;
	private String deliveryInstructions;

	public Address()
	{
	}

	public Address(int id, String name, String country, String address, String address2, String zipCode, String city,
		String deliveryInstructions)
	{
		this.id = id;
		this.name = name;
		this.country = country;
		this.address = address;
		this.address2 = address2;
		this.zipCode = zipCode;
		this.city = city;
		this.deliveryInstructions = deliveryInstructions;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getAddress2()
	{
		return address2;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getDeliveryInstructions()
	{
		return deliveryInstructions;
	}

	public void setDeliveryInstructions(String deliveryInstructions)
	{
		this.deliveryInstructions = deliveryInstructions;
	}
}