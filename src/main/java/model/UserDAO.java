package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import control.exceptions.DAOException;

public class UserDAO implements BeanDAO<User> {

	private DataSource ds = null;
	private static String table = "user";

	public UserDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(User bean) throws DAOException {
		String query = "INSERT INTO " + table + " " +
					   "(username, password) VALUES (?, ?)";
		
		try(PreparedStatement pstmt = ds.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, bean.getUsername());
			pstmt.setString(2, bean.getPassword());
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
	public User retrieveByID(int id) throws DAOException {
		return null;
	}
	
	public User retrieveByUsrAndPsw(String usr, String psw) throws DAOException {
		User user = null;
		String query = "SELECT * " +
					   "FROM " + table + " " +
					   "WHERE username = ? AND password = ?";
		
		try(PreparedStatement pstmt = ds.getConnection().prepareStatement(query)) {
			pstmt.setString(1, usr);
			pstmt.setString(2, psw);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					int id = rs.getInt("id");
					String username = rs.getString("username");
					String password = rs.getString("password");
					Boolean isAdmin = rs.getBoolean("is_admin"); 
					
					user = new User(id, username, password, isAdmin);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(table);
		}
		
		return user;
	}

	@Override
	public Collection<User> retrieveAll(String order) throws DAOException {
		List<User> userList = new ArrayList<User>();
		String query = "SELECT * " +
					   "FROM " + table;
		
		try(PreparedStatement pstmt = ds.getConnection().prepareStatement(query);
			ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				Boolean isAdmin = rs.getBoolean("is_admin"); 
				
				userList.add(new User(id, username, password, isAdmin));
			}
		} catch (SQLException e) {
			throw new DAOException(table);
		}
		
		return userList;
	}

}
