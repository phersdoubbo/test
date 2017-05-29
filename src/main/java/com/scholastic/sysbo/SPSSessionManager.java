//
// SPSSessionManager created by alexpod on  May 21, 2004
//

package com.scholastic.sysbo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;



//import net.sf.json.JSONObject;
//import net.sf.json.JSONSerializer;

public class SPSSessionManager {
	public static final String SPS_SESSION_COOKIE_NAME = "SPS_SESSION";
	public static final String OPENAM_TOKEN_COOKIE_NAME = "iPlanetDirectoryPro";
	public static final String IE_SUX_COOKIE_NAME = "XUS_EI";
	public static final String SPS_TSTAMP_COOKIE_NAME = "SPS_TSP";
	public static final String SPS_USER_DATA_COOKIE_NAME = "SPS_UD";
	
	public static final String WOI_SPS_SESSION_COOKIE_NAME = "WOI_SPS_SESSION";
	public static final String WOI_IE_SUX_COOKIE_NAME = "WOI_XUS_EI";
	public static final String WOI_SPS_TSTAMP_COOKIE_NAME = "WOI_SPS_TSP";
	public static final String WOI_SPS_USER_DATA_COOKIE_NAME = "WOI_SPS_UD";
	private static final String BUS_COOKIE_NAME = "SPS_BUS";
	public final static String EDUREGCONFIRMATION = "Omni_EducatorRegistration";
	public final static String NONEDUREGCONFIRMATION = "Omni_ConsumerRegistration";
	public final static String ORGBORDERID = "orgBorderId";
	
	public static final String SPS_SESSION_SECURE_COOKIE_NAME = "SPS_SESSION_SECURE";
	public static final String SPS_TSTAMP_SECURE_COOKIE_NAME = "SPS_TSP_SECURE";
	public static final String WOI_SPS_SESSION_SECURE_COOKIE_NAME = "WOI_SPS_SESSION_SECURE";
	public static final String WOI_SPS_TSTAMP_SECURE_COOKIE_NAME = "WOI_SPS_TSP_SECURE";
	
	/**
	 * @deprecated
	 */
	private final static String PRINTABLES_COOKIE_NAME = "PR_SUB";
	
	private static String JBSKey = null;

	private static final Logger myLogger = Logger.getLogger("session");

