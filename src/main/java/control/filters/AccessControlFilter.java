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
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Cart;
import model.User;
import model.dao.UserDAO;

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
		
		httpRequest.setCharacterEncoding("UTF-8");
		
		String targetPage = httpRequest.getServletPath().toLowerCase();
		String indexPage = httpRequest.getContextPath() + "/common/index.jsp";
		
		User user = (User)httpRequest.getSession().getAttribute("user");
		
		if((targetPage.contains("browse") || targetPage.contains("admin")) && user == null) {
			httpResponse.sendRedirect(indexPage);
			return;
		}
		
		if(targetPage.contains("moviepage.jsp") && httpRequest.getAttribute("movie") == null) {
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
		
		if(user != null) {
			if(httpRequest.getSession().getAttribute("cart") == null) {
				httpRequest.getSession().setAttribute("cart", new Cart());
			}
			UserDAO userDAO = new UserDAO((DataSource)httpRequest.getServletContext().getAttribute("DataSource"));
			try {
				httpRequest.setAttribute("user", userDAO.retrieveByID(user.getId()));
			} catch (DAOException e) {
				httpRequest.getSession().invalidate();
				e.printStackTrace();
				throw new ServletException(e);
			}
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
