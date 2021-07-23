package com.itech.javatest.interceptor;

import java.util.*;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itech.javatest.utils.*;

import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author jhl
 */
public class SIIRTOnlyInterceptor extends HandlerInterceptorAdapter {

    static Logger logger = Logger.getLogger(SIIRTOnlyInterceptor.class);

    String allow;
    private String notAuthPage;
    private String maintenanceMapping;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getAllow() {
        return allow;
    }

    public void setNotAuthPage(String notAuthPage) {
        this.notAuthPage = notAuthPage;
    }

    public String getNotAuthPage() {
        return notAuthPage;
    }

    public void setMaintenanceMapping(String maintenanceMapping) {
        this.maintenanceMapping = maintenanceMapping;
    }

    public String getMaintenanceMapping() {
        return maintenanceMapping;
    }

    //before the actual handler will be executed
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
           Object handler) throws Exception {

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a z");
        String todS = sdf.format(today);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSIIRTOnlyInterceptor at " + todS);
        logger.info("SIIRTOnlyInterceptor at " + todS);

        String remoteHost = request.getRemoteHost();
        logger.info("RemoteHost String in SIIRTOnlyInterceptor is = " + remoteHost);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nRemoteHost String in SIIRTOnlyInterceptor is = " + remoteHost);

        String remoteAddr = request.getRemoteAddr();
        logger.info("RemoteAddr String in SIIRTOnlyInterceptor is = " + remoteAddr);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nRemoteAddr String in SIIRTOnlyInterceptor is = " + remoteAddr);

        String requestUri = request.getRequestURI();
        logger.info("RequestUri String in SIIRTOnlyInterceptor is = " + requestUri);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nRequestUri String in SIIRTOnlyInterceptor is = " + requestUri);

        String queryStr = request.getQueryString();
        logger.info("Query String in SIIRTOnlyInterceptor is = " + queryStr);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nQuery String in SIIRTOnlyInterceptor is = " + queryStr);

        if ((queryStr != null) && (queryStr.length() > 0)) {
        if (queryStr.equalsIgnoreCase("actor=notAuth") || queryStr.equalsIgnoreCase("actor=handleError") 
                || queryStr.equalsIgnoreCase("actor=showMaintenance"))
        return true;
        } else if ((requestUri != null) && (requestUri.length() > 0)) {
        if (requestUri.startsWith("/antsadmin/antsDocs"))
        return true;
        }

//        String remoteAddr = request.getRemoteAddr();
//        boolean come = AntsAdminUtil.checkRemoteAddr(remoteAddr, "WEB");

        boolean allowIP = JTUtils.checkRemoteAddr(remoteAddr, "WEB");
        boolean maintain = JTUtils.isMaintain();
        boolean siirtOnly = JTUtils.siteRestricted();

        logger.info("remoteAddr in SIIRTOnlyInterceptor is = " + remoteAddr);
        logger.info("remoteAddr is allowed for access? " + allowIP);
        logger.info("Site is under maintenance? " + maintain);
        logger.info("Site is restricted area? " + siirtOnly);
        logger.info("Not Authorize page = " + notAuthPage);
        logger.info("Maintenance page = " + maintenanceMapping);

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nremoteAddr in SIIRTOnlyInterceptor is = " + remoteAddr);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nremoteAddr is allowed for access? " + allowIP);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSite is under maintenance? " + maintain);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSite is restricted area? " + siirtOnly);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nNot Authorize page = " + notAuthPage);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMaintenance page = " + maintenanceMapping);

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSIIRTOnlyInterceptor Action ends.");
        logger.info("SIIRTOnlyInterceptor Action ends.");

        if (maintain) {
        if (!allowIP) {
        response.sendRedirect(maintenanceMapping);
        return false;
        }
        } else if (siirtOnly) {
        if (!allowIP) {
//        response.sendRedirect("main/notAuth");
        response.sendRedirect(notAuthPage);
        return false;
        }
        }

        return true;
    }
}
