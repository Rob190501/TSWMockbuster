package control.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.dao.UserDAO;

public class SetAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public SetAdminServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").trim();
		Integer id = Integer.parseInt(request.getParameter("id").trim());
		UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			if(action.equals("set")) {
				userDAO.setAdmin(id, Boolean.TRUE);
			}
			if(action.equals("remove")) {
				userDAO.setAdmin(id, Boolean.FALSE);
			}
			
			response.sendRedirect(request.getContextPath() + "/admin/allUsersPage.jsp");
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
