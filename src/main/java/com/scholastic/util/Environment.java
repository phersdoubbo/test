package com.scholastic.util;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Environment extends MymoEnvironment {

    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);

 
    private static String[] requiredPriceListColumns;
    private static String pricebookXsdPath;

    
    private static String fileSeparator;
    private static  VelocityEngine  velocity2;
    
    
    private Environment() {
    }

    static {
        try {
            velocity();
            reload(false);
        } catch (Exception e) {
            LOGGER.error("reload catched error", e);
        }

    }
    private static void velocity() {

        velocity2 = new VelocityEngine();
        velocity2.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
        velocity2.setProperty("runtime.log.logsystem.log4j.category", "velocity");
        velocity2.setProperty("velocimacro.library", "");
        velocity2.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocity2.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocity2.init();
        
    }
    public static String reload(boolean modeTest) throws Exception {

      

        return "true";

    }

    
    public static String getFileSeparator() {
        return fileSeparator;
    }



    public static void setFileSeparator(String fileSeparator) {
        Environment.fileSeparator = fileSeparator;
    }



    public static VelocityEngine getVelocity2() {
        return velocity2;
    }
    public void setVelocity2(VelocityEngine velocity2) {
        this.velocity2 = velocity2;
    }


    
    
    

    public static String[] getRequiredPriceListColumns() {
        return requiredPriceListColumns;
    }

    public static void setRequiredPriceListColumns(String[] requiredPriceListColumns) {
        Environment.requiredPriceListColumns = requiredPriceListColumns;
    }

    public static String getPricebookXsdPath() {
        return pricebookXsdPath;
    }

    public static void setPricebookXsdPath(String pricebookXsdPath) {
        Environment.pricebookXsdPath = pricebookXsdPath;
    }

   


}
