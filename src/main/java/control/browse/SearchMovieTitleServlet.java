package control.browse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

import control.exceptions.DAOException;
import model.Movie;
import model.dao.MovieDAO;

public class SearchMovieTitleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchMovieTitleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		MovieDAO movieDAO = new MovieDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		try {
			ArrayList<Movie> movies = (ArrayList<Movie>) movieDAO.retrieveByTitle(title);
			JSONArray jsonMovies = new JSONArray();
			JSONObject jsonResponse = new JSONObject();
			
			for(Movie movie : movies) {
				JSONObject jsonMovie = new JSONObject();
				jsonMovie.put("id", movie.getId());
				jsonMovie.put("posterpath", movie.getPosterPath());
				jsonMovies.put(jsonMovie);
			}
			
			jsonResponse.put("movies", jsonMovies);
			out.print(jsonResponse.toString());
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
