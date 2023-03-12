package com.varukha.webproject.model.dao.impl;


import com.varukha.webproject.model.entity.AddressFirst;
import com.varukha.webproject.exception.DAOException;
import com.varukha.webproject.model.connection.ConnectionPool;
import com.varukha.webproject.model.dao.AddressFirstDAO;
import com.varukha.webproject.model.dao.SQL_Queries;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;


/**
 * Class AddressFirstDAOImpl implements methods for interacting with the MySQL Database.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class AddressFirstDAOImpl implements AddressFirstDAO {

    private static final Logger logger = LogManager.getLogger();
    private final ConnectionPool connectionPool;

    public AddressFirstDAOImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Method addFirstAddress used to adding first address to database.
     *
     * @param addressFirst contain first address data that will be added to database.
     * @return id of first address that was added.
     * @throws DAOException is wrapper for SQLException.
     */
    @Override
    public long addFirstAddress(AddressFirst addressFirst) throws DAOException {
        logger.log(Level.INFO, "Add first address to database: " + addressFirst);
        long firstAddressId = 0;
        int k = 0;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement st = connection.prepareStatement(SQL_Queries.ADD_FIRST_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(++k, addressFirst.getFirstCity());
            st.setString(++k, addressFirst.getFirstStreetName());
            st.setString(++k, addressFirst.getFirstStreetNumber());
            st.setString(++k, addressFirst.getFirstHouseNumber());
            int countRow = st.executeUpdate();
            ResultSet resultSet = st.getGeneratedKeys();
            if (resultSet.next()) {
                firstAddressId = resultSet.getLong(1);
                logger.log(Level.INFO, "First address: " + addressFirst + " was successfully added to database.");
            } else {
                logger.log(Level.ERROR, "First address:: " + addressFirst + " was not added to database");
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in addFirstAddress method " + e.getMessage() + " - " + e.getErrorCode());
            throw new DAOException("DAO exception in addFirstAddress method" + addressFirst, e);
        }
        return firstAddressId;
    }

    /**
     * Method updateFirstAddressData used to updating first address data.
     *
     * @param data contain a set of data to change first address.
     * @return boolean result of operation. Return true if update was successful and false if was not.
     * @throws DAOException is wrapper for SQLException.
     */
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
            st.setLong(++k, data.getFirstAddressId());
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
