package com.varukha.webproject.model.dao;


public final class SQL_Queries {

    // Users

    public static final String FIND_ALL_USERS = "SELECT id_user, name , surname, email, phone, role, account FROM users";
    public static final String FIND_USER_BY_EMAIL = "SELECT id_user, name , surname, email, phone, password, role, account FROM users WHERE email = ?";
    public static final String FIND_PASSWORD_BY_EMAIL = "SELECT password FROM users WHERE email = ?";
    public static final String FIND_USERS_BY_ID = "SELECT id_user, name , surname, email, phone, role, account FROM users WHERE id_user = ?";
    public static final String UPDATE_DELIVERY_PAYMENT_STATUS = "UPDATE invoice set isPaid = TRUE WHERE id_invoice = ?";
    public static final String ADD_MONEY_TO_USER_ACCOUNT = "UPDATE users SET account = account + ? WHERE id_user = ?";
    public static final String PAYMENT_FOR_DELIVERY_SERVICE = "UPDATE users SET account = account - ? WHERE id_user = ?";
    public static final String ADD_USER = "INSERT INTO users (name , surname, email, phone, password, role, account) values(?,?,?,?,?,?,?)";


    // Orders

    public static final String ADD_ORDER = "INSERT INTO orders (order_name , order_type, order_description, price, weight, length, height, width, volume, volume_weight) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_ORDER_DATA = "UPDATE orders SET order_name = ? , order_type = ?, order_description = ?, price = ?, weight = ?, length = ?, height = ?, width = ?, volume = ?, volume_weight = ? WHERE id_order = ?";


    // Delivery

    public static final String ADD_DELIVERY = "INSERT INTO delivery (delivery_type, distance, recipient_name, recipient_surname, recipient_phone) values(?, ?, ?, ?, ?)";
    public static final String UPDATE_DELIVERY_DATA = "UPDATE delivery set delivery_type = ?, distance = ?, recipient_name = ?, recipient_surname = ?, recipient_phone = ? WHERE id_delivery = ?";


    // Invoice

    public static final String CHANGE_INVOICE_MANAGER = "UPDATE invoice SET delivery_date = ?, order_status = ? WHERE id_invoice = ?";
    public static final String ADD_INVOICE = "INSERT INTO invoice (delivery_price, total_price, isPaid, order_status, delivery_id, user_id, order_id, first_address_id, second_address_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_INVOICE_DATA = "UPDATE invoice set delivery_price = ?, total_price = ? WHERE id_invoice = ?";

    public static final String FIND_ALL_INVOICE_DATA_BY_USER_ID_FROM_ROW = "SELECT\n" +
            "\tfirst_address.id_first_address, first_address.first_city, first_address.first_street_name, first_address.first_street_number, first_address.first_house_number,\n" +
            "\tsecond_address.id_second_address, second_address.second_city, second_address.second_street_name, second_address.second_street_number, second_address.second_house_number,\n" +
            "   delivery.id_delivery, delivery.delivery_type, delivery.distance, delivery.recipient_name, delivery.recipient_surname, delivery.recipient_phone, \n" +
            "   users.id_user, users.name, users.surname, users.email, users.phone, users.account, \n" +
            "   orders.id_order, orders.order_name, orders.order_type, orders.order_description, orders.price, orders.weight, orders.length, orders.height, orders.width, orders.volume, orders.volume_weight, \n" +
            "   invoice.id_invoice, invoice.delivery_date, invoice.delivery_price, invoice.total_price, invoice.creation_date_time, invoice.isPaid, invoice.order_status\n" +
            "   \n" +
            "  FROM invoice\n" +
            "  \n" +
            "    JOIN first_address on first_address.id_first_address = invoice.first_address_id\n" +
            "\tJOIN second_address on  second_address.id_second_address = invoice.second_address_id\n" +
            "   JOIN delivery on delivery.id_delivery = invoice.delivery_id \n" +
            "   JOIN orders on orders.id_order = invoice.order_id \n" +
            "   JOIN users on users.id_user = invoice.user_id \n" +
            "   WHERE users.id_user = ? LIMIT ?,?";

