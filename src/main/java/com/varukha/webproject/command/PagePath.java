package com.varukha.webproject.command;


/**
 * Class contain all page paths and commands routs
 *
 * @author Dmytro Varukha
 *
 */


public final class PagePath {




	// Basic pages and commands routs

	public static final String LOG_IN_PAGE = "/pages/logIn.jsp";
	public static final String INDEX = "/index.jsp";
	public static final String TO_LOG_IN_PAGE ="/controller?command=to_sign_in";

	public static final String REGISTRATION_PAGE = "/pages/registration.jsp";
	public static final String TO_REGISTRATION_PAGE ="/controller?command=to_sign_up";

	public static final String MAIN_PAGE = "/pages/main.jsp";
	public static final String TO_MAIN_PAGE="/controller?command=to_main";

	public static final String PRICE_CALCULATION_PAGE = "/pages/calculations.jsp";
	public static final String TO_PRICE_CALCULATION_PAGE ="/controller?command=to_price_calculation_page";


	public static final String ERROR = "/pages/error/error_404.jsp";



	public static final String TARIFFS_PAGE = "/pages/tariffs.jsp";
	public static final String TO_TARIFFS_PAGE ="/controller?command=to_tariffs_page";

	public static final String DELIVERY_SERVICE_PAGE = "/pages/deliveryService.jsp";
	public static final String TO_DELIVERY_SERVICE_PAGE ="/controller?command=to_delivery_service_page";

	public static final String ORDER_INFO_PAGE = "/pages/orderInfoPage.jsp";
	public static final String TO_ORDER_INFO_PAGE = "/controller?command=to_order_info_page_user";

	public static final String ORDER_INFO_PAGE_GUEST = "/pages/orderInfoPageGuest.jsp";
	public static final String TO_ORDER_INFO_PAGE_GUEST = "/controller?command=to_order_info_page_guest";

	public static final String NO_ACCESS_PAGE = "/pages/noAccessPage.jsp";
	public static final String HEADER = "/pages/header.jsp";

	public static final String TO_NO_ACCESS_PAGE = "/controller?command=to_no_access_page";

	public static final String TO_SERVICES_PAGE="/controller?command=to_services";





	// Manager pages and commands routs


	public static final String BILL_PAGE = "/pages/bill.jsp";
	public static final String TO_BILL_PAGE ="/controller?command=to_bill_page";

	public static final String PERSONAL_PAGE_MANAGER = "/pages/personalPageManager.jsp";

	public static final String ORDERS_MANAGER_PAGE = "/pages/ordersManagerPage.jsp";
	public static final String TO_ORDERS_MANAGER_PAGE = "/controller?command=to_order_page_manager";
	public static final String ORDER_UPDATE_PAGE_MANAGER = "/pages/orderUpdateDataManager.jsp";
	public static final String TO_ORDER_UPDATE_PAGE_MANAGER = "/controller?command=to_order_update_page_manager";




	// User pages and commands routs

	public static final String PERSONAL_PAGE_USER = "/pages/personalPageUser.jsp";

	public static final String ORDER_UPDATE_PAGE_USER = "/pages/orderUpdateDataUser.jsp";
	public static final String TO_ORDER_UPDATE_PAGE_USER = "/controller?command=to_order_update_page";

	public static final String CREATE_ORDER_PAGE = "/pages/orderCreate.jsp";
	public static final String TO_CREATE_ORDER_PAGE = "/controller?command=to_create_order_page";

	public static final String ORDERS_USER_PAGE = "/pages/ordersUserPage.jsp";
	public static final String TO_ORDERS_USER_PAGE = "/controller?command=to_orders_page_user";

	public static final String DELIVERY_PAYMENT_PAGE = "/pages/userPaymentPage.jsp";
	public static final String TO_DELIVERY_PAYMENT_PAGE = "/controller?command=to_delivery_payment_page";

	public static final String SORTED_USER_ORDERS_BY_DELIVERY_CITY = "/pages/viewUserOrdersByDeliveryCity.jsp";
	public static final String TO_SORTED_USER_ORDERS_BY_DELIVERY_CITY = "/controller?command=to_sorted_user_orders_list_by_delivery_city";



	// To personal page

	public static final String TO_PERSONAL_PAGE = "/controller?command=to_personal_page";


	private PagePath() {
	}
}
