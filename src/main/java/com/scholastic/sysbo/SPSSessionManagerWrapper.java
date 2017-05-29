//
// SPSSessionManagerWrapper created by alexpod on  Oct 5, 2004
//
 
package com.scholastic.sysbo;




public class SPSSessionManagerWrapper {

	
	
	public String getUserNameBySession(String sessionValue){
		return getSpsIdBySession(sessionValue);
	}
	public String getSpsIdBySession(String sessionValue){
		return SPSSessionManager.getSpsIdBySession(sessionValue);
	}
	public String getSpsIdBySession(String sessionValue, boolean isSecureCookie){
		return SPSSessionManager.getSpsIdBySession(sessionValue, isSecureCookie);
	}
	public String isLoggedIn(String sessionValue, String tStamp){
		return SPSSessionManager.isLoggedIn(sessionValue, tStamp)?"TRUE":"FALSE";
	}
	
	public String prepareSessionValue(String userName){
		return SPSSessionManager.prepareSessionValue(userName);
	}
	public String prepareSessionValue(String userName,boolean secure){
		return SPSSessionManager.prepareSessionValue(userName,secure);
	}
	
	public String prepareTStampValue(){
		return SPSSessionManager.prepareTStampValue();
	}
	
	public String prepareTStampValue(boolean doSecure){
		return SPSSessionManager.prepareTStampValue(doSecure);
	}
	
}
