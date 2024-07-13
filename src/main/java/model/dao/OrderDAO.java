package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.Order;
import model.PurchasedMovie;
import model.RentedMovie;

public class OrderDAO implements DAOInterface<Order> {

	private DataSource ds;
	private static String orderTable = "orders";
	private static String rentTable = "movie_rental_order";
	private static String purchaseTable = "movie_purchase_order";
	
	public OrderDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(Order bean) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order retrieveByID(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Collection<Order> retrieveByUser(int userID) throws DAOException {
		List<Order> orders= new ArrayList<Order>();
		String ordersQuery = "SELECT * FROM " + orderTable + " " +
							 "WHERE user_id = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(ordersQuery)) {
			pstmt.setInt(1, userID);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer orderID = rs.getInt("id");
					LocalDate date = rs.getDate("order_date").toLocalDate();
					Float amount = rs.getFloat("order_amount");
					
					Order order = new Order(orderID, date, amount);
					
					orders.add(order);
				}
			}
			
		} catch (SQLException e) {
			throw new DAOException(orderTable);
		}
		
		return orders;
	}
	
	public Order retrieveOrderDetails(Integer userID, Integer orderID) throws DAOException {
		Order order = null;
		String query = "SELECT * FROM " + orderTable + " " +
					   "WHERE id = ? AND user_id = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, orderID);
			pstmt.setInt(2, userID);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					LocalDate date = rs.getDate("order_date").toLocalDate();
					Float amount = rs.getFloat("order_amount");
					
					order = new Order(orderID, date, amount);
					retrieveRents(order);
					retrievePurchases(order);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(orderTable);
		}
		
		return order;
	}
	
	private void retrieveRents(Order order) throws SQLException, DAOException {
		String rentQuery = "SELECT * FROM " + rentTable + " " +
				   		   "WHERE order_id = ?";
		MovieDAO movieDAO = new MovieDAO(ds);
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(rentQuery)) {
			pstmt.setInt(1, order.getId());
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer movieID = rs.getInt("movie_id");
					Float dailyPrice = rs.getFloat("daily_price");
					Integer days = rs.getInt("days");
					
					Movie movie = movieDAO.retrieveByID(movieID);
					
					RentedMovie rentedMovie = new RentedMovie(movie.getId(),
															  movie.getTitle(),
															  movie.getPlot(), 
															  movie.getDuration(), 
															  movie.getYear(), 
															  movie.getAvailableLicenses(), 
															  movie.getDailyRentalPrice(), 
															  movie.getPurchasePrice(),
															  movie.isVisible(), 
															  movie.getPosterPath(),
															  order, dailyPrice, days);
					order.addRentedMovie(rentedMovie);
				}
			}
		}
		return;
	}
	
	private void retrievePurchases(Order order) throws SQLException, DAOException {
		String rentQuery = "SELECT * FROM " + purchaseTable + " " +
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
					
					PurchasedMovie purchasedMovie = new PurchasedMovie(movie.getId(),
															  		   movie.getTitle(),
															  		   movie.getPlot(), 
															  		   movie.getDuration(), 
															  		   movie.getYear(), 
															  		   movie.getAvailableLicenses(), 
															  		   movie.getDailyRentalPrice(), 
															  		   movie.getPurchasePrice(),
															  		   movie.isVisible(), 
															  		   movie.getPosterPath(),
															  		   order, price);
					order.addPurchasedMovie(purchasedMovie);
				}
			}
		}
		return;
	}

	@Override
	public Collection<Order> retrieveAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
