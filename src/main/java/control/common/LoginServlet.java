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
import model.Utente;
import model.dao.UtenteDAO;

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
		
		Utente utente = retrieveUtente(email, password);

		if(utente == null) {
			errors.add("Username e/o password errati");
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		session.setAttribute("utente", utente);
		if(utente.isAdmin()) {
			response.sendRedirect(request.getContextPath() + "/admin/adminPage.jsp");
		}
		else {
			response.sendRedirect(request.getContextPath() + "/browse/browsePage.jsp");
		}
		
		return;
	}

	private Utente retrieveUtente(String email, String password) {
		UtenteDAO userDAO = new UtenteDAO((DataSource)getServletContext().getAttribute("DataSource"));
		Utente user = null;
		
		try {
			 user = userDAO.retrieveByEmailAndPsw(email, password);
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
