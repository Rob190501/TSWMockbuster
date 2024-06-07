package control.common;

import java.io.IOException;
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
		password = password.trim();
		
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
		return username.equals("admin") && password.equals("password");
	}
	
	private Boolean isUser(String username, String password) {
		return username.equals("user") && password.equals("password");
	}

}
