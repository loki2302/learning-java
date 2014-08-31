package me.loki2302;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(name = "jspServlet", urlPatterns = {"/jsp"})
public class DummyJspServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currentTime", new Date().toString());
        req.getRequestDispatcher("/WEB-INF/page.jsp").forward(req, resp);
    }
}
