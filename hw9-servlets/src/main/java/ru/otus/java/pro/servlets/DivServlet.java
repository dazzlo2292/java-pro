package ru.otus.java.pro.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "DivServlet", urlPatterns = "/div")
public class DivServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        int x = Integer.parseInt(req.getParameter("x"));
        int y = Integer.parseInt(req.getParameter("y"));

        if (y == 0) {
            out.printf("<html><body><h1>Деление на ноль невозможно!</h1></body></html>");
            out.close();
        }

        double result = (double)x / y;

        out.printf("<html><body><h1>%.2f</h1></body></html>", result);
        out.close();
    }

    @Override
    public void init() throws ServletException {
    }
}
