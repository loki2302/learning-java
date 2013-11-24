package me.loki2302;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "testServlet", urlPatterns = {"/servlet1"})
public class MyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final String instanceName;
    
    public MyServlet() {
        this("default instance");
    }
    
    public MyServlet(String instanceName) {
        this.instanceName = instanceName;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.printf("hi there (%s)", instanceName);
        writer.close();
    }
}