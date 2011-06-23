package org.pan.oauth.test.puller;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.pan.oauth.context.ApplicationContext;
import org.pan.oauth.model.TwitterOAuthModel;
import org.pan.oauth.model.UserModel;
import org.pan.oauth.puller.TwitterServicePuller;
import org.pan.oauth.test.BaseTestCase;

public class TwitterServicePullerTestCase extends BaseTestCase {
	
	private TwitterServicePuller twitterServicePuller;
	
	@Before
	public void initTest() {
		twitterServicePuller = ApplicationContext.INSTANCE.getTwitterServicePuller();		
	}
	
	@Test
	public void testVerifyCredentials() {
		
		String accessToken = props.getProperty("access.token");
		String accessTokenSecret = props.getProperty("access.token.secret");
		
		TwitterOAuthModel twitterModel = new TwitterOAuthModel(accessToken, accessTokenSecret);
		
		UserModel user = twitterServicePuller.verifyCredentials(twitterModel);
		Assert.assertNotNull(user);
		System.out.println("Name: " + user.getName());
		System.out.println("Username: " + user.getUsername());
		System.out.println("Email: " + user.getEmail());
	}

}
