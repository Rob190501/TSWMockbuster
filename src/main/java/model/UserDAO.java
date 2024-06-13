package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

public class UserDAO implements BeanDAO<User> {

	private DataSource ds = null;
	private static String table = "user";

	public UserDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void doSave(User bean) throws SQLException {
		String query = "Select * from user";
		Connection con = ds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			System.out.println(rs.getString("username"));
		}
	}

	@Override
	public boolean doDelete(int id) throws SQLException {
		return true;
	}

	@Override
	public User doRetrieveByKey(int id) throws SQLException {
		
		return null;
	}
	
	public User doRetrieveByUsrAndPsw(String usr, String psw) throws SQLException {
		User user = null;
		String query = "SELECT * " +
					   "FROM " + table + " " +
					   "WHERE username = ? AND password = ?";
		
		Connection con = ds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, usr);
		pstmt.setString(2, psw);
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			Boolean isAdmin = rs.getBoolean("is_admin"); 
			
			user = new User(id, username, password, isAdmin);
		}
		
		return user;
	}

	@Override
	public Collection<User> doRetrieveAll(String order) throws SQLException {
		List<User> userList = new ArrayList<User>();
		String query = "SELECT * " +
					   "FROM " + table;
		
		Connection con = ds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			Boolean isAdmin = rs.getBoolean("is_admin"); 
			
			userList.add(new User(id, username, password, isAdmin));
		}
		
		return userList;
	}

}
