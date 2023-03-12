package com.varukha.webproject.model.dao;

import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.exception.DAOException;

import java.util.List;
import java.util.Optional;


/**
 * Interface UserDAO contain contracts of methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public interface UserDAO {

    /**
     * Method findAllUsers used to find all users from users database table.
     *
     * @return list of all users from database.
     * @throws DAOException is wrapper for SQLException.
     */
    List<User> findAllUsers() throws DAOException;

    /**
     * Method findUserEmailPassword used to find user password by email.
     *
     * @param email user email address in which the search is performed.
     * @return optional result of operation. Return user password if available and return empty if not.
     * @throws DAOException is wrapper for SQLException
     */
    Optional<String> findUserEmailPassword(String email) throws DAOException;

    /**
     * Method findUserByEmail used to find user by email.
     *
     * @param email user email address in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws DAOException is wrapper for SQLException
     */
    Optional<User> findUserByEmail(String email) throws DAOException;

    /**
     * Method findUserById used to find user by user id.
     *
     * @param userId user id in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws DAOException is wrapper for SQLException
     */
    Optional<User> findUserById(long userId) throws DAOException;

    /**
     * Method addUser used to adding new user to database.
     *
     * @param user            contain user data that will be added to database.
     * @param encodedPassword contain encoded password data that will be added to database in order to data security.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean addUser(User user, String encodedPassword) throws DAOException;

    /**
     * Method topUpUserAccount used to top up user account in order to pay delivery service.
     *
     * @param userId      user id in which the search is performed.
     * @param userAccount contain sum of money in order to top up user account.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean topUpUserAccount(long userId, String userAccount) throws DAOException;

    /**
     * Method payForDeliveryService used to pay delivery service.
     *
     * @param userId     user id in which the search is performed.
     * @param totalPrice contain payment sum for delivery service.
     * @return boolean result of operation. Return true if payment was successfully and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    boolean payForDeliveryService(String totalPrice, long userId) throws DAOException;

}







