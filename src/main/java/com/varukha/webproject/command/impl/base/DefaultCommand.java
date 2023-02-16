package com.varukha.webproject.command.impl.base;


import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This type of command returns when system can't define the command
 * @author Dmytro Varukha
 *
 */
public class DefaultCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		router.setPagePath(PagePath.MAIN_PAGE);
		logger.log(Level.INFO, "Unknown command ");
		return router;
	}
}