package com.apisoft.customer.web.api;

/**
 * Customer api response status.
 *
 * @author Salah Abu Msameh
 */
public enum ResponseStatus {

    SUCCESS("success"),
    FAILED("fail");

    private String status;

    /**
     * constructor.
     * @param status status value
     */
    ResponseStatus(final String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }
}
