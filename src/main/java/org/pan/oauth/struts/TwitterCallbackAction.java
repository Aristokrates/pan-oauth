package org.pan.oauth.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pan.oauth.context.ApplicationContext;
import org.pan.oauth.exception.CustomOAuthException;
import org.pan.oauth.model.TwitterOAuthModel;
import org.pan.oauth.model.UserServiceModel;
import org.pan.oauth.puller.TwitterServicePuller;

import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterCallbackAction extends Action {
	
	private final static String SUCCESS = "success";

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		InfoDisplayModel model = (InfoDisplayModel) form;
		
		RequestToken requestToken = (RequestToken) request.getSession().getAttribute("twitter-token");
		
		request.getSession().removeAttribute("twitter-token");
		
		String oauthVerifier = (String) request.getParameter("oauth_verifier");
		String oauthToken = (String) request.getParameter("oauth_token");
		
		if (requestToken == null || !requestToken.getToken().equals(oauthToken)) {
			throw new CustomOAuthException("Callback process failed. Check the session.");
		}
		
		AccessToken accessToken = ApplicationContext.INSTANCE.getTwitterServiceAuthenticator().getAccessToken(requestToken, oauthVerifier);

		TwitterServicePuller twitterServicePuller = ApplicationContext.INSTANCE.getTwitterServicePuller();

		TwitterOAuthModel twitterModel = new TwitterOAuthModel(accessToken.getToken(), accessToken.getTokenSecret());

		UserServiceModel user = twitterServicePuller.verifyCredentials(twitterModel);

		model.setName(user.getName());
		model.setUsername(user.getUsername());
		model.setEmail(user.getEmail());

		return mapping.findForward(SUCCESS);

	}

}
