package org.pan.oauth.model;

/**
 * Twitter oauth model. 
 * <p>
 * Holds user's access token and access token secret
 * 
 * @author Pance.Isajeski
 *
 */
public class TwitterOAuthModel {
	
	private String accessToken;
	
	private String accessTokenSecret;

	public TwitterOAuthModel(String accessToken, String accessTokenSecret) {
		super();
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}
}
