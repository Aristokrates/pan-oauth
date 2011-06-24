package org.pan.oauth.struts;

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

public class GoogleCallbackAction extends Action {

	private final static String SUCCESS = "success";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		InfoDisplayModel model = (InfoDisplayModel) form;
		
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
		
		model.setName(user.getName());
		model.setUsername(user.getUsername());
		model.setEmail(user.getEmail());
		
		System.out.println("***Verification finished***");		

		return mapping.findForward(SUCCESS);
	}
}
