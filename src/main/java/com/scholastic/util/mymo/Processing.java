package com.scholastic.util.mymo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Processing {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processing.class);

    private String baseDir;
    private String fileName2;
    private boolean debug;
    private String thread;
    public Processing(String source,String baseDir){
        Random rand = new Random(); 
        int value = rand.nextInt(1000); 
        this.fileName2= source;
        this.baseDir= baseDir;
        this.thread=String.valueOf(rand.nextInt((1000 - 1) + 1) + 1);
        
    }
    
    
    
    
    
    
    private  Date lockTime() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime();

    }


    

    

    
    public  String logGet(String fileLog) {
        try {
             Pattern pattern;
             Matcher matcher;
          //   
             String VALID_CHARS = "^[_A-Za-z0-9-\\.-]+";
             pattern = Pattern.compile(VALID_CHARS);
            matcher = pattern.matcher(fileLog);
           boolean t = matcher.matches();
            if (!t){
                return "invalid filename " +fileLog;
            }
            
            //fileLog = fileLog.replace(".", "").replace("\\", "");
            return new String(Files.readAllBytes(Paths.get(baseDir + fileLog)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Did you lost something? " + fileLog ;

    }


    public  void logAppend(List<String> data ) {
        for (Iterator iterator = data.iterator(); iterator.hasNext();) {
            String object = (String) iterator.next();
            log( baseDir,  fileName2,true, object);
            
        }
       
    }
    
    public  void info(String... data ) {
        log( baseDir,  fileName2,true, data);
    }
    public  void logAppend(String... data ) {
        
        log( baseDir,  fileName2,true, data);
        
    }
    
    public  void error(Exception e) {
        log( baseDir,  fileName2,true,exceptionToString(e));
    }
    
    public  void logAppend(Exception e) {
        log( baseDir,  fileName2,true,exceptionToString(e));
    }
    
    public  void logInsert(String... data) {
        log( baseDir,  fileName2,false, data);
    }
    
    
    private  void log( String path, String fileName2,boolean append,String... data) {
        if (!debug){
            return;
        }
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
        //    String name = getProperName();
         File file = new File(path+  fileName2);
           if (!file.exists()) {
                file.createNewFile();
            }
         fw = new FileWriter(file.getAbsoluteFile(), append);
            bw = new BufferedWriter(fw);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatDateTime = now.format(formatter);
            
            StringBuilder builder = new  StringBuilder();
            for (int i = 0; i < data.length; i++) {
                builder.append(data[i]);
            }
            LOGGER.info("Logger!!"+builder.toString());
      //      System.out.println("Sysout!!"+builder.toString());
            bw.write("t-"+thread+", "+ formatDateTime + " | "+ builder.toString() + "\n\r");
       } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

    }
    
    
    private  static String  exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString(); // stack trace as a string
    }







    public  String  getHostnameAndPort(  HttpServletRequest srvrq){
        try{
        String hostAndPort =""; //default running locally
        StringBuilder requestURL = new StringBuilder(srvrq.getRequestURL().toString());
        String requrl = requestURL.toString();
        
        int i = requrl.indexOf(":");
        int j = requrl.lastIndexOf(":");
        
        if(i==j){
                //port number not present
                //add it back for AWS
                if(requrl.indexOf(srvrq.getRequestURI())>=0){
                        String k = requrl.substring(0, requrl.indexOf(srvrq.getRequestURI()));
                        hostAndPort = k ;
                }
        }else{
                String k = requrl.substring(0, requrl.indexOf(srvrq.getRequestURI()));
                hostAndPort = k;
        }
        
       // LOGGER.info("hostAndPort: " + hostAndPort);
        return hostAndPort;
        }catch(Exception e){
            
        }
        
        return "";
    }








    public boolean isDebug() {
        return debug;
    }






    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    
    
    
}
