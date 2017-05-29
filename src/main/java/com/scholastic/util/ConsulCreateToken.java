package com.scholastic.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ConsulCreateToken {

    
    public static void main2(String[] args) {
        
        String environment="dev";
        String application="oosv2";
        final String  consul;
        final String token;
       
        if ("prd".equalsIgnoreCase(environment) || ("stg".equalsIgnoreCase(environment))){
            consul="http://consul-east1.scholastic.net:8500/v1/kv/ec/";
            token="XXXXXXXX";
        }else{
            consul="http://consul-sandbox-east1.scholastic.net:8500/v1/kv/ec/";
            token ="fbea9cc8-4b63-5a31-f5a6-eda9e3a6c68f";
        }
        
        
        Consumer<String> server = (line) -> 
        {
            String locate = application+"/"+environment;
            if (line.contains("=")){

                String key = line.substring(0, line.indexOf("="));
                String value = line.substring( line.indexOf("=")+1,line.length());
                
                
                if ("local".equalsIgnoreCase(environment)){
                    System.out.println("[Environment]::SetEnvironmentVariable(\""+key+"\", \""+value+"\", \"User\")");      
                }else{
                    
                    System.out.println("curl -X  PUT "+consul+locate+"/"+key+" -d \""+value+"\"  --header \"X-Consul-Token: "+token+"\""); 
                }
            }
            
           
               
                
       //     }
        };

     
        
        
        
          process(environment, server);
    //      process(environment, server);
    }

    private static void process(String environment, Consumer<String> execute) {
        try (Stream<String> stream = Files.lines(Paths.get("C:/Users/Fernaull/workspace/neon/POSTBACK/oosv2/src/main/resources/prop_"+environment+".properties"), Charset.forName("Cp1252"))) {
                      stream.forEach(execute);
              } catch (IOException e) {
                      e.printStackTrace();
              }
    }
}
