package com.apisoft.customer.web.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.apisoft.customer.exception.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * Rest api response builder.
 * 
 * @author Salah Abu Msameh
 */
public class ApiResponseBuilder {

    /**
     * build an ok <code>200</code> response.
     *
     * @return
     */
    public static <R> ResponseEntity<ApiResponse<R>> ok() {
        return ResponseEntity.ok(successResponse());
    }
    
    /**
     * build an ok (200) response.
     *
     * @param responseBody actual response body which contains the requested data from the current api call
     * @return
     */
    public static <R> ResponseEntity<ApiResponse<R>> ok(final R responseBody) {
        return ResponseEntity.ok(successResponse(responseBody));
    }
    
    /**
     * build an created (201) response.
     
     * @param entityPath created entity resource path
     * @return created model
     * @throws URISyntaxException if failed to create a location URI
     */
    public static <R> ResponseEntity<ApiResponse<R>> created(final String entityPath) throws URISyntaxException {
        return ResponseEntity
                .created(new URI(entityPath))
                .build();
    }
    
    /**
     * build an created (201) response with the given response body.
     *
     * @param responseBody actual response body which contains the requested data from the current api call
     * @return created model
     * @throws URISyntaxException if failed to create a location URI
     */
    public static <R> ResponseEntity<ApiResponse<R>> created(final R responseBody) throws URISyntaxException {
        return ResponseEntity
                .created(new URI(""))
                .body(successResponse(responseBody));
    }
    
    /**
     * build an created (201) response with the given response body.
     *
     * @param location created entity resource path
	 * @param responseBody actual response body which contains the requested data from the current api call
     * @return created model
     * @throws URISyntaxException if failed to create a location URI
     */
    public static <R> ResponseEntity<ApiResponse<R>> created(final URI location, final R responseBody)
            throws URISyntaxException {
        
        if(location != null) {
            return ResponseEntity
                    .created(location)
                    .body(successResponse(responseBody));
        }
    
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(successResponse(responseBody));
    }
    
    /**
     * build an bad request (400) response.
     *
     * @return
     */
    public static <R> ResponseEntity<ApiResponse<R>> internalServerError() {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Errors.INTERNAL_SERVER_ERROR);
    }

    /**
     * build an internal server error with (400) response code.
     *
     * @param error common api error
     * @param arguments list of values to be replaced in the error(s) placeholders if any
     * @return
     */
    public static <R> ResponseEntity<ApiResponse<R>> badRequest(final Errors error, final String errorDetails,
                                                                final Object[] arguments) {
        return errorResponse(HttpStatus.BAD_REQUEST, error, errorDetails, arguments);
    }

    /**
     * build an internal server error with (400) response code.
     *
     * @param validationErrors list of the request fields validation error messages
     * @return
     */
    public static ResponseEntity<Object> badRequest(final List<String> validationErrors) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse(Errors.REQUEST_FIELDS_VALIDATION_ERRORS.errorCode,
                        Errors.REQUEST_FIELDS_VALIDATION_ERRORS.errorMessage,
                        null, null, validationErrors));
    }
    
    /**
     * Creates a generic error response.
     *
     * @param status response http status number
     * @param message error details
     * @return
     */
    public static ResponseEntity<Object> genericError(final HttpStatus status, final String message) {
        return ResponseEntity
                .status(status)
                .body(errorResponse(Errors.GENERIC_ERROR.errorCode,
                                    Errors.GENERIC_ERROR.errorMessage,
                                    message, null, null));
    }

    /**
     * Creates a success response with empty response body.
     *
     * @return
     */
    private static <R> ApiResponse<R> successResponse() {
        return successResponse(null);
    }
    
    /**
     * Creates a success response with a response body.
     *
     * @param responseBody actual response body which contains the requested data from the current api call
     * @return
     */
    private static <R> ApiResponse<R> successResponse(final R responseBody) {
        return response(ResponseStatus.SUCCESS.status, responseBody, null, null, null,
                null, null);
    }
    
    /**
     * create an error response with the given values.
     *
     * @param status response status (success or fail)
     * @param error common error
     * @return
     */
    public static <R> ResponseEntity<ApiResponse<R>> errorResponse(final HttpStatus status, final Errors error) {
        return ResponseEntity
                .status(status)
                .body(errorResponse(error, null));
    }
    
    /**
     * create an error response with the given values.
     *
     * @param status response status (success or fail)
     * @param error common error
     * @param arguments list of values to be replaced in the error(s) placeholders if any
     * @return
     */
    public static <R> ResponseEntity<ApiResponse<R>> errorResponse(final HttpStatus status,
                                                                   final Errors error,
                                                                   final String errorDetails,
                                                                   final Object[] arguments) {
        return ResponseEntity
                .status(status)
                .body(errorResponse(error.errorCode, error.errorMessage, errorDetails, arguments, null));
    }
    
    /**
     * create a response with a failed status.
     *
     * @param error common error
     * @param arguments list of values to be replaced in the error(s) placeholders if any
     * @return
     */
    private static <R> ApiResponse<R> errorResponse(final Errors error, final Object[] arguments) {
        return errorResponse(error.errorCode, error.errorMessage, null, arguments, null);
    }

    /**
     * create a response with a failed status.
     *
	 * @param errorCode common error code
	 * @param errorMsg common error message
	 * @param arguments list of values to be replaced in the error(s) placeholders if any
	 * @param errorDetails detailed error message
	 * @param validationErrors list of request fields validation errors
     * @return
     */
    private static <R> ApiResponse<R> errorResponse(final String errorCode,
                                                    final String errorMsg,
                                                    final String errorDetails,
                                                    final Object[] arguments,
                                                    final List<String> validationErrors) {
        return response(ResponseStatus.FAILED.status,null, errorCode, errorMsg, arguments, errorDetails,
                validationErrors);
    }

    /**
     * Create and return api response content.
     *
     * @param status response status (success or fail)
     * @param responseBody actual response body which contains the requested data from the current api call
     * @param errorCode common error code
     * @param errorMsg common error message
     * @param arguments list of values to be replaced in the error(s) placeholders if any
     * @param errorDetails detailed error message
	 * @param validationErrors list of request fields validation errors
     * @return
     */
    private static <R> ApiResponse<R> response(final String status,
                                               final R responseBody,
                                               final String errorCode,
                                               final String errorMsg,
                                               final Object[] arguments,
                                               final String errorDetails,
                                               final List<String> validationErrors) {

        ApiResponse<R> response = new ApiResponse<R>();

        response.setResponseStatus(status);
        response.setDate(LocalDateTime.now());
        response.setRequestNo(UUID.randomUUID().toString());
        response.setResponseBody(responseBody);
        response.setErrorCode(errorCode);
        response.setErrorMessage(arguments != null ? MessageFormat.format(errorMsg, arguments) : null);
        response.setErrorDetails(errorDetails);
        response.setFieldValidationErrors(validationErrors);

        return response;
    }
}
