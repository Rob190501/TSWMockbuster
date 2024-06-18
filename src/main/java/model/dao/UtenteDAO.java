package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Utente;

public class UtenteDAO implements DAOInterface<Utente> {

	private DataSource ds = null;
	private static String table = "utente";

	public UtenteDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(Utente bean) throws DAOException {
		String query = "INSERT INTO " + table + " " +
					   "(email, password, indirizzo_fatturazione) VALUES (?, ?, ?)";
		
		try(PreparedStatement pstmt = ds.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, bean.getEmail());
			pstmt.setString(2, bean.getPassword());
			pstmt.setString(3, bean.getIndirizzoFatturazione());
			pstmt.executeUpdate();
			
			try(ResultSet rs = pstmt.getGeneratedKeys()) {
				if(rs.next()) {
					bean.setId(rs.getInt(1));
				}
			}
			
		} catch (SQLException e) {
			throw new DAOException(table);
		}
		
		return;
	}

	@Override
	public void delete(int id) throws DAOException {
		return;
	}

	@Override
	public Utente retrieveByID(int id) throws DAOException {
		return null;
	}
	
	public Utente retrieveByEmailAndPsw(String eml, String psw) throws DAOException {
		Utente user = null;
		String query = "SELECT * " +
					   "FROM " + table + " " +
					   "WHERE email = ? AND password = ?";
		
		try(PreparedStatement pstmt = ds.getConnection().prepareStatement(query)) {
			pstmt.setString(1, eml);
			pstmt.setString(2, psw);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("id");
					String email = rs.getString("email");
					String password = rs.getString("password");
					String indirizzoFatturazione = rs.getString("indirizzo_fatturazione");
					Boolean isAdmin = rs.getBoolean("is_admin"); 
					
					user = new Utente(id, email, password, indirizzoFatturazione, isAdmin);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(table);
		}
		
		return user;
	}

	@Override
	public Collection<Utente> retrieveAll(String order) throws DAOException {
		List<Utente> userList = new ArrayList<Utente>();
		String query = "SELECT * " +
					   "FROM " + table;
		
		try(PreparedStatement pstmt = ds.getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				int id = rs.getInt("id");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String indirizzoFatturazione = rs.getString("indirizzo_fatturazione");
				Boolean isAdmin = rs.getBoolean("is_admin"); 
				
				userList.add(new Utente(id, email, password, indirizzoFatturazione, isAdmin));
			}
		} catch (SQLException e) {
			throw new DAOException(table);
		}
		
		return userList;
	}

}
