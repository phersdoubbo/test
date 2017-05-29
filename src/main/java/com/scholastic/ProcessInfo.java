package com.scholastic;

import java.io.InputStream;

import com.scholastic.util.EmailServices;
import com.scholastic.util.ImportBean;
import com.scholastic.util.ImportProperty;
import com.scholastic.util.RestTemplate;

public class ProcessInfo {

   public synchronized static void async(ServiceBean s){
       
       if ("true".equalsIgnoreCase(s.getEnable())){
           ImportProperty trigger=new ImportProperty();
          System.out.println("avbout to process " + s.getName());
           trigger.setURL(s.getUrl());
           trigger.setAuthorizationHeader(s.getAuthorization());
           trigger.setCookiesPlain(s.getCookies());
           if (!"".equalsIgnoreCase(s.getInputFile())){
               System.out.println("s.getInputFile()="+s.getInputFile());
               trigger.setBody(  convertStreamToString(s.getInputFile()));
           }
           if (!"".equalsIgnoreCase(s.getContentType())){
  
               trigger.setContentType((  s.getContentType()));
           }
           ImportBean value=null;  
           if ("GET".equalsIgnoreCase(s.getMethod())){
                value = RestTemplate.getURL(trigger);  
           }else   if ("POST".equalsIgnoreCase(s.getMethod())){
                value = RestTemplate.postURL(trigger) ;
           }
          
           System.out.println(s.getName() +", "+value.getMd5()+", " +value.getResponseBody() );
           if (!s.getMd5().equalsIgnoreCase(value.getMd5())){
              EmailServices.sendEmail("services@scholastic.com", "Fulloa@scholastic.com", "Failing " + s.getName(),value.getResponseCode()+", "+value.getMd5()+", "+ s.getUrl()+","+ value.getResponseBody());
           }
       }
   }
   
   
   
  

   //-------method to convert into string
  private synchronized static  String convertStreamToString(String fileName) {
      
     
    //   System.out.println(q);
           try {
               InputStream in = ProcessInfo.class.getResourceAsStream(fileName );
               return new java.util.Scanner(in).useDelimiter("\\A").next();
           } catch (java.util.NoSuchElementException e) {
               return "";
           }
       }
   
   
    
}
