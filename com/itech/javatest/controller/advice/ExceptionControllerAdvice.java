/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.controller.advice;

import java.io.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.mail.MailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import com.itech.javatest.utils.*;

//import com.itech.javatest.jtuser.model.JTUser;

/**
 *
 * @author jhl
 */
@ControllerAdvice
@EnableWebMvc   // Optionally setup Spring MVC defaults if you aren't doing so elsewhere
public class ExceptionControllerAdvice {

    @Autowired
    @Qualifier("mailSender")
    MailSender mailSender;

    @Autowired
    @Qualifier("exMailTemplate")
    SimpleMailMessage exMailTemplate;

//    @Value( "${site.appHost}" )
    @Value( "#{siteProps['site.appHost']}" )
    String appHost;
//    @Value( "${site.siteApp}" )
    @Value( "#{siteProps['site.siteApp']}" )
    String siteApp;

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpServletRequest request, HttpSession session, Exception e) {

        ModelAndView mv = new ModelAndView("main/exception");
//        mv.addObject("name", e.getClass().getSimpleName());
//        mv.addObject("message", e.getMessage());

//        NISTUser nu = (NISTUser) session.getAttribute("user");
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(this.exMailTemplate);

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

//        sb.append("Login User: " + nu.getLastFirst() + "(" + nu.getUsername() + ")\n\n");

        sb.append("Remote Host: " + request.getRemoteHost() + "\n");
        sb.append("Remote Address: " + request.getRemoteAddr() + "\n");
        sb.append("Request URI: " + request.getRequestURI() + "\n");
        sb.append("Request URL: " + request.getRequestURL().toString() + "\n\n");

        sb.append("Application Host: " + appHost + "\n\n");
        sb.append("ANTS Application: " + siteApp + "\n\n");

        sb.append("Exception Name: " + e.getClass().getSimpleName() + "\n\n");
        sb.append("Message: " + e.getMessage() + "\n\n");

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        sb.append("Exception Stack Trace: " + "\n");
        sb.append(sw.toString());

        msg.setText(sb.toString());

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + msg.getSubject());
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + sb.toString());

/*
*/
        try{
//        if (request.getRemoteHost().indexOf("129.6.26") >= 0) {
//            msg.setTo(new String [] { "Lee, Jason <Jason.Lee@nist.gov>", 
//                "Antonishek, John K. <john.antonishek@nist.gov>" });
//            this.mailSender.send(msg);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent to Jason and John due to scan discovery from subnet 129.6.26");
//        } else {
//            this.mailSender.send(msg);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent");
//        }
            boolean sent = false;
            sent = JTUtils.emailAction(request.getRemoteHost(), msg);
//            ae.setIsSent(sent);
        }
        catch(Exception ex) {
            // simply log it and go on...
//            System.err.println(ex.getMessage());            
            System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nThe Email has not been sent");
            ex.printStackTrace();            
        }

        return mv;
    }

/*
*/

}
