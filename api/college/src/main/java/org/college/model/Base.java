package org.college.model;

import org.college.constants.AppConstants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Base {
	private ResultStatus resultStatus;

	public Base() {
		this.resultStatus = new ResultStatus(AppConstants.SUCCESS);
	}

	public Base(ResultStatus resultStatus) {
		super();
		this.resultStatus = resultStatus;
	}

	public ResultStatus getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(ResultStatus resultStatus) {
		this.resultStatus = resultStatus;
	}

}
