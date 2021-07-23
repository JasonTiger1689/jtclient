/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.filter;

import java.io.IOException;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import com.itech.javatest.utils.*;

/**
 *
 * @author jhl
 */
//public class ANTSJavaResponseFilter implements Filter {
public class JTResponseFilter extends OncePerRequestFilter {

    static Logger logger = Logger.getLogger(JTResponseFilter.class);

    private String xfoMode = "DENY";
    private String xsspMode = "1; mode=block";
    private String hstsMode = "max-age=8640000; includeSubDomains";

    /**
     * Add X-FRAME-OPTIONS response header to tell IE8 (and any other browsers who 
     * decide to implement) not to display this content in a frame. For details, please 
     * refer to http://blogs.msdn.com/sdl/archive/2009/02/05/clickjacking-defense-in-ie8.aspx.
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain chain) throws IOException, ServletException 
    {
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nEnter ANTSResponseFilter doFilterInternal");
        logger.info("Enter ANTSResponseFilter doFilterInternal");

//        response.setHeader("Access-Control-Allow-Origin", "*");
        if (request.getHeader("Access-Control-Request-Method") != null 
                && "OPTIONS".equals(request.getMethod())) {
            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Methods", "GET, POST");
            response.addHeader("Access-Control-Allow-Headers", 
                    "X-Requested-With,Origin,Content-Type, Accept");
        }

        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("X-UA-Compatible", "IE=edge,chrome=1" );

        res.setHeader("Cache-Control","no-cache,no-store,must-revalidate,max-age=0");
        res.setHeader("Pragma","no-cache");
        res.setDateHeader("Expires", 0);

        res.setHeader("X-FRAME-OPTIONS", xfoMode );
        res.setHeader("X-XSS-PROTECTION", xsspMode );
        res.setHeader("STRICT-TRANSPORT-SECURITY", hstsMode );

//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSResponseFilter doFilter X-FRAME-OPTIONS = " + xfoMode);
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSResponseFilter doFilter X-XSS-PROTECTION = " + xsspMode);
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSResponseFilter doFilter STRICT-TRANSPORT-SECURITY = " + hstsMode);

//        logger.info("JTResponseFilter doFilter X-FRAME-OPTIONS = " + xfoMode);
//        logger.info("JTResponseFilter doFilter X-XSS-PROTECTION = " + xsspMode);
//        logger.info("JTResponseFilter doFilter STRICT-TRANSPORT-SECURITY = " + hstsMode);

        chain.doFilter(request, response);
    }

}
