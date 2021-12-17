/**
 * 
 */
package org.college.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vivek Sharma
 *
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Resource already exists in database")
public class DuplicateResourceException extends ServiceException {

	private static final long serialVersionUID = 7936185796285694228L;

	public DuplicateResourceException() {
		super();
	}

	public DuplicateResourceException(String code, String errorMsg) {
		super(code, errorMsg);
	}

	public DuplicateResourceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicateResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateResourceException(String message) {
		super(message);
	}

	public DuplicateResourceException(Throwable cause) {
		super(cause);
	}

}
