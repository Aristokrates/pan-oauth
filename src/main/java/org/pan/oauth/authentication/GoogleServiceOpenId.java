package org.pan.oauth.authentication;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.pan.oauth.context.PropertyPlaceHolder;
import org.pan.oauth.exception.CustomOpenIdAuthException;
import org.pan.oauth.model.GoogleWrapperModel;
import org.pan.oauth.model.UserModel;

/**
 * Google service authentication using OpenId protocol
 * 
 * @author Pance.Isajeski
 *
 */
public class GoogleServiceOpenId {
	
	private ConsumerManager manager;
		
	public GoogleServiceOpenId() {
		
		super();
		
        try {
			manager = new ConsumerManager();
		} catch (ConsumerException e) {
			throw new CustomOpenIdAuthException(e);
		}
	}

    @SuppressWarnings("unchecked")
	public GoogleWrapperModel authRequest() throws IOException, ServletException {
        try {       
            // --- Forward proxy setup (only if needed) ---
//             ProxyProperties proxyProps = new ProxyProperties();
//             proxyProps.setProxyHostName("10.0.1.21");
//             proxyProps.setProxyPort(8080);
//             proxyProps.setUserName("SEAVUS-CORP\\Pance.Isajeski");
//             proxyProps.setPassword("$1birokrates");
//             HttpClientFactory.setProxyProperties(proxyProps);

            // perform discovery on the user-supplied identifier
            List<DiscoveryInformation> discoveries = manager.discover(
            		PropertyPlaceHolder.INSTANCE.getOpenIdEndpoint());

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = manager.associate(discoveries);

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = manager.authenticate(
            		discovered, PropertyPlaceHolder.INSTANCE.getOpenIdCallbackUrl());

            // Attribute Exchange
            FetchRequest fetch = FetchRequest.createFetchRequest();
            fetch.addAttribute("email",
                    "http://schema.openid.net/contact/email", 
                    true);                                  
            
            fetch.addAttribute("firstname",
                    "http://axschema.org/namePerson/first",
                    true);                                    
            
            fetch.addAttribute("lastname",
                    "http://axschema.org/namePerson/last",
                    true);

            // attach the extension to the authentication request
            authReq.addExtension(fetch);
            
            String redirectionUrl = authReq.getDestinationUrl(true);
            
            System.out.println("Redirection Url: " + redirectionUrl);           
            
            return new GoogleWrapperModel(redirectionUrl, discovered);

        }
        catch (OpenIDException e) {
            throw new CustomOpenIdAuthException(e);
        }
    }
    
	public UserModel verifyResponseAndGetData(ParameterList response, String receivingURL, DiscoveryInformation discovered) {
		try {
			VerificationResult verification = manager.verify(
					receivingURL.toString(),
					response, discovered);

			// examine the verification result and extract the verified identifier
			Identifier verified = verification.getVerifiedId();
			if (verified != null) {
				AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

				if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
					FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);

					String email = fetchResp.getAttributeValue("email");
					System.out.println("***Email received: " + email);
					String firstName = fetchResp.getAttributeValue("firstname");
					System.out.println("***First name received: " + firstName);
					String lastName = fetchResp.getAttributeValue("lastname");
					System.out.println("***Last name received: " + lastName);
					
					return new UserModel(firstName, lastName, email);
				}
			}
		}
		catch (OpenIDException e) {
			throw new CustomOpenIdAuthException(e);
		}

		return null;
	}
}
