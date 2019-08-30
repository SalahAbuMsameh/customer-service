package com.apisoft.customer.exception;

/**
 * This exception represents a user bad request.<br/>
 * Throwing this exception will cause a 400 response to the client.
 *
 * @author Salah Abu Msameh
 */
public class BadRequestException extends CustomerMsException {

    protected Object[] arguments;
    private String errorDetails;

    /**
     * constructor.
     * @param error error enum param (code & default message)
     */
    public BadRequestException(final Errors error) {
        this(error, null, null);
    }

    /**
     * constructor.
     *
     * @param error error enum param (code & default message)
     * @param arguments values to be replaced on the default message placeholder if any
     */
    public BadRequestException(final Errors error, final Object... arguments) {
        this(error, null, arguments);
    }

    /**
     * constructor.
     *
     * @param error error enum param (code & default message)
     * @param errorDetails detailed error message
     * @param arguments alues to be replaced on the default message placeholder if any
     */
    public BadRequestException(final Errors error, final String errorDetails, final Object... arguments) {
        super(null);
        this.error = error;
        this.errorDetails = errorDetails;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}
