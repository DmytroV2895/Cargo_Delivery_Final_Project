package com.varukha.webproject.command.impl.base;


import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Log out command
 * @author Dmytro Varukha
 *
 */
public class LogOutCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		logger.log(Level.INFO, "Execute LogOutCommand");
		Router router = new Router();
		HttpSession session = request.getSession();
		session.invalidate();
		router.setPagePath(PagePath.TO_MAIN_PAGE);
		return router;
	}
}
