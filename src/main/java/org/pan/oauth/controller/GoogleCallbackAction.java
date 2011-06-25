package org.pan.oauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.ParameterList;
import org.pan.oauth.context.ApplicationContext;
import org.pan.oauth.model.UserServiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Google callback http post handler
 * 
 * @author Pance.Isajeski
 *
 */
public class GoogleCallbackAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(GoogleCallbackAction.class);
	
	private final static String SUCCESS = "success";
	private final static String INDEX = "index";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.debug("Google callback initiated");
		
		DisplayForm model = (DisplayForm) form;
		
		// extract the parameters from the authentication response
		// (which comes in as a HTTP request from the OpenID provider)
		ParameterList responseParameterList = new ParameterList(request.getParameterMap());

		// retrieve the previously stored discovery information
		DiscoveryInformation discovered = (DiscoveryInformation)request.getSession().getAttribute("openid-discovery");
		request.getSession().removeAttribute("openid-discovery");

		// extract the receiving URL from the HTTP request
		StringBuffer receivingURL = request.getRequestURL();
		String queryString = request.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			receivingURL.append("?").append(request.getQueryString());
		}
		
		UserServiceModel user = ApplicationContext.INSTANCE.getGoogleServiceAuthenticator().verifyResponseAndGetData(responseParameterList, 
				receivingURL.toString(), discovered);		
		
		if (user == null) {
			// canceled request
			log.debug("Forwarding to home page");
			return mapping.findForward(INDEX);
		}
		
		model.setName(user.getName());
		model.setUsername(user.getUsername());
		model.setEmail(user.getEmail());

		return mapping.findForward(SUCCESS);
	}
}
