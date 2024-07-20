package control.filters;

import java.io.IOException;
import java.util.ArrayList;

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

@WebFilter("/MovieUpdateFilter")
public class MovieUpdateFilter extends HttpFilter implements Filter {
    
    public MovieUpdateFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		httpRequest.setCharacterEncoding("UTF-8");
		
		String method = httpRequest.getMethod().toLowerCase();
		
		if(method.equals("get")) {
			try {
				Integer movieid = Integer.parseInt(request.getParameter("movieid"));
			}
			catch (Exception e) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/index.jsp");
				return;
			}
		}
		
		if(method.equals("post")) {
			String id = httpRequest.getParameter("movieid").trim();
			if(!isValidInteger(id)) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/index.jsp");
				return;
			}
			
			String title = httpRequest.getParameter("title").trim();
			String plot = httpRequest.getParameter("plot").trim();
			String duration = httpRequest.getParameter("duration").trim();
			String year = httpRequest.getParameter("year").trim();
			String availableLicenses = httpRequest.getParameter("availableLicenses").trim();
			String dailyRentalPrice = httpRequest.getParameter("dailyRentalPrice").trim();
			String purchasePrice = httpRequest.getParameter("purchasePrice").trim();
			ArrayList<String> errors = new ArrayList<>();
			
			if(!isValidText(title)) {
				errors.add("Titolo non valido. Consentite solo lettere, numeri e spazi.");
			}
			if(!isValidText(plot)) {
				errors.add("Trama non valida. Consentite solo lettere, numeri e spazi.");
			}
			if(!isValidInteger(duration)) {
				errors.add("Durata non valida. Consentiti solo numeri interi >= 0.");
			}
			if(!isValidYear(year)) {
				errors.add("Anno non valido. Anno min. 1888.");
			}
			if(!isValidInteger(availableLicenses)) {
				errors.add("Numero licenze non valido. Consentiti solo numeri interi >= 0.");
			}
			if(!isValidPrice(dailyRentalPrice)) {
				errors.add("Prezzo Noleggio Giornaliero non valido. Consentiti solo numeri interi >= 0.");
			}
			if(!isValidPrice(purchasePrice)) {
				errors.add("Prezzo di acquisto non valido. Consentiti solo numeri interi >= 0.");
			}
			
			if(!errors.isEmpty()) {
				httpRequest.setAttribute("errors", errors);
				httpRequest.getRequestDispatcher("/admin/MovieUpdateServlet?movieid=" + id).forward(request, response);
				return;
			}
		}
		
		chain.doFilter(request, response);
	}
	
	public boolean isValidText(String text) {
		String regex = "[\\w\\sàèìòù.,']+";
		return text == null ? Boolean.FALSE : text.trim().matches(regex);
	}
	
	public boolean isValidInteger(String integer) {
		try {
			return Integer.parseInt(integer) >= 0;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isValidYear(String year) {
		try {
			return Integer.parseInt(year) >= 1888;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isValidPrice(String price) {
		try {
			return Float.parseFloat(price) >= 0;
		} catch (Exception e) {
			return false;
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
