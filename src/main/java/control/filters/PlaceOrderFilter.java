package control.filters;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;
import model.User;

public class PlaceOrderFilter extends HttpFilter implements Filter {
    
    public PlaceOrderFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		Cart cart = (Cart)httpRequest.getSession().getAttribute("cart");
		User user = (User)httpRequest.getSession().getAttribute("user");
		
		String cartPath = httpRequest.getContextPath() + "/browse/cartPage.jsp";
		ArrayList<String> errors = new ArrayList<String>();
		
		if(cart.isEmpty()) {
			httpResponse.sendRedirect(cartPath);
			return;
		}
		
		if(!user.isAdmin()) {
			if(user.getCredit() < cart.getTotal()) {
				errors.add("Credito non sufficiente");
				httpRequest.setAttribute("errors", errors);
				httpRequest.getRequestDispatcher("/browse/cartPage.jsp").forward(httpRequest, httpResponse);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
