package com.varukha.webproject.command;

/**
 * Class Message contains all types of messages that using in the application in order to
 * display on user interface.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public final class Message {

	public static final String USER_WAS_NOT_ADDED = "User was not added";
	public static final String USER_WAS_NOT_FOUND = "User was not found";
	public static final String INCORRECT_EMAIL_OR_PASSWORD = "Incorrect email or password";
	public static final String NOTHING_FOUND = "Nothing were found";
	public static final String SUCCESSFUL ="Successful";
	public static final String UPDATE_INVOICE_MANAGER_MESSAGE ="Invoice data were updated successfully";
	public static final String NO_ORDERS_WERE_FOUND ="No orders were found";
	public static final String ADD_MONEY_SUCCESSFUL ="Your top up balance operation was successful";
	public static final String ADD_MONEY_NOT_SUCCESSFUL ="Something wrong during your top up balance operation";
	public static final String GUEST_NO_ORDER_INFO ="No orders for you!";
	public static final String ORDER_WAS_CREATED ="Congratulation, your order was created successfully";
	public static final String ORDER_WAS_UPDATED ="Congratulation, your order was updated successfully";
	public static final String INCORRECT_DATA_INPUT = "Incorrect order type or cargo dimensions or unavailable delivery type. Please visit the tariffs page to find out accessible input data";
	public static final String INCORRECT_ORDER_TYPE = "Incorrect order type because of exceeding of permissible limits for weight value";
	public static final String INCORRECT_WEIGHT_INPUT = "Unsupported operation exceed an accessible limit for current order type or ";
	public static final String PAYMENT_SUCCESSFUL = "You payment operation is successful";
	public static final String UNKNOWN_PROBLEM = "Unknown problem";
	public static final String UNSUCCESSFUL ="Unsuccessful";
	public static final String USER_ALREADY_EXIST ="User with that login already exists";
	public static final String PASSWORD_DOES_NOT_MATCH ="Password and confirmed password do not match";
	public static final String USER_CREATED ="User successfully created";
	public static final String CALCULATION_SUCCESS = "Your successfully calculated delivery price!";
	public static final String ACCESS_DENIED = "Access denied";

	private Message() {
		
	}
}
