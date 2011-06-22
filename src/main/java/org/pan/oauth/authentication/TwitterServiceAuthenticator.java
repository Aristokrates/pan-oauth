package org.pan.oauth.authentication;

import org.pan.oauth.exception.CustomOAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *  Twitter service for authentication using OAuth 2.0
 * 
 * @author Pance.Isajeski
 *
 */
public class TwitterServiceAuthenticator {

	private static final Logger log = LoggerFactory.getLogger(TwitterServiceAuthenticator.class);

	private String twitterCallbackUrl;

	private OAuthProvider provider;

	private OAuthConsumer consumer;

	public TwitterServiceAuthenticator(String twitterCallbackUrl, 
			OAuthProvider provider, OAuthConsumer consumer) {
		
		super();
		this.twitterCallbackUrl = twitterCallbackUrl;
		this.provider = provider;
		this.consumer = consumer;
	}

	/**
	 * Get the Twitter redirect URL and unauthorized request token.
	 *
	 * @param device
	 * @return
	 */
	public String getTwitterURL(String twitterCallbackSecret, String username) {

		//Get the Twitter redirect URL and set unauthorized request token and token secret to the customer
		try {
			return provider.retrieveRequestToken(consumer, twitterCallbackUrl);

		} catch (OAuthException exception) {
			throw new CustomOAuthException(exception);
		}
	}

	/**
	 * @param token The Request Token.
	 * @param secret The Token Secret.
	 * @param oauthVerifier The verification code.
	 * @return
	 */
	public AccessToken getAccessToken(String token, String oauthVerifier) {

		String tokenSecret = getRequestToken().getTokenSecret();

		consumer.setTokenWithSecret(token, tokenSecret);
		try {
			provider.retrieveAccessToken(consumer, oauthVerifier);
			return new AccessToken(consumer.getToken(), consumer.getTokenSecret());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private RequestToken getRequestToken() {
		return new RequestToken(consumer.getToken(), consumer.getTokenSecret());
	}

}
