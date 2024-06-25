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
		
		User user = (User)httpRequest.getSession().getAttribute("user");
		Boolean isAdmin = user == null? null : user.isAdmin();
		
		/*if(!targetPath.contains("common") && user == null) {
			httpResponse.sendRedirect(indexPath);
			return;
		}
		
		if(targetPath.contains("admin") && isNotAdmin(isAdmin)) {
			httpResponse.sendRedirect(indexPath);
			return;
		}*/
		
		//if(path.contains("login"))
		
		
		if((targetPath.contains("admin") && isNotAdmin(isAdmin)) || (targetPath.contains("browse") && isAdmin == null)) {
			httpResponse.sendRedirect(indexPath);
			return;
		}
		if(targetPath.contains("login") && isAdmin != null) {
			httpResponse.sendRedirect(indexPath);
			return;
		}
		
		

		chain.doFilter(request, response);
	}

	private Boolean isNotAdmin(Boolean isAdmin) {
		return isAdmin == null? Boolean.TRUE : !isAdmin;
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
