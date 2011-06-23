package org.pan.oauth.model;

import org.openid4java.discovery.DiscoveryInformation;

public class GoogleWrapperModel {
	
	private String redirectionUrl;
	
	private DiscoveryInformation discoveryInformation;

	public GoogleWrapperModel(String redirectionUrl, DiscoveryInformation discoveryInformation) {
		super();
		this.redirectionUrl = redirectionUrl;
		this.discoveryInformation = discoveryInformation;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	public DiscoveryInformation getDiscoveryInformation() {
		return discoveryInformation;
	}

	public void setDiscoveryInformation(DiscoveryInformation discoveryInformation) {
		this.discoveryInformation = discoveryInformation;
	}
}
