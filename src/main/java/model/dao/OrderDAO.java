package model.dao;

import java.sql.Connection;
import java.sql.Date;
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
import model.User;

public class OrderDAO implements DAOInterface<Order> {

	private DataSource ds;
	private static String table = "orders";
	
	public OrderDAO(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public void save(Order bean) throws DAOException {
		
	}

	@Override
	public void delete(int id) throws DAOException {
		
	}

	@Override
	public Order retrieveByID(int id) throws DAOException {
		return null;
	}
	
	public Collection<Order> retrieveByUser(int userID) throws DAOException {
		ArrayList<Order> orders= new ArrayList<Order>();
		String ordersQuery = "SELECT * FROM " + table + " " +
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
			throw new DAOException(e);
		}
		
		return orders;
	}
	
	public Order retrieveOrderDetails(Integer userID, Integer orderID) throws DAOException {
		Order order = null;
		String query = "SELECT * FROM " + table + " " +
					   "WHERE id = ? AND user_id = ?";
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, orderID);
			pstmt.setInt(2, userID);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					LocalDate date = rs.getDate("order_date").toLocalDate();
					Float amount = rs.getFloat("order_amount");
					UserDAO userDAO = new UserDAO(ds);
					RentedMovieDAO rmd = new RentedMovieDAO(ds);
					PurchasedMovieDAO pmd = new PurchasedMovieDAO(ds);
					
					User user = userDAO.retrieveByID(userID);
					
					order = new Order(orderID, date, amount, user);
					rmd.retrieveByOrder(order);
					pmd.retrieveByOrder(order);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		}
		
		return order;
	}

	@Override
	public Collection<Order> retrieveAll() throws DAOException {
		String query = "SELECT * FROM " + table;
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();) {
			
			while(rs.next()) {
				Integer orderID = rs.getInt("id");
				Integer userID = rs.getInt("user_id");
				
				orders.add(retrieveOrderDetails(userID, orderID));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return orders;
	}
	
	public Collection<Order> retrieveAllBetween(LocalDate from, LocalDate to) throws DAOException {
		String query = "SELECT * FROM " + table + " " +
					   "WHERE (order_date BETWEEN ? AND ?)";
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			pstmt.setDate(1, Date.valueOf(from));
			pstmt.setDate(2, Date.valueOf(to));
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer orderID = rs.getInt("id");
					Integer userID = rs.getInt("user_id");
					
					orders.add(retrieveOrderDetails(userID, orderID));
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return orders;
	}
	
	public Collection<Order> retrieveAllBetween(LocalDate from, LocalDate to, Integer userID) throws DAOException {
		String query = "SELECT * FROM " + table + " " +
					   "WHERE order_date BETWEEN ? AND ? " +
					   "AND user_id = ?";
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try(Connection conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			pstmt.setDate(1, Date.valueOf(from));
			pstmt.setDate(2, Date.valueOf(to));
			pstmt.setInt(3, userID);
			
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					Integer orderID = rs.getInt("id");
					
					orders.add(retrieveOrderDetails(userID, orderID));
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return orders;
	}
}
