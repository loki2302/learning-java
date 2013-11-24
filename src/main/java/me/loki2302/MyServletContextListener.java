package me.loki2302;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized()");
        
        ServletContext servletContext = sce.getServletContext();
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("MyOtherServlet", new MyServlet("custom instance"));            
        servletRegistration.addMapping("/servlet2");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed()");
    }
}