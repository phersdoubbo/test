package com.scholastic.util;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Configuration {

    public static String NEW_SETTINGS = "New Settings";

    private static Configuration instance = null;
    private String directory;
    private Properties config = new Properties();
    private Properties privateProp = new Properties();
private boolean propertyFile;
    private Configuration() throws Exception {
        initialize("privateProp.properties",privateProp);
        propertyFile = "file".equalsIgnoreCase(privateProp.getProperty("oosv2.property.source"));
        if (propertyFile){
            initialize(Environment.getExternalProperties()+".properties",config);      
        }
      
       
    }

    public static Configuration getInstance() throws Exception {
        try {
            if (instance == null) {
                instance = new Configuration();
            }
            return instance;
        } catch (Exception e) {
            throw e;
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public String getPropertyConfig(String key) throws Exception {
        
        if (!propertyFile){
            return System.getenv(key);
        }
        
        if (key == null || key.trim().length() == 0)
            throw new Exception("INVALID_KEY_ERROR");

        try {
            key = key.trim().toLowerCase();
            return config.getProperty(key);
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }
    public String getPropertyPrivateProp(String key) throws Exception {
        if (key == null || key.trim().length() == 0)
            throw new Exception("INVALID_KEY_ERROR");

        try {
            key = key.trim().toLowerCase();
            return privateProp.getProperty(key);
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public void setProperty(String key, String value) throws Exception {
        if (key == null || key.trim().length() == 0)
            throw new Exception("INVALID_KEY_ERROR");

        if (value == null || value.trim().length() == 0)
            throw new Exception("INVALID_KEY_ERROR");

        try {
            key = key.trim().toLowerCase();
            value = value.trim();
            config.put(key, value);
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public List<String> getList(String key) throws Exception {
        if (key == null || key.trim().length() == 0)
            throw new Exception("INVALID_KEY_ERROR");

        try {
            key = key.trim().toLowerCase();
            key += ".";
            Enumeration properties = config.keys();
            String property = null;
            String number = null;
            int len = key.length();
            List<String> result = new ArrayList<String>();
            String value = null;

            // List layout: key.n
            while (properties.hasMoreElements()) {
                property = (String) properties.nextElement();
                if (property.startsWith(key)) {
                    number = property.substring(len, property.length());
                    try {
                        Integer.parseInt(number);

                        value = config.getProperty(property);
                        result.add(value);
                    } catch (Throwable e) {
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    private void initialize(String fileName,Properties prop) throws Exception {
        InputStream file = null;
        try {

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            file = classLoader.getResourceAsStream(fileName);
            if (file == null) {
                //
            }
            Properties swap = new Properties();
            swap.load(file);
            Enumeration keys = swap.keys();
            String key = null;
            String value = null;
            while (keys.hasMoreElements()) {
                key = (String) keys.nextElement();
                value = swap.getProperty(key);
                key = key.trim().toLowerCase();
                value = value.trim();
                prop.put(key, value);
            }

        } catch (Throwable e) {
            throw new Exception(e);
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (Throwable e) {
                }
            }
        }
    }

    public List<String> getKeys() throws Exception {
        try {
            Enumeration keys = config.keys();
            List<String> result = new ArrayList<String>();
            while (keys.hasMoreElements()) {
                result.add((String) keys.nextElement());
            }
            return result;
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }

    public String getDirectory() {
        return directory;
    }

}
