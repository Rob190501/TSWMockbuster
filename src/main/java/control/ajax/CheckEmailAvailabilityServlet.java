package control.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.json.JSONObject;

import control.exceptions.DAOException;
import model.dao.UserDAO;


@WebServlet("/CheckEmailAvailabilityServlet")
public class CheckEmailAvailabilityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public CheckEmailAvailabilityServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String email = request.getParameter("email");
    	UserDAO utenteDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
    	
    	response.setContentType("application/json");
		PrintWriter out = response.getWriter();
    	
    	try {
			Boolean isAvailable = utenteDAO.checkEmailAvailability(email);
			JSONObject json = new JSONObject();
			json.put("isAvailable", isAvailable);
			out.print(json.toString());
		} catch (DAOException e) {
			e.printStackTrace();
		}
    }
}
