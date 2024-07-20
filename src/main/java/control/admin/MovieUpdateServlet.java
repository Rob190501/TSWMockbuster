package control.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.dao.MovieDAO;

@WebServlet("/MovieUpdateServlet")
public class MovieUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public MovieUpdateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MovieDAO movieDAO = new MovieDAO((DataSource)getServletContext().getAttribute("DataSource"));
		Integer movieid = Integer.parseInt(request.getParameter("movieid"));
		try {
			Movie movie = movieDAO.retrieveByID(movieid);
			
			if(movie == null) {
				response.sendRedirect(request.getContextPath() + "/common/index.jsp");
				return;
			}
			
			request.setAttribute("movie", movie);
			request.getRequestDispatcher("/admin/movieUpdate.jsp").forward(request, response);
			return;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		MovieDAO movieDAO = new MovieDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		Integer id = Integer.parseInt(request.getParameter("movieid"));
		String title = (request.getParameter("title")).trim();
		String plot = (request.getParameter("plot")).trim();
		Integer duration = Integer.parseInt(request.getParameter("duration"));
		Integer year = Integer.parseInt(request.getParameter("year"));
		Integer availableLicenses = Integer.parseInt(request.getParameter("availableLicenses"));
		Float dailyRentalPrice = Float.parseFloat(request.getParameter("dailyRentalPrice"));
		Float purchasePrice = Float.parseFloat(request.getParameter("purchasePrice"));
		Boolean isVisible = request.getParameter("isVisible") != null;
		
		Movie movie = new Movie(id, title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, isVisible);
		
		try {
			movieDAO.updateMovie(movie);
			response.sendRedirect(request.getContextPath() + "/browse/MoviePageServlet?id=" + movie.getId());
			return;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
