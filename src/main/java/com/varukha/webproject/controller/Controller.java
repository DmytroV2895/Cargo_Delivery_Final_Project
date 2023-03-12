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

import static com.varukha.webproject.command.ParameterAndAttribute.COMMAND;

/**
 * Class Controller used to process requests from client.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
@WebServlet (name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    protected static final Logger logger = LogManager.getLogger();
    private static final CommandFactory COMMAND_FACTORY = CommandFactory.getCommandFactory();

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
        Command command = COMMAND_FACTORY.getCurrentCommand(request.getParameter(COMMAND));
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
