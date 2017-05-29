package com.scholastic.util;

import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class Constant  {
	
	

	static final String ORDH_SUB_DATE = "dateSubmittted";
	static final String ORDH_EXP_DATE = "expected_date";
	static final String ORDH_ORD_NUM = "order_id";
	static final String ORDH_ORD_ID = "orderId";
	static final String ORDH_STORE_HDR = "store_label";
	static final String ORDH_ORD_STATUS = "status";
	static final String ORDH_ORD_TRACK_HREF = "tracking_href";
	static final String ORDH_TOTAL = "total";
	static boolean IS_ASCENDING;
	static SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
	static SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
	
	public static final String StatusCancelled= "Cancelled";
	public static final String StatusOrderSubmitted= "Order Submitted";
	public static final String StatusCoolProcessing= "Processing";
	public static final String StatusSSOProcessing= "Processing";
	public static final String StatusShipped= "Shipped";
	public static final String StatusShipping= "Shipping";
	public static final String StatusDelivered= "Delivered";
	public static final String StatusShippingToSchool= "Shipping to school";
	public static final String ReadingClub= "Reading Club for Teachers";
	public static final String ReadingClubParents= "Reading Club for Parents";
	
	public static final String DisplayNo= "N";
	public static final String DisplayYes= "Y";
	
	public static  String upsLicense;
	public static  String upsUserID;
	public static  String upsUserPassword;	
	public static  String upsUrl;
	public static  String upsCustomerContext;
	
	//public static  String wsdlDetailAS400;
	//public static  String wsdlDetailAS400PCOOL;
	
	//public static  String wsdlTrackAS400;
	
	
	public static  String uspsUrl;
	public static  String uspsUser;

	
	//public static  Integer connectionTimeOut;
	public static  Integer timeOut;
	public static  Integer UPSConnectionTimeOut;
	public static  Integer UPSTimeOut;
	

	static{
		
		try{
	
		 ResourceBundle privateProp =  ResourceBundle.getBundle("privateProp");

		 upsLicense = getProperty(privateProp,"oosv2.upsLicense");
		 upsUserID = getProperty(privateProp,"oosv2.upsUserID");
		 upsUserPassword = getProperty(privateProp,"oosv2.upsUserPassword");
		 upsCustomerContext= getProperty(privateProp,"oosv2.upsCustomerContext");
		 upsUrl= getProperty(privateProp,"oosv2.upsUrl");
		uspsUrl= getProperty(privateProp,"oosv2.uspsUrl");
		uspsUser= getProperty(privateProp,"oosv2.uspsUser");		
		String tmp = getProperty(privateProp,"oosv2.timeOut");
		if (tmp==null){tmp="25000";}
		timeOut= Integer.valueOf(tmp.trim());
		UPSConnectionTimeOut= Integer.valueOf(getProperty(privateProp,"oosv2.UPSConnectionTimeOut"));
		UPSTimeOut= Integer.valueOf(getProperty(privateProp,"oosv2.UPSTimeOut").trim());
		}catch(Exception e){e.printStackTrace();}
		 
	}


	private static String getProperty(ResourceBundle myResources,String key) {
		try {
			return myResources.getString(key);	
		} catch (Exception e) {
			return "";
		}
		
		
	}
	
	
	

}