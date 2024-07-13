package control.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.dao.UserDAO;

@WebFilter("/SignupFormFilter")
public class SignupFormFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;
    private ServletContext servletContext;

    public SignupFormFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		UserDAO userDAO = new UserDAO((DataSource)servletContext.getAttribute("DataSource"));
		
		String email = httpRequest.getParameter("email");
		String password = httpRequest.getParameter("password");
		String firstName = httpRequest.getParameter("firstName");
		String lastName = httpRequest.getParameter("lastName");
		String billingAddress = httpRequest.getParameter("billingAddress");
		
		RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/common/signup.jsp");
		ArrayList<String> errors = new ArrayList<>();
		
		if(!isValidEmail(email)) {
			errors.add("Email non valida");
		} else {
			try {
				if(!userDAO.checkEmailAvailability(email)) {
					errors.add("Email gia' registrata");
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new ServletException(e);
			}
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
			httpRequest.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	private static boolean isValidEmail(String email) {
        String regex = "[\\w\\.-]+@([\\w-]+\\.)+\\w{2,}";
        return email == null ? Boolean.FALSE : email.trim().matches(regex);
    }
	
	private static boolean isValidName(String name) {
        String regex = "[A-Za-z]+";
        return name == null ? Boolean.FALSE : name.trim().matches(regex);
    }
	
	private static boolean isValidPassword(String password) {
        String regex = "(\\w+){4,10}";
        return password == null ? Boolean.FALSE : password.trim().matches(regex);
    }
	
	private static boolean isValidBillingAddress(String address) {
        String regex = "([A-Za-z]+\\s)+\\d+\\s\\d{5}\\s[A-Za-z]+";
        return address == null ? Boolean.FALSE : address.trim().matches(regex);
    }

	public void init(FilterConfig fConfig) throws ServletException {
		this.servletContext = fConfig.getServletContext();
	}

}
