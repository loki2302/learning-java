package me.loki2302;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

@WebServlet(name = "jspServlet", urlPatterns = {"/jsp"})
public class DummyJspServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("currentTime", new Date().toString());
        req.setAttribute("things", Arrays.asList("Thing one", "Thing two", "Thing three"));

        req.setAttribute("people", Arrays.asList(
                person("loki2302", 10),
                person("qwerty", 13),
                person("omg", 222)
        ));

        req.setAttribute("calculator", new Calculator());
        req.setAttribute("urlProvider", new UrlProvider());

        req.getRequestDispatcher("/WEB-INF/page.jsp").forward(req, resp);
    }

    private static Person person(String name, int age) {
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        return person;
    }

    public static class Person {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    public static class Calculator {
        public int addNumbers(int a, int b) {
            return a + b;
        }
    }

    public static class UrlProvider {
        public String makeUrl(String where) {
            return String.format("http://www.%s.com/", where);
        }
    }
}
