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
import model.Order;
import model.dao.OrderDAO;

public class GetAllOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetAllOrdersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO orderDAO = new OrderDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			Collection<Order> orders = orderDAO.retrieveAll();
			request.setAttribute("orders", orders);
			request.getRequestDispatcher("/admin/allOrdersPage.jsp").forward(request, response);
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
