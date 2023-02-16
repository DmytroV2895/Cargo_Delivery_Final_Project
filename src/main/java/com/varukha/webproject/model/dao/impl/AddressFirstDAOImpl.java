package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.entity.AddressFirst;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.AddressFirstDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static com.varukha.webproject.model.dao.ColumnName.*;


public class AddressFirstDAOImpl implements AddressFirstDAO {

    private static final Logger logger = LogManager.getLogger();
    private final ConnectionPool connectionPool;
    public AddressFirstDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public long addFirstAddress(AddressFirst firstAddress) throws DAOException {
        logger.log(Level.INFO, "Add first address to database: " + firstAddress);
        long firstAddressId = 0 ;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_FIRST_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(++k, firstAddress.getFirstCity());
            st.setString(++k, firstAddress.getFirstStreetName());
            st.setString(++k, firstAddress.getFirstStreetNumber());
            st.setString(++k, firstAddress.getFirstHouseNumber());
            int countRow = st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                firstAddressId = resultSet.getLong(1);
                logger.log(Level.INFO, "First address: " + firstAddress + " was successfully added to database.");
            } else {
                logger.log(Level.ERROR, "First address:: " + firstAddress + " was not added to database");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in addFirstAddress method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in addFirstAddress method" + firstAddress, e);
        }
        return firstAddressId;
    }

    @Override
    public boolean updateFirstAddressData(AddressFirst data) throws DAOException {
        logger.log(Level.INFO, "Update first address data in database: " + data);
        boolean isChanged = false;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.UPDATE_FIRST_ADDRESS)) {
            st.setString(++k, data.getFirstCity());
            st.setString(++k, data.getFirstStreetName());
            st.setString(++k, data.getFirstStreetNumber());
            st.setString(++k, data.getFirstHouseNumber());
            int rowCount = st.executeUpdate();
            if (rowCount != 0) {
                isChanged = true;
                logger.log(Level.INFO, "First address: " + data + " was successfully updated");
            } else {
                logger.log(Level.ERROR, "First address:: " + data + " was not updated");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in updateFirstAddressData method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in updateFirstAddressData method" + data, e);
        }
        return isChanged;
    }
}
