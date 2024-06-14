package model;

public class User {
	
	private int id;
	private String username;
	private String password;
	private Boolean isAdmin;
	
	public User(int id, String username, String password, Boolean isAdmin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public User(String username, String password, Boolean isAdmin) {
		this.id = -1;
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
