package control.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.User;
import model.dao.UserDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/common/login.jsp");
		ArrayList<String> errors = new ArrayList<>();
		HttpSession session = request.getSession();

		if(email == null || email.trim().equals("")) {
			errors.add("Campo Username vuoto");
		}
		if(password == null || password.trim().equals("")) {
			errors.add("Campo Password vuoto");
		}
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}

		email = email.trim();
		password = toHash(password.trim());
		
		User user = retrieveUser(email, password);

		if(user == null) {
			errors.add("Username e/o password errati");
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		session.setAttribute("user", user);
		if(user.isAdmin()) {
			response.sendRedirect(request.getContextPath() + "/admin/adminPage.jsp");
		}
		else {
			response.sendRedirect(request.getContextPath() + "/browse/browsePage.jsp");
		}
		
		return;
	}

	private User retrieveUser(String email, String password) {
		UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		User user = null;
		
		try {
			 user = userDAO.retrieveByEmailAndPassword(email, password);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return user;
	}

	private String toHash(String password) {
		String hashString = null;
		
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest (password.getBytes(StandardCharsets.UTF_8));
			hashString = "";
			for (byte element : hash) {
				hashString += Integer.toHexString((element & 0xFF) | 0x100).substring(1,3);
			}
		}
		catch(java.security.NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		
		return hashString;
	}
}
