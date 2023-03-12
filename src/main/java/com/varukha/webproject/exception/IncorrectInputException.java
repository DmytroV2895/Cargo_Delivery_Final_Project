package com.varukha.webproject.exception;

/**
 * Class IncorrectInputException used to provide correctness
 * application work by throwing exceptions in case of incorrect calculation data.
 *
 * @author Dmytro Varukha
 * @version 1.0
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