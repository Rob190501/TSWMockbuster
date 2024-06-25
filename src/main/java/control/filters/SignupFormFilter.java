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

@WebFilter("/SignupFormFilter")
public class SignupFormFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

    public SignupFormFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String billingAddress = request.getParameter("billingAddress");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/common/signup.jsp");
		ArrayList<String> errors = new ArrayList<>();
		
		if(!isValidEmail(email)) {
			errors.add("Email non valida");
		}
		
		if(!isValidPassword(password)) {
			errors.add("Password non valida");
		}
		
		if(!isValidName(firstName)) {
			errors.add("Nome non valido");
		}
		
		if(!isValidName(lastName)) {
			errors.add("Cognome non valido");
		}
		
		if(!isValidBillingAddress(billingAddress)) {
			errors.add("Indirizzo non valido");
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
        return email.matches(regex);
    }
	
	private static boolean isValidName(String name) {
        String regex = "[A-Za-z]+";
        return name.matches(regex);
    }
	
	private static boolean isValidPassword(String password) {
        String regex = "(\\w+){4,10}";
        return password.matches(regex);
    }
	
	private static boolean isValidBillingAddress(String address) {
        String regex = "([A-Za-z]+\\s)+\\d+\\s\\d{5}\\s[A-Za-z]+";
        return address.matches(regex);
    }

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
