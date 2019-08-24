package com.apisoft.customer.exception;

/**
 * Customer microservice custom exception.
 *
 * @author Salah Abu Msameh
 */
public class CustomerMsException extends Exception {

    protected Errors error;

    /**
     * Default exception.<br/>
     * This constructor will produce an internal server error response with 500 status code.
     *
     * @param t throwable caused the exception
     */
    public CustomerMsException(Throwable t) {
       super(t);
       this.error = Errors.INTERNAL_SERVER_ERROR;
    }

    public Errors getError() {
        return error;
    }
}
