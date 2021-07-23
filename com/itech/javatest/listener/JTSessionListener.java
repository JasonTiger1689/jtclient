/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.listener;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.itech.javatest.utils.*;

/**
 *
 * @author jhl
 */
@WebListener
public class JTSessionListener implements HttpSessionListener {
//public class JTSessionListener implements HttpSessionListener {
    private static int sessionCount = 0;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        synchronized (this) {
            sessionCount++;
        }
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSession Created: " + event.getSession().getId());
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nTotal Sessions: " + sessionCount);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        synchronized (this) {
            sessionCount--;
        }
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSession Destroyed: " + event.getSession().getId());
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nTotal Sessions: " + sessionCount);
        JTUtils.expireSessionInfo(event.getSession().getId());
//        AntsClientUtil.removeSessionInfo(event.getSession().getId());
//        event.getSession().
    }

}
