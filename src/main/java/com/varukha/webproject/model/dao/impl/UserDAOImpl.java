package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.model.entity.User;
import com.varukha.webproject.model.entity.User.Role;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.SQL_Queries;
import com.varukha.webproject.model.dao.UserDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.varukha.webproject.model.dao.ColumnName.*;

/**
 * Class UserDAOImpl implements methods for interacting with the MySQL Database.
 * @author Dmytro Varukha
 * @version 1.0
 */
public class UserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool;
    private static final Logger logger = LogManager.getLogger();
    public UserDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Method findAllUsers used to find all users from users database table.
     * @return list of all users from database.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public List<User> findAllUsers() throws DAOException {
        logger.log(Level.INFO, "Finding all users");
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Queries.FIND_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(createUser(resultSet));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findAll method: " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in  findAllUsers method", e);
        }
        return users;
    }

    /**
     * Method findUserEmailPassword used to find user password by email.
     * @param email user email address in which the search is performed.
     * @return optional result of operation. Return user password if available and return empty if not.
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<String> findUserEmailPassword(String email) throws DAOException {
        logger.log(Level.INFO, "Finding user password by email: " + email);
        Optional<String> optionalPassword;
        String password;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Queries.FIND_PASSWORD_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(USER_PASSWORD);
                optionalPassword = Optional.of(password);
            } else {
                optionalPassword = Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findUserPasswordByEmail method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in findUserPasswordByEmail method ", e);
        }
        return optionalPassword;
    }

    /**
     * Method findUserByEmail used to find user by email.
     * @param email user email address in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<User> findUserByEmail(String email) throws DAOException {
        logger.log(Level.INFO, "Finding users by email: " + email);
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Queries.FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = createUser(resultSet);
                optionalUser = Optional.of(user);
            } else {
                logger.log(Level.INFO, "Didn't find any user with email: " + email);
                optionalUser = Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findUserByEmail method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in method findUserByEmail ", e);
        }
        return optionalUser;
    }

    /**
     * Method findUserById used to find user by user id.
     * @param userId user id in which the search is performed.
     * @return optional result of operation. Return user if available and return empty if not.
     * @throws DAOException is wrapper for SQLException
     */
    @Override
    public Optional<User> findUserById(long userId) throws DAOException {
        logger.log(Level.INFO, "Find user by Id: " + userId);
        int k = 0;
        Optional<User> optionalUser;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Queries.FIND_USERS_BY_ID)) {
            statement.setLong(++k, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = createUser(resultSet);
                optionalUser = Optional.of(user);
            } else {
                logger.log(Level.INFO, "Didn't find any user with userId: " + userId);
                optionalUser = Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findUserById method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in method findUserById ", e);
        }
        return optionalUser;
    }

    /**
     * Method addUser used to adding new user to database.
     * @param user contain user data that will be added to database.
     * @param encodedPassword contain encoded password data that will be added to database in order to data security.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean addUser(User user, String encodedPassword) throws DAOException {
        logger.log(Level.INFO, "Adding user to database: " + user);
        boolean userAdded = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_USER)) {
            st.setString(++k, user.getName());
            st.setString(++k, user.getSurname());
            st.setString(++k, user.getEmail());
            st.setString(++k, user.getPhone());
            st.setString(++k, encodedPassword);
            st.setString(++k, String.valueOf(user.getRole()));
            st.setBigDecimal(++k, user.getPaymentAccount());
            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                userAdded = true;
                logger.log(Level.INFO, "User was added successfully to database " + user);
            } else {
                logger.log(Level.ERROR, "User was not added to database ");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in addUser method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in method addUser, when we try to add user:" + user, e);
        }
        return userAdded;
    }

    /**
     * Method topUpUserAccount used to top up user account in order to pay delivery service.
     * @param userId user id in which the search is performed.
     * @param userAccount contain sum of money in order to top up user account.
     * @return boolean result of operation. Return true if new user was added and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean topUpUserAccount(long userId, String userAccount) throws DAOException {
        logger.log(Level.INFO, "Add money to user account. User id: " + ", account balance:");
        boolean isAdded = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_MONEY_TO_USER_ACCOUNT)) {
            st.setString(++k, userAccount);
            st.setLong(++k, userId);
            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isAdded = true;
                logger.log(Level.INFO, "User's account balance  was changed successfully. User id: " + userId + ", account balance: "+ userAccount);
            } else {
                logger.log(Level.INFO, "User's account balance wasn't changed. User id: " + ", account balance:");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in addMoneyToUserAccount method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("Dao exception in method addMoneyToUserAccount", e);
        }
        return isAdded;
    }

    /**
     * Method payForDeliveryService used to pay delivery service.
     * @param userId user id in which the search is performed.
     * @param totalPrice contain payment sum for delivery service.
     * @return boolean result of operation. Return true if payment was successfully and false if not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean payForDeliveryService(String totalPrice, long userId) throws DAOException {
        logger.log(Level.INFO, "Payment operation for delivery service. Delivery price:" + totalPrice);
        boolean isPaid = false;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.PAYMENT_FOR_DELIVERY_SERVICE)) {
            st.setString(1, totalPrice);
            st.setLong(2, userId);

            if (st.executeUpdate() != 0) {
                isPaid = true;
                logger.log(Level.INFO, "Payment operation was successful");
            } else {
                logger.log(Level.INFO, "Payment operation was not successful");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in paymentForDeliveryService method "
                    + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("Dao exception in method paymentForDeliveryService", e);
        }
        return isPaid;
    }

    /**
     * Method createUser used to create set of user data with all information about user.
     * @param resultSet set of data about user from database.
     * @return user information from database.
     * @throws SQLException is an exception that provides information on a database access error or other errors.
     */
    public User createUser(ResultSet resultSet) throws SQLException {
        logger.log(Level.INFO, "Creating new user");
        int userId = resultSet.getInt(ID_USER);
        String name = resultSet.getString(USER_NAME);
        String surname = resultSet.getString(USER_SURNAME);
        String userEmail = resultSet.getString(USER_EMAIL);
        String phone = resultSet.getString(USER_PHONE);
        Role role = Role.valueOf(resultSet.getString(USER_ROLE).toUpperCase());
        BigDecimal userAccount = resultSet.getBigDecimal(USER_PAYMENT_ACCOUNT);
        User user = new User.Builder()
                .setUserId(userId)
                .setName(name)
                .setSurname(surname)
                .setEmail(userEmail)
                .setPhone(phone)
                .setRole(role)
                .setPaymentAccount(userAccount)
                .build();
        logger.log(Level.INFO, "New user was created. UserId: " + userId + " name: " + name + " surname " + surname);
        return user;
    }
}





