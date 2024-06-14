package control.exceptions;

public class DAOException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DAOException(String tableName) {
		super("Errore nelle operazioni " + tableName);
	}

}
