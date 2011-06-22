package org.pan.oauth.factory;
import java.util.Properties;

import org.pan.oauth.puller.TwitterServicePuller;

/**
 * Factory for twitter service puller singleton instance
 * 
 * @author Pance.Isajeski
 *
 */
public enum TwitterServicePullerFactory {
	
	INSTANCE;
	
	private static final String CONSUMER_KEY = "twitter.consumer.key";
	private static final String CONSUMER_SECRET = "twitter.consumer.secret";
	
	private TwitterServicePuller twitterServicePuller;

	private TwitterServicePullerFactory() {
		
		Properties properties = PropertyHolderFactory.INSTANCE.getProperties();

		this.twitterServicePuller = new TwitterServicePuller(properties.getProperty(CONSUMER_KEY), 
				properties.getProperty(CONSUMER_SECRET));
	}

	public TwitterServicePuller getTwitterServicePuller() {
		return twitterServicePuller;
	}
}
