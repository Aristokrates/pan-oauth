package org.pan.oauth.factory;
import java.util.Properties;

import org.pan.oauth.puller.TwitterServicePuller;

/**
 * Factory for twitter service singleton instance
 * 
 * @author Pance.Isajeski
 *
 */
public enum TwitterServicePullerFactory {
	
	INSTANCE;
	
	private TwitterServicePuller twitterServicePuller;

	private TwitterServicePullerFactory() {
		
		Properties properties = PropertyHolderFactory.INSTANCE.getProperties();

		this.twitterServicePuller = new TwitterServicePuller(properties.getProperty("consumer.key"), 
				properties.getProperty("consumer.secret"));
	}

	public TwitterServicePuller getTwitterServicePuller() {
		return twitterServicePuller;
	}
}
