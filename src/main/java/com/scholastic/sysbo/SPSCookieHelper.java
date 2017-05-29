//
// SPSCookieHelper created by alexpod on  May 25, 2005
//
 
package com.scholastic.sysbo;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



public class SPSCookieHelper {
	private static final Logger myLogger = Logger.getLogger("SPSCookieHelper");
	public static int THIRTY_DAYS = 2592000;

	public static boolean placeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String value, boolean isTmp, boolean isEncrypted){
		int timeOut = -199;
		if(!isTmp)
			timeOut = THIRTY_DAYS;
		return placeCookie(request, response, cookieName, value, timeOut, isEncrypted);
	}
	
	public static boolean placeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String value, int timeOut, boolean isEncrypted){
		if(isEncrypted) {
			if(isSecureCookieName(cookieName))
				value = encryptValue(value, true);
			else
				value = encryptValue(value);
		}
		Cookie cookie = findCookie(request, cookieName);
		if(cookie != null)
			cookie.setValue(value);
		else
			cookie = new Cookie(cookieName, value);
		cookie.setPath("/");
		
		if(timeOut != -199)
			cookie.setMaxAge(timeOut);
		
		String domain = guessDomain(request);
		if(domain.indexOf("scholastic") != -1)
			cookie.setDomain(domain);
		
		if(isSecureCookieName(cookieName)) {
			//cookie.setPath("/; HttpOnly;");
			cookie.setSecure(true);
			//cookie.setHttpOnly(true);
			
		}
		response.addCookie(cookie);
		
		
		return true;
	}


	public static void killCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
		killCookie(request, response, cookieName, false);
	}
	public static void killCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, boolean skipPath){
         Cookie cookie = new Cookie(cookieName, "");
         if(!skipPath)
        	 cookie.setPath("/");
         cookie.setMaxAge(0);
         String domain = guessDomain(request);
         if(domain.indexOf("scholastic") != -1)
                 cookie.setDomain(domain);
         response.addCookie(cookie);
	}
	
	public static String getCookieValue(HttpServletRequest request, String cookieName, boolean isEncrypted){
		if (request.getAttribute(cookieName) != null){
			String openAMSpsId = (String) request.getAttribute(cookieName);
			return openAMSpsId;
		}
		Cookie cookie = findCookie(request, cookieName);
		if(cookie == null){
			if(myLogger.isDebugEnabled())
				myLogger.debug("did not find cookie named: " + cookieName);
			return null;
		}
		
		String value = cookie.getValue();
		
		if(SchStringUtils.isEmpty(value)){
			if(myLogger.isDebugEnabled())
				myLogger.debug("did not find value in the cookie: " + cookieName);
			return null;
		}
		
		if(isEncrypted) {
			if(isSecureCookieName(cookieName))
				return decryptValue(value, true);
			else
				return decryptValue(value);
		}else
			return value;
	}
	
	public static Cookie findCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            if(myLogger.isDebugEnabled())
                myLogger.debug("No cookies found!!");
            return null;
        }

        for(int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equalsIgnoreCase(cookieName)){
                return cookies[i];
            }
        }
        return null;
	}
	
	public static String encryptValue(String value){
		return encryptValue(value, false);
	}
	public static String encryptValue(String value, boolean isSecure){
        if(myLogger.isDebugEnabled())
            myLogger.debug("Ecrypting: " + value);

        if(SchStringUtils.isEmpty(value)){
	        if(myLogger.isDebugEnabled())
                myLogger.debug("value is empty, returning...");
	        return value;
        }
        
        if(isSecure)        	
        	value = SchBlowfishCodec.singleton(true).encodeText(value);
        else
        	value = SchBlowfishCodec.singleton().encodeText(value);
        if(value != null)
                value = value.replace('\n',' ').trim();

        return value;
    }
	public static String decryptValue(String value){
		return decryptValue(value, false);
	}

	public static String decryptValue(String value, boolean isSecure){
        if(myLogger.isDebugEnabled())
            myLogger.debug("decryptValue called, decoding: " + value);

        if(SchStringUtils.isEmpty(value)){
            if(myLogger.isDebugEnabled())
                myLogger.debug("value is empty, returning...");
            return null;
        }

        value+='\n';
        if(isSecure)
        	value = SchBlowfishCodec.singleton(true).decodeText(value);
        else
        	value = SchBlowfishCodec.singleton().decodeText(value);
        if(myLogger.isDebugEnabled())
            myLogger.debug("Decrypted value  = " + value);
        return value;
    }
	
	public static String guessDomain(HttpServletRequest request){
		String serverName = ".scholastic.com";
		
		if (request!=null && request.getServerName()!=null){
			if(request.getServerName().indexOf(".net") != -1)
				return ".scholastic.net";
			else if(request.getServerName().indexOf(".com") != -1)
				return ".scholastic.com";
		}
		
		return serverName;
	}
	
	public static boolean isSecureCookieName(String cookieName) {
		
		return (!SchStringUtils.isEmpty(cookieName) && 
				( SPSSessionManager.SPS_SESSION_SECURE_COOKIE_NAME.equals(cookieName)
				|| SPSSessionManager.SPS_TSTAMP_SECURE_COOKIE_NAME.equals(cookieName)
				|| SPSSessionManager.WOI_SPS_SESSION_SECURE_COOKIE_NAME.equals(cookieName)
				|| SPSSessionManager.WOI_SPS_TSTAMP_SECURE_COOKIE_NAME.equals(cookieName)));
	}

	
	public static String getSpsId(HttpServletRequest request) {
		String spsId="";
		String timeStamp="";
		javax.servlet.http.Cookie[] cookies = request.getCookies();
		 if (cookies!=null){
			 for (int i = 0; i < cookies.length; i++) {
				 javax.servlet.http.Cookie cookie = cookies[i];
				if ( SPSSessionManager.SPS_SESSION_COOKIE_NAME.equalsIgnoreCase(cookie.getName())){
					System.out.println("cookie.getValue()="+cookie.getValue());
					spsId = SPSCookieHelper.decryptValue( cookie.getValue());
					if (spsId==null){
						spsId = SPSCookieHelper.decryptValue( cookie.getValue()+"==");
					}
				}else if ( SPSSessionManager.SPS_TSTAMP_COOKIE_NAME.equalsIgnoreCase(cookie.getName())){
					timeStamp = SPSCookieHelper.decryptValue( cookie.getValue());
					if (timeStamp==null){
						timeStamp = SPSCookieHelper.decryptValue( cookie.getValue()+"==");
					}
				}

			}
			 
		 }
		 if (SPSSessionManager.isValidTimeStamp(timeStamp)){
			 return spsId;	 
		 }else{
			 return "";
		 }
	}


}
