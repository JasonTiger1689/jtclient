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
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.mail.MailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import com.itech.javatest.utils.*;

//import com.itech.javatest.nistuser.model.NISTUser;

/**
 *
 * @author jhl
 */
@ControllerAdvice
@EnableWebMvc   // Optionally setup Spring MVC defaults if you aren't doing so elsewhere
public class ModelAttributeAdvice {

    private String xfoMode = "DENY";
    private String xsspMode = "1; mode=block";
    private String hstsMode = "max-age=8640000; includeSubDomains";

    @Autowired
    @Qualifier("mailSender")
    MailSender mailSender;

    @Autowired
    @Qualifier("exMailTemplate")
    SimpleMailMessage exMailTemplate;

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request, 
            HttpServletResponse response, HttpSession session) {
//    public ModelAndView addAttributes(Model model, HttpServletRequest request, HttpSession session) {

//        ModelAndView mv = new ModelAndView("main/exception");
//        mv.addObject("name", e.getClass().getSimpleName());
//        model.addAttribute("siteTitle", "NIST - ANTS");

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        model.addAttribute("today", sdf.format(today));

//        NISTUser nu = (NISTUser) session.getAttribute("user");

//        return mv;
    }

/*
*/

}
