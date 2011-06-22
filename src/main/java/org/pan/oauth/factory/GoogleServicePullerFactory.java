package org.pan.oauth.factory;

import org.pan.oauth.puller.GoogleServicePuller;

/**
 * Factory for google service singleton instance
 * 
 * @author Pance.Isajeski
 *
 */
public enum GoogleServicePullerFactory {
	
	INSTANCE;
	
	private GoogleServicePuller googleServicePuller;

	private GoogleServicePullerFactory() {
		this.googleServicePuller = new GoogleServicePuller();
	}

	public GoogleServicePuller getGoogleServicePuller() {
		return googleServicePuller;
	}
}