    public static final String FIND_INVOICE_BY_ID = "SELECT\n" +
            "\tfirst_address.id_first_address, first_address.first_city, first_address.first_street_name, first_address.first_street_number, first_address.first_house_number,\n" +
            "\tsecond_address.id_second_address, second_address.second_city, second_address.second_street_name, second_address.second_street_number, second_address.second_house_number,\n" +
            "   delivery.id_delivery, delivery.delivery_type, delivery.distance, delivery.recipient_name, delivery.recipient_surname, delivery.recipient_phone, \n" +
            "   users.id_user, users.name, users.surname, users.email, users.phone, users.account, \n" +
            "   orders.id_order, orders.order_name, orders.order_type, orders.order_description, orders.price, orders.weight, orders.length, orders.height, orders.width, orders.volume, orders.volume_weight, \n" +
            "   invoice.id_invoice, invoice.delivery_date, invoice.delivery_price, invoice.total_price, invoice.creation_date_time, invoice.isPaid, invoice.order_status\n" +
            "   \n" +
            "  FROM invoice\n" +
            "  \n" +
            "    JOIN first_address on first_address.id_first_address = invoice.first_address_id\n" +
            "\tJOIN second_address on  second_address.id_second_address = invoice.second_address_id\n" +
            "   JOIN delivery on delivery.id_delivery = invoice.delivery_id \n" +
            "   JOIN orders on orders.id_order = invoice.order_id \n" +
            "   JOIN users on users.id_user = invoice.user_id \n" +
            "   WHERE invoice.id_invoice = ?";

    public static final String FIND_INVOICE_DELIVERY_DATE = "SELECT\n" +
            "\tfirst_address.id_first_address, first_address.first_city, first_address.first_street_name, first_address.first_street_number, first_address.first_house_number,\n" +
            "\tsecond_address.id_second_address, second_address.second_city, second_address.second_street_name, second_address.second_street_number, second_address.second_house_number,\n" +
            "   delivery.id_delivery, delivery.delivery_type, delivery.distance, delivery.recipient_name, delivery.recipient_surname, delivery.recipient_phone, \n" +
            "   users.id_user, users.name, users.surname, users.email, users.phone, users.account, \n" +
            "   orders.id_order, orders.order_name, orders.order_type, orders.order_description, orders.price, orders.weight, orders.length, orders.height, orders.width, orders.volume, orders.volume_weight, \n" +
            "   invoice.id_invoice, invoice.delivery_date, invoice.delivery_price, invoice.total_price, invoice.creation_date_time, invoice.isPaid, invoice.order_status\n" +
            "   \n" +
            "  FROM invoice\n" +
            "  \n" +
            "    JOIN first_address on first_address.id_first_address = invoice.first_address_id\n" +
            "\tJOIN second_address on  second_address.id_second_address = invoice.second_address_id\n" +
            "   JOIN delivery on delivery.id_delivery = invoice.delivery_id \n" +
            "   JOIN orders on orders.id_order = invoice.order_id \n" +
            "   JOIN users on users.id_user = invoice.user_id \n" +
            "   WHERE invoice.delivery_date = ?";

    public static final String FIND_INVOICE_BY_DESTINATION = "SELECT \n" +
            "\tfirst_address.id_first_address, first_address.first_city, first_address.first_street_name, first_address.first_street_number, first_address.first_house_number,\n" +
            "\tsecond_address.id_second_address, second_address.second_city, second_address.second_street_name, second_address.second_street_number, second_address.second_house_number,\n" +
            "   delivery.id_delivery, delivery.delivery_type, delivery.distance, delivery.recipient_name, delivery.recipient_surname, delivery.recipient_phone, \n" +
            "   users.id_user, users.name, users.surname, users.email, users.phone, users.account, \n" +
            "   orders.id_order, orders.order_name, orders.order_type, orders.order_description, orders.price, orders.weight, orders.length, orders.height, orders.width, orders.volume, orders.volume_weight, \n" +
            "   invoice.id_invoice, invoice.delivery_date, invoice.delivery_price, invoice.total_price, invoice.creation_date_time, invoice.isPaid, invoice.order_status\n" +
            "   \n" +
            "  FROM invoice\n" +
            "  \n" +
            "    JOIN first_address on first_address.id_first_address = invoice.first_address_id\n" +
            "\tJOIN second_address on  second_address.id_second_address = invoice.second_address_id\n" +
            "   JOIN delivery on delivery.id_delivery = invoice.delivery_id \n" +
            "   JOIN orders on orders.id_order = invoice.order_id \n" +
            "   JOIN users on users.id_user = invoice.user_id \n" +
            "   WHERE ((first_address.first_city = ?) AND (second_address.second_city = ?)) OR ((second_address.second_city = ?) AND (first_address.first_city = ?))";

