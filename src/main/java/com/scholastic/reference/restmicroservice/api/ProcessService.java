package com.scholastic.reference.restmicroservice.api;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.scholastic.ProcessInfo;
import com.scholastic.ServiceBean;
import com.scholastic.util.EmailServices;
import com.scholastic.util.Environment;
import com.scholastic.util.ImportBean;
import com.scholastic.util.ImportProperty;
import com.scholastic.util.RestTemplate;
import com.scholastic.util.mymo.Processing;

import io.swagger.annotations.Api;

@Path("/sch-wso")
@Api("/sch-wso")
@Service
public class ProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessService.class);
    
    
    @Autowired
    private HttpServletRequest srvrq;
    
    
    @GET
    @Path("/debug")
    @Produces(MediaType.APPLICATION_JSON)
    public String  debug(@Context HttpServletRequest request)  {
        Processing p = new Processing("OOSv2",Environment.getTemporalFolder());
        //p.setFileName2("OOSv2.log");
        return p.logGet("OOSv2");
    }
    
    @GET
    @Path("/check")
    @Produces(MediaType.TEXT_HTML)
    public String tracking(@Context HttpServletRequest request)  {
        
        List<ServiceBean> ss = new ArrayList<ServiceBean>();
       
      
        Gson g = new Gson();
        
        
        System.out.println(g.toJson(ss));
        
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("json/endpoint.json");
        String t = new java.util.Scanner(is).useDelimiter("\\A").next();
        
        ServiceBean[] sb = g.fromJson(t, ServiceBean[].class);
        for (int i = 0; i < sb.length; i++) {
            
            ServiceBean s = sb[i];
            System.out.println("Processing " + s.getUrl());
            Runnable task2 = () ->  ProcessInfo.async(s);
            new Thread(task2).start();
        }
        EmailServices.sendEmail("services@scholastic.com", "Fulloa@scholastic.com", "Done testing all services","empty body");
        return g.toJson(ss);
    }
  
   

    
    public String getPort(){
        return "";
    }
 
    
    public void debugRequest() {
        //needed to troubles shoot building the correct url
        LOGGER.info("RequestURL: " + srvrq.getRequestURL());
        LOGGER.info("RemotePort: " + srvrq.getRemotePort());
        LOGGER.info("ContextPath: " + srvrq.getContextPath());
        LOGGER.info("LocalName: " + srvrq.getLocalName());
        LOGGER.info("PathInfo: " + srvrq.getPathInfo());
        LOGGER.info("RemoteHost: " + srvrq.getRemoteHost());
        LOGGER.info("RequestURI: " + srvrq.getRequestURI());
        LOGGER.info("RequestURL(): " + srvrq.getRequestURL());
    }
    
    
    
    public String initXML(String templateName ,VelocityContext context) {
        if (context == null){
            context = new VelocityContext();
        }

        Template template = Environment.getVelocity2().getTemplate(templateName, "UTF-8");
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    
    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
           if ( filename.trim().startsWith("filename")) {
              String[] name = filename.split("=");
              String exactFileName = name[1].trim().replaceAll("\"", "");
              return exactFileName;
           }else   if (filename.trim().contains("ftype")) {
              // System.out.println(filename);
               return "ftype";
           }
        }
        return "unknown";
     }
    
    
    


    
    
    
    
}