	public static long MAX_AGE = 30 * 60 * 1000;
	public static int MAX_AGE_20_YEARS = 20 * 365 * 24 * 60 * 60;
	public static  String OPENAM_HOST = "";
	static {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("SessionSettings");
			MAX_AGE = new Long(bundle.getString("MAX_AGE")).longValue() * 60 * 1000;
			
			
			JBSKey = bundle.getString("JBSKey");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			myLogger.error("Failed to load JBSKey, will use default");
			JBSKey = "81f3965245db91bd74b1288c381c4108";
			MAX_AGE = 30 * 60 * 1000;
			myLogger.error("Failed to load MAX_AGE, will use default of 30min ");
			
		}
		
		
		
		
	}
	
	// ================== No cookies ==================================

	public static String prepareSessionValue(String spsId) {
		return SPSCookieHelper.encryptValue(spsId);	
	}

	
	public static String prepareSessionValue(String spsId,boolean secure) {
			return SPSCookieHelper.encryptValue(spsId,secure);
	}

	
	public static String prepareTStampValue() {
		return SPSCookieHelper.encryptValue(""
				+ (System.currentTimeMillis() + MAX_AGE));
	}
	
	public static String prepareTStampValue(boolean doSecure) {
		return SPSCookieHelper.encryptValue(""
				+ (System.currentTimeMillis() + MAX_AGE),doSecure);
	}
	
	

	public static boolean isLoggedIn(String sessionValue, String tStamp) {
		if (SchStringUtils.isEmpty(sessionValue)) {
			if (myLogger.isDebugEnabled())
				myLogger.debug("Not logged in: invalid SessionValue");
			return false;
		}

		if (!SchStringUtils.isValidNumber(tStamp)) {
			if (myLogger.isDebugEnabled())
				myLogger.debug("Not logged in: TS is not a number!!");
			return false;
		}

		
		
		long loginTime = new Long(tStamp).longValue();
		long currTime = System.currentTimeMillis();

		if (currTime > loginTime) {
			if (myLogger.isDebugEnabled())
				myLogger
						.debug("Not logged in: " + currTime + " > " + loginTime);
			return false;
		} else {
			if (myLogger.isDebugEnabled())
				myLogger.debug("YES,  logged in: " + currTime + " < "
						+ loginTime);
		}

		return true;
	}

	public static boolean isValidTimeStamp(String tStamp) {
		if (!SchStringUtils.isValidNumber(tStamp)) {
			if (myLogger.isDebugEnabled())
				myLogger.debug("Not logged in: TS is not a number!!");
			return false;
		}

		long loginTime = new Long(tStamp).longValue();
		long currTime = System.currentTimeMillis();

		if (currTime > loginTime) {
			if (myLogger.isDebugEnabled())
				myLogger
						.debug("Not logged in: " + currTime + " > " + loginTime);
			return false;
		} else {
			if (myLogger.isDebugEnabled())
				myLogger.debug("YES,  logged in: " + currTime + " < "
						+ loginTime);
		}

		return true;
	}
	
	/**
	 * @deprecated
	 */
	public static String getUserNameBySession(String sessionValue) {
		return getSpsIdBySession(sessionValue);
	}
	public static String getSpsIdBySession(String sessionValue) {
		return getSpsIdBySession(sessionValue, false);
	}	
	public static String getSpsIdBySession(String sessionValue, boolean isSecureCookie) {
		if(isSecureCookie)
			return SPSCookieHelper.decryptValue(sessionValue, true);
		else
			return SPSCookieHelper.decryptValue(sessionValue);
	}
	
	public static boolean hasSubscriptionBySession(HttpServletRequest request, String type){
		String cookieValue= SPSCookieHelper.getCookieValue(request, SPSSessionManager.BUS_COOKIE_NAME, false);
		return cookieValue==null?type.equals(SPSCookieHelper.getCookieValue(request, SPSSessionManager.PRINTABLES_COOKIE_NAME, false)):Arrays.asList(cookieValue.split("[|]")).contains(type);
	}

	// ================== With cookies ==================================

	public static boolean loginUser(String spsId,HttpServletRequest request, HttpServletResponse response){
		return loginUser(spsId,request,response,false);
	}
	public static boolean loginUser(String spsId, HttpServletRequest request, HttpServletResponse response,boolean fromWOI) {
		myLogger.debug("SPsSessionManager loginUser fromWOI..."+fromWOI);
		if(fromWOI){
			SPSCookieHelper.placeCookie(request, response, WOI_SPS_SESSION_COOKIE_NAME, spsId, false, true);
			SPSCookieHelper.placeCookie(request, response, WOI_IE_SUX_COOKIE_NAME, spsId, true, true);	
			if(request.isSecure()) {
				SPSCookieHelper.placeCookie(request, response, WOI_SPS_SESSION_SECURE_COOKIE_NAME, spsId, false, true);
			}
		}else{
			SPSCookieHelper.placeCookie(request, response, SPS_SESSION_COOKIE_NAME, spsId, false, true);
			SPSCookieHelper.placeCookie(request, response, IE_SUX_COOKIE_NAME, spsId, true, true);	
			if( request.isSecure()) {
				SPSCookieHelper.placeCookie(request, response, SPS_SESSION_SECURE_COOKIE_NAME, spsId, false, true);
			}
		}
				
		if(fromWOI){
			return extendSession(response, request,fromWOI);	
		}else{
			return extendSession(response, request);
		}
		
	}

	
	public static void killWoiSession(HttpServletRequest request, HttpServletResponse response) {
		SPSCookieHelper.killCookie(request, response, WOI_SPS_SESSION_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, WOI_IE_SUX_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, WOI_SPS_TSTAMP_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, WOI_SPS_USER_DATA_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, BUS_COOKIE_NAME);
		
		SPSCookieHelper.killCookie(request, response, WOI_SPS_SESSION_SECURE_COOKIE_NAME, true);
		SPSCookieHelper.killCookie(request, response, WOI_SPS_TSTAMP_SECURE_COOKIE_NAME, true);
		//SPSLithiumSSO.killLithiumSession(request, response);
		//SPSPrefCtrSSO.killPrefCtrSession(request, response);		
		if(request.getSession() != null)
			request.getSession().removeAttribute("WOI_USER_IN_REQUEST");		
	}
	
	public static void killSession(HttpServletRequest request, HttpServletResponse response) {
		SPSCookieHelper.killCookie(request, response, SPS_SESSION_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, IE_SUX_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, SPS_TSTAMP_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, SPS_USER_DATA_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, BUS_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, ORGBORDERID);
		SPSCookieHelper.killCookie(request, response, OPENAM_TOKEN_COOKIE_NAME);	
		
		SPSCookieHelper.killCookie(request, response, SPS_SESSION_SECURE_COOKIE_NAME, true);
		SPSCookieHelper.killCookie(request, response, SPS_TSTAMP_SECURE_COOKIE_NAME, true);
		
		SPSCookieHelper.killCookie(request, response, SPS_SESSION_SECURE_COOKIE_NAME);
		SPSCookieHelper.killCookie(request, response, SPS_TSTAMP_SECURE_COOKIE_NAME);
		
		//SPSLithiumSSO.killLithiumSession(request, response);
		//SPSPrefCtrSSO.killPrefCtrSession(request, response);
		if (myLogger.isDebugEnabled())
				myLogger.debug("Will try to remove SPSUser from request");
		if(request.getSession() != null) {
			if (myLogger.isDebugEnabled())
				myLogger.debug("Removing SPSUser from request");
			request.getSession().removeAttribute("USER_IN_REQUEST");
		}
	}
	
	public static void killSubscriptionSession(HttpServletRequest request, HttpServletResponse response){
		SPSCookieHelper.killCookie(request, response, SPSSessionManager.BUS_COOKIE_NAME);
	}


	public static boolean isWoiLoggedIn(HttpServletRequest request) {
		String woicookie = SPSCookieHelper.getCookieValue(request,WOI_SPS_SESSION_COOKIE_NAME, true);
		myLogger.debug("#####WOIcooki###....."+woicookie);
		return isLoggedIn(woicookie, getTStampValue(request,true));
	}
	


	public static boolean isWoiLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		if (!isWoiLoggedIn(request))
			return false;
		return extendSession(response, request,true);
	}


	public static boolean isLoggedIn(HttpServletRequest request) {
		return isLoggedIn(SPSCookieHelper.getCookieValue(request, ( true)?SPS_SESSION_SECURE_COOKIE_NAME:SPS_SESSION_COOKIE_NAME, true), 
				getTStampValue(request,false));
	}
