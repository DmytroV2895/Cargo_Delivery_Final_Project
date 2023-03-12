package com.varukha.webproject.model.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class ConnectionPool obtain realization of custom connection pool with blocking queue and
 * queue for given away connections and ready to given away connections.
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ConnectionPool {

    private static final AtomicBoolean isCreated = new AtomicBoolean();
    public static final Logger logger = LogManager.getLogger();
    private final BlockingQueue<ProxyConnection> freeConnection;
    private final Queue<ProxyConnection> givenAwayConnections;
    private static final Lock locker = new ReentrantLock(true);
    private static ConnectionPool instance = new ConnectionPool();
    private static final int DEFAULT_POOL_SIZE = 8;


    /**
     * Method ConnectionPool used to create connection pool by specified
     * connection pool size.
     * In order to ensure secure access to connection is used proxy connection that
     * takes the real connection as an attribute.
     */
    private ConnectionPool() {
        freeConnection = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        givenAwayConnections = new ArrayDeque<>(DEFAULT_POOL_SIZE);
        logger.log(Level.INFO, "Creating connection pool");

        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                Connection connection = ConnectionCreator.getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnection.add(proxyConnection);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Could not create a connection to database: " + e.getMessage());
            }
        }
        if (freeConnection.size() == 0) {
            logger.log(Level.FATAL, "Connection pool was not created. Current pool size: " + freeConnection.size());
            throw new RuntimeException("connections pool don't created");
        }
        logger.log(Level.INFO, "Connection pool was created successfully");
    }

    /**
     * Method getInstance used to get an instance of connection that was created.
     * @return the instance of connection.
     */
    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            locker.lock();
            if (instance == null) {
                instance = new ConnectionPool();
                isCreated.set(true);
            }
            locker.unlock();
        }
        return instance;
    }

    /**
     * Method getConnection used to get connection from created connection pool.
     * Using method take() provides thread safe if two connections will arrive and
     * will not allow two streams to take the same connection. Also, if the connection
     * pool will be empty it will be waited when connection will put.
     * @return connection from connection pool.
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnection.take();
            givenAwayConnections.add((ProxyConnection) connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "InterruptedException in method getConnection " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Method releaseConnection used to release connection back to connection pool.
     */
    public void releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection && givenAwayConnections.remove(connection)) {
            try {
                freeConnection.put((ProxyConnection) connection);
            } catch (InterruptedException e) {
                logger.log(Level.ERROR, "InterruptedException in method getConnection " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method destroyPool used to destroy connection pool when web application is shutting down
     * and unregister JDBC driver when the web application was stopped.
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            closeAnyConnection();
        }
        deregisterDrivers();
    }

    /**
     * Method closeAnyConnection used to close any connections.
     */
    private void closeAnyConnection() {
        try {
            freeConnection.take().reallyClose();
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "InterruptedException in method destroyPool " + e.getMessage());
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQLException in method destroyPool " + e.getMessage());
        }
    }

    /**
     * Method deregisterDrivers used to unregister JDBC driver when the web application was stopped.
     */
    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "SQLException in method deregisterDrivers " + e.getMessage());
            }
        });
    }
}






