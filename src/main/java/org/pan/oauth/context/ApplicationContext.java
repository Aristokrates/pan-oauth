package org.pan.oauth.context;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

import org.pan.oauth.authentication.GoogleServiceOpenId;
import org.pan.oauth.authentication.TwitterServiceOAuth;
import org.pan.oauth.puller.GoogleServicePuller;
import org.pan.oauth.puller.TwitterServicePuller;

public enum ApplicationContext {

	INSTANCE;

	private GoogleServicePuller googleServicePuller;
	private GoogleServiceOpenId googleServiceAuthenticator;
	private TwitterServiceOAuth twitterServiceAuthenticator;
	private TwitterServicePuller twitterServicePuller;
	private PropertyPlaceHolder propertyPlaceholder = PropertyPlaceHolder.INSTANCE;

	private ApplicationContext() {		
		createGoogleServicePuller();
		createGoogleServiceAuthenticator();
		createTwitterServiceAuthenticator();
		createTwitterServicePuller();
	}

	private void createGoogleServiceAuthenticator() {
		this.googleServiceAuthenticator = new GoogleServiceOpenId();		
	}

	private void createGoogleServicePuller() {
		this.googleServicePuller = new GoogleServicePuller();
	}

	private void createTwitterServicePuller() {

		this.twitterServicePuller = new TwitterServicePuller(propertyPlaceholder.getTwitterConsumerKey(), 
				propertyPlaceholder.getTwitterConsumerKeySecret());
	}

	private void createTwitterServiceAuthenticator() {

		OAuthProvider provider = new DefaultOAuthProvider(propertyPlaceholder.getTwitterRequestTokenUrl(), 
				propertyPlaceholder.getTwitterAccessTokenUrl(), 
				propertyPlaceholder.getTwitterAuthorizeUrl());

		OAuthConsumer consumer = new DefaultOAuthConsumer(propertyPlaceholder.getTwitterConsumerKey(),
				propertyPlaceholder.getTwitterConsumerKeySecret());

		this.twitterServiceAuthenticator = new TwitterServiceOAuth(
				propertyPlaceholder.getTwitterCallbackUrl(), 
				provider, 
				consumer);
	}

	public GoogleServicePuller getGoogleServicePuller() {
		return googleServicePuller;
	}

	public GoogleServiceOpenId getGoogleServiceAuthenticator() {
		return googleServiceAuthenticator;
	}

	public TwitterServiceOAuth getTwitterServiceAuthenticator() {
		return twitterServiceAuthenticator;
	}

	public TwitterServicePuller getTwitterServicePuller() {
		return twitterServicePuller;
	}
}
