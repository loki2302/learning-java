package me.loki2302;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "asyncServlet", urlPatterns = {"/async"}, asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
    
    @Override
    public void init() throws ServletException {
        System.out.println("AsyncServlet initialized");
    }
    
    @Override
    public void destroy() {
        executor.shutdown();
        System.out.println("AsyncServlet destroyed");
    }    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {        
        AsyncContext asyncContext = req.startAsync();
        executor.schedule(new RequestHandlerRunnable(asyncContext), 500, TimeUnit.MILLISECONDS);
        System.out.printf("Scheduled handler for %s\n", req.getRequestURI());
    }
    
    private static class RequestHandlerRunnable implements Runnable {
        private final AsyncContext asyncContext;
        
        public RequestHandlerRunnable(AsyncContext asyncContext) {
            this.asyncContext = asyncContext;
        }

        @Override
        public void run() {
            HttpServletRequest request = (HttpServletRequest)asyncContext.getRequest();
            System.out.printf("Started handler for %s\n", request.getRequestURI());
            
            HttpServletResponse response = (HttpServletResponse)asyncContext.getResponse();
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(response.getOutputStream());
                writer.println("hello async");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(writer != null) {
                    writer.close();
                }                
                
                asyncContext.complete();
                
                System.out.printf("Finished handler for %s\n", request.getRequestURI());
            }            
        }        
    }   
}