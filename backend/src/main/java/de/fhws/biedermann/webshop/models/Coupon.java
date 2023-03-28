package de.fhws.biedermann.webshop.models;

public class Coupon implements IModel {

	private String name;
	private double percent;
	private boolean active;

	public Coupon(){}

	public Coupon(String name, double percent, boolean active)
	{
		this.name = name;
		this.percent = percent;
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
