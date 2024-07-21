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

@WebFilter("/MovieRetrieveFilter")
public class MovieRetrieveFilter extends HttpFilter implements Filter {
	
    public MovieRetrieveFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		try {
			String page = httpRequest.getParameter("page").trim();
			
			if(!page.equals("index") && !page.equals("notvisible")) {
				throw new Exception();
			}
		} catch(Exception e) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/index.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
