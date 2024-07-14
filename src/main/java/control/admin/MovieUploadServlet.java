package control.admin;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.dao.MovieDAO;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	maxFileSize = 1024 * 1024 * 10,      // 10MB
	maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class MovieUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String IMAGE_DIR = "images/posters";

    public MovieUploadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = (String)request.getAttribute("title");
		String plot = (String)request.getParameter("plot");
		Integer duration = Integer.parseInt((String)request.getAttribute("duration"));
		Integer year = Integer.parseInt((String)request.getAttribute("year"));
		Integer availableLicenses = Integer.parseInt((String)request.getAttribute("availableLicenses"));
		Float dailyRentalPrice = Float.parseFloat((String)request.getAttribute("dailyRentalPrice"));
		Float purchasePrice = Float.parseFloat((String)request.getAttribute("purchasePrice"));
		Part poster = request.getPart("poster");
		String posterName = poster.getSubmittedFileName();
		
		Movie movie = new Movie(title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, posterName);
		String savePath = request.getServletContext().getRealPath("") + IMAGE_DIR;
		
		MovieDAO movieDAO = new MovieDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			movieDAO.save(movie);
			poster.write(savePath + File.separator + posterName);
			response.sendRedirect(request.getContextPath() + "/common/index.jsp");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}
