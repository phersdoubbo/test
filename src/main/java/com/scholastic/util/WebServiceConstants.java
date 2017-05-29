package com.scholastic.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


import org.apache.log4j.Logger;





public class WebServiceConstants {
	
	


	private static Logger logger = Logger.getLogger(WebServiceConstants.class.getName());

	
	public static final String SSO = "sso";
	public static final String TSO = "tso";
	public static final String MINI_PRINTABLES = "pmb";
	public static final String COOL = "cool";
	public static final String AS400 = "as400";
	
	

	public static String guessDomain(HttpServletRequest request){
		try{
			String serverName="";
			if (request!=null){
				serverName = request.getServerName();
				if (serverName==null){
					return ".scholastic.com";
				}
			}
		
			
			if(serverName.indexOf(".net") != -1)
				return ".scholastic.net";
			else 
				return ".scholastic.com";
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static String url(String requestString){
		String responseOut= "";
		try{
			
			requestString = java.net.URLEncoder.encode(requestString, "UTF-8");
			
			
			
		URL url = new URL(requestString);	//production
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(4000);
		conn.setReadTimeout(4000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setDoOutput(true);
		
		PrintWriter outResponse = new PrintWriter(conn.getOutputStream());

		outResponse.close();
		BufferedReader in = new BufferedReader( new
		InputStreamReader(conn.getInputStream()));
		/* server response is available by reading the in object */
		
		String exit=null;
		  

	    while (( exit = in.readLine()) != null)
	        
	    {    
	    	responseOut+=exit;
	   }
		}catch (Exception e) {
			e.printStackTrace();
		}
	    return  responseOut;
	}
	
	}
