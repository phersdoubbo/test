package com.scholastic.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;


public class ImportProperty {

    private static final int DEFAULT_TIMEOUT = 30000;

    String url;
    Cookie[] cookie;
    String cookiesPlain;
    String contentType;
    int connectionTimeOut = DEFAULT_TIMEOUT;
    int timeout = DEFAULT_TIMEOUT;
    boolean trustAllHosts = false;
    StringBuilder body = new StringBuilder();
    String authorizationHeader = "";

    
    
    
    
    public String getCookiesPlain() {
        return cookiesPlain;
    }

    public void setCookiesPlain(String cookiesPlain) {
        this.cookiesPlain = cookiesPlain;
    }

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public Cookie[] getCookie() {
        return cookie;
    }

    public void setCookie(Cookie[] cookie) {
        this.cookie = cookie;
    }

    public String getBody() {
        return body.toString();
    }

    public void setBody(Map properties) {
        body = new StringBuilder();
        String ls = "&";
        for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
            Map.Entry pairs = (Map.Entry) it.next();
            body.append(pairs.getKey() + "=" + pairs.getValue() + ls);
        }
    }

    public void setBody(String bodyValue) {
        body = new StringBuilder();
        body.append(bodyValue);
    }

    public boolean isTrustAllHosts() {
        return trustAllHosts;
    }

    public void setTrustAllHosts(boolean trustAllHosts) {
        this.trustAllHosts = trustAllHosts;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

}
