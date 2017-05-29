//
// SPSBasicConfig created by alexpod on  Mar 1, 2005
//
 
package com.scholastic.util;

import java.util.ResourceBundle;

import com.scholastic.sysbo.SchStringUtils;


//import com.scholastic.util.SchLogger;


public class OOSv2Configuration{
	public static final String SPS_CONFIG = "SPSConfig";
	public static final String SPS_ENV = "SPSEnv";
	public static final String SPS_OOSV2 = "oosv2";
	
	private static ResourceBundle configBundle = null;
	private static ResourceBundle envBundle = null;
	private static ResourceBundle oosv2Bundle = null;
	static{
		reload();
		
	}
	static void reload(){

		configBundle = null;
		envBundle = null;
		oosv2Bundle = null;
		
		try{
			configBundle = ResourceBundle.getBundle(SPS_CONFIG);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		try{
			envBundle = ResourceBundle.getBundle(SPS_ENV);
		} catch(Exception ex) {
		}
		try{
			oosv2Bundle = ResourceBundle.getBundle(SPS_OOSV2);
		} catch(Exception ex) {
		}
		try{
			String bb = "";
			try{
				
			}catch(Exception e){}
		} catch(Exception ex) {
		}


	}
	
	
	public static String getProperty(String propName,String property){
		if (SPS_ENV.equalsIgnoreCase(property)){
			try{
				return envBundle.getString(propName);
			}catch(Exception e1){}
		}else if (SPS_OOSV2.equalsIgnoreCase(property)){			
			try{
				return oosv2Bundle.getString(propName);
			}catch(Exception e1){}
		}else if (SPS_CONFIG.equalsIgnoreCase(property)){			
			try{
				return configBundle.getString(propName);
			}catch(Exception e1){}
		}
		return "";
	}
	
	
	
	
	
	
	
}
