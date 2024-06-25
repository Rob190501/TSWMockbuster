package model;

public class User {
	
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String billingAddress;
	private Boolean isAdmin;
	
	public User(int id, String email, String password, String firstName, String lastName, String billingAddress, Boolean isAdmin) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.billingAddress = billingAddress;
		this.isAdmin = isAdmin;
	}
	
	public User(String email, String password, String firstName, String lastName, String billingAddress) {
		this.id = -1;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.billingAddress = billingAddress;
		this.isAdmin = Boolean.FALSE;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setCognome(String lastName) {
		this.lastName = lastName;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
