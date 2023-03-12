package com.varukha.webproject.model.service;

import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Interface UserService contain contracts of service methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface UserService {

    /**
     * Method getAllUsers is an intermediate service method for communication between
     * the user view layer and the database and used to get all users.
     *
     * @return list of all users from database.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    List<User> getAllUsers() throws ServiceException;

    /**
     * Method getUserEmailPassword is an intermediate service method for communication between
     * the user view layer and the database and used to get user password by email.
     *
     * @param email    user email address in which the search is performed.
     * @param password user password in which the search is performed.
     * @return optional result of operation. Return user password if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    Optional<User> getUserEmailPassword(String email, String password) throws ServiceException;

    /**
     * Method getUserByEmail is an intermediate service method for communication between
     * the user view layer and the database and used to get user by email.
     *
     * @param email user email address in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    Optional<User> getUserByEmail(String email) throws ServiceException;

    /**
     * Method getUserById is an intermediate service method for communication between
     * the user view layer and the database and used to get user by user id.
     *
     * @param userId user id in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    Optional<User> getUserById(long userId) throws ServiceException;

    /**
     * Method addUser used is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new user to database.
     *
     * @param userData contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    boolean addUser(Map<String, String> userData) throws ServiceException;

    /**
     * Method addMoneyToUserAccount is an intermediate service method for communication between
     * the user view layer and the database and used to top up user account in order to pay delivery service.
     *
     * @param userId      user id in which the search is performed.
     * @param userAccount contain sum of money in order to top up user account.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    boolean addMoneyToUserAccount(long userId, String userAccount) throws ServiceException;

    /**
     * Method payForDeliveryService is an intermediate service method for communication between
     * the user view layer and the database and used to pay delivery service.
     *
     * @param userId     user id in which the search is performed.
     * @param totalPrice contain payment sum for delivery service.
     * @return boolean result of operation. Return true if payment was successfully and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    boolean payForDeliveryService(String totalPrice, long userId) throws ServiceException;

}
