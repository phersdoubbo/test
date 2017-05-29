package com.scholastic.reference.restmicroservice.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scholastic.sysbo.SPSCookieHelper;
import com.scholastic.util.Environment;

import io.swagger.annotations.Api;

@Path("/sch-wso")
@Api("/sch-wso")
@Service
public class HealthCheckApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckApiService.class);
   
    @Value("${snapshot.version}")
    private String snapshotVersion;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/healthCheck")
    public String healthCheck() {
        return "{\"version\":\"" + snapshotVersion + "\"}";
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/task")
    public String task(@Context HttpServletRequest request) {
           return "sch-wsotasks ec/sch_wso";
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cookies")
    public String cookies(@Context HttpServletRequest request) {
        String returnp = "SPS_SESSION_SECURE="+SPSCookieHelper.getCookieValue(request, "SPS_SESSION_SECURE", true);
        returnp+=  "SPS_TSP_SECURE="+SPSCookieHelper.getCookieValue(request, "SPS_TSP_SECURE", true);
        returnp += "_ORIG_SPS_SESSION_SECURE="+SPSCookieHelper.getCookieValue(request, "SPS_SESSION_SECURE", false);
        returnp+=  "ORIG_SPS_TSP_SECURE="+SPSCookieHelper.getCookieValue(request, "SPS_TSP_SECURE", false);
        return "oosv2tasks"+returnp;
    }
    
    
   
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/environment")
    public String environment() {
        return "{\"environment\":\"" + Environment.getSchEnv() + "\"}";
    }
    
}