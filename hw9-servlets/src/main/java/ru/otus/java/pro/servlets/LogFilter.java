package ru.otus.java.pro.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormattedMessage;


import java.io.IOException;
import java.util.Date;

@WebFilter(value = {"/add","/subtract","/multiply","/div"})
public class LogFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LogFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        StringFormattedMessage log = new StringFormattedMessage((new Date() + " -> " + httpServletRequest.getServletPath() + "?x=%s&y=%s"), httpServletRequest.getParameter("x"), httpServletRequest.getParameter("y"));
        logger.info(log);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
