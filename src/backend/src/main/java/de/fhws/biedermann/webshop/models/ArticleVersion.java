package de.fhws.biedermann.webshop.models;

public class ArticleVersion implements IModel { // specified Item in frontend

	private int id; //Aus m zu n Table
	private int articleNumber; //ID des article
	private int quantity;
	private int gbSize;
	private String color;
	private String name; //model_name
	private double amount;
	private int pictureId;

	public ArticleVersion(){}

	public ArticleVersion(int id, int articleNumber, int quantity, int gbSize, String color, String name, double amount, int pictureId)
	{
		this.id = id;
		this.articleNumber = articleNumber;
		this.quantity = quantity;
		this.gbSize = gbSize;
		this.color = color;
		this.name=name;
		this.amount=amount;
		this.pictureId=pictureId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getGbSize() {
		return gbSize;
	}

	public void setGbSize(int gbSize) {
		this.gbSize = gbSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getPictureId() {
		return pictureId;
	}

	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
}
