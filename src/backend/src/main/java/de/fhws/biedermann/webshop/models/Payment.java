package de.fhws.biedermann.webshop.models;

public class Payment implements IModel {
	private String iban;
	private String bic;
	private String accountHolder;

	public Payment(){}

	public Payment(String iban, String bic, String accountHolder)
	{
		this.iban = iban;
		this.bic = bic;
		this.accountHolder = accountHolder;
	}

	public static Payment getRandomPayment()
	{
		return new Payment("DE12 2345 1234 1264 9765", "BYLADEM1", "Max Mustermann");
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
}
