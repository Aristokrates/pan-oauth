package org.pan.oauth.test.puller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.pan.oauth.factory.TwitterServicePullerFactory;
import org.pan.oauth.model.TwitterOAuthModel;
import org.pan.oauth.model.UserModel;
import org.pan.oauth.puller.TwitterServicePuller;
import org.pan.oauth.test.BaseTestCase;

public class TwitterServicePullerTestCase extends BaseTestCase {
	
	private TwitterServicePuller twitterServicePuller;
	private Properties properties;
	
	@Before
	public void initTest() {
		twitterServicePuller = TwitterServicePullerFactory.INSTANCE.getTwitterServicePuller();		
		properties = new Properties();
		
		InputStream defaultTwitterPropertiesIS = TwitterServicePullerTestCase.class.getResourceAsStream("/twitter.test.properties");
		try {
			properties.load(defaultTwitterPropertiesIS);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void testVerifyCredentials() {
		
		String accessToken = properties.getProperty("access.token");
		String accessTokenSecret = properties.getProperty("access.token.secret");
		
		TwitterOAuthModel twitterModel = new TwitterOAuthModel(accessToken, accessTokenSecret);
		
		UserModel user = twitterServicePuller.verifyCredentials(twitterModel);
		Assert.assertNotNull(user);
	}

}
