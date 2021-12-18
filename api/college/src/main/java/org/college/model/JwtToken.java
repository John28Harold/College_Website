package org.college.model;

public class JwtToken extends Base {

	private String accessToken;
	private String refreshToken;

	public JwtToken() {
		super();
	}

	public JwtToken(String accessToken, String refreshToken) {
		this();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public JwtToken(ResultStatus resultStatus) {
		super(resultStatus);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
