package com.varukha.webproject.command;

/**
 * Class CommandName contain names of commands from jsp pages
 * that used to execute user's operations.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */

public final class CommandName {

    // Base commands
    public static final String TO_MAIN = "to_main";
    public static final String TO_NO_ACCESS_PAGE = "to_no_access_page";
    public static final String TO_ORDER_INFO_PAGE_GUEST = "to_order_info_page_guest";
    public static final String SET_LOCALE = "set_locale";
    public static final String FIND_ORDER_BY_INVOICE_NUMBER = "find_order_by_invoice_number";
    public static final String CALCULATE_DELIVERY_PRICE = "calculate_delivery_price";
    public static final String TO_PRICE_CALCULATION_PAGE = "to_price_calculation_page";
    public static final String CALCULATE_TOTAL_SERVICE_PRICE = "calculate_total_price";
    public static final String TO_DELIVERY_SERVICE_PAGE = "to_delivery_service_page";
    public static final String TO_TARIFFS_PAGE = "to_tariffs_page";
    public static final String LOG_IN = "log_in";
    public static final String TO_SIGN_IN = "to_sign_in";
    public static final String LOG_OUT = "log_out";
    public static final String SIGN_UP = "sign_up";
    public static final String TO_SIGN_UP = "to_sign_up";
    public static final String UNKNOWN_COMMAND = "unknown_command";
    public static final String TO_PERSONAL_PAGE = "to_personal_page";

    // User commands
    public static final String TO_ORDER_INFO_PAGE_USER = "to_order_info_page_user";
    public static final String GET_ALL_ORDERS_PAGINATION_COMMAND_USER = "get_all_orders_pagination_command_user";
    public static final String UPDATE_ORDER_INFO = "update_order_info";
    public static final String TO_SORTED_USER_ORDERS_LIST_BY_DELIVERY_CITY = "to_sorted_user_orders_list_by_delivery_city";
    public static final String SORT_USER_ORDERS_LIST_BY_DELIVERY_CITY = "sort_user_orders_list_by_delivery_city";
    public static final String TO_ORDER_UPDATE_PAGE = "to_order_update_page";
    public static final String DELIVERY_PAYMENT = "delivery_payment";
    public static final String TO_DELIVERY_PAYMENT_PAGE = "to_delivery_payment_page";
    public static final String TOP_UP_THE_BALANCE = "top_up_the_balance";
    public static final String TO_ORDERS_PAGE_USER = "to_orders_page_user";
    public static final String CREATE_NEW_ORDER = "create_new_order";
    public static final String TO_CREATE_ORDER_PAGE = "to_create_order_page";


    // Manager commands
    public static final String GET_ALL_ORDERS_PAGINATION_COMMAND_MANAGER = "get_all_orders_pagination_command_manager";
    public static final String GET_DELIVERY_REPORT_BY_DESTINATION_PDF = "get_delivery_report_by_destination_pdf";
    public static final String GET_DELIVERY_REPORT_BY_DAYS_PDF = "get_delivery_report_by_days_pdf";
    public static final String CREATE_PAYMENT_BILL_PDF = "create_payment_bill_pdf";
    public static final String TO_ORDER_PAGE_MANAGER = "to_order_page_manager";
    public static final String INVOICE_UPDATE_MANAGER_COMMAND = "invoice_update_manager_command";
    public static final String TO_ORDER_UPDATE_PAGE_MANAGER = "to_order_update_page_manager";
    public static final String TO_BILL_PAGE = "to_bill_page";

    private CommandName() {

    }

}
