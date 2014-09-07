package me.loki2302;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class HelloWorldTag extends SimpleTagSupport {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspContext jspContext = getJspContext();
        JspWriter jspWriter = jspContext.getOut();
        if(message == null || message.isEmpty()) {
            jspWriter.println("<span style=\"color: magenta;\">I am Hello World tag</span>");
        } else {
            jspWriter.println("<span style=\"color: cyan;\">" + message + "</span>");
        }
    }
}
