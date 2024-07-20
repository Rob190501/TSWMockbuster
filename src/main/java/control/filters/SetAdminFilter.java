package control.filters;

import java.io.IOException;
import java.net.http.HttpRequest;

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

import model.User;

@WebFilter("/SetAdminFilter")
public class SetAdminFilter extends HttpFilter implements Filter {
    
    public SetAdminFilter() {
        super();
    }
    
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		User user = (User)httpRequest.getSession().getAttribute("user");
		
		try {
			String action = httpRequest.getParameter("action").trim();
			Integer id = Integer.parseInt(httpRequest.getParameter("id").trim());
			
			if(!action.equals("set") && !action.equals("remove")) {
				throw new Exception();
			}
			
			if(user.getId().equals(id)) {
				throw new Exception();
			}
		} catch(Exception e) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/allUsersPage.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
