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
		RequestDispatcher dispatcher = request.getRequestDispatcher("/browse/ordersPage.jsp");
		
		String usertmp = request.getParameter("userid");
		String ordertmp = request.getParameter("orderid");
		
		Integer userID = null;
		Integer orderID = null;
		
		if(usertmp != null) {
			userID = Integer.parseInt(usertmp);
			orderID = Integer.parseInt(ordertmp);
		}
		
		
		if(ordertmp != null) {
			try {
				Order orderDetails = orderDAO.retrieveOrderDetails(userID, orderID);
				request.setAttribute("order", orderDetails);
				RequestDispatcher di = request.getRequestDispatcher("/browse/orderDetailPage.jsp");
				di.forward(request, response);
				return;
				
				
				
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Collection<Order> orders = orderDAO.retrieveByUser(user.getId());
			request.setAttribute("orders", orders);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
