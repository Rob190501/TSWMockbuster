package control.context;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MainContext implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		DataSource ds = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/mockbuster");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}

		context.setAttribute("DataSource", ds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
