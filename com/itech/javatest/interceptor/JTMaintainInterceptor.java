/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.interceptor;

import java.util.*;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import gov.nist.antsclient.utils.*;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author jhl
 */
public class JTMaintainInterceptor extends HandlerInterceptorAdapter {

    static Logger logger = Logger.getLogger(JTMaintainInterceptor.class);

    private int maintenanceStartTime;
    private int maintenanceEndTime;
    private String maintenanceDate;
    private String maintenanceMapping;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    public void setMaintenanceDate(String maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public void setMaintenanceMapping(String maintenanceMapping) {
        this.maintenanceMapping = maintenanceMapping;
    }

    public void setMaintenanceStartTime(int maintenanceStartTime) {
        this.maintenanceStartTime = maintenanceStartTime;
    }

    public void setMaintenanceEndTime(int maintenanceEndTime) {
        this.maintenanceEndTime = maintenanceEndTime;
    }

    //before the actual handler will be executed
    public boolean preHandle(HttpServletRequest request, 
            HttpServletResponse response, Object handler) throws Exception {

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(cal.HOUR_OF_DAY);

        String date = dateFormat.format(new Date()); 

        if (date.equals(maintenanceDate)) {
        if (hour >= maintenanceStartTime && hour <= maintenanceEndTime) {
//maintenance time, send to maintenance page
            response.sendRedirect(maintenanceMapping);
            return false;
        } else {
            return true;
        }
        } else {
            return true;
        }
    }

}
