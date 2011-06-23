package org.pan.oauth.test.authentication;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pan.oauth.authentication.TwitterServiceOAuth;
import org.pan.oauth.context.ApplicationContext;
import org.pan.oauth.model.TwitterWrapperModel;
import org.pan.oauth.test.BaseTestCase;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterServiceAuthenticatorTestCase extends BaseTestCase {
	
	private TwitterServiceOAuth twitterServiceAuthenticator;
	
	@Before
	public void initTest() {
		twitterServiceAuthenticator = ApplicationContext.INSTANCE.getTwitterServiceAuthenticator();
	}
	
	@Test
	public void testRequestToken() {
		TwitterWrapperModel twitterWrapper = twitterServiceAuthenticator.getTwitterURL();
		System.out.println("Get oauth_verifier from Url: " + twitterWrapper.getRedirectUrl());
		
		RequestToken requestToken = twitterWrapper.getTwitterRequestToken();
		Assert.assertNotNull(requestToken);
		System.out.println("request_oauth_token: " + requestToken.getToken());
		System.out.println("request_token_secret: " + requestToken.getTokenSecret());
	}
	
	@Test
	@Ignore
	public void testAccessToken() {
		
		// Ignore comment: This test requires manual input from user and getting oauth verifier
		
		String oauthToken = props.getProperty("request_oauth_token");
		String oauthTokenSecret = props.getProperty("request_token_secret");
		String oauthVerifier = props.getProperty("oauth_verifier");
		
		AccessToken token = twitterServiceAuthenticator.getAccessToken(new RequestToken(oauthToken, oauthTokenSecret), oauthVerifier);
		Assert.assertNotNull(token);
		System.out.println("Access-Token: " + token.getToken());
		System.out.println("Access-Token-Secret: " + token.getTokenSecret());
	}
	
}
