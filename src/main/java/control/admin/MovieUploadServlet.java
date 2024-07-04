package control.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	maxFileSize = 1024 * 1024 * 10,      // 10MB
	maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class MovieUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MovieUploadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		
		System.out.println(title);
		System.out.println(plot);
		System.out.println(duration);
		System.out.println(year);
		System.out.println(availableLicenses);
		System.out.println(dailyRentalPrice);
		System.out.println(purchasePrice);
		
		
		//System.out.println(request.getServletContext().getRealPath(""));
		
	}

}
