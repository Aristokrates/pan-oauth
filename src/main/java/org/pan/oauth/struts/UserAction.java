package org.pan.oauth.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.pan.oauth.authentication.TwitterServiceAuthenticator;
import org.pan.oauth.context.ApplicationContext;
import org.pan.oauth.model.GoogleWrapperModel;
import org.pan.oauth.model.TwitterWrapperModel;

public class UserAction extends DispatchAction  {
	 
    public ActionForward doTwitterAuth(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	TwitterServiceAuthenticator twitterServiceAuthenticator = ApplicationContext.INSTANCE.getTwitterServiceAuthenticator();
    	TwitterWrapperModel twitterWrapper = twitterServiceAuthenticator.getTwitterURL(returnTo(request));
    	
    	request.getSession().setAttribute("twitter-token", twitterWrapper.getTwitterRequestToken());
        return new ActionForward(twitterWrapper.getRedirectUrl(), true);
    }

    public ActionForward doGoogleAuth(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	GoogleWrapperModel googleWrapper = ApplicationContext.INSTANCE.getGoogleServiceAuthenticator().authRequest(returnTo(request));

    	request.getSession().setAttribute("openid-discovery", googleWrapper.getDiscoveryInformation());

    	return new ActionForward(googleWrapper.getRedirectionUrl(), true);
    }
    
    private String returnTo(HttpServletRequest request) {
        return new StringBuffer(baseUrl(request))
                .append(request.getContextPath()).toString();
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
