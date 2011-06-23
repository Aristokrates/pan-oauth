package org.pan.oauth.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Property placeholder
 * <p>
 * Contains user defined properties injected in application
 * 
 * @author Pance.Isajeski
 *
 */
public enum PropertyPlaceHolder {
	
	INSTANCE;
	
	private static final String CONSUMER_KEY = "twitter.consumer.key";
	private static final String CONSUMER_SECRET = "twitter.consumer.secret";
	private static final String CALLBACK_URL = "twitter.callback.url";
	private static final String REQUEST_TOKEN_URL = "twitter.requestTokenUrl";
	private static final String ACCESS_TOKEN_URL = "twitter.accessTokenUrl";
	private static final String AUTHORIZE_URL = "twitter.authorizeUrl";		
	
	private static final String OPEN_ID_ENDPOINT = "openid.endpoint";
	private static final String OPEN_ID_CALLBACK_URL = "openid.callback.url";
	
	private Properties properties;

	private PropertyPlaceHolder() {
		
		properties = new Properties();
		
		InputStream globalPropertiesIs = PropertyPlaceHolder.class.getResourceAsStream("/global.properties");
		try {
			properties.load(globalPropertiesIs);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
				
		initProxySettings();
		
	}

	private void initProxySettings() {
		String str = properties.getProperty("http.proxy.enabled");
		Boolean httpProxyEnabled = (str != null && str.equals("yes")) ? true : false;

		if (httpProxyEnabled != null && httpProxyEnabled) {
			Map<String, String> httpProxySettings = initHttpProxySettingsMap();
			Iterator<String> iter = httpProxySettings.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				String value = httpProxySettings.get(key);
				System.setProperty(key, value);
			}

		}
	}

	private Map<String, String> initHttpProxySettingsMap() {
		
		List<String> listOfProperties = Arrays.asList("http.proxyHost", "http.proxyPort", "http.proxyUser", 
				"http.proxyPassword", "http.nonProxyHosts", "https.proxyHost", "https.proxyPort", "https.proxyUser", 
				"https.proxyPassword", "https.nonProxyHosts");
		
		Map<String, String> proxySettingsMap = new HashMap<String, String>();
		
		for (String prop : listOfProperties) {
			proxySettingsMap.put(prop, properties.getProperty(prop));		
		}
		
		return proxySettingsMap;
	}

	public Properties getProperties() {
		return properties;
	}
	
	public String getTwitterConsumerKey() {
		return properties.getProperty(CONSUMER_KEY);
	}
	
	public String getTwitterConsumerKeySecret() {
		return properties.getProperty(CONSUMER_SECRET);
	}
	
	public String getTwitterCallbackUrl() {
		return properties.getProperty(CALLBACK_URL);
	}
	
	public String getTwitterRequestTokenUrl() {
		return properties.getProperty(REQUEST_TOKEN_URL);
	}
	
	public String getTwitterAccessTokenUrl() {
		return properties.getProperty(ACCESS_TOKEN_URL);
	}
	
	public String getTwitterAuthorizeUrl() {
		return properties.getProperty(AUTHORIZE_URL);
	}
	
	public String getOpenIdEndpoint() {
		return properties.getProperty(OPEN_ID_ENDPOINT);
	}
	
	public String getOpenIdCallbackUrl() {
		return properties.getProperty(OPEN_ID_CALLBACK_URL);
	}
	
}
