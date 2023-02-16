package com.varukha.webproject.model.dao;


import com.varukha.webproject.entity.Order;
import com.varukha.webproject.exception.DAOException;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The interface CargoDao
 * @author Dmytro Varukha
 *
 */
public interface OrderDAO {


	/**
	 * @param resultSet
	 * @return new Order
	 * @throws SQLException
	 */
	Order createOrder(ResultSet resultSet) throws SQLException;


	/**
	 * Add order to database
	 * @param order
	 * @return id order that was added
	 * @throws DAOException
	 */
	long addOrder(Order order) throws DAOException;

	/**
	 * Change order data in database
	 * @param data to update order information
	 * @return boolean result of operation
	 * @throws DAOException
	 */
	boolean updateOrderData(Order data) throws DAOException;


//	/**
//	 * Find all orders
//	 * @return List of orders
//	 * @throws DaoException
//	 */
//	List<Order> findAllOrders() throws DaoException;
//
//
//	/**
//	 * Find cargo by id
//	 * @param userId
//	 * @return Optional<Order>
//	 * @throws DaoException
//	 */
//	List<Order> findOrdersByUserId(long userId) throws DaoException;
//
//
//	/**
//	 * Find price of order by order id
//	 * @param orderId
//	 * @return Optional<Order>
//	 * @throws DaoException
//	 */
//	Optional<Order> findOrderPriceByOrderID(long orderId) throws DaoException;
//
//	/**
//	 * Find cargo by id
//	 * @param paymentStatus
//	 * @return boolean result of operation
//	 * @throws DaoException
//	 */
//	List<Order> findOrderByPaymentStatus(boolean paymentStatus) throws DaoException;
//
//
//	/**
//	 * Set order price by orderId
//	 * @param orderId
//	 * @param price
//	 * @return order price
//	 * @throws DaoException
//	 */
//	boolean setOrderPrice(long orderId, Order price) throws DaoException;

//	/**
//	 * Set order delivery status by id
//	 * @param deliveryId
//	 * @param status
//	 * @return delivery status of order
//	 * @throws DaoException
//	 */
//	boolean setOrderDeliveryStatus(long deliveryId, Order status) throws DaoException;


//	/**
//	 * @return int number of rows in database with orders
//	 * @throws DaoException
//	 */
//	int findNumberOfRows() throws DaoException;
//
//
//	/**
//	 * @param fromRow
//	 * @param numberOfOrdersOnPage
//	 * @return List of users from row
//	 * @throws DaoException
//	 */
//	List<Order> findOrdersFromRow(int fromRow, int numberOfOrdersOnPage) throws DaoException;


}
