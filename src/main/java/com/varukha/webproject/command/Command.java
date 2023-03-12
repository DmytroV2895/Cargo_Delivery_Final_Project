package com.varukha.webproject.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/**
 * Interface Command contain contracts of single execute method
 * that used to execute application commands.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface Command {

    /**
     * Method execute used to execute application commands.
     * @param request{{@link HttpServletRequest} request from view layer and send set necessary attributes.
     * @param response{{@link HttpServletResponse} response from application(server side) to user (view layer).
     * @return {@link Router} router to the specified page.
     */
    Router execute(HttpServletRequest request, HttpServletResponse response);

}
