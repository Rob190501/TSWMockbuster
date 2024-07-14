package control.common;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.dao.MovieDAO;

public class MovieRetrieveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public MovieRetrieveServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MovieDAO movieDAO = new MovieDAO((DataSource)getServletContext().getAttribute("DataSource"));
		try {
			Collection<Movie> movieList = movieDAO.retrieveAll();
			request.setAttribute("movieList", movieList);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/common/index.jsp");
			dispatcher.forward(request, response);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
