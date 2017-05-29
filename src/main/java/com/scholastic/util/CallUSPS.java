package com.scholastic.util;


public class CallUSPS {

	
	public String getXML(String trackNumber){
		String out ="";
		
		String urlHit = "[uspsUrl]&XML=\"<TrackRequest USERID=[uspsUser]><TrackID ID=[trackId]></TrackID></TrackRequest>\"";
		urlHit=urlHit.replaceAll("\\[uspsUrl\\]",Constant.uspsUrl );
		urlHit=urlHit.replaceAll("\\[uspsUser\\]", Constant.uspsUser.trim());
		if (trackNumber!=null){
		urlHit=urlHit.replaceAll("\\[trackId\\]",trackNumber );
		}
		String msg = WebServiceConstants.url(urlHit);
		
		out = msg;
		
		return out;
	}
	
	
	public String validate(String msg){
		String out ="";
		
		
		
		return out;
	}
	
	
	/*
	Request
	http://testing.shippingapis.com/ShippingAPITest.dll?API=TrackV2&XML=<TrackRequest USERID="978SCHOL5259"><TrackID ID="EJ958083578US"></TrackID></TrackRequest> 
	Response
	<TrackResponse><TrackInfo ID="EJ958083578US"><TrackSummary>Your item was delivered at 8:10 am on June 1 in Wilmington DE 19801.</TrackSummary><TrackDetail>May 30 11:07 am NOTICE LEFT WILMINGTON DE 19801.</TrackDetail><TrackDetail>May 30 10:08 am ARRIVAL AT UNIT WILMINGTON DE 19850.</TrackDetail><TrackDetail>May 29 9:55 am ACCEPT OR PICKUP EDGEWATER NJ 07020.</TrackDetail></TrackInfo></TrackResponse>

production
http://production.shippingapis.com/ShippingAPI.dll?API=TrackV2&XML=<TrackFieldRequest USERID="978SCHOL5259"><TrackID ID="9102901016946503097981"></TrackID></TrackFieldRequest> 
http://production.shippingapis.com/ShippingAPI.dll?API=TrackV2&XML=<TrackFieldRequest USERID="978SCHOL5259"><TrackID ID="9102901016946503098339"></TrackID></TrackFieldRequest>
9102901016946503098339        
	Request
	http://testing.shippingapis.com/ShippingAPITest.dll?API=TrackV2&XML=<TrackFieldRequest USERID="978SCHOL5259"><TrackID ID="EJ958083578US"></TrackID></TrackFieldRequest> 
	Response
	<TrackResponse><TrackInfo ID="EJ958083578US"><TrackSummary><EventTime>8:10 am</EventTime><EventDate>June 1, 2001</EventDate><Event>DELIVERED</Event><EventCity>WILMINGTON</EventCity><EventState>DE</EventState><EventZIPCode>19801</EventZIPCode><EventCountry/><FirmName></FirmName><Name></Name><AuthorizedAgent></AuthorizedAgent></TrackSummary><TrackDetail><EventTime>11:07 am</EventTime><EventDate>May 30, 2001</EventDate><Event>NOTICE LEFT</Event><EventCity>WILMINGTON</EventCity><EventState>DE</EventState><EventZIPCode>19801</EventZIPCode><EventCountry/><FirmName/><Name/><AuthorizedAgent/></TrackDetail><TrackDetail><EventTime>10:08 am</EventTime><EventDate>May 30, 2001</EventDate><Event>ARRIVAL AT UNIT</Event><EventCity>WILMINGTON</EventCity><EventState>DE</EventState><EventZIPCode>19850</EventZIPCode><EventCountry/><FirmName/><Name/><AuthorizedAgent/></TrackDetail><TrackDetail><EventTime>9:55 pm</EventTime><EventDate>May 29, 2001</EventDate><Event>ACCEPTANCE</Event><EventCity>EDGEWATER</EventCity><EventState>NJ</EventState><EventZIPCode>07020</EventZIPCode><EventCountry/><FirmName/><Name/><AuthorizedAgent/></TrackDetail></TrackInfo></TrackResponse>
*/

	
	
}
