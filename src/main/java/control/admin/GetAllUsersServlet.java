package control.admin;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.dao.UserDAO;
import model.User;

public class GetAllUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public GetAllUsersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		try {
			Collection<User> users = userDAO.retrieveAll();
			request.setAttribute("users", users);
			request.getRequestDispatcher("/admin/allUsersPage.jsp").forward(request, response);
			return;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
