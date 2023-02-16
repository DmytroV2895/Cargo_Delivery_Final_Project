package com.varukha.webproject.exception;

/**
 * @author Dmytro Varukha
 *
 */

public class IncorrectInputException extends Exception {


    public IncorrectInputException() {
        super();
    }

    public IncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputException(String message) {
        super(message);
    }

    public IncorrectInputException(Throwable cause) {
        super(cause);
    }
}