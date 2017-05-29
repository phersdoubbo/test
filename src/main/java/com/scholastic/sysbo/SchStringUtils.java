package com.scholastic.sysbo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchStringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchStringUtils.class);

    private SchStringUtils() {
    }

    public static boolean isValidNumber(String number, int maxLenght){
        if(number.length() > maxLenght)
                return false;
        return isValidNumber(number);
}

    public static boolean isValidNumber(String number){
            try{
                    Long.valueOf(number);
            }catch(Exception e){
                    return false;
            }
            return true;
    }
    
    public static boolean isEmpty(String string) {
        return !(string != null && string.trim().length() > 0);
    }

    public static boolean isValidDouble(String string) {
        try {
            Double.valueOf(string);
        } catch (Exception e) {
            LOGGER.error("isValidDouble", e);
            return false;
        }
        return true;
    }

    public static int toInt(String number) {
        int result = 0;
        try {
            result = (new Integer(number)).intValue();
        } catch (Exception e) {
            LOGGER.error("toInt", e);
        }

        return result;
    }

    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            LOGGER.error("toInt", e);
        }

        return false;
    }

    public static String fillPrefix(int data, String prefix, int size) {
        return fillPrefix(String.valueOf(data), prefix, size);
    }

    public static String fillPrefix(String dataValue, String prefix, int size) {
        if (dataValue.length() >= size) {
            return dataValue;
        }
        String data = dataValue;
        for (int i = 0; i < size - dataValue.length(); i++) {
            data = prefix + data;
        }
        return data;
    }

    public static int getValidNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            LOGGER.error("getValidNumber", e);
            return -1;
        }
    }

    public static boolean isEmpty(List list) {
        if (list != null && !list.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String[] array) {
        if (array != null && array.length > 0) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(Map map) {
        if (map != null && map.size() > 0) {
            return false;
        }
        return true;
    }

    public static int getIntValue(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            LOGGER.error("getIntValue", e);
            return -1;
        }
    }

    public static String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

}
