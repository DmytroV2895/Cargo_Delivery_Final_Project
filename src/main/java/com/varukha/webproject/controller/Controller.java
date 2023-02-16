package com.varukha.webproject.controller;

import com.varukha.webproject.command.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Controller for queries from client
 * @author Dmytro Varukha
 */


@WebServlet (name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    protected static final Logger logger = LogManager.getLogger();

    public void init() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.log(Level.INFO, "Start controller");
        String commandFromPage = request.getParameter(ParameterAndAttribute.COMMAND);
        Command command = CommandProvider.defineCommand(commandFromPage);
        Router router = command.execute(request, response);
        switch (router.getType()) {
            case FORWARD -> {
                logger.log(Level.DEBUG, "forward");
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPagePath());
                dispatcher.forward(request, response);
            }
            case REDIRECT -> {
                response.sendRedirect(router.getPagePath());
                logger.log(Level.DEBUG, "redirect");
            }
            default -> {
                logger.log(Level.ERROR, "Incorrect router type:" + router.getType());
                response.sendRedirect(PagePath.MAIN_PAGE);
            }
        }
    }
}
