package control.browse;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Order;
import model.User;
import model.dao.OrderDAO;

public class GetOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public GetOrdersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO orderDAO = new OrderDAO((DataSource)getServletContext().getAttribute("DataSource"));
		User user = (User)request.getSession().getAttribute("user");
		
		if(request.getParameter("userid") == null || request.getParameter("orderid") == null) {
			try {
				Collection<Order> orders = orderDAO.retrieveByUser(user.getId());
				request.setAttribute("orders", orders);
			} catch (DAOException e) {
				e.printStackTrace();
				throw new ServletException();
			}
			request.getRequestDispatcher("/browse/ordersPage.jsp").forward(request, response);
			return;
		}
		
		Integer userID = Integer.parseInt(request.getParameter("userid"));
		Integer orderID = Integer.parseInt(request.getParameter("orderid"));
		
		try {
			Order orderDetails = orderDAO.retrieveOrderDetails(userID, orderID);
			if(orderDetails == null) {
				response.sendRedirect(request.getContextPath() + "/browse/GetOrdersServlet");
				return;
			}
			request.setAttribute("order", orderDetails);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		request.getRequestDispatcher("/browse/orderDetailsPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