//	public static boolean isOPenAMLoggedIn(HttpServletRequest request,HttpServletResponse response) {}	

	public static boolean isSuccess(String code) {
		return ("200".equals(code) || "201".equals(code) || "202".equals(code));
	}

	public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) {
		if (!isLoggedIn(request)) {			
			
		}
		return extendSession(response, request);
	}
	
	/**
	 * @deprecated use getSpsIdBySession
	 */
	public static String getUserNameBySession(HttpServletRequest request) {
		return getSpsIdBySession(request, null);
	}
	/**
	 * @deprecated use getSpsIdBySession
	 */
	public static String getUserNameBySession(HttpServletRequest request, HttpServletResponse response) {
		return getSpsIdBySession(request, response);
	}
	
	
	public static String getWoiSpsIdBySession(HttpServletRequest request) {
		return getWoiSpsIdBySession(request, null);
	}
	public static String getWoiSpsIdBySession(HttpServletRequest request, HttpServletResponse response) {
		if (response == null) {
			if (!isWoiLoggedIn(request))
				return null;
		} else {
			if (!isWoiLoggedIn(request, response))
				return null;
		}
		return SPSCookieHelper.getCookieValue(request, WOI_SPS_SESSION_COOKIE_NAME, true);
	}
		
	public static String getSpsIdBySession(HttpServletRequest request) {
		return getSpsIdBySession(request, null);
	}
	public static String getSpsIdBySession(HttpServletRequest request, HttpServletResponse response) {
		if (response == null) {
			if (!isLoggedIn(request))
				return null;
		} else {
			if (!isLoggedIn(request, response))
				return null;
		}
		return SPSCookieHelper.getCookieValue(request, (true)?SPS_SESSION_SECURE_COOKIE_NAME:SPS_SESSION_COOKIE_NAME, true);
	}
	
	public static String getPersistedUser(HttpServletRequest request) {
		return SPSCookieHelper.getCookieValue(request, (true)?SPS_SESSION_SECURE_COOKIE_NAME:SPS_SESSION_COOKIE_NAME, true);
	}
	
	
	protected static String getTStampValue(HttpServletRequest request,boolean fromWOI) {
		String value = null;
		value = (fromWOI) ? SPSCookieHelper.getCookieValue(request,(true)?WOI_SPS_TSTAMP_SECURE_COOKIE_NAME:WOI_SPS_TSTAMP_COOKIE_NAME, true)
						  : SPSCookieHelper.getCookieValue(request, (true)?SPS_TSTAMP_SECURE_COOKIE_NAME:SPS_TSTAMP_COOKIE_NAME, true);
		if (myLogger.isDebugEnabled()) {
			myLogger.debug(SPS_TSTAMP_COOKIE_NAME + " value: " + value);
			myLogger.debug(SPS_TSTAMP_SECURE_COOKIE_NAME + " value: " + value);
			myLogger.debug(WOI_SPS_TSTAMP_COOKIE_NAME + " value: " + value);
		}
		return value;
	}

	protected static boolean extendSession(HttpServletResponse response, HttpServletRequest request) {
		/*if(response == null)
			return false;
		String value = "" + (System.currentTimeMillis() + MAX_AGE);
		SPSCookieHelper.placeCookie(request, response, SPS_TSTAMP_COOKIE_NAME, value, true, true);
		//SPSLithiumSSO.extendLithiumSession(request, response);
		//SPSPrefCtrSSO.extendPrefCtrSession(request, response);
		return true;*/
		return extendSession(response,request,false);
	}
	
	public static String extendSecureSessionValue() {
		String value = "" + (System.currentTimeMillis() + MAX_AGE);		
		return SPSCookieHelper.encryptValue(value, true);
	}
	
	public static String extendSessionValue() {
		String value = "" + (System.currentTimeMillis() + MAX_AGE);
		value =  SPSCookieHelper.encryptValue(value);
		return value ;
	}
	
	protected static boolean extendSession(HttpServletResponse response, HttpServletRequest request,boolean fromWOI) {
		if(response == null)
			return false;
		String value = "" + (System.currentTimeMillis() + MAX_AGE);
		
		if(fromWOI){
			SPSCookieHelper.placeCookie(request, response, WOI_SPS_TSTAMP_COOKIE_NAME, value, true, true);
			if(true)
				SPSCookieHelper.placeCookie(request, response, WOI_SPS_TSTAMP_SECURE_COOKIE_NAME, value, true, true);
				
		}else{
			SPSCookieHelper.placeCookie(request, response, SPS_TSTAMP_COOKIE_NAME, value, true, true);	
			if(true)
				SPSCookieHelper.placeCookie(request, response, SPS_TSTAMP_SECURE_COOKIE_NAME, value, true, true);
		}

		return true;
	}
	
	public static String generateJBSSsoToken(String logonID){
		try {
			logonID = logonID+JBSKey;
		    StringBuffer hexString = new StringBuffer();
		    byte[] digestedByte = null;
		
		    MessageDigest algorithm = MessageDigest.getInstance("MD5");
		    algorithm.reset();
		    algorithm.update(logonID.getBytes());
		    digestedByte = algorithm.digest();
		
		    for (int i = 0; i < digestedByte.length; i++) {
		            String hex = Integer.toHexString(0xFF & digestedByte[i]);
		            if (hex.length() == 1) {
		                    hexString.append("0" + hex);
		            } else {
		                    hexString.append(hex);
		            }
		    }
		    return hexString.toString();
		    
		} catch (NoSuchAlgorithmException e) {
		   
		}
		return null;
	}


	


	public static boolean placeBusCookie(HttpServletRequest request, HttpServletResponse response, String value){
		return SPSCookieHelper.placeCookie(request,response,BUS_COOKIE_NAME, value, MAX_AGE_20_YEARS, false);
	}
	
	public static boolean placeBusCookie(HttpServletRequest request, HttpServletResponse response, String value,boolean fromWOI){
		return SPSCookieHelper.placeCookie(request,response,BUS_COOKIE_NAME, value, MAX_AGE_20_YEARS, false);
	}



	public static String getCookiesAsToken( String spsID,String spsUD){		
		String token="";
		String spsTSP = SPSSessionManager.prepareTStampValue();
		long extraTime = 5 * 60 * 1000;
		long plusTime = System.currentTimeMillis() + extraTime;
		String timeStamp =SPSCookieHelper.encryptValue(new StringBuffer("").append(plusTime).toString());
		token = SPSCookieHelper.encryptValue(new StringBuffer(spsID).append("@@").append(spsTSP).append("@@").append(spsUD).append("@@").append(timeStamp).toString());
		return token;
	}

	
	
	public static Cookie[] cookieAll(String spsId) {
		String timeStamp = "" + (System.currentTimeMillis() + SPSSessionManager.MAX_AGE);
	//	String timeStamp = "1594708396695";
		Cookie spsSession = new Cookie(SPSSessionManager.SPS_SESSION_COOKIE_NAME, SPSCookieHelper.encryptValue(spsId,false));
		spsSession.setSecure(false);
		
		Cookie spsSessionSecureCookie = new Cookie(SPSSessionManager.SPS_SESSION_SECURE_COOKIE_NAME, SPSCookieHelper.encryptValue(spsId,true));
		spsSessionSecureCookie.setSecure(true);
		
		Cookie spsTSCookie = new Cookie(SPSSessionManager.SPS_TSTAMP_COOKIE_NAME, SPSCookieHelper.encryptValue(timeStamp,false));
		spsTSCookie.setSecure(false);
		
		Cookie spsTSSecureCookie = new Cookie(SPSSessionManager.SPS_TSTAMP_SECURE_COOKIE_NAME, SPSCookieHelper.encryptValue(timeStamp,true));
		spsTSSecureCookie.setSecure(true);
		
		Cookie[] cookie = new Cookie[4];
		cookie[0]=spsSessionSecureCookie;
		cookie[1]=spsSession ;
		cookie[2]=spsTSSecureCookie ;
		cookie[3]=spsTSCookie ;
		
		return cookie;
	}
	
	
}
