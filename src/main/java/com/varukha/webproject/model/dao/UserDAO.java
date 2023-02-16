package com.varukha.webproject.model.dao;

import com.varukha.webproject.entity.User;
import com.varukha.webproject.entity.User.*;
import com.varukha.webproject.exception.DAOException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The interface UserDao
 * @author Dmytro Varukha
 *
 */

public interface UserDAO {

    /**
     * Find all users
     * @return  list of all users.
     * @throws DAOException is wrapper for SQLException
     */
    List<User> findAllUsers() throws DAOException;


    /**
     * @param email user email address
     * @return Optional<User>
     * @throws DAOException is wrapper for SQLException
     */
    Optional<String> findUserEmailPassword(String email) throws DAOException;


    /**
     * @param email user email address
     * @return Optional<User>
     * @throws DAOException is wrapper for SQLException
     */
    Optional<User> findUserByEmail(String email) throws DAOException;


    /**
     * Find User by id
     * @param userId in order to find user
     * @return user from current session
     * @throws DAOException is wrapper for SQLException
     */
    List<User> findUserById(long userId)  throws DAOException;


    /**
     * @param user;

     * @return boolean result of operation
     * @throws DAOException is wrapper for SQLException
     */
    boolean addUser(User user, String encodedPassword) throws DAOException;


//    /**
//     * @param user;
//     * @return boolean result of operation
//     * @throws DaoException
//     */
//    boolean changePersonalInfo(User user) throws DaoException;



    /**
     * @param userId;
     * @param userAccount;
     * @return boolean result of adding money to user account
     * @throws DAOException is wrapper for SQLException
     */
    boolean addMoneyToUserAccount (long userId, String userAccount) throws DAOException;



    /**
     * @param totalPrice total price for delivery service;
     * @param userId payer Id (default registered user)
     * @return boolean result of adding money to user account
     * @throws DAOException is wrapper for SQLException
     */
    boolean payForDeliveryService(String totalPrice, long userId) throws DAOException;

}







