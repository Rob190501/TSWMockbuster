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
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		ArrayList<String> errors = new ArrayList<>();
		RequestDispatcher dispatcher = request.getRequestDispatcher("/common/login.jsp");
		
		if(!isValidEmail(email)) {
			errors.add("Campo Username vuoto");
		}
		
		if(!isValidPassword(password)) {
			errors.add("Campo Password vuoto");
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	private static boolean isValidEmail(String email) {
        String regex = "[\\w\\.-]+@([\\w-]+\\.)+\\w{2,}";
        return email == null ? Boolean.FALSE : email.trim().matches(regex);
    }
	
	private static boolean isValidPassword(String password) {
        String regex = "(\\w+){4,10}";
        return password == null ? Boolean.FALSE : password.trim().matches(regex);
    }

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
