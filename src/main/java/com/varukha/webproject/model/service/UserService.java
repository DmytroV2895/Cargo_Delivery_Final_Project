package com.varukha.webproject.model.service;

import com.varukha.webproject.entity.User;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * The interface user service
 * @author Dmytro Varukha
 *
 */

public interface UserService {

    /**
     * Find all users
     * @return  list of all users.
     */
    List<User> getAllUsers() throws ServiceException;


    /**
     * @param email
     * @param password
     * @return Optional<User> with these email and password
     * @throws ServiceException
     */
    Optional<User> getUserEmailPassword(String email, String password) throws ServiceException;


    /**
     * @param email
     * @return Optional<User>
     * @throws DAOException
     */
    Optional<User> getUserByEmail(String email) throws ServiceException;


    /**
     * Find User by userId
     * @param userId for find
     * @return user. If user does not exist return empty collection.
     * @throws ServiceException
     */
    List<User> getUserById(long userId) throws ServiceException;


    /**
     * @param userData
     * * @return boolean result of operation
     * @throws DAOException
     */
    boolean addUser(Map<String, String> userData) throws ServiceException;



    /**
     * @param userId
     * @param userAccount
     * @return boolean result of adding money to user account
     * @throws ServiceException
     */
    boolean addMoneyToUserAccount (long userId, String userAccount) throws ServiceException;




    /**
     * @param deliveryPrice;
     * @param userId;
     * @return boolean result of adding money to user account
     * @throws ServiceException
     */
    boolean payForDeliveryService(String deliveryPrice, long userId) throws ServiceException;

}
