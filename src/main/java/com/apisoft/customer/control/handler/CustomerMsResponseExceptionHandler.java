package com.apisoft.customer.control.handler;

import com.apisoft.customer.exception.BadRequestException;
import com.apisoft.customer.exception.CustomerMsException;
import com.apisoft.customer.exception.Errors;
import com.apisoft.customer.web.api.ApiResponse;
import com.apisoft.customer.web.api.ApiResponseBuilder;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Customer service exception response handler.<br/>
 * This handler is responsible for handling all kind of {@link CustomerMsException} type and convert it to rest (json) response.
 * 
 * @author Salah Abu Msameh
 */
@ControllerAdvice
public class CustomerMsResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		
		StringBuilder sb = new StringBuilder();
		
	    sb.append(ex.getMethod());
	    sb.append(" method is not supported for this request. Supported methods is/are ");
	    
	    Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
	    if(supportedMethods != null) {
			supportedMethods.forEach(t -> sb.append(t + " "));
		}
	    
		return ApiResponseBuilder.genericError(status, sb.toString());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(final HttpMediaTypeNotAcceptableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(final MissingPathVariableException ex,
				final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(final ServletRequestBindingException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(final ConversionNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(final HttpMessageNotWritableException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

		return ApiResponseBuilder.badRequest(ex.getBindingResult().getAllErrors()
				.stream()
				.map(entry -> entry.getDefaultMessage())
				.collect(Collectors.toList()));
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
				final HttpStatus status, final WebRequest request) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		
		return ApiResponseBuilder.genericError(status, new StringBuilder("No handler found for ")
				.append(ex.getHttpMethod())
				.append(" ")
				.append(ex.getRequestURL())
				.toString());
	}
	
	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(final AsyncRequestTimeoutException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest webRequest) {
		return ApiResponseBuilder.genericError(status, ex.getMessage());
	}

	/**
	 * handle wsb bad request exception type.
	 * 
	 * @param ex bad request exception
	 * @return
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponse<Void>> handleBadRequestException(final BadRequestException ex) {
		return ApiResponseBuilder.badRequest(ex.getError(), ex.getErrorDetails(), ex.getArguments());
	}
	
	/**
	 * handle ws exception type.
	 * 
	 * @param ex custom exception
	 * @return
	 */
	@ExceptionHandler(CustomerMsException.class)
	public ResponseEntity<ApiResponse<Void>> handleCustomerMsException(final CustomerMsException ex) {
		return ApiResponseBuilder.internalServerError();
	}
	
	/**
	 * Default handler.
	 * 
	 * @param ex general exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handle(final Exception ex) {
		return ApiResponseBuilder.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, Errors.INTERNAL_SERVER_ERROR);
	}
}
