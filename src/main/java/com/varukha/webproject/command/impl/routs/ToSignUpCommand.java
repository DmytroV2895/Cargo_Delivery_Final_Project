package com.varukha.webproject.command.impl.routs;


import com.varukha.webproject.command.Command;
import com.varukha.webproject.command.PagePath;
import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 *  Go to sign up page command
 * @author Dmytro Varukha
 * @version 1.0
 *
 */
public class ToSignUpCommand implements Command {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		logger.log(Level.INFO, "Executing signUp command");
		Router router = new Router();
		HttpSession session = request.getSession();
		session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_REGISTRATION_PAGE);
		router.setPagePath(PagePath.REGISTRATION_PAGE);
		return router;
	}
}
