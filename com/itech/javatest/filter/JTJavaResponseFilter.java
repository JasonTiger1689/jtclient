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

import com.itech.javatest.utils.*;

/**
 *
 * @author jhl
 */
//public class JTJavaResponseFilter implements Filter {
public class JTJavaResponseFilter implements Filter {

    static Logger logger = Logger.getLogger(JTJavaResponseFilter.class);

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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nEnter ANTSJavaResponseFilter doFilter");
        logger.info("Enter ANTSJavaResponseFilter doFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

//        res.setHeader("Access-Control-Allow-Origin", "*");
        if (req.getHeader("Access-Control-Request-Method") != null 
                && "OPTIONS".equals(req.getMethod())) {
            // CORS "pre-flight" request
            res.addHeader("Access-Control-Allow-Methods", "GET, POST");
            res.addHeader("Access-Control-Allow-Headers", 
                    "X-Requested-With,Origin,Content-Type, Accept");
        }

        res.addHeader("X-UA-Compatible", "IE=edge,chrome=1" );

        res.setHeader("Cache-Control","no-cache,no-store,must-revalidate,max-age=0");
        res.setHeader("Pragma","no-cache");
        res.setDateHeader("Expires", 0);

        res.addHeader("X-FRAME-OPTIONS", xfoMode );
        res.addHeader("X-XSS-PROTECTION", xsspMode );
        res.addHeader("STRICT-TRANSPORT-SECURITY", hstsMode );

//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSJavaResponseFilter doFilter X-FRAME-OPTIONS = " + xfoMode);
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSJavaResponseFilter doFilter X-XSS-PROTECTION = " + xsspMode);
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSJavaResponseFilter doFilter STRICT-TRANSPORT-SECURITY = " + hstsMode);

//        logger.info("JTJavaResponseFilter doFilter X-FRAME-OPTIONS = " + xfoMode);
//        logger.info("JTJavaResponseFilter doFilter X-XSS-PROTECTION = " + xsspMode);
//        logger.info("JTJavaResponseFilter doFilter STRICT-TRANSPORT-SECURITY = " + hstsMode);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nEnter JTJavaResponseFilter init");
//        logger.info("Enter JTJavaResponseFilter init");

        String configMode = filterConfig.getInitParameter("xfoMode");
        if ( configMode != null ) {
            xfoMode = configMode;
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSJavaResponseFilter init xfoMode is not null");
//        logger.info("JTJavaResponseFilter init xfoMode is not null");
        }

        configMode = filterConfig.getInitParameter("xsspMode");
        if ( configMode != null ) {
            xsspMode = configMode;
        } else {
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSJavaResponseFilter init xsspMode is null");
//        logger.info("JTJavaResponseFilter init xsspMode is null");
        }

        configMode = filterConfig.getInitParameter("hstsMode");
        if ( configMode != null ) {
            hstsMode = configMode;
        } else {
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nANTSJavaResponseFilter init hstsMode is null");
//        logger.info("JTJavaResponseFilter init hstsMode is null");
        }
    }

}
