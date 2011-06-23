package org.pan.oauth.context;

import java.util.Properties;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import org.pan.oauth.authentication.TwitterServiceAuthenticator;
import org.pan.oauth.puller.GoogleServicePuller;
import org.pan.oauth.puller.TwitterServicePuller;

public enum ApplicationContext {

	INSTANCE;

	private GoogleServicePuller googleServicePuller;
	private TwitterServiceAuthenticator twitterServiceAuthenticator;
	private TwitterServicePuller twitterServicePuller;
	private PropertyPlaceHolder propertyPlaceholder = PropertyPlaceHolder.INSTANCE;

	private ApplicationContext() {		
		createGoogleServicePuller();
		createTwitterServiceAuthenticator();
		createTwitterServicePuller();
	}

	private void createGoogleServicePuller() {
		this.googleServicePuller = new GoogleServicePuller();
	}

	private void createTwitterServicePuller() {

		this.twitterServicePuller = new TwitterServicePuller(propertyPlaceholder.getTwitterConsumerKey(), 
				propertyPlaceholder.getTwitterConsumerKeySecret());
	}

	private void createTwitterServiceAuthenticator() {
		Properties properties = PropertyPlaceHolder.INSTANCE.getProperties();

		OAuthProvider provider = new DefaultOAuthProvider(propertyPlaceholder.getTwitterRequestTokenUrl(), 
				propertyPlaceholder.getTwitterAccessTokenUrl(), 
				propertyPlaceholder.getTwitterAuthorizeUrl());

		OAuthConsumer consumer = new DefaultOAuthConsumer(propertyPlaceholder.getTwitterConsumerKey(),
				properties.getProperty(propertyPlaceholder.getTwitterConsumerKeySecret()));

		this.twitterServiceAuthenticator = new TwitterServiceAuthenticator(
				propertyPlaceholder.getTwitterCallbackUrl(), 
				provider, 
				consumer);
	}

	public GoogleServicePuller getGoogleServicePuller() {
		return googleServicePuller;
	}

	public TwitterServiceAuthenticator getTwitterServiceAuthenticator() {
		return twitterServiceAuthenticator;
	}

	public TwitterServicePuller getTwitterServicePuller() {
		return twitterServicePuller;
	}
}