    public static final String COUNT_ALL_INVOICES = "SELECT COUNT(*) FROM invoice";
    public static final String COUNT_ALL_INVOICES_BY_USER_ID = "SELECT COUNT(*) FROM invoice WHERE user_id = ?";
    public static final String FIND_INVOICES_FROM_ROW = "SELECT \n" +
            "\tfirst_address.id_first_address, first_address.first_city, first_address.first_street_name, first_address.first_street_number, first_address.first_house_number,\n" +
            "\tsecond_address.id_second_address, second_address.second_city, second_address.second_street_name, second_address.second_street_number, second_address.second_house_number,\n" +
            "   delivery.id_delivery, delivery.delivery_type, delivery.distance, delivery.recipient_name, delivery.recipient_surname, delivery.recipient_phone, \n" +
            "   users.id_user, users.name, users.surname, users.email, users.phone, users.account, \n" +
            "   orders.id_order, orders.order_name, orders.order_type, orders.order_description, orders.price, orders.weight, orders.length, orders.height, orders.width, orders.volume, orders.volume_weight, \n" +
            "   invoice.id_invoice, invoice.delivery_date, invoice.delivery_price, invoice.total_price, invoice.creation_date_time, invoice.isPaid, invoice.order_status\n" +
            "   \n" +
            "  FROM invoice\n" +
            "  \n" +
            "    JOIN first_address on first_address.id_first_address = invoice.first_address_id\n" +
            "\tJOIN second_address on  second_address.id_second_address = invoice.second_address_id\n" +
            "   JOIN delivery on delivery.id_delivery = invoice.delivery_id \n" +
            "   JOIN orders on orders.id_order = invoice.order_id \n" +
            "   JOIN users on users.id_user = invoice.user_id \n" +
            "   ORDER BY invoice.creation_date_time LIMIT ?,?";
    public static final String FIND_INVOICES_FROM_ROW_BY_DELIVERY_CITY = "SELECT \n" +
            "\tfirst_address.id_first_address, first_address.first_city, first_address.first_street_name, first_address.first_street_number, first_address.first_house_number,\n" +
            "\tsecond_address.id_second_address, second_address.second_city, second_address.second_street_name, second_address.second_street_number, second_address.second_house_number,\n" +
            "   delivery.id_delivery, delivery.delivery_type, delivery.distance, delivery.recipient_name, delivery.recipient_surname, delivery.recipient_phone, \n" +
            "   users.id_user, users.name, users.surname, users.email, users.phone, users.account, \n" +
            "   orders.id_order, orders.order_name, orders.order_type, orders.order_description, orders.price, orders.weight, orders.length, orders.height, orders.width, orders.volume, orders.volume_weight, \n" +
            "   invoice.id_invoice, invoice.delivery_date, invoice.delivery_price, invoice.total_price, invoice.creation_date_time, invoice.isPaid, invoice.order_status\n" +
            "   \n" +
            "  FROM invoice\n" +
            "  \n" +
            "    JOIN first_address on first_address.id_first_address = invoice.first_address_id\n" +
            "\tJOIN second_address on  second_address.id_second_address = invoice.second_address_id\n" +
            "   JOIN delivery on delivery.id_delivery = invoice.delivery_id \n" +
            "   JOIN orders on orders.id_order = invoice.order_id \n" +
            "   JOIN users on users.id_user = invoice.user_id \n" +
            "   WHERE users.id_user = ? AND second_address.second_city = ? LIMIT ?,?";

    public static final String COUNT_ALL_INVOICES_BY_DELIVERY_CITY = "SELECT COUNT(*) FROM invoice\n" +
            "JOIN users on users.id_user = invoice.user_id\n" +
            "JOIN second_address on second_address.id_second_address = invoice.second_address_id\n" +
            "WHERE users.id_user = ? AND second_address.second_city = ?";


    // Address First

    public static final String ADD_FIRST_ADDRESS = "INSERT INTO first_address (first_city , first_street_name, first_street_number, first_house_number) values (?, ?, ?, ?)";
    public static final String UPDATE_FIRST_ADDRESS = "UPDATE  first_address SET first_city = ?, first_street_name = ?, first_street_number = ?, first_house_number = ? WHERE id_first_address = ?";


    // Address Second

    public static final String ADD_SECOND_ADDRESS = "INSERT INTO second_address (second_city , second_street_name, second_street_number, second_house_number) values (?, ?, ?, ?)";
    public static final String UPDATE_SECOND_ADDRESS = "UPDATE second_address SET second_city = ?, second_street_name = ?, second_street_number = ?, second_house_number = ? WHERE id_second_address = ?";



}
