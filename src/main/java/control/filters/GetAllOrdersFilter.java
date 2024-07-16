package control.filters;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

@WebFilter("/GetAllOrdersFilter")
public class GetAllOrdersFilter extends HttpFilter implements Filter {
    
    public GetAllOrdersFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		try {
			LocalDate from = LocalDate.parse(httpRequest.getParameter("from"), DateTimeFormatter.ISO_DATE);
			LocalDate to = LocalDate.parse(httpRequest.getParameter("to"), DateTimeFormatter.ISO_DATE);
			System.out.println(to);
		} catch (Exception e) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/allOrdersPage.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
