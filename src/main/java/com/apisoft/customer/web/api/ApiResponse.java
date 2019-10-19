package com.apisoft.customer.web.api;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * This response is the response wrapper class for all kind of web services.
 * 
 * @author Salah Abu Msameh
 */
@JsonRootName("response")
@JsonInclude(Include.NON_NULL)
public class ApiResponse<R> {

	private String responseStatus;
	private LocalDateTime date;
	private String requestNo;
	private String errorCode;
	private String errorMessage;
	private String errorDetails;
	private List<String> fieldValidationErrors;
	private R responseBody;
	
	public String getResponseStatus() {
		return responseStatus;
	}
	
	@JsonProperty("response_status")
	public void setResponseStatus(final String responseStatus) {
		this.responseStatus = responseStatus;
	}
	
	@JsonProperty("date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	public LocalDateTime getDate() {
		return date;
	}
	
	public void setDate(final LocalDateTime date) {
		this.date = date;
	}

	@JsonProperty("request_no")
	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(final String requestNo) {
		this.requestNo = requestNo;
	}

	@JsonProperty("error_code")
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}
	
	@JsonProperty("error_message")
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@JsonProperty("error_details")
	public String getErrorDetails() {
		return errorDetails;
	}
	
	public void setErrorDetails(final String errorDetails) {
		this.errorDetails = errorDetails;
	}

	@JsonProperty("request_validation_errors")
	public List<String> getFieldValidationErrors() {
		return fieldValidationErrors;
	}

	public void setFieldValidationErrors(final List<String> fieldValidationErrors) {
		this.fieldValidationErrors = fieldValidationErrors;
	}

	@JsonProperty("response_body")
	public R getResponseBody() {
		return responseBody;
	}
	
	public void setResponseBody(final R responseBody) {
		this.responseBody = responseBody;
	}
}
