package org.pan.oauth.factory;

import java.util.Properties;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import org.pan.oauth.authentication.TwitterServiceAuthenticator;

/**
 * Factory for twitter service authenticator singleton instance
 * 
 * @author Pance.Isajeski
 *
 */
public enum TwitterServiceAuthenticatorFactory {
	
	INSTANCE;
	
	private static final String CONSUMER_KEY = "twitter.consumer.key";
	private static final String CONSUMER_SECRET = "twitter.consumer.secret";
	private static final String CALLBACK_URL = "twitter.callback.url";
	private static final String REQUEST_TOKEN_URL = "twitter.requestTokenUrl";
	private static final String ACCESS_TOKEN_URL = "twitter.accessTokenUrl";
	private static final String AUTHORIZE_URL = "twitter.authorizeUrl";
	
	private TwitterServiceAuthenticator twitterServiceAuthenticator;

	private TwitterServiceAuthenticatorFactory() {
		
		Properties properties = PropertyHolderFactory.INSTANCE.getProperties();
		
		OAuthProvider provider = new DefaultOAuthProvider(properties.getProperty(REQUEST_TOKEN_URL), 
				properties.getProperty(ACCESS_TOKEN_URL), 
				properties.getProperty(AUTHORIZE_URL));
		
		OAuthConsumer consumer = new DefaultOAuthConsumer(properties.getProperty(CONSUMER_KEY),
				properties.getProperty(CONSUMER_SECRET));

		this.twitterServiceAuthenticator = new TwitterServiceAuthenticator(properties.getProperty(CALLBACK_URL), 
				provider, consumer);
	}

	public TwitterServiceAuthenticator getTwitterServiceAuthenticator() {
		return twitterServiceAuthenticator;
	}

}
