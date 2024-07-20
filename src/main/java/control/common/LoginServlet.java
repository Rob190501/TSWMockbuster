package control.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
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
import model.Cart;
import model.User;
import model.dao.UserDAO;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim();
		
		UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			password = toHash(password);
			
			User user = userDAO.retrieveByEmailAndPassword(email, password);
			
			if(user == null) {
				ArrayList<String> errors = new ArrayList<>();
				errors.add("Username e/o password errati");
				request.setAttribute("errors", errors);
				request.getRequestDispatcher("/common/login.jsp").forward(request, response);
				return;
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			session.setAttribute("cart", new Cart());
			response.sendRedirect(request.getContextPath() + "/common/index.jsp");
		} catch (NoSuchAlgorithmException | DAOException e) {
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
