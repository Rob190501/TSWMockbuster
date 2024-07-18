package control.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Cart;
import model.Movie;
import model.PurchasedMovie;
import model.RentedMovie;
import model.dao.MovieDAO;

public class RefreshCartFilter extends HttpFilter implements Filter {
    
    public RefreshCartFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		Cart cart = (Cart)httpRequest.getSession().getAttribute("cart");
		MovieDAO movieDAO = new MovieDAO((DataSource)httpRequest.getServletContext().getAttribute("DataSource"));
		
		if (cart == null) {
	        cart = new Cart();
	        httpRequest.getSession().setAttribute("cart", cart);
	    }
		
		try {
			Collection<RentedMovie> refreshedRentedMovies = new ArrayList<RentedMovie>();
			for(RentedMovie movie : cart.getRentedMovies()) {
				Movie refreshed = movieDAO.retrieveByID(movie.getId());
				refreshedRentedMovies.add(new RentedMovie(refreshed, refreshed.getDailyRentalPrice(), movie.getDays()));
			}
			cart.setRentedMovies(refreshedRentedMovies);
			
			Collection<PurchasedMovie> refreshedPurchasedMovies = new ArrayList<PurchasedMovie>();
			for(PurchasedMovie movie : cart.getPurchasedMovies()) {
				Movie refreshed = movieDAO.retrieveByID(movie.getId());
				refreshedPurchasedMovies.add(new PurchasedMovie(refreshed, refreshed.getPurchasePrice()));
			}
			cart.setPurchasedMovies(refreshedPurchasedMovies);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
