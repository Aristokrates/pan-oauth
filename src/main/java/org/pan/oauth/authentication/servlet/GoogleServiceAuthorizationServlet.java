package org.pan.oauth.authentication.servlet;

import javax.servlet.http.HttpServlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchResponse;

/**
 * Google service for authentication using Federated login
 * 
 * @author Pance.Isajeski
 *
 */
public class GoogleServiceAuthorizationServlet extends HttpServlet {

	private static final long serialVersionUID = -4512284617488524622L;
	public ConsumerManager manager;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		verifyResponse(req);
	}

	// --- processing the authentication response ---
	public Identifier verifyResponse(HttpServletRequest httpReq)
	{
		try
		{
			// extract the parameters from the authentication response
			// (which comes in as a HTTP request from the OpenID provider)
			ParameterList response =
				new ParameterList(httpReq.getParameterMap());

			// retrieve the previously stored discovery information
			DiscoveryInformation discovered = (DiscoveryInformation)
			httpReq.getSession().getAttribute("openid-disc");

			// extract the receiving URL from the HTTP request
			StringBuffer receivingURL = httpReq.getRequestURL();
			String queryString = httpReq.getQueryString();
			if (queryString != null && queryString.length() > 0)
				receivingURL.append("?").append(httpReq.getQueryString());

			// verify the response; ConsumerManager needs to be the same
			// (static) instance used to place the authentication request
			VerificationResult verification = manager.verify(
					receivingURL.toString(),
					response, discovered);

			// examine the verification result and extract the verified identifier
			Identifier verified = verification.getVerifiedId();
			if (verified != null)
			{
				AuthSuccess authSuccess =
					(AuthSuccess) verification.getAuthResponse();

				if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX))
				{
					FetchResponse fetchResp = (FetchResponse) authSuccess
					.getExtension(AxMessage.OPENID_NS_AX);

					String email = fetchResp.getAttributeValue("email");
					System.out.println("***Email received: " + email);
					String firstName = fetchResp.getAttributeValue("firstname");
					System.out.println("***First name received: " + firstName);
					String lastName = fetchResp.getAttributeValue("lastname");
					System.out.println("***Last name received: " + lastName);
				}

				return verified;  // success
			}
		}
		catch (OpenIDException e)
		{
			// present error to the user
		}

		System.out.println("***Verification finished***");
		return null;
	}

}
