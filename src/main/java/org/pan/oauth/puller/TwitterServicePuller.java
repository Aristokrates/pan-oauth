package org.pan.oauth.puller;

import org.pan.oauth.exception.CustomOAuthException;
import org.pan.oauth.model.TwitterOAuthModel;
import org.pan.oauth.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


/**
 * Pulling data from twitter service
 * 
 * @author Pance.Isajeski
 *
 */
public class TwitterServicePuller {

	private static final Logger log = LoggerFactory.getLogger(TwitterServicePuller.class);	
	
	private String consumerKey;
	
	private String consumerSecret; 
	
	public TwitterServicePuller(String consumerKey, String consumerSecret) {
		super();
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	
	public UserModel verifyCredentials(TwitterOAuthModel twitterModel) {
		
		User user = null;

		try {
			Twitter client = getClient(twitterModel.getAccessToken(), twitterModel.getAccessTokenSecret());
			user = client.verifyCredentials();
		} catch (TwitterException e) {
			log.error("Twitter exception occured. Aborting...");
			throw new CustomOAuthException(e);
		}

		return new UserModel(user.getName(), user.getScreenName(), String.valueOf(user.getId()));
	}
	
	private Twitter getClient(
			final String accessToken,
			final String accessTokenSecret) {

		Configuration config = new ConfigurationBuilder().setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret).build();		
		return new TwitterFactory(config).getInstance(new AccessToken(accessToken, accessTokenSecret));
	}
}
