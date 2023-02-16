package com.varukha.webproject.model.service.impl;

import com.varukha.webproject.command.ParameterAndAttribute;
import com.varukha.webproject.entity.User;
import com.varukha.webproject.entity.User.*;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.exception.ServiceException;
import com.varukha.webproject.model.dao.ColumnName;
import com.varukha.webproject.model.dao.UserDAO;
import com.varukha.webproject.model.service.UserService;
import com.varukha.webproject.model.service.validation.DataValidator;
//import com.varukha.webproject.util.MailSender;
import com.varukha.webproject.util.Encoder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();
    private final UserDAO userDAO;


    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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
                logger.log(Level.ERROR, "DAO exception in FindUsersByLoginPassword method " + e);
                throw new ServiceException(e);
            }
        } else {
            optionalUser = Optional.empty();
        }
        return optionalUser;
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws ServiceException {
        logger.log(Level.DEBUG, "Get user by email: " + email);
        Optional<User> user;
        try {
            if (DataValidator.isEmailValid(email)) {
                user = userDAO.findUserByEmail(email);
            } else {
                user = Optional.empty();
            }
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in findUserByEmail method " + e);
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public List<User> getUserById(long userId) throws ServiceException {
        logger.log(Level.DEBUG, "Get user by userId:" + userId);
        List<User> user;
        try {
            user = userDAO.findUserById(userId);
            logger.log(Level.INFO, "User by userId: " + user);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in getUserById method " + e);
            throw new ServiceException(e);
        }
        return user;
    }


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

//    @Override
//    public boolean changePersonalInfo(User user, Map<String, String> userData) throws ServiceException {
//        logger.log(Level.DEBUG, "Changing personal information of: " + user);
//        boolean isChanged = false;
//        if (DataValidator.isNameValid(userData.get(ParameterAndAttribute.USER_NAME))
//                && DataValidator.isNameValid(userData.get(ParameterAndAttribute.USER_SURNAME))) {
//            user.setName(userData.get(ParameterAndAttribute.USER_NAME));
//            user.setSurname(userData.get(ParameterAndAttribute.USER_SURNAME));
//            user.setPhone(userData.get(ParameterAndAttribute.USER_PHONE));
//            try {
//                isChanged = userDAO.changePersonalInfo(user);
//            } catch (DaoException e) {
//                logger.log(Level.ERROR, "DAO exception in changePersonalInfo method  " + e);
//                throw new ServiceException(e);
//            }
//        }
//        return isChanged;
//    }


    @Override
    public boolean addMoneyToUserAccount(long userId, String userAccount) throws ServiceException {
        logger.log(Level.DEBUG, "Add money to user account: " + userAccount);
        boolean isAdded;
        try {
            isAdded = userDAO.addMoneyToUserAccount(userId, userAccount);
        } catch (DAOException e) {
            logger.log(Level.ERROR, "DAO exception in method FindUsersByRole" + e);
            throw new ServiceException(e);
        }
        return isAdded;
    }

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



