package com.varukha.webproject.command.impl.routs;


import com.varukha.webproject.command.*;
import com.varukha.webproject.controller.context.AppContext;
import com.varukha.webproject.entity.Invoice;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.service.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


/**
 * The command find all orders by userId
 * @author Dmytro Varukha
 * @version 1.0
 *
 */
public class ToOrdersPageUserCommand implements Command {
	private static final Logger logger = LogManager.getLogger();
	InvoiceService invoiceService = AppContext.getAppContext().getInvoiceService();
	int startRow = 0;

	@Override
	public Router execute(HttpServletRequest request, HttpServletResponse response) {
		logger.log(Level.INFO, "Executing ToOrdersPageUserCommand");
		int numberOfPages;
		Router router = new Router();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(ParameterAndAttribute.USER);
		long userId = user.getUserId();

		try {
			List<Invoice> invoices = invoiceService.getAllOrdersInfoFromInvoiceByUserId(userId, startRow);
			numberOfPages = invoiceService.getNumberOfRowsInInvoiceTableByUserId(userId);
			if (!invoices.isEmpty()) {
				session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_ORDERS_USER_PAGE);
				router.setPagePath(PagePath.ORDERS_USER_PAGE);
				request.setAttribute(ParameterAndAttribute.INVOICE_LIST, invoices);
				session.setAttribute(ParameterAndAttribute.NUMBER_OF_PAGES, numberOfPages);
			} else {
				logger.log(Level.ERROR, "Nothing were found");
				request.setAttribute(ParameterAndAttribute.MESSAGE, Message.NO_ORDERS_WERE_FOUND);
				session.setAttribute(ParameterAndAttribute.CURRENT_PAGE, PagePath.TO_PERSONAL_PAGE);
				router.setPagePath(PagePath.PERSONAL_PAGE_USER);
			}
		} catch (ServiceException e) {
			logger.log(Level.ERROR, "Service exception in ToOrdersPageUserCommand" + e.getMessage());
			router.setPagePath(PagePath.ERROR);
		}
		return router;
	}
}
