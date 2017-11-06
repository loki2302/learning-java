package me.loki2302;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class UrlProviderTag extends TagSupport {
    private String where;

    public void setWhere(String where) {
        this.where = where;
    }

    @Override
    public int doStartTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        DummyJspServlet.UrlProvider urlProvider =
                (DummyJspServlet.UrlProvider)request.getAttribute("urlProvider");
        if(urlProvider == null) {
            throw new RuntimeException("servletRequest's urlProvider attribute is not set");
        }

        String url = urlProvider.makeUrl(where);

        JspWriter jspWriter = pageContext.getOut();
        try {
            jspWriter.print(url);
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
