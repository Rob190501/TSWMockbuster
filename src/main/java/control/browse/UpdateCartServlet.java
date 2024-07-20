package control.browse;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Cart;
import model.Movie;
import model.PurchasedMovie;
import model.RentedMovie;
import model.dao.MovieDAO;

public class UpdateCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public UpdateCartServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").trim();
		
		if(action.equals("add")) {
			addToCart(request, response);
		}
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(action.equals("remove")) {
			Integer movieID = Integer.parseInt(request.getParameter("movieid").trim());
			cart.removeFromCart(movieID);
		}
		if(action.equals("empty")) {
			cart.empty();
		}
		if(action.equals("increasedays")) {
			Integer movieID = Integer.parseInt(request.getParameter("movieid").trim());
			cart.increaseRentDays(movieID);
		}
		if(action.equals("decreasedays")) {
			Integer movieID = Integer.parseInt(request.getParameter("movieid").trim());
			cart.decreaseRentDays(movieID);
		}
		
		response.sendRedirect(request.getContextPath() + "/browse/cartPage.jsp");
	}
	
	private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		MovieDAO movieDAO = new MovieDAO((DataSource)request.getServletContext().getAttribute("DataSource"));
		
		String type = request.getParameter("type").trim();
		Integer movieID = Integer.parseInt(request.getParameter("movieid").trim());
			
		try {
			if(!cart.purchasesContains(movieID) && !cart.rentsContains(movieID)) {
				Movie movie = movieDAO.retrieveByID(movieID);
				
				if(type.equals("rent")) {
					Integer days = Integer.parseInt(request.getParameter("days"));
					RentedMovie rentedMovie = new RentedMovie(movie, movie.getDailyRentalPrice(), days);
					if(rentedMovie != null && days >= 0 && days <= rentedMovie.getAvailableLicenses()) {
						cart.addRentedMovie(rentedMovie);
					}
					return;
				}
				
				if(type.equals("purchase")) {			
					PurchasedMovie purchasedMovie = new PurchasedMovie(movie, movie.getPurchasePrice());
					if(purchasedMovie != null && purchasedMovie.getAvailableLicenses() >= 1) {
						cart.addPurchasedMovie(purchasedMovie);
					}
					return;
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
