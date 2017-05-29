package com.scholastic.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.Cookie;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.sysbo.SchStringUtils;

public class RestTemplate {

    private static HttpClient httpclient = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplate.class);
    private static final int TIME_OUT = 30000;
    private static final int MAX_HOST = 50;
    private static final int HTTP_OK = 200;
    private static final int HTTP_ERROR = 600;
    private static final int HTTP_ERROR_400 = 400;

    private static final String COOKIE = "Cookie";
    private static final String AUTH = "Authorization";

    private static boolean allTrusted = false;
    static {

        HttpState initialState = new HttpState();
        MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager();
        //manager.setMaxConnectionsPerHost(MAX_HOST);
      //  manager.setMaxTotalConnections(MAX_HOST);

        httpclient = new HttpClient(manager);
     //   httpclient.setConnectionTimeout(TIME_OUT);
     //  httpclient.setTimeout(TIME_OUT);
        
        httpclient.setState(initialState);
    }

    private RestTemplate() {
    }

    public static String importPage(String url) {

        GetMethod httpget = null;
        try {

            httpget = new GetMethod(url);

            int result = httpclient.executeMethod(httpget);
            String resBody = httpget.getResponseBodyAsString();

            if (result == HTTP_OK) {
                return resBody;
            } else {

                return "";
            }

        } catch (Exception e) {
            LOGGER.error("importPage", e);

            return "";
        } finally {
            if (httpget != null) {
                httpget.releaseConnection();
            }
        }
    }

    private static void trustAllHosts() {
        System.setProperty("jsse.enableSNIExtension", "false");

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });

        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                    throws java.security.cert.CertificateException {
                // Do Nothing

            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
                    throws java.security.cert.CertificateException {
                // Do nothing

            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            LOGGER.error("trustAllHosts", e);
        }
    }

    public static ImportBean getURL(ImportProperty config) {
        ImportBean ret = new ImportBean();
        URLConnection urlConn = null;
        long meassure = 0;
        try {

            URL urlHit = new URL(config.getURL());
            urlConn = urlHit.openConnection();
            urlConn.setConnectTimeout(config.getConnectionTimeOut());
            urlConn.setReadTimeout(config.getTimeout());
            urlConn.setAllowUserInteraction(false);

            String myCookie = characterSeparated(config.getCookie(), ";");
             myCookie = config.getCookiesPlain()+myCookie;
            urlConn.setRequestProperty(COOKIE, myCookie);
            if (!SchStringUtils.isEmpty(config.getAuthorizationHeader())) {
                urlConn.setRequestProperty(AUTH, config.getAuthorizationHeader());
            }
            
            
            
            
            if (!allTrusted && config.isTrustAllHosts()) {
                trustAllHosts();
                allTrusted = true;

            }

            urlConn.setDoOutput(true);

            meassure = System.currentTimeMillis();
            String response = readStream((HttpURLConnection) urlConn);

           
            ret.setResponseBody(response.toString());
            ret.setResponseCode(((HttpURLConnection) urlConn).getResponseCode());
          
        } catch (SocketTimeoutException eo) {
            ret.setResponseBody("timeout");
            ret.setResponseCode(503);
        } catch (Exception e) {

            ret.setResponseBody(e.getMessage());
            ret.setResponseCode(HTTP_ERROR);
          
        } finally {
            if (urlConn != null) {
                ((HttpURLConnection) urlConn).disconnect();
            }

        }       
        ret.setExecutionTime(System.currentTimeMillis() - meassure);
        ret.setMd5(MD5.string(ret.getResponseBody()));
        return ret;

    }

    public static String characterSeparated(Cookie[] cookies, String character) {
        StringBuilder buffer = new StringBuilder();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie!=null){
                    buffer.append(cookie.getName() + "=" + cookie.getValue() + character);    
                }
                
            }

            if (buffer.length() > 0) {
                return buffer.toString();
            } else {
                return "";
            }

        } else {
            return "";
        }

    }

    public static ImportBean postURL(ImportProperty config) {
        ImportBean bean2 = new ImportBean();

        if (config.isTrustAllHosts()) {
            trustAllHosts();
        }
        long lStartTime = System.currentTimeMillis();
        try {
            URL obj = new URL(config.getURL());
            HttpURLConnection con = null;
            con = (HttpURLConnection) obj.openConnection();
            String userAgent = "";
            String acceptLanguage = "en-US,en;q=0.5";

            con.setConnectTimeout(config.getConnectionTimeOut());
            con.setReadTimeout(config.getTimeout());

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", userAgent);
            con.setRequestProperty("Accept-Language", acceptLanguage);
            String myCookie = cookieToString(config.getCookie(), ";");
            con.setRequestProperty(COOKIE, myCookie);
            if (config.getContentType() != null) {
                con.setRequestProperty("Content-Type", config.getContentType());
            }
            con.setRequestProperty("SOAPAction",";");
            

            if (!SchStringUtils.isEmpty(config.getAuthorizationHeader())) {
                con.setRequestProperty(AUTH, config.getAuthorizationHeader());
            }
            con.setDoOutput(true);
            lStartTime = System.currentTimeMillis();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            String body = config.getBody();
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            bean2.setResponseBody(readStream((HttpURLConnection) con));
            bean2.setResponseCode(con.getResponseCode());
      //      bean2.setExecutionTime(System.currentTimeMillis() - lStartTime);

        } catch (Exception e) {
    //    bean2.setExecutionTime(System.currentTimeMillis() - lStartTime);

            LOGGER.error("postURL", e);
            bean2.setResponseCode(HTTP_ERROR);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String trackstrace = sw.toString();
            bean2.setResponseBody("mymo-core url error=" + config.getURL() + trackstrace);
        }

        bean2.setExecutionTime(System.currentTimeMillis() - lStartTime);
        bean2.setMd5(MD5.string(bean2.getResponseBody()));
        return bean2;
    }

    public static ImportBean putURL(ImportProperty config) {
        ImportBean ret = new ImportBean();

        if (config.isTrustAllHosts()) {
            trustAllHosts();
        }
        try {
            URL obj = new URL(config.getURL());
            HttpURLConnection con = null;
            con = (HttpURLConnection) obj.openConnection();
            String userAgent = "";
            String acceptLanguage = "en-US,en;q=0.5";

            con.setConnectTimeout(config.getConnectionTimeOut());
            con.setReadTimeout(config.getTimeout());

            con.setRequestMethod("PUT");
            con.setRequestProperty("User-Agent", userAgent);
            con.setRequestProperty("Accept-Language", acceptLanguage);
            String myCookie = cookieToString(config.getCookie(), ";");
            con.setRequestProperty(COOKIE, myCookie);
            if (config.getContentType() != null) {
                con.setRequestProperty("Content-Type", config.getContentType());
            }

            if (!SchStringUtils.isEmpty(config.getAuthorizationHeader())) {
                con.setRequestProperty(AUTH, config.getAuthorizationHeader());
            }
            con.setDoOutput(true);
            long meassure = System.currentTimeMillis();
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            String body = config.getBody();
            wr.writeBytes(body);
            wr.flush();
            wr.close();
            ret.setExecutionTime(System.currentTimeMillis() - meassure);
            ret.setResponseBody(readStream(con));
            ret.setResponseCode(con.getResponseCode());
        } catch (Exception e) {

            ret.setResponseCode(-1);
            ret.setExecutionTime(-1);
            ret.setResponseBody(e.getMessage());
            LOGGER.error("putURL", e);
        }

        return ret;
    }

    private static String readStream(HttpURLConnection con) throws IOException {

        InputStream is;
        if (con.getResponseCode() >= HTTP_ERROR_400) {
            is = con.getErrorStream();         
        } else {
            is = con.getInputStream();
        }
        if (is==null){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            in.close();
        }

        return builder.toString();
    }

    public static String cookieToString(Cookie[] cookies, String separator) {
        StringBuilder buffer = new StringBuilder();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie c = cookies[i];
                if (c!=null){
                    buffer.append(c.getName() + "=" + c.getValue() + separator);    
                }
                
            }
        }

        if (buffer.length() > 0) {
            return buffer.toString().substring(0, buffer.lastIndexOf(separator));
        } else {
            return "";
        }
    }

    
    
}






//----

 class MD5 {

    
    private static final int BUFFER = 1024;
    private static final int APPEND = 16;

    private MD5() {
    }

    public static String string(String value) {

        StringBuilder hexString = new StringBuilder();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());

            byte[] byteData = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, APPEND).substring(1));
            }

            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexString.toString();
    }

    public static String file(String filePath) {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(filePath);
            byte[] dataBytes = new byte[BUFFER];

            int nread = 0;
            while ((nread = fis.read(dataBytes)) != -1) {
                md.update(dataBytes, 0, nread);
            }
            byte[] mdbytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, APPEND).substring(1));
            }
            for (int i = 0; i < mdbytes.length; i++) {
                String hex = Integer.toHexString(0xff & mdbytes[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hexString.toString();
    }

}
