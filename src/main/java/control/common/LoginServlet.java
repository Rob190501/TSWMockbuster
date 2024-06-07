package control.common;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/common/login.jsp");
		
		ArrayList<String> errors = new ArrayList<String>();
		
		if(username == null || username.trim().equals(""))
			errors.add("Campo Username vuoto");
		if(password == null || username.trim().equals(""))
			errors.add("Campo Password vuoto");
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		username = username.trim();
		password = toHash(password.trim());
		
		if(isAdmin(username, password)) {
			request.getSession().setAttribute("isAdmin", Boolean.TRUE);
			response.sendRedirect(request.getContextPath() + "/admin/adminPage.jsp");
		}
		else if(isUser(username, password)) {
			request.getSession().setAttribute("isAdmin", Boolean.FALSE);
			response.sendRedirect(request.getContextPath() + "/browse/browsePage.jsp");
		}
		else {
			errors.add("Username e/o password errati");
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
		}	
	}
	
	private Boolean isAdmin(String username, String password) {
		return username.equals("admin") && password.equals("b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86");
	}
	
	private Boolean isUser(String username, String password) {
		return username.equals("user") && password.equals("b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86");
	}
	
	private String toHash(String password) {
		String hashString = null;
		try {
			java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest (password.getBytes(StandardCharsets.UTF_8));
			hashString = "";
			for (int i = 0; i < hash.length; i++) {
				hashString += Integer.toHexString((hash[i] & 0xFF) | 0x100).substring(1,3);
			}
		}
		catch(java.security.NoSuchAlgorithmException e) {
			System.out.println(e);
		}
		return hashString;
	}
}
