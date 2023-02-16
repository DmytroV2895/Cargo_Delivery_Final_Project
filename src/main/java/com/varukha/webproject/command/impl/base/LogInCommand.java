package com.varukha.webproject.command.impl.base;


import com.varukha.webproject.command.*;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.impl.UserDAOImpl;
import com.varukha.webproject.model.service.UserService;
import com.varukha.webproject.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;


/**
 * Log in command
 * @author Dmytro Varukha
 *
 */
public class LogInCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
	private UserService userService = new UserServiceImpl(new UserDAOImpl(ConnectionPool.getInstance()));

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		HttpSession session = request.getSession();
		logger.log(Level.DEBUG, "Executing  logIn command");
		User user;
		String email = request.getParameter(ParameterAndAttribute.USER_EMAIL);
		String password = request.getParameter(ParameterAndAttribute.USER_PASSWORD);
		logger.log(Level.DEBUG, "User email: " + email + " User password: " + password);
		Optional<User> optionalUser;
		try {
			optionalUser = userService.getUserEmailPassword(email, password);
//
			if (optionalUser.isPresent()) {
				user = optionalUser.get();
				router.setPagePath(PagePath.TO_MAIN_PAGE);
				session.setAttribute(ParameterAndAttribute.USER, user);
			} else {
				router.setPagePath(PagePath.LOG_IN_PAGE);
				request.setAttribute(ParameterAndAttribute.MESSAGE, Message.INCORRECT_EMAIL_OR_PASSWORD);
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "UserServiceException in getUserEmailPassword method " + e);
			router.setPagePath(PagePath.ERROR);
		}
		return router;
	}
}