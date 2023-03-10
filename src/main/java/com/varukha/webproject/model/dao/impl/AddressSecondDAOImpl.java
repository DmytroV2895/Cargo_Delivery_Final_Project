package com.varukha.webproject.model.dao.impl;

import com.varukha.webproject.model.entity.AddressSecond;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.AddressSecondDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


/**
 * Class AddressSecondDAOImpl implements methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class AddressSecondDAOImpl implements AddressSecondDAO {
    private static final Logger logger = LogManager.getLogger();
    private final ConnectionPool connectionPool;

    public AddressSecondDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Method addSecondAddress used to adding second address to database.
     *
     * @param addressSecond contain second address data that will be added to database.
     * @return id of second address that was added.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public long addSecondAddress(AddressSecond addressSecond) throws DAOException {
        logger.log(Level.INFO, "Trying to add first address to database: " + addressSecond);
        long addressSecondId = 0;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_SECOND_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(++k, String.valueOf(addressSecond.getSecondCity()));
            st.setString(++k, addressSecond.getSecondStreetName());
            st.setString(++k, addressSecond.getSecondStreetNumber());
            st.setString(++k, addressSecond.getSecondHouseNumber());
            int countRow = st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                addressSecondId = resultSet.getLong(1);
                logger.log(Level.INFO, "Second address: " + addressSecond + " was successfully added to database");
            } else {
                logger.log(Level.ERROR, "Second address: " + addressSecond + " was not added to database");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in addSecondAddress method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in addSecondAddress method" + addressSecond, e);
        }
        return addressSecondId;
    }

    /**
     * Method updateSecondAddressData used to updating second address data.
     *
     * @param data contain a set of data to change second address.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public boolean updateSecondAddressData(AddressSecond data) throws DAOException {
        logger.log(Level.INFO, "Update second address in database: " + data);
        boolean isChanged = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.UPDATE_SECOND_ADDRESS)) {
            st.setString(++k, String.valueOf(data.getSecondCity()));
            st.setString(++k, data.getSecondStreetName());
            st.setString(++k, data.getSecondStreetNumber());
            st.setString(++k, data.getSecondHouseNumber());
            st.setLong(++k, data.getSecondAddressId());
            if (st.executeUpdate() != 0) {
                isChanged = true;
                logger.log(Level.INFO, "Second address: " + data + " was successfully updated");
            } else {
                logger.log(Level.ERROR, "Second address: " + data + " was not updated");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in updateSecondAddress method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in updateSecondAddress method" + data, e);
        }
        return isChanged;
    }
}
