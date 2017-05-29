package com.scholastic.util;

import java.io.File;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.sysbo.SchStringUtils;


public class MymoEnvironment {

    private static final Logger LOGGER = LoggerFactory.getLogger(MymoEnvironment.class);
    private static ResourceBundle externalProp = null;
    private static ResourceBundle privateProp = null;

    private static String externalProperties;
    protected static boolean testMode = false;
    private static String schEnv;
    private static String schKey;
    private static String temporalFolder;
    private static String appPath;
    private static boolean propertyFile;
    static {
        try {
            setSchEnv(getEnv("SCH_ENV"));
            setSchKey(getEnv("SCH_KEY"));
            

            privateProp = ResourceBundle.getBundle("privateProp");
            propertyFile="file".equalsIgnoreCase(privateProp.getString("oosv2.property.source"));
            
            if (propertyFile){
                setExternalProperties("externalProp_" + getSchEnv());
                externalProp = ResourceBundle.getBundle(getExternalProperties());
            }
          
            
            setAppPath(getProperty("oosv2.appPath"));

        } catch (Exception e) {
            LOGGER.error("static", e);
        }

    }

    protected MymoEnvironment() {
        super();
    }

    private static String getEnv(String key) throws Exception {
        String value = System.getenv(key);
        if (value == null && testMode) {
            throw new Exception("Empty key: " + key);
        } else if (value == null && !testMode) {
            value = "";
        }
        return value;
    }

    protected static String getEnvCurrentApp(String key) throws Exception {
        String value = getProperty(key);
        if ((value == null || "".equalsIgnoreCase(value)) && testMode) {
            throw new Exception("key: " + key + ", can't be empty");
        } else if (value == null && !testMode) {
            value = "";
        }

        return value;
    }

 

    public static String getProperty(String propName) {
        if (propertyFile){
            if (externalProp.containsKey(propName)) {
                return externalProp.getString(propName);
            }        
        }
       String var = System.getenv(propName);

        if (SchStringUtils.isEmpty(var)){
            if (privateProp.containsKey(propName)) {
                return privateProp.getString(propName);
            }     
        }
       
        return var;
    }

    public static String getSchEnv() {
        return schEnv;
    }

    public static void setSchEnv(String value) {
        if (value==null || "".equalsIgnoreCase(value)){
            value="test";
        }
        schEnv = value;
    }

    public static String getSchKey() {
        return schKey;
    }

    public static void setSchKey(String value) {
        schKey = value;
    }

    public static String getExternalProperties() {
        return externalProperties;
    }

    public static void setExternalProperties(String value) {
        externalProperties = value;
    }

    public static String getTemporalFolder() {
        if (temporalFolder != null) {
            return temporalFolder;
        }
        if ("/".equalsIgnoreCase(File.separator)) {
            return "/tmp/";
        } else {
            return "c:/tmp/";
        }

    }

    public static void setTemporalFolder(String temporalFolder) {
        MymoEnvironment.temporalFolder = temporalFolder;
    }

    public static String getAppPath() {
        return appPath;
    }

    public static void setAppPath(String appPath) {
        MymoEnvironment.appPath = appPath;
    }
   
}