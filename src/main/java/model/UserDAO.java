package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

public class UserDAO implements BeanDAO<User> {
	
	private DataSource ds = null;

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
		return false;
	}

	@Override
	public User doRetrieveByKey(int id) throws SQLException {
		return null;
	}

	@Override
	public Collection<User> doRetrieveAll(String order) throws SQLException {
		return null;
	}

}
