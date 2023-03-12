package com.varukha.webproject.command;

import com.varukha.webproject.command.impl.base.*;
import com.varukha.webproject.command.impl.calculates.CalculateDeliveryPriceCommand;
import com.varukha.webproject.command.impl.calculates.CalculateDeliveryServiceCommand;
import com.varukha.webproject.command.impl.manager.FindAllOrdersManagerCommand;
import com.varukha.webproject.command.impl.manager.PDFReportCommand;
import com.varukha.webproject.command.impl.manager.UpdateInvoiceManagerCommand;
import com.varukha.webproject.command.impl.routs.*;
import com.varukha.webproject.command.impl.user.*;
import com.varukha.webproject.util.report.impl.PDFReportByReportType;
import com.varukha.webproject.util.report.impl.PDFReportFactory;
import java.util.HashMap;
import java.util.Map;
import static com.varukha.webproject.command.CommandName.*;

/**
 * Class CommandFactory contains all types of commands that using in application.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public final class CommandFactory {

    private static final CommandFactory commandFactory = new CommandFactory();
    static PDFReportFactory pdfReportFactory;
    private static final Map<String, Command> COMMAND_MAP = new HashMap<>();
    public static CommandFactory getCommandFactory() {
        return commandFactory;
    }

    static {
        COMMAND_MAP.put(TO_SORTED_USER_ORDERS_LIST_BY_DELIVERY_CITY, new ToFilteredOrdersByDeliveryCityCommand());
        COMMAND_MAP.put(SORT_USER_ORDERS_LIST_BY_DELIVERY_CITY, new FilterAllOrdersByDeliveryAddressCommand());
        COMMAND_MAP.put(TO_MAIN, new ToMainCommand());
        COMMAND_MAP.put(UPDATE_ORDER_INFO, new UpdateInvoiceInformationCommand());
        COMMAND_MAP.put(GET_ALL_ORDERS_PAGINATION_COMMAND_USER, new FindAllOrdersUserCommand());
        COMMAND_MAP.put(GET_ALL_ORDERS_PAGINATION_COMMAND_MANAGER, new FindAllOrdersManagerCommand());

        COMMAND_MAP.put(TO_NO_ACCESS_PAGE, new ToNoAccessPage());
        COMMAND_MAP.put(GET_DELIVERY_REPORT_BY_DESTINATION_PDF, new PDFReportCommand(new PDFReportByReportType(pdfReportFactory)));
        COMMAND_MAP.put(GET_DELIVERY_REPORT_BY_DAYS_PDF, new PDFReportCommand(new PDFReportByReportType(pdfReportFactory)));

        COMMAND_MAP.put(CREATE_PAYMENT_BILL_PDF, new PDFReportCommand(new PDFReportByReportType(pdfReportFactory)));
        COMMAND_MAP.put(TO_ORDER_INFO_PAGE_USER, new ToOrderInfoPageUserCommand());
        COMMAND_MAP.put(TO_ORDER_INFO_PAGE_GUEST, new ToOrderInfoPageGuestCommand());
        COMMAND_MAP.put(TO_ORDER_PAGE_MANAGER, new ToOrdersPageManagerCommand());
        COMMAND_MAP.put(INVOICE_UPDATE_MANAGER_COMMAND, new UpdateInvoiceManagerCommand());

        COMMAND_MAP.put(TO_ORDER_UPDATE_PAGE_MANAGER, new ToOrdersUpdatePageManagerCommand());
        COMMAND_MAP.put(TO_ORDER_UPDATE_PAGE, new ToOrderUpdatePageUserCommand());
        COMMAND_MAP.put(DELIVERY_PAYMENT, new DeliveryPaymentCommand());
        COMMAND_MAP.put(TO_DELIVERY_PAYMENT_PAGE, new ToDeliveryServicePaymentPageCommand());
        COMMAND_MAP.put(TOP_UP_THE_BALANCE, new TopUpTheUserBalanceCommand());

        COMMAND_MAP.put(SET_LOCALE, new SetLocaleCommand());
        COMMAND_MAP.put(TO_ORDERS_PAGE_USER, new ToOrdersPageUserCommand());
        COMMAND_MAP.put(CALCULATE_DELIVERY_PRICE, new CalculateDeliveryPriceCommand());
        COMMAND_MAP.put(TO_PRICE_CALCULATION_PAGE, new ToDeliveryPriceCalculationCommand());
        COMMAND_MAP.put(CREATE_NEW_ORDER, new CreateNewOrderCommand());
        COMMAND_MAP.put(TO_CREATE_ORDER_PAGE, new ToOrderCreatePageUserCommand());
        COMMAND_MAP.put(TO_BILL_PAGE, new ToBillCreationPageManagerCommand());

        COMMAND_MAP.put(TO_DELIVERY_SERVICE_PAGE, new ToDeliveryServiceCommand());
        COMMAND_MAP.put(TO_TARIFFS_PAGE, new ToTariffsCommand());
        COMMAND_MAP.put(TO_PERSONAL_PAGE, new ToPersonalPageCommand());
        COMMAND_MAP.put(LOG_IN, new LogInCommand());
        COMMAND_MAP.put(TO_SIGN_IN, new ToLogInCommand());
        COMMAND_MAP.put(LOG_OUT, new LogOutCommand());
        COMMAND_MAP.put(SIGN_UP, new SignUpCommand());
        COMMAND_MAP.put(TO_SIGN_UP, new ToSignUpCommand());
        COMMAND_MAP.put(UNKNOWN_COMMAND, new DefaultCommand());
        COMMAND_MAP.put(FIND_ORDER_BY_INVOICE_NUMBER, new FindRecipientOrderByInvoiceIdCommand());
        COMMAND_MAP.put(CALCULATE_TOTAL_SERVICE_PRICE, new CalculateDeliveryServiceCommand());

    }
    private CommandFactory() {}

    /**
     * Method getCurrentCommand contain command by its name
     *
     * @param commandName - command name to search in map
     * @return required command implementation or DefaultAction if there is no such command.
     */
    public Command getCurrentCommand(String commandName) {
        return COMMAND_MAP.getOrDefault(commandName, new DefaultCommand());
    }
}