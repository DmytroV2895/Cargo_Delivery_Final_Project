package com.varukha.webproject.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface Command {

    /**
     * Executes command
     * @param request{{@link HttpServletRequest}
     * @return {@link Router}
     */
    Router execute(HttpServletRequest request, HttpServletResponse response);

}
