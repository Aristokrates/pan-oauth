package org.pan.oauth.authentication.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pan.oauth.context.ApplicationContext;
import org.pan.oauth.model.GoogleWrapperModel;

/**
 * Google servlet for authentication using Federated login
 * 
 * @author Pance.Isajeski
 *
 */
public class GoogleServiceAuthenticatorServlet extends HttpServlet {

	private static final long serialVersionUID = -4581842199124615092L;
	
	@Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	public void processRequest(HttpServletRequest httpReq, HttpServletResponse httpResp) 
		throws IOException, ServletException {

		GoogleWrapperModel googleWrapper = ApplicationContext.INSTANCE.getGoogleServiceAuthenticator().authRequest();

		httpReq.getSession().setAttribute("openid-disc", googleWrapper.getDiscoveryInformation());

		httpResp.sendRedirect(googleWrapper.getRedirectionUrl());

		System.out.println("***Authorization finished***");
	}
}
