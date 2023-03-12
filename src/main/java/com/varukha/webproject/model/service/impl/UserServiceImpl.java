package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.model.entity.User.*;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.ColumnName;
import com.varukha.webproject.model.dao.UserDAO;
import com.varukha.webproject.model.service.UserService;
import com.varukha.webproject.util.validation.DataValidator;
import com.varukha.webproject.util.Encoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Class UserServiceImpl implements methods for
 * interacting between View layer and the MySQL Data Access layer
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Method getAllUsers is an intermediate service method for communication between
     * the user view layer and the database and used to get all users.
     *
     * @return list of all users from database.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public List<User> getAllUsers() throws ServiceException {
        logger.log(Level.DEBUG, "Getting all users");
        List<User> users;
        try {
            users = userDAO.findAllUsers();
            logger.log(Level.INFO, "All users: " + users);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in findAllUsers method " + e);
            throw new ServiceException(e);
        }
        return users;
    }

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
    @Override
    public Optional<User> getUserEmailPassword(String email, String password) throws ServiceException {
        logger.log(Level.DEBUG, "findUserByEmailPassword()");
        Optional<User> optionalUser;
        if (DataValidator.isEmailValid(email)) {
            String encodedPassword = Encoder.encodePassword(password);
            logger.log(Level.DEBUG, "Encoded password: " + encodedPassword);
            try {
                Optional<String> passwordFromDBOptional = userDAO.findUserEmailPassword(email);
                if (passwordFromDBOptional.isPresent()) {
                    String passwordFromDB = passwordFromDBOptional.get();
                    logger.log(Level.DEBUG, "passwordFromDB: " + passwordFromDB);
                    if (passwordFromDB.equals(encodedPassword)) {
                        logger.log(Level.INFO, "Passwords are equals. Authorization by user email " + email + " is successful");
                        optionalUser = userDAO.findUserByEmail(email);
                    } else {
                        optionalUser = Optional.empty();
                    }
                } else {
                    optionalUser = Optional.empty();
                }
            } catch (DAOException e) {
                logger.log(Level.ERROR, "DAO exception in getUserEmailPassword method " + e);
                throw new ServiceException(e);
            }
        } else {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }

    /**
     * Method getUserByEmail is an intermediate service method for communication between
     * the user view layer and the database and used to get user by email.
     *
     * @param email user email address in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public Optional<User> getUserByEmail(String email) throws ServiceException {
        logger.log(Level.DEBUG, "Get user by email: " + email);
        Optional<User> optionalUserByEmail;
        try {
            if (DataValidator.isEmailValid(email)) {
                optionalUserByEmail = userDAO.findUserByEmail(email);
            } else {
                optionalUserByEmail = Optional.empty();
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getUserByEmail method " + e);
            throw new ServiceException(e);
        }
        return optionalUserByEmail;
    }

    /**
     * Method getUserById is an intermediate service method for communication between
     * the user view layer and the database and used to get user by user id.
     *
     * @param userId user id in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public Optional<User> getUserById(long userId) throws ServiceException {
        logger.log(Level.DEBUG, "Get user by userId:" + userId);
        Optional<User> optionalUserById;
        try {
            optionalUserById = userDAO.findUserById(userId);
            logger.log(Level.INFO, "User by userId: " + optionalUserById);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getUserById method " + e);
            throw new ServiceException(e);
        }
        return optionalUserById;
    }

    /**
     * Method addUser used is an intermediate service method for communication between
     * the user view layer and the database and used to set necessary data in order to add new user to database.
     *
     * @param userData contain a set of data from user request that will be process in data access layer.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public boolean addUser(Map<String, String> userData) throws ServiceException {
        logger.log(Level.DEBUG, "Adding user. UserData:" + userData);
        boolean isUserAdded;
        if (DataValidator.isNameValid(userData.get(ParameterAndAttribute.USER_NAME))
                && DataValidator.isNameValid(userData.get(ParameterAndAttribute.USER_SURNAME))
                && DataValidator.isEmailValid(userData.get(ParameterAndAttribute.USER_EMAIL))
                && DataValidator.isPhoneNumberValid(userData.get(ParameterAndAttribute.USER_PHONE))
                && DataValidator.isPasswordValid(userData.get(ParameterAndAttribute.USER_PASSWORD))) {

            String encodedPassword = Encoder.encodePassword(userData.get(ColumnName.USER_PASSWORD));
            User user = new User.Builder()
                    .setName(userData.get(ParameterAndAttribute.USER_NAME))
                    .setSurname(userData.get(ParameterAndAttribute.USER_SURNAME))
                    .setEmail(userData.get(ParameterAndAttribute.USER_EMAIL))
                    .setPhone(userData.get(ParameterAndAttribute.USER_PHONE))
                    .setUserPassword(userData.get(ParameterAndAttribute.USER_PASSWORD))
                    .setPhone(userData.get(ParameterAndAttribute.USER_PHONE))
                    .setRole(Role.USER)
                    .setPaymentAccount(BigDecimal.valueOf(0))
                    .build();
            try {
                isUserAdded = userDAO.addUser(user, encodedPassword);
            } catch (DAOException e) {
                logger.log(Level.ERROR, "DAO exception in addUser method " + e);
                throw new ServiceException(e);
            }
        } else {
            isUserAdded = false;
        }
        return isUserAdded;
    }

    /**
     * Method addMoneyToUserAccount is an intermediate service method for communication between
     * the user view layer and the database and used to top up user account in order to pay delivery service.
     *
     * @param userId      user id in which the search is performed.
     * @param userAccount contain sum of money in order to top up user account.
     * @throws ServiceException is wrapper for DAOException that throws exception during the runtime because of
     *                          data validation fail or others mistakes.
     */
    @Override
    public boolean addMoneyToUserAccount(long userId, String userAccount) throws ServiceException {
        logger.log(Level.DEBUG, "Add money to user account: " + userAccount);
        boolean isAdded;
        try {
            isAdded = userDAO.topUpUserAccount(userId, userAccount);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in method addMoneyToUserAccount" + e);
            throw new ServiceException(e);
        }
        return isAdded;
    }

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
    @Override
    public boolean payForDeliveryService(String totalPrice, long userId) throws ServiceException {
        logger.log(Level.DEBUG, "Payment operation for delivering service. delivery price: " + totalPrice);
        boolean isPaid;
        try {
            isPaid = userDAO.payForDeliveryService(totalPrice, userId);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in payForDeliveryService method " + e);
            throw new ServiceException(e);
        }
        return isPaid;
    }
}



