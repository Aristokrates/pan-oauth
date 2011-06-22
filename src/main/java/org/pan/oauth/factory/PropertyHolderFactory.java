package org.pan.oauth.factory;

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
 * 
 * @author Pance.Isajeski
 *
 */
public enum PropertyHolderFactory {
	
	INSTANCE;
	
	private Properties properties;

	private PropertyHolderFactory() {
		
		this.properties = new Properties();
		
		InputStream globalPropertiesIs = PropertyHolderFactory.class.getResourceAsStream("/global.properties");
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
				"http.proxyPassword", "http.nonProxyHosts");
		
		Map<String, String> proxySettingsMap = new HashMap<String, String>();
		
		for (String prop : listOfProperties) {
			proxySettingsMap.put(prop, properties.getProperty(prop));		
		}
		
		return proxySettingsMap;
	}

	public Properties getProperties() {
		return properties;
	}
}
