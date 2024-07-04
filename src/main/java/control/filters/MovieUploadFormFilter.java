package control.filters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.SizeLimitExceededException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@WebFilter("/MovieUploadFormFilter")
public class MovieUploadFormFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;
	private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
	
    public MovieUploadFormFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/admin/movieUpload.jsp");
		ArrayList<String> errors = new ArrayList<>();
		
		Map<String, String> formFields = new HashMap<>();
        for (Part part : httpRequest.getParts()) {
            if (part.getSubmittedFileName() == null) { // It is a form field
                formFields.put(part.getName(), new String(part.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
            }
        }
        
        String title = formFields.get("title");
        String plot = formFields.get("plot");
        String duration = formFields.get("duration");
        String year = formFields.get("year");
        String availableLicenses = formFields.get("availableLicenses");
        String dailyRentalPrice = formFields.get("dailyRentalPrice");
        String purchasePrice = formFields.get("purchasePrice");
		Part poster = httpRequest.getPart("poster");
		
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
		
		if(!isValidPoster(poster)) {
			errors.add("Locandina non valida. Consentite solo immagini JPEG e PNG max 10MB.");
		}
		
		if(!errors.isEmpty()) {
			httpRequest.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		for (Map.Entry<String, String> entry : formFields.entrySet()) {
		    httpRequest.setAttribute(entry.getKey(), entry.getValue());
		}
		
		chain.doFilter(request, response);
	}
	
	public boolean isValidText(String text) {
		String regex = "[\\w\\s]+";
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
	
	public boolean isValidPoster(Part poster) {
		if(poster == null) {
			return false;
		}
		
		if(poster.getSize() > MAX_FILE_SIZE) {
			return false;
		}
		
		try {
			InputStream inputStream = poster.getInputStream();
			byte[] magicNumber = new byte[8];
	        inputStream.read(magicNumber);
	        inputStream.reset(); // Reset the input stream to read the whole file later
	
	        // Check for PNG magic number
	        if (magicNumber[0] == (byte) 0x89 &&
	            magicNumber[1] == (byte) 0x50 &&
	            magicNumber[2] == (byte) 0x4E &&
	            magicNumber[3] == (byte) 0x47 &&
	            magicNumber[4] == (byte) 0x0D &&
	            magicNumber[5] == (byte) 0x0A &&
	            magicNumber[6] == (byte) 0x1A &&
	            magicNumber[7] == (byte) 0x0A) {
	            return true;
	        }
	
	        // Check for JPEG magic number
	        if (magicNumber[0] == (byte) 0xFF &&
	            magicNumber[1] == (byte) 0xD8 &&
	            magicNumber[2] == (byte) 0xFF) {
	            return true;
	        }
		} catch (Exception e) {
			return false;
		}
        
        // Poster is not PNG or JPEG
        return false;
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
