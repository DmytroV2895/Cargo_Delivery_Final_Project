package com.varukha.webproject.model.dao;

/**
 * Column names in database
 * @author Dmytro Varukha
 *
 */
public final class ColumnName {

	//User

	public static final String ID_USER = "id_user";
	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "name";
	public static final String USER_SURNAME = "surname";
	public static final String USER_EMAIL = "email";
	public static final String USER_PASSWORD = "password";;
	public static final String USER_PHONE = "phone";
	public static final String USER_ROLE = "role";
	public static final String USER_PAYMENT_ACCOUNT = "account";
	public static final String USER_PROFILE_IS_ACTIVE = "isActive";


	// Orders

	public static final String ID_ORDER = "id_order";
	public static final String ORDER_ID = "order_id";
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


	// Delivery

	public static final String ID_DELIVERY = "id_delivery";
	public static final String DELIVERY_TYPE = "delivery_type";
	public static final String DELIVERY_DISTANCE = "distance";
	public static final String RECIPIENT_NAME = "recipient_name";
	public static final String RECIPIENT_SURNAME = "recipient_surname";
	public static final String RECIPIENT_PHONE = "recipient_phone";


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

	public static final String ID_INVOICE = "id_invoice";
	public static final String DELIVERY_DATE = "delivery_date";
	public static final String DELIVERY_PRICE = "delivery_price";
	public static final String TOTAL_PRICE = "total_price";
	public static final String INVOICE_CREATION_DATE_TIME = "creation_date_time";
	public static final String DELIVERY_PAID_STATUS = "isPAid";
	public static final String ORDER_STATUS = "order_status";


	private ColumnName() {
	}
}
