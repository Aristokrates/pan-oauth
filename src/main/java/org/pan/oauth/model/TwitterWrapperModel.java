package org.pan.oauth.model;

import twitter4j.auth.RequestToken;

/**
 * Wrapper model
 * 
 * @author Pance.Isajeski
 *
 */
public class TwitterWrapperModel {

    private RequestToken twitterRequestToken;
    private String redirectUrl;

    public TwitterWrapperModel(String redirectUrl, RequestToken requestToken) {
        this.twitterRequestToken = requestToken;
        this.redirectUrl = redirectUrl;
    }

    /**
     * @return the twitterRequestToken
     */
    public RequestToken getTwitterRequestToken() {
        return twitterRequestToken;
    }

    /**
     * @param twitterRequestToken the twitterRequestToken to set
     */
    public void setTwitterRequestToken(RequestToken twitterRequestToken) {
        this.twitterRequestToken = twitterRequestToken;
    }

    /**
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * @param redirectUrl the redirectUrl to set
     */
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

}
