/**
 * 
 */
package org.college.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResultStatus {

	private String status;
	private String errorMessage;

	public ResultStatus(String status) {
		super();
		this.status = status;
	}

	public ResultStatus(String status, String errorMessage) {
		super();
		this.status = status;
		this.errorMessage = errorMessage;
	}

	public ResultStatus() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
