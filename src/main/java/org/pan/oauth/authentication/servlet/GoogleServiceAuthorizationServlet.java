package org.pan.oauth.authentication.servlet;

import javax.servlet.http.HttpServlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.ParameterList;
import org.pan.oauth.context.ApplicationContext;

/**
 * Google service for authentication using Federated login
 * 
 * @author Pance.Isajeski
 *
 */
public class GoogleServiceAuthorizationServlet extends HttpServlet {

	private static final long serialVersionUID = -5512546635220093556L;	

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		doPost(req, resp);
	}

	/**
	 * Handle the response from the OpenID Provider.
	 *
	 * @param req Current servlet request
	 * @param resp Current servlet response
	 * @throws ServletException if unable to process request
	 * @throws IOException if unable to process request
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processResponse(req);
	}

	// --- processing the authentication response ---
	public void processResponse(HttpServletRequest httpReq) {
		// extract the parameters from the authentication response
		// (which comes in as a HTTP request from the OpenID provider)
		ParameterList response = new ParameterList(httpReq.getParameterMap());

		// retrieve the previously stored discovery information
		DiscoveryInformation discovered = (DiscoveryInformation)httpReq.getSession().getAttribute("openid-disc");

		// extract the receiving URL from the HTTP request
		StringBuffer receivingURL = httpReq.getRequestURL();
		String queryString = httpReq.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			receivingURL.append("?").append(httpReq.getQueryString());
		}
		
		ApplicationContext.INSTANCE.getGoogleServiceAuthenticator().verifyResponse(response, receivingURL.toString(), discovered);		
		
		System.out.println("***Verification finished***");		
	}
}
