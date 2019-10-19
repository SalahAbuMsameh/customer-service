package com.apisoft.customer.exception;

/**
 * Customer microservice default error list.
 *
 * @author Salah Abu Msameh
 */
public enum Errors {

    INTERNAL_SERVER_ERROR("ERR-001", "Internal server error"),
    REQUEST_FIELDS_VALIDATION_ERRORS("ERR-002", "Request field(s) validation error(s)"),
    GENERIC_ERROR("ERR-003", "Generic error"),
    NO_CUSTOMER_FOUND("ERR-004", "No customer found for customer id [{0}]");

    private String errorCode;
    private String errorMessage;

    /**
     * constructor.
     *
     * @param code error code
     * @param errorMessage error default message
     */
    Errors(final String code, final String errorMessage) {
        this.errorCode = code;
        this.errorMessage = errorMessage;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
