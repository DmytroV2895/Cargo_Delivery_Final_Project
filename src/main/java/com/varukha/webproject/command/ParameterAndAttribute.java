package com.varukha.webproject.command;

/**
 * Contain all parameters and attributes that use into system
 * @author Dmytro Varukha
 *
 */
public final class ParameterAndAttribute {


	public static final String COMMAND = "command";
	public static final String CURRENT_PAGE = "current_page";
	public static final String DOCUMENT = "document";
	public static final String PARCEL = "parcel";
	public static final String CARGO = "cargo";
	public static final String LANGUAGE = "language";
	public static final String LOCALE = "locale";

	public static final String MESSAGE = "message";
	public static final String USER = "user";
	public static final String USER_EMAIL = "email";
	public static final String USER_CONFIRMED_PASSWORD = "confirmed_password";
	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "name";
	public static final String USER_SURNAME = "surname";
	public static final String USER_PHONE = "phone";
	public static final String USER_PASSWORD = "password";
	public static final String USER_ACCOUNT = "user_account";
	public static final String USER_ACCOUNT_MONEY_SUM = "user_account_add_money";
	public static final String USER_ROLE = "role";
	public static final String DELIVERY_ID = "delivery_id";

	// Orders
	public static final String ID_ORDER = "order_id";
	public static final String ORDER_NAME = "order_name";
	public static final String ORDER_TYPE = "order_type";
	public static final String ORDER_DESCRIPTION = "order_description";
	public static final String ORDER_PRICE = "price";
	public static final String ORDER_WEIGHT = "weight";
	public static final String ORDER_LENGTH = "length";
	public static final String ORDER_HEIGHT = "height";
	public static final String ORDER_WIDTH = "width";
	public static final String ORDER_VOLUME_WEIGHT = "volume_weight";
	public static final String ORDER_VOLUME = "volume";
	public static final String ORDER_PAYMENT_STATUS = "payment_status";


	// Delivery
	public static final String DELIVERY_TYPE = "delivery_type";
	public static final String DELIVERY_DISTANCE = "distance";
	public static final String RECIPIENT_NAME = "recipient_name";
	public static final String RECIPIENT_SURNAME = "recipient_surname";
	public static final String RECIPIENT_PHONE = "recipient_phone";
	public static final String FIRST_ADDRESS_ID = "first_address_id";
	public static final String SECOND_ADDRESS_ID = "second_address_id";


	// First Address
	public static final String ID_FIRST_ADDRESS = "id_first_address";
	public static final String FIRST_CITY = "first_city";
	public static final String FIRST_STREET_NAME = "first_street_name";
	public static final String FIRST_STREET_NUMBER = "first_street_number";
	public static final String FIRST_HOUSE_NUMBER = "first_house_number";


	// Second Address
	public static final String ID_SECOND_ADDRESS = "id_second_address";
	public static final String SECOND_CITY = "second_city";
	public static final String SECOND_STREET_NAME = "second_street_name";
	public static final String SECOND_STREET_NUMBER = "second_street_number";
	public static final String SECOND_HOUSE_NUMBER = "second_house_number";


	// Invoice
	public static final String INVOICE_BY_ID = "invoice_by_id";
	public static final String ID_INVOICE = "id_invoice";
	public static final String DELIVERY_DATE = "delivery_date";
	public static final String ORDER_STATUS = "order_status";
	public static final String USER_BY_ID = "user_by_id";
	public static final String INVOICE_LIST = "invoice_list";
	public static final String INVOICE_LIST_MANAGER = "invoice_list_manager";
	public static final String TOTAL_PRICE = "total_price";
	public static final String START_FROM = "start_from";
	public static final String NUMBER_OF_PAGES = "number_of_pages";


	private ParameterAndAttribute() {
		
	}
}
