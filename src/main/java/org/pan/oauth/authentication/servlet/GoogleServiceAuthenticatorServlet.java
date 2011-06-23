package org.pan.oauth.authentication.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.ax.FetchRequest;

/**
 * Google service for authentication using Federated login
 * 
 * @author Pance.Isajeski
 *
 */
public class GoogleServiceAuthenticatorServlet extends HttpServlet {

	private static final long serialVersionUID = -4581842199124615092L;
	
	private static final String OPENID_DISCOVER_URL = "http://aristokrates.myopenid.com/";
	
	public ConsumerManager manager;
	private String returnToPath;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        returnToPath = "/redirect";
        try {
			manager = new ConsumerManager();
		} catch (ConsumerException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		authRequest(OPENID_DISCOVER_URL, req, resp);
	}
	
	// --- placing the authentication request ---
    @SuppressWarnings("unchecked")
	public String authRequest(String userSuppliedString,
                              HttpServletRequest httpReq,
                              HttpServletResponse httpResp)
            throws IOException, ServletException
    {
        try
        {

            // configure the return_to URL where your application will receive
            // the authentication responses from the OpenID provider
            String returnToUrl = returnTo(httpReq);
            
            // --- Forward proxy setup (only if needed) ---
//             ProxyProperties proxyProps = new ProxyProperties();
//             proxyProps.setProxyHostName("10.0.1.21");
//             proxyProps.setProxyPort(8080);
//             proxyProps.setUserName("SEAVUS-CORP\\Pance.Isajeski");
//             proxyProps.setPassword("$1birokrates");
//             HttpClientFactory.setProxyProperties(proxyProps);

            // perform discovery on the user-supplied identifier
            List<DiscoveryInformation> discoveries = manager.discover(userSuppliedString);

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = manager.associate(discoveries);

            // store the discovery information in the user's session
            httpReq.getSession().setAttribute("openid-disc", discovered);

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

            // Attribute Exchange example: fetching the 'email' attribute
            FetchRequest fetch = FetchRequest.createFetchRequest();
            fetch.addAttribute("email",
                    // attribute alias
                    "http://schema.openid.net/contact/email",   // type URI
                    true);                                      // required
            
            fetch.addAttribute("firstname",
                    // attribute alias
                    "http://axschema.org/namePerson/first",   // type URI
                    true);                                      // required
            
            fetch.addAttribute("lastname",
                    // attribute alias
                    "http://axschema.org/namePerson/last",   // type URI
                    true);                                      // required

            // attach the extension to the authentication request
            authReq.addExtension(fetch);
            
            String redUrl = authReq.getDestinationUrl(true);
            
            System.out.println("Redirection Url: " + redUrl);
            
            httpResp.sendRedirect(redUrl);
        }
        catch (OpenIDException e)
        {
            e.printStackTrace();
        }
        
        System.out.println("***Auth finished***");

        return null;
    }
    
    private String returnTo(HttpServletRequest request) {
        return new StringBuffer(baseUrl(request))
                .append(request.getContextPath())
                .append(returnToPath).toString();
    }
    
    private String baseUrl(HttpServletRequest request) {
        StringBuffer url = new StringBuffer(request.getScheme())
                .append("://").append(request.getServerName());

        if ((request.getScheme().equalsIgnoreCase("http")
                && request.getServerPort() != 80)
                || (request.getScheme().equalsIgnoreCase("https")
                && request.getServerPort() != 443)) {
            url.append(":").append(request.getServerPort());
        }

        return url.toString();
    }

}
