package com.varukha.webproject.exception;

/**
 * DAO Exception as wrapper for SQLException
 * @author Dmytro Varukha
 *
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
