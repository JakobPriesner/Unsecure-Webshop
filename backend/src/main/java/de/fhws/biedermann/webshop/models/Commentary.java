package de.fhws.biedermann.webshop.models;

public class Commentary implements IModel {

	private int id;
	private String commentText;
	private int userId;
	private String firstName;
	private String lastName;
	private String profilePicture;


	public Commentary(){}

	public Commentary(int id, String commentText, int userId, String firstName, String lastName, String profilePicture)
	{
		this.id = id;
		this.commentText = commentText;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicture = profilePicture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
}
