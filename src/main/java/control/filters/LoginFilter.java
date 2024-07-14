package control.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

@WebFilter("/LoginFilter")
public class LoginFilter extends HttpFilter implements Filter {
       
    public LoginFilter() {
        super();
    }

	public void destroy() {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/common/login.jsp");
		String email = request.getParameter("email").trim();
		String password = request.getParameter("password").trim();
		ArrayList<String> errors = new ArrayList<>();
		
		if(email == null || email.trim().equals("")) {
			errors.add("Campo Username vuoto");
		}
		
		if(password == null || password.trim().equals("")) {
			errors.add("Campo Password vuoto");
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
