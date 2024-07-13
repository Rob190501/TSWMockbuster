package control.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.User;
import model.dao.UserDAO;

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignupServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		request.getRequestDispatcher("/errors/methodNotAllowed.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password;
		try {
			password = toHash(request.getParameter("password").trim());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException();
		}
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String billingAddress = request.getParameter("billingAddress");
		User user = new User(email, password, firstName, lastName, billingAddress);
		
		UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			userDAO.save(user);
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/common/index.jsp");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	private String toHash(String password) throws NoSuchAlgorithmException {
		String hashString = null;
		java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
		byte[] hash = digest.digest (password.getBytes(StandardCharsets.UTF_8));
		hashString = "";
		for (byte element : hash) {
			hashString += Integer.toHexString((element & 0xFF) | 0x100).substring(1,3);
		}
		return hashString;
	}

}
