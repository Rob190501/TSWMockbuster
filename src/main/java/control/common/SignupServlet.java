package control.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Utente;
import model.dao.UtenteDAO;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignupServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = toHash(request.getParameter("password"));
		String indirizzoFatturazione = request.getParameter("indirizzoFatturazione");
		Utente utente = new Utente(email, password, indirizzoFatturazione);
		
		UtenteDAO userDAO = new UtenteDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			userDAO.save(utente);
			request.getSession().setAttribute("utente", utente);
			response.sendRedirect(request.getContextPath() + "/browse/browsePage.jsp");
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
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
