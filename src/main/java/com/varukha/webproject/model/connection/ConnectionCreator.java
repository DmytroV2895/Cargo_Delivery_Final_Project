package com.varukha.webproject.model.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class ConnectionCreator that create a connection to database by using properties file
 * @author Dmytro Varukha
 *
 */

public class ConnectionCreator {

    private static final Logger logger = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String DATABASE_PROPERTIES = "database.properties";
    private static final String PROPERTY_URL = "db.url";
    private static final String PROPERTY_USER = "db.user";
    private static final String PROPERTY_PASSWORD = "db.password";
    private static final String PROPERTY_DRIVER = "db.driver";
    private static final String DATABASE_URL;
    private static final String DATABASE_PASSWORD;
    private static final String DATABASE_USER;
    private static final String DATABASE_DRIVER;

    private ConnectionCreator() {
    }



    static {

        try (InputStream inputStream = ConnectionCreator.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES)) {
            properties.load(inputStream);
            DATABASE_URL = properties.getProperty(PROPERTY_URL);
            DATABASE_USER = properties.getProperty(PROPERTY_USER);
            DATABASE_PASSWORD = properties.getProperty(PROPERTY_PASSWORD);
            DATABASE_DRIVER = properties.getProperty(PROPERTY_DRIVER);
            Class.forName(DATABASE_DRIVER);

        } catch (FileNotFoundException e) {
            logger.log(Level.FATAL, "Throwing FileNotFoundException " + e.getMessage());
            throw new RuntimeException();
        } catch (IOException e) {
            logger.log(Level.FATAL, "Throwing IOException " + e.getMessage());
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "Driver: " + properties.getProperty(PROPERTY_DRIVER) + " was not found");
            throw new RuntimeException();
        }
    }


    /**
     * @return new connection to database
     * @throws SQLException
     */

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}



