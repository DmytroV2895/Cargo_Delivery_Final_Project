package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.entity.User;
import com.varukha.webproject.entity.User.Role;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.SQL_Queries;
import com.varukha.webproject.model.dao.UserDAO;
import com.varukha.webproject.util.Encoder;
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
 * Class user DAO
 *
 * @author Dmytro Varukha
 */

public class UserDAOImpl implements UserDAO {

    private final ConnectionPool connectionPool;
    private static final Logger logger = LogManager.getLogger();

    public UserDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


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

    @Override
    public List<User> findUserById(long userId) throws DAOException {
        logger.log(Level.INFO, "Find user by Id: " + userId);
        List<User> userById = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_Queries.FIND_USERS_BY_ID)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = createUser(resultSet);
                userById.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in findUserById method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in method findUserById ", e);
        }
        return userById;
    }

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

//    @Override
//    public boolean changePersonalInfo(User user) throws DaoException {
//        logger.log(Level.INFO, "Changing user personal info. User id:" + user.getUserId());
//        boolean isChanged = false;
//        try (Connection connection = connectionPool.getConnection();
//             PreparedStatement statement = connection.prepareStatement(SQL_Queries.CHANGE_USER_PERSONAL_INFO)) {
//            statement.setLong(1, user.getUserId());
//            statement.setString(2, user.getName());
//            statement.setString(3, user.getSurname());
//            statement.setString(4, user.getEmail());
//            statement.setString(5, user.getPhone());
//            statement.setString(6, user.getPassword());
//
//            int rowCount = statement.executeUpdate();
//            if (rowCount != 0) {
//                isChanged = true;
//                logger.log(Level.INFO, "User personal information was changed successfully. User id:" + user.getUserId());
//            } else {
//                logger.log(Level.INFO, "User personal information wasn't changed. User id:" + user.getUserId());
//            }
//        } catch (SQLException e) {
//            logger.log(Level.ERROR, "SQLException in changePersonalInfo method " + e.getMessage() + " - " + e.getErrorCode());
//            throw new DaoException("Dao exception in method changePersonalInfo", e);
//        }
//        return isChanged;
//    }


    /**
     * method createUser
     *
     * @param resultSet
     * @return new user
     * @throws DAOException
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


    @Override
    public boolean addMoneyToUserAccount(long userId, String userAccount) throws DAOException {
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
            logger.log(Level.ERROR, "SQLException in paymentForDeliveryService method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("Dao exception in method paymentForDeliveryService", e);
        }
        return isPaid;
    }


}





