package control.filters;

import java.io.IOException;

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
import javax.servlet.http.HttpSession;

import model.User;

@WebFilter("/AccessControlFilter")
public class AccessControlFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;

	public AccessControlFilter() {
        super();
    }

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String targetPage = httpRequest.getServletPath().toLowerCase();
		String indexPage = httpRequest.getContextPath() + "/common/index.jsp";
		
		User user = (User)httpRequest.getSession().getAttribute("user");
		
		if((targetPage.contains("browse") || targetPage.contains("admin")) && user == null) {
			httpResponse.sendRedirect(indexPage);
			return;
		}
		
		if(targetPage.contains("moviepage.jsp") && request.getAttribute("movie") == null) {
			httpResponse.sendRedirect(indexPage);
			return;
		}
		
		if(targetPage.contains("admin") && isNotAdmin(user)) {
			httpResponse.sendRedirect(indexPage);
			return;
		}
		
		if((targetPage.contains("login") || targetPage.contains("signup")) && user != null) {
			httpResponse.sendRedirect(indexPage);
			return;
		}
		
		chain.doFilter(request, response);
	}

	private Boolean isNotAdmin(User user) {
		return user == null? Boolean.TRUE : !user.isAdmin();
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
