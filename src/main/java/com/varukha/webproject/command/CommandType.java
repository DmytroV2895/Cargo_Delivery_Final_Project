package com.varukha.webproject.command;


import com.varukha.webproject.command.impl.base.*;
import com.varukha.webproject.command.impl.calculates.CalculateDeliveryPriceCommand;
import com.varukha.webproject.command.impl.calculates.CalculateDeliveryServiceCommand;
import com.varukha.webproject.command.impl.manager.*;
import com.varukha.webproject.command.impl.routs.*;
import com.varukha.webproject.command.impl.user.*;


/**
 * Enum contains all types of commands using in application
 *
 * @author Dmytro Varukha
 */
public enum CommandType {


    TO_SORTED_USER_ORDERS_LIST_BY_DELIVERY_CITY {
        {
            this.command = new ToSortedOrdersByDeliveryAddressCommand();
        }
    },

    SORT_USER_ORDERS_LIST_BY_DELIVERY_CITY {
        {
            this.command = new SortAllOrdersByDeliveryAddressCommand();
        }
    },

    TO_MAIN {
        {
            this.command = new ToMainCommand();
        }
    },

    UPDATE_ORDER_INFO {
        {
            this.command = new UpdateInvoiceInformationCommand();
        }
    },

    GET_ALL_ORDERS_PAGINATION_COMMAND_USER {
        {
            this.command = new FindAllOrdersUserCommand();
        }
    },

    GET_ALL_ORDERS_PAGINATION_COMMAND_MANAGER {
        {
            this.command = new FindAllOrdersManagerCommand();
        }
    },

    TO_NO_ACCESS_PAGE {
        {
            this.command = new ToNoAccessPage();
        }
    },


    GET_DELIVERY_REPORT_BY_DESTINATION_PDF {
        {
            this.command = new PdfCreationManagerDeliveryReportByDestinationCommand();
        }
    },

    GET_DELIVERY_REPORT_BY_DAYS_PDF {
        {
            this.command = new PdfCreationMangerDeliveryReportByDaysCommand();
        }
    },

    CREATE_PAYMENT_BILL_PDF {
        {
            this.command = new PdfCreationPaymentBillCommand();
        }
    },

    TO_ORDER_INFO_PAGE_USER {
        {
            this.command = new ToOrderInfoPageUserCommand();
        }
    },

    TO_ORDER_INFO_PAGE_GUEST {
        {
            this.command = new ToOrderInfoPageGuestCommand();
        }
    },

    TO_ORDER_PAGE_MANAGER {
        {
            this.command = new ToOrdersPageManagerCommand();
        }
    },

   INVOICE_UPDATE_MANAGER_COMMAND {
        {
            this.command = new UpdateInvoiceManagerCommand();
        }
    },

    TO_ORDER_UPDATE_PAGE_MANAGER {
        {
            this.command = new ToOrdersUpdatePageManagerCommand();
        }
    },

    TO_ORDER_UPDATE_PAGE {
        {
            this.command = new ToOrderUpdatePageUserCommand();
        }
    },

    DELIVERY_PAYMENT {
        {
            this.command = new DeliveryPaymentCommand();
        }
    },

    TOP_UP_THE_BALANCE {
        {
            this.command = new TopUpTheUserBalanceCommand();
        }
    },

    TO_DELIVERY_PAYMENT_PAGE {
        {
            this.command = new ToDeliveryServicePaymentPageCommand();
        }
    },

    SET_LOCALE {
        {
            this.command = new SetLocaleCommand();
        }
    },

    FIND_ORDER_BY_INVOICE_NUMBER {
        {
            this.command = new FindRecipientOrderByInvoiceIdCommand();
        }
    },
    TO_ORDERS_PAGE_USER {
        {
            this.command = new ToOrdersPageUserCommand();
        }
    },

    CALCULATE_DELIVERY_PRICE {
        {
            this.command = new CalculateDeliveryPriceCommand();
        }
    },

    CALCULATE_TOTAL_SERVICE_PRICE {
        {
            this.command = new CalculateDeliveryServiceCommand();
        }
    },

    CREATE_NEW_ORDER {
        {
            this.command = new CreateNewOrderCommand();
        }
    },

    TO_BILL_PAGE {
        {
            this.command = new ToBillCreationPageManagerCommand();
        }
    },
    TO_CREATE_ORDER_PAGE {
        {
            this.command = new ToOrderCreatePageUserCommand();
        }
    },
    TO_DELIVERY_SERVICE_PAGE {
        {
            this.command = new ToDeliveryServiceCommand();
        }
    },
    TO_TARIFFS_PAGE {
        {
            this.command = new ToTariffsCommand();
        }
    },
    TO_PRICE_CALCULATION_PAGE {
        {
            this.command = new ToPriceCalculationCommand();
        }
    },

    TO_PERSONAL_PAGE {
        {
            this.command = new ToPersonalPageCommand();
        }
    },

    LOG_IN {
        {
            this.command = new LogInCommand();
        }
    },
    LOG_OUT {
        {
            this.command = new LogOutCommand();
        }
    },
    SIGN_UP {
        {
            this.command = new SignUpCommand();
        }
    },
    TO_SIGN_IN {
        {
            this.command = new ToLogInCommand();
        }
    },
    TO_SIGN_UP {
        {
            this.command = new ToSignUpCommand();
        }
    },
    UNKNOWN_COMMAND {
        {
            this.command = new DefaultCommand();
        }
    };





    Command command;

    /**
     * @return {@link Command}
     */
    public Command getCurrentCommand() {
        return command;
    }
}
