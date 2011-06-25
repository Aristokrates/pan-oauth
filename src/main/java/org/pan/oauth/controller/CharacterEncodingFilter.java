package org.pan.oauth.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet filter for encoding character
 * 
 * @author Aristokrates
 *
 */
public class CharacterEncodingFilter implements Filter {
    /**
     * Character set encoding.
     */
    private String encoding;

    /*
     * @see Filter#init(javax.servlet.FilterConfig) 
     */
    public void init(FilterConfig config) throws ServletException {
        // Set character set encoding from the init parameters or set UTF-8
        // as default value.
        encoding = config.getInitParameter("encoding") != null ?
            config.getInitParameter("encoding") : "UTF-8";
    }

    /*
     * @see Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        request.setCharacterEncoding(encoding);

        chain.doFilter(request, response);
    }

    /*
     * @see Filter#destroy()
     */
    public void destroy() {
    }
}

