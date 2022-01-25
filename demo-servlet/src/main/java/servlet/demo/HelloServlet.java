package servlet.demo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {
    public void doGet (HttpServletRequest req,
                       HttpServletResponse res)
            throws ServletException, IOException
    {
        PrintWriter out = res.getWriter();

        out.println("Hello, world!");
        out.close();
    }
}