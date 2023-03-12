package com.varukha.webproject.exception;

/**
 * Class DAOException as wrapper for SQLException used to provide correctness
 * application work by throwing exceptions in case of errors on Data Accesses layer.
 *
 * @author Dmytro Varukha
 * @version 1.0
 */
public class DAOException extends Exception {

    public DAOException() {
        super();
    }
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    public DAOException(String message) {
        super(message);
    }

    public DAOException(Throwable cause) {
        super(cause);

    }
}
