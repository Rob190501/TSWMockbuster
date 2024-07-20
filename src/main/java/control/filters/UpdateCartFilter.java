package control.filters;

import java.io.IOException;
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

public class UpdateCartFilter extends HttpFilter implements Filter {
     
    public UpdateCartFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpRequest.setCharacterEncoding("UTF-8");
		
		if(httpRequest.getSession().getAttribute("cart") == null) {
			httpRequest.getSession().setAttribute("cart", new Cart());
		}
		
		try {
			String action = httpRequest.getParameter("action").trim();
			ArrayList<String> allowedActions = new ArrayList<String>();
			allowedActions.add("add");
			allowedActions.add("remove");
			allowedActions.add("empty");
			allowedActions.add("increasedays");
			allowedActions.add("decreasedays");
			
			if(!allowedActions.contains(action)) {
				throw new Exception();
			}
			
			if(!action.equals("empty")) {
				Integer movieID = Integer.parseInt(httpRequest.getParameter("movieid").trim());
			}
			
			if(action.equals("add")) {
				String type = httpRequest.getParameter("type").trim();
				ArrayList<String> allowedTypes = new ArrayList<String>();
				allowedTypes.add("purchase");
				allowedTypes.add("rent");
				
				if(!allowedTypes.contains(type)) {
					throw new Exception();
				}
				if(type.equals("rent")) {
					Integer days = Integer.parseInt(httpRequest.getParameter("days").trim());
				}
			}
		} catch(Exception e) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/browse/cartPage.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
