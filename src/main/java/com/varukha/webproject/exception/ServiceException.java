package com.varukha.webproject.exception;

/**
 * Class ServiceException is wrapper for DAOException used to provide correctness
 * application work by throwing exceptions in case of
 * data validation fail or others mistakes in service methods.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class ServiceException extends Exception {

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
