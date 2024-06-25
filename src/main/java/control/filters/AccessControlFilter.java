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
		
		String targetPath = httpRequest.getServletPath().toLowerCase();
		String indexPath = httpRequest.getContextPath() + "/common/index.jsp";
		String adminPath = httpRequest.getContextPath() + "/admin/adminPage.jsp";
		String browsePath = httpRequest.getContextPath() + "/browse/browsePage.jsp";
		
		User user = (User)httpRequest.getSession().getAttribute("user");
		
		if(user == null && (targetPath.contains("browse") || targetPath.contains("admin"))) {
			httpResponse.sendRedirect(indexPath);
			return;
		}
		
		if(targetPath.contains("admin") && isNotAdmin(user)) {
			httpResponse.sendRedirect(indexPath);
			return;
		}
		
		if((targetPath.contains("login") || targetPath.contains("signup")) && user != null) {
			if(user.isAdmin()) {
				httpResponse.sendRedirect(adminPath);
				return;
			}
			httpResponse.sendRedirect(browsePath);
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
