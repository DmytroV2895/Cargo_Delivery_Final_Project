package com.varukha.webproject.controller.listeners;

import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.model.connection.ConnectionPool;
import jakarta.servlet.*;

import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
     * ContextListener  class. Using for to initialize all required classes and
     * unregister JDBC driver when the web application was stopped
     * @author Dmytro Varukha
     * @version 1.0
     */

    @WebListener
    public class ContextListener implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();

        /**
         * creates AppContext and passes ServletContext to initialize all required classes
         * @param sce passed by application
         */
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            AppContext.createAppContext();
            logger.log(Level.INFO, "Set app context");
        }

    /**
     * Unregister JDBC driver when the web application was stopped
     * @param sce passed by application
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
        logger.log(Level.INFO, "Unregister JDBC driver");
    }
}
