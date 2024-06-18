package model;

public class Utente {
	
	private int id;
	private String email;
	private String password;
	private String indirizzoFatturazione;
	private Boolean isAdmin;
	
	public Utente(int id, String email, String password, String indirizzoFatturazione, Boolean isAdmin) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.indirizzoFatturazione = indirizzoFatturazione;
		this.isAdmin = isAdmin;
	}
	
	public Utente(String email, String password, String indirizzoFatturazione) {
		this.id = -1;
		this.email = email;
		this.password = password;
		this.indirizzoFatturazione = indirizzoFatturazione;
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

	public String getIndirizzoFatturazione() {
		return indirizzoFatturazione;
	}

	public void setIndirizzoFatturazione(String indirizzoFatturazione) {
		this.indirizzoFatturazione = indirizzoFatturazione;
	}

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
