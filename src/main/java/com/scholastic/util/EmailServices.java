package com.scholastic.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scholastic.util.Environment;

public class EmailServices {

    private static String source;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServices.class);

    static {
        try {
            source = " " + (InetAddress.getLocalHost()).toString();
        } catch (Exception e) {
            LOGGER.error("loading source", e);
        }
    }

    private EmailServices() {
    }



    public static boolean sendEmail(String from, String to2, String subject, String body) {
        String subject2 = subject ;//+ source;

        try {
            String[] toList = to2.split(",");
            Properties properties = System.getProperties();

            properties.setProperty("mail.smtp.host", "nj2bizmail.scholastic.net");//ses-relay.scholastic.tech
            Session session = Session.getDefaultInstance(properties);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            for (int i = 0; i < toList.length; i++) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toList[i]));
            }
            message.setSubject(subject2);
           // message.setSubject("test1", "test2");
            message.setText(body);
            Transport.send(message);
            return true;
        } catch (Exception mex) {
            LOGGER.error("sendEmail", mex);
            return false;
        }
    }

    public static String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
