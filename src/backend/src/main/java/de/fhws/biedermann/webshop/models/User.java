package de.fhws.biedermann.webshop.models;

public class User implements IModel {
	private int id;
	private String mail;
	private String firstName;
	private String lastName;
	private boolean newsletter;
	private String salutation;
	private String title;
	private String profilePicture;
	private String description;
	private String password;

	public User(){}

	public User(int id, String mail, String firstName, String lastName, boolean newsletter, String salutation, String title, String profilePicture, String description ){
		this.id=id;
		this.mail=mail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.newsletter=newsletter;
		this.salutation=salutation;
		this.title=title;
		this.profilePicture=profilePicture;
		this.description=description;
	}

	public User(int id, String mail, String firstName, String lastName, boolean newsletter, String salutation, String title, String profilePicture, String description, String password){
		this.id=id;
		this.mail=mail;
		this.firstName = firstName;
		this.lastName = lastName;
		this.newsletter=newsletter;
		this.salutation=salutation;
		this.title=title;
		this.profilePicture=profilePicture;
		this.description=description;
		this.password=password;
	}

	public static User getRandomUser() {
		return new User(1, "mail@mail.com", "Max", "Mustermann", true, "Hi, my name is Max", "Dr.", "/picture", "description" );
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isNewsletter() {
		return newsletter;
	}

	public void setNewsletter(boolean newsletter) {
		this.newsletter = newsletter;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
