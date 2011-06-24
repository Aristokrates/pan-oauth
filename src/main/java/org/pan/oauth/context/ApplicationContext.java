package org.pan.oauth.context;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import org.pan.oauth.authentication.GoogleServiceOpenIdAuthenticator;
import org.pan.oauth.authentication.TwitterServiceAuthenticator;
import org.pan.oauth.puller.GoogleServicePuller;
import org.pan.oauth.puller.TwitterServicePuller;

/**
 * Bean Application context
 * <p>
 * Contains and initialize bean singleton instances. Replacement for DI
 * 
 * @author Pance.Isajeski
 *
 */
public enum ApplicationContext {

	INSTANCE;

	private GoogleServicePuller googleServicePuller;
	private GoogleServiceOpenIdAuthenticator googleServiceAuthenticator;
	private TwitterServiceAuthenticator twitterServiceAuthenticator;
	private TwitterServicePuller twitterServicePuller;
	private PropertyPlaceHolder propertyPlaceholder = PropertyPlaceHolder.INSTANCE;

	private ApplicationContext() {		
		createGoogleServicePuller();
		createGoogleServiceAuthenticator();
		createTwitterServiceAuthenticator();
		createTwitterServicePuller();
	}

	private void createGoogleServiceAuthenticator() {
		googleServiceAuthenticator = new GoogleServiceOpenIdAuthenticator();		
	}

	private void createGoogleServicePuller() {
		googleServicePuller = new GoogleServicePuller();
	}

	private void createTwitterServicePuller() {

		twitterServicePuller = new TwitterServicePuller(
				propertyPlaceholder.getTwitterConsumerKey(), 
				propertyPlaceholder.getTwitterConsumerKeySecret());
	}

	private void createTwitterServiceAuthenticator() {

		OAuthProvider provider = new DefaultOAuthProvider(
				propertyPlaceholder.getTwitterRequestTokenUrl(), 
				propertyPlaceholder.getTwitterAccessTokenUrl(), 
				propertyPlaceholder.getTwitterAuthorizeUrl());

		OAuthConsumer consumer = new DefaultOAuthConsumer(
				propertyPlaceholder.getTwitterConsumerKey(),
				propertyPlaceholder.getTwitterConsumerKeySecret());

		twitterServiceAuthenticator = new TwitterServiceAuthenticator(provider, consumer);
	}

	public GoogleServicePuller getGoogleServicePuller() {
		return googleServicePuller;
	}

	public GoogleServiceOpenIdAuthenticator getGoogleServiceAuthenticator() {
		return googleServiceAuthenticator;
	}

	public TwitterServiceAuthenticator getTwitterServiceAuthenticator() {
		return twitterServiceAuthenticator;
	}

	public TwitterServicePuller getTwitterServicePuller() {
		return twitterServicePuller;
	}
}
