package control.browse;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Cart;
import model.Movie;
import model.User;
import model.dao.MovieDAO;

public class MoviePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MoviePageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id").trim());
		User user = (User)request.getSession().getAttribute("user");
		MovieDAO movieDAO = new MovieDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			Movie movie = movieDAO.retrieveByID(id);
			if(movie == null || (!user.isAdmin() && !movie.isVisible()) ) {
				response.sendRedirect(request.getContextPath() + "/common/index.jsp");
				return;
			}
			
			request.setAttribute("movie", movie);
			request.getRequestDispatcher("/browse/moviePage.jsp").forward(request, response);
			return;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
