package org.pan.oauth.authentication;

import org.pan.oauth.exception.CustomOAuthException;
import org.pan.oauth.model.TwitterWrapperModel;
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
//TODO (pai) investigate & consider non-singleton usage
public class TwitterServiceOAuth {

	private static final Logger log = LoggerFactory.getLogger(TwitterServiceOAuth.class);

	private String twitterCallbackUrl;

	private OAuthProvider provider;

	private OAuthConsumer consumer;

	public TwitterServiceOAuth(String twitterCallbackUrl, 
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
	public TwitterWrapperModel getTwitterURL() {

		//Get the Twitter redirect URL and set unauthorized request token and token secret to the customer
		try {
			String url = provider.retrieveRequestToken(consumer, twitterCallbackUrl);
			return new TwitterWrapperModel(url, buildRequestToken());

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
	public AccessToken getAccessToken(RequestToken token, String oauthVerifier) {

		consumer.setTokenWithSecret(token.getToken(), token.getTokenSecret());
		try {
			provider.retrieveAccessToken(consumer, oauthVerifier);
			return new AccessToken(consumer.getToken(), consumer.getTokenSecret());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private RequestToken buildRequestToken() {
		return new RequestToken(consumer.getToken(), consumer.getTokenSecret());
	}
}
