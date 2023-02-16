package com.varukha.webproject.command.impl.base;


import com.varukha.webproject.command.*;
import com.varukha.webproject.command.Router.*;
import com.varukha.webproject.controller.context.AppContext;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Command for registration new user
 * @author Dmytro Varukha
 *
 */
public class SignUpCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
	private final UserService userService = AppContext.getAppContext().getUserService();

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		Router router = new Router();
		Map<String, String> userData = new HashMap<>();
		logger.log(Level.DEBUG, "Execute method SignUp");
		String name = request.getParameter(ParameterAndAttribute.USER_NAME);
		String surname = request.getParameter(ParameterAndAttribute.USER_SURNAME);
		String email = request.getParameter(ParameterAndAttribute.USER_EMAIL);
		String password = request.getParameter(ParameterAndAttribute.USER_PASSWORD);
		String phone = request.getParameter(ParameterAndAttribute.USER_PHONE);
		String confirmedPassword = request.getParameter(ParameterAndAttribute.USER_CONFIRMED_PASSWORD);
		userData.put(ParameterAndAttribute.USER_NAME, name);
		userData.put(ParameterAndAttribute.USER_SURNAME, surname);
		userData.put(ParameterAndAttribute.USER_EMAIL, email);
		userData.put(ParameterAndAttribute.USER_PASSWORD, password);
		userData.put(ParameterAndAttribute.USER_PHONE, phone);
		if (password.equals(confirmedPassword)) {
			try {
				if (userService.getUserByEmail(email).isEmpty()) {
					if (userService.addUser(userData)) {
						String page = request.getContextPath();
						router.setPagePath(page);
						router.setType(Type.REDIRECT);
					} else {
						router.setPagePath(PagePath.REGISTRATION_PAGE);
						request.setAttribute(ParameterAndAttribute.MESSAGE, Message.CANT_ADD_USER);
					}
				} else {
					router.setPagePath(PagePath.REGISTRATION_PAGE);
					request.setAttribute(ParameterAndAttribute.MESSAGE, Message.USER_ALREADY_EXISTS);
				}
			} catch (ServiceException e) {
				logger.log(Level.ERROR, "UserServiceException in SignUpCommand " + e);
				router.setPagePath(PagePath.ERROR);
			}
		} else {
			router.setPagePath(PagePath.REGISTRATION_PAGE);
			request.setAttribute(ParameterAndAttribute.MESSAGE, Message.PASSWORD_DO_NOT_MATCH);
		}
		return router;
	}
}
