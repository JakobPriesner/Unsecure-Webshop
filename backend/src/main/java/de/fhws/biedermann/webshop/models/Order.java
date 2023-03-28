package de.fhws.biedermann.webshop.models;

import java.util.List;

public class Order implements IModel {

	private int orderNumber;
	private List<ArticleVersion> specifiedItems;
	private Coupon coupon;
	private Address address;
	private Payment payment;
	private String date;
	private double amount;

	public Order(){}

	public Order(int orderNumber, List<ArticleVersion> specifiedItems, Coupon coupon, Address address, Payment payment,
				 String date, double amount)
	{
		this.orderNumber = orderNumber;
		this.specifiedItems = specifiedItems;
		this.coupon = coupon;
		this.address = address;
		this.payment = payment;
		this.date = date;
		this.amount = amount;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<ArticleVersion> getSpecifiedItems() {
		return specifiedItems;
	}

	public void setSpecifiedItems(List<ArticleVersion> specifiedItems) {
		this.specifiedItems = specifiedItems;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
