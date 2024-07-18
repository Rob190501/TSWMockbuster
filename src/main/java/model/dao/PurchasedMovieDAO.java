package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.Order;
import model.PurchasedMovie;

public class PurchasedMovieDAO implements DAOInterface<PurchasedMovie> {

	private DataSource ds;
	private static String table = "movie_purchase_order";
	
	public PurchasedMovieDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(PurchasedMovie bean) throws DAOException {
	}

	@Override
	public void delete(int id) throws DAOException {
	}

	@Override
	public PurchasedMovie retrieveByID(int id) throws DAOException {
		return null;
	}
	
	public void retrieveByOrder(Order order) throws SQLException, DAOException {
		String rentQuery = "SELECT * FROM " + table + " " +
				   		   "WHERE order_id = ?";
		MovieDAO movieDAO = new MovieDAO(ds);
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(rentQuery)) {
			pstmt.setInt(1, order.getId());
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer movieID = rs.getInt("movie_id");
					Float price = rs.getFloat("price");
					
					Movie movie = movieDAO.retrieveByID(movieID);
					
					PurchasedMovie purchasedMovie = new PurchasedMovie(movie, order, price);
					order.addPurchasedMovie(purchasedMovie);
				}
			}
		}
		return;
	}

	@Override
	public Collection<PurchasedMovie> retrieveAll() throws DAOException {
		return null;
	}

}
