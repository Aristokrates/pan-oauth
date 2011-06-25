package org.pan.oauth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pan.oauth.context.ApplicationContext;
import org.pan.oauth.model.TwitterOAuthModel;
import org.pan.oauth.model.UserServiceModel;
import org.pan.oauth.puller.TwitterServicePuller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 *  Twitter callback http post handler
 * 
 * @author Pance.Isajeski
 *
 */
public class TwitterCallbackAction extends Action {
	
	private static final Logger log = LoggerFactory.getLogger(TwitterCallbackAction.class);
	
	private final static String SUCCESS = "success";
	private final static String INDEX = "index";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.debug("Twitter callback initiated");
		DisplayForm model = (DisplayForm) form;
		
		RequestToken requestToken = (RequestToken) request.getSession().getAttribute("twitter-token");
		
		request.getSession().removeAttribute("twitter-token");
		
		String oauthVerifier = request.getParameter("oauth_verifier");
		String oauthToken = request.getParameter("oauth_token");
		
		if (requestToken == null || oauthVerifier == null 
				|| oauthToken == null || !requestToken.getToken().equals(oauthToken)) {
			log.warn("User has canceled the request. Forwarding to home page");
			return mapping.findForward(INDEX);
		}
		
		AccessToken accessToken = ApplicationContext.INSTANCE.getTwitterServiceAuthenticator().getAccessToken(requestToken, oauthVerifier);

		TwitterServicePuller twitterServicePuller = ApplicationContext.INSTANCE.getTwitterServicePuller();

		TwitterOAuthModel twitterModel = new TwitterOAuthModel(accessToken.getToken(), accessToken.getTokenSecret());

		UserServiceModel user = twitterServicePuller.verifyCredentialsAndGetData(twitterModel);

		model.setName(user.getName());
		model.setUsername(user.getUsername());
		model.setEmail(user.getEmail());

		return mapping.findForward(SUCCESS);
	}
}
