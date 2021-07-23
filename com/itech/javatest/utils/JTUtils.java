/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.utils;

import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import java.util.regex.Pattern;

import javax.mail.SendFailedException;

import org.springframework.util.StringUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.springframework.mail.MailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;

import com.itech.javatest.codes.model.Codes;
import com.itech.javatest.codes.service.CodesService;

//import com.itech.javatest.seqnum.model.Seqnum;
//import com.itech.javatest.seqnum.service.SeqnumService;

//import com.itech.javatest.taskemail.model.EventEmail;
//import com.itech.javatest.taskemail.service.EventEmailService;

//import com.itech.javatest.allowips.model.AllowIPs;
//import com.itech.javatest.allowips.service.AllowIPsService;

//import com.itech.javatest.admin.model.*;
//import com.itech.javatest.admin.service.AdminService;

//import com.itech.javatest.jtuser.model.*;
//import com.itech.javatest.jtuser.service.JTUserService;

//import com.itech.javatest.dbutil.model.*;
//import com.itech.javatest.dbutil.service.AdminDBService;

/**
 *
 * @author jhl
 */
@Component
@Configurable(autowire = Autowire.BY_TYPE)
public class JTUtils {

    protected static Logger logger = Logger.getLogger(JTUtils.class);

    @Autowired
    @Qualifier("mailSender")
    MailSender mailSender;

    @Autowired
    @Qualifier("exMailTemplate")
    SimpleMailMessage exMailTemplate;

    @Autowired
    @Qualifier("intMailTemplate")
    SimpleMailMessage intMailTemplate;

    @Autowired
    @Qualifier("jtMailTemplate")
    SimpleMailMessage jtMailTemplate;

//    @Value( "${site.appHost}" )
    @Value( "#{siteProps['site.appHost']}" )
    String appHost;

//    @Value( "${site.siteApp}" )
    @Value( "#{siteProps['site.siteApp']}" )
    String siteApp;

//    @Value( "${site.siteDom}" )
    @Value( "#{siteProps['site.siteDom']}" )
    String siteDom;

//    @Value( "${host.allow}" )
    @Value( "#{siteProps['host.allow']}" )
    String allow;

//    @Value( "${addr.timeout}" )
    @Value( "#{siteProps['addr.timeout']}" )
    String addrTimeout;

//    @Value( "${email.from}" )
    @Value( "#{siteProps['email.from']}" )
    String from;
//    @Value( "${email.admin}" )
    @Value( "#{siteProps['email.admin']}" )
    String admin;
//    @Value( "${email.cc}" )
    @Value( "#{siteProps['email.cc']}" )
    String cc;
//    @Value( "${email.to}" )
    @Value( "#{siteProps['email.to']}" )
    String to;

//    @Value( "${email.exSubject}" )
    @Value( "#{siteProps['email.exSubject']}" )
    String exSubject;
//    @Value( "${email.intSubject}" )
    @Value( "#{siteProps['email.intSubject']}" )
    String intSubject;
//    @Value( "${email.loginSubject}" )
    @Value( "#{siteProps['email.loginSubject']}" )
    String loginSubject;

    private static final Pattern CLEANMAC = Pattern.compile("[.:-]");

    public static String EmailSep = ", ";

    public static String EmailSep2 = "; ";

    public static String ContactEmail = 
           "SELECT PRIMARY_EMAIL_ADDRESS FROM nist.personinfo WHERE PEOPLE_ID = ? ";

    public static String ContactEmail_WHERE = 
           "SELECT PRIMARY_EMAIL_ADDRESS FROM nist.personinfo ";

    public static String ContactPhone = 
           "SELECT PHONE_NUMBER FROM nist.personinfo WHERE PEOPLE_ID = ? ";

    public static String ContactPhone_WHERE = 
           "SELECT PHONE_NUMBER FROM nist.personinfo ";

    public static String LASTFIRST = 
           "SELECT LASTFIRST FROM nist.personinfo WHERE PEOPLE_ID = ? ";

    public static String FIRSTLAST = 
           "SELECT FIRSTLAST FROM nist.personinfo WHERE PEOPLE_ID = ? ";

    public static final Pattern SQLDATE = Pattern.compile("[-]");

    public static String cleanMAC (String macAddress) {

        return (macAddress == null) ? macAddress : CLEANMAC.matcher(macAddress).replaceAll("");
    }

    public static String formatMAC (String macAddress, char sep) {

        if (macAddress != null) {
        if (!CLEANMAC.matcher(macAddress).matches()) {
            int i = macAddress.trim().length();
            if (i == 12) {
                StringBuilder sb = new StringBuilder(macAddress.trim());
                i -= 2;
                for (; i > 0; ) {
//                    sb.insert(i, '-');
                    sb.insert(i, sep);
                    i -= 2;
                }
                macAddress = sb.toString();
            }
        }
        }
        return macAddress;
    }

    public static String processMAC (String macAddress) {

        if (macAddress != null) {
            int i = macAddress.trim().length();
            if (i == 12) {
                StringBuilder sb = new StringBuilder(macAddress);
                i -= 2;
                for (; i > 0; ) {
                    sb.insert(i, '-');
                    i -= 2;
                }
                macAddress = sb.toString();
            }
        }
        return macAddress;
    }

    public static String processDT (String dt) {

        if (dt != null) {
            int i = dt.indexOf('.');
            if (i >= 0) {
                dt = dt.substring(0, i);
            }
        }
        return dt;
    }

    public static String nullString (String s) {

        return (((s != null) && (s.length() > 0)) ? s : "NULL");
    }

    public static String emptyString (String s) {

        return (((s != null) && (s.length() > 0)) ? s : "");
    }

    public static String formatDate (String dateStr, String sep) {

        return (dateStr == null) ? dateStr : SQLDATE.matcher(dateStr).replaceAll(sep);
    }

    public static boolean verifySession (HttpSession session) {

        if (session != null) {
        Object obj = session.getAttribute("login");
        if  ((obj != null) && ((Boolean)obj)) {
            return true;
//            return resetSessionAccessTime(session);
        } else
            return false;
        }
        else
            return false;
    }

    public static boolean resetSessionAccessTime (HttpSession session) {

        JTUtils acu = new JTUtils();
/*
        AdminUser nu = null;

        AdminSessionInfo sesInfo = null;
        HashMap<String, AdminSessionInfo> sesMap = null;
        if (session != null) {
        nu = (AdminUser) session.getAttribute("user");
        ServletContext sc = session.getServletContext();
        sesMap = (HashMap) sc.getAttribute("sesMap");
        if (sesMap == null) {
        sesMap = new HashMap<String, AdminSessionInfo>();
        }

        sesInfo = sesMap.get(session.getId());
        if (sesInfo == null) {
        sesInfo = new AdminSessionInfo();

        sesInfo.setSessionID(session.getId());
        sesInfo.setCreationTime(session.getCreationTime());
        sesInfo.setLastAccessedTime(session.getLastAccessedTime());
        sesInfo.setUser(nu);
        }

        sesInfo.setLastAccessedTime(session.getLastAccessedTime());
        sesMap.put(session.getId(), sesInfo);
        sc.setAttribute("sesMap", sesMap);

        SessionInformation si = acu.sessionRegistry.getSessionInformation(session.getId());
        if (si == null) {
        acu.sessionRegistry.registerNewSession(session.getId(), nu.getUsername());
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + session.getId() + ") is null");
        } else if (si.isExpired()) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + session.getId() + ") is expired");
        }
        } else 
        return false;
*/
        return true;
    }
/*
    public static boolean resetSessionUser (HttpSession session, JTUser nu) {

        AdminSessionInfo sesInfo = null;
        HashMap<String, AdminSessionInfo> sesMap = null;
        if (session != null) {
        ServletContext sc = session.getServletContext();
        sesMap = (HashMap) sc.getAttribute("sesMap");
        if (sesMap != null) {
        sesInfo = sesMap.get(session.getId());
        if (sesInfo != null) {
        sesInfo.setUser(nu);
        } else 
        return false;
        sesMap.put(session.getId(), sesInfo);
        } else 
        return false;
        sc.setAttribute("sesMap", sesMap);
        } else 
        return false;

        return true;
    }
*/
    public static boolean addSessionInfo (HttpSession session) {

        JTUtils acu = new JTUtils();
/*
        AdminUser nu = null;
        AdminSessionInfo sesInfo = null;
        HashMap<String, AdminSessionInfo> sesMap = null;

        if (session != null) {
        nu = (AdminUser) session.getAttribute("user");
        ServletContext sc = session.getServletContext();
        sesMap = (HashMap) sc.getAttribute("sesMap");
        if (sesMap == null) 
        sesMap = new HashMap<String, AdminSessionInfo>();

        sesInfo = sesMap.get(session.getId());
        if (sesInfo == null) 
        sesInfo = new AdminSessionInfo();

        sesInfo.setSessionID(session.getId());
        sesInfo.setCreationTime(session.getCreationTime());
        sesInfo.setLastAccessedTime(session.getLastAccessedTime());
        sesInfo.setUser(nu);

        sesMap.put(session.getId(), sesInfo);
        sc.setAttribute("sesMap", sesMap);

        SessionInformation si = acu.sessionRegistry.getSessionInformation(session.getId());
        if (si == null) {
        acu.sessionRegistry.registerNewSession(session.getId(), nu.getUsername());
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + session.getId() + ") is null");
        } else if (si.isExpired()) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + session.getId() + ") is expired");
        }
        } else 
        return false;
*/
        return true;
    }

    public static boolean expireSessionInfo (HttpSession session) {

        JTUtils acu = new JTUtils();
/*
        String id = null;
        AdminUser nu = null;
        AdminSessionInfo sesInfo = null;
        SessionInformation si = null;
        HashMap<String, AdminSessionInfo> sesMap = null;

        if (session != null) {
        id = session.getId();
        nu = (AdminUser) session.getAttribute("user");
        ServletContext sc = session.getServletContext();
        sesMap = (HashMap) sc.getAttribute("sesMap");

        if (sesMap != null) {
        sesInfo = sesMap.get(id);
        if (sesInfo != null) {
        acu.adbService.saveLogin(nu.getPeopleID(), nu.getUsername(), nu.getOuNumber(), nu.getDomainGroup(), 
                    nu.getOuNumber(), "Session Timeout", acu.addrTimeout);
        sesMap.remove(id);
        si = acu.sessionRegistry.getSessionInformation(id);
        if (si != null) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + id + ") is expired now");
        acu.sessionRegistry.removeSessionInformation(id);
        }
        }
        }
        sc.setAttribute("sesMap", sesMap);
        } else {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil expireSessionInfo session is null");
        return false;
        }
*/
        return true;
    }

    public static boolean expireSessionInfo (String sessionID) {

        JTUtils acu = new JTUtils();
/*
//        String id = null;
        AdminUser nu = null;
        AdminSessionInfo sesInfo = null;
        SessionInformation si = null;
        HashMap<String, AdminSessionInfo> sesMap = null;

        sesMap = (HashMap) acu.context.getAttribute("sesMap");

        if (sesMap != null) {
        sesInfo = sesMap.get(sessionID);
        if (sesInfo != null) {
        nu = sesInfo.getUser();
        acu.adbService.saveLogin(nu.getPeopleID(), nu.getUsername(), nu.getOuNumber(), nu.getDomainGroup(), 
                    nu.getOuNumber(), "Session Timeout", acu.addrTimeout);
        sesMap.remove(sessionID);
        si = acu.sessionRegistry.getSessionInformation(sessionID);
        if (si != null) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + sessionID + ") is expired now");
//        si.expireNow();
        acu.sessionRegistry.removeSessionInformation(sessionID);
        }
        }
        }
        acu.context.setAttribute("sesMap", sesMap);
*/
        return true;
    }

    public static boolean removeSessionInfo (HttpSession session) {

        JTUtils acu = new JTUtils();
/*
        String id = null;
        AdminUser nu = null;
        AdminSessionInfo sesInfo = null;
        SessionInformation si = null;
        HashMap<String, AdminSessionInfo> sesMap = null;

        if (session != null) {
        id = session.getId();
        nu = (AdminUser) session.getAttribute("user");
        ServletContext sc = session.getServletContext();
        sesMap = (HashMap) sc.getAttribute("sesMap");

        if (sesMap != null) {
        sesInfo = sesMap.get(id);
        if (sesInfo != null) 
        nu = sesInfo.getUser();
        acu.adbService.saveLogin(nu.getPeopleID(), nu.getUsername(), nu.getOuNumber(), nu.getDomainGroup(), 
                    nu.getOuNumber(), "Logout", acu.addrTimeout);
        sesMap.remove(id);
        si = acu.sessionRegistry.getSessionInformation(id);
        if (si != null) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + id + ") is removed now");
//        si.expireNow();
        acu.sessionRegistry.removeSessionInformation(id);
        }
        }
        sc.setAttribute("sesMap", sesMap);
        } else {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil removeSessionInfo session is null");
        return false;
        }
*/
        return true;
    }

    public static boolean removeSessionInfo (String sessionID) {

        JTUtils acu = new JTUtils();
/*
//        String id = null;
        AdminUser nu = null;
        AdminSessionInfo sesInfo = null;
        SessionInformation si = null;
        HashMap<String, AdminSessionInfo> sesMap = null;

        sesMap = (HashMap) acu.context.getAttribute("sesMap");

        if (sesMap != null) {
        sesInfo = sesMap.get(sessionID);
        if (sesInfo != null) {
        nu = sesInfo.getUser();
        acu.adbService.saveLogin(nu.getPeopleID(), nu.getUsername(), nu.getOuNumber(), nu.getDomainGroup(), 
                    nu.getOuNumber(), "Logout", acu.addrTimeout);
        sesMap.remove(sessionID);

        si = acu.sessionRegistry.getSessionInformation(sessionID);
        if (si != null) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AntsAdminUtil sessionRegistry.getSessionInformation(" + sessionID + ") is removed now");
        acu.sessionRegistry.removeSessionInformation(sessionID);
        }
        }
        }
        acu.context.setAttribute("sesMap", sesMap);
*/
        return true;
    }

    public static ModelAndView getLoginMV () {
        ModelAndView mv = new ModelAndView("admin/login", "au", null);
        return mv;
    }

    public static String getLoginPage () {
        return "admin/login";
    }

    public static ModelAndView getMainMenuMV () {
        ModelAndView mv = new ModelAndView("admin/mainmenu", "au", null);
        return mv;
    }

    public static String getMainMenuPage () {
        return "admin/mainmenu";
    }

    public static ModelAndView getViewSessionsMV () {
        ModelAndView mv = new ModelAndView("admin/viewSessions", "au", null);
        return mv;
    }

    public static String getViewSessionsPage () {
        return "admin/viewSessions";
    }

    public static ModelAndView getLogoutMV () {
        ModelAndView mv = new ModelAndView("admin/logout", "au", null);
        return mv;
    }

    public static String getLogoutPage () {
        return "admin/logout";
    }

    public static ModelAndView getTimeoutMV () {
        ModelAndView mv = new ModelAndView("admin/timeout", "au", null);
        return mv;
    }

    public static String getTimeoutPage () {
        return "admin/timeout";
    }

    public static ModelAndView getDeniedMV () {
        ModelAndView mv = new ModelAndView("admin/denied", "au", null);
        return mv;
    }

    public static String getDeniedPage () {
        return "admin/denied";
    }

    public static ModelAndView getNotAuthMV () {
        ModelAndView mv = new ModelAndView("admin/notAuth", "au", null);
        return mv;
    }

    public static String getNotAuthPage () {
        return "admin/notAuth";
    }

    public static ModelAndView getBadRequestMV () {
        ModelAndView mv = new ModelAndView("admin/badrequest", "au", null);
        return mv;
    }

    public static String getBadRequestPage () {
        return "admin/badrequest";
    }

    public static ModelAndView getEmptyListMV () {
        ModelAndView mv = new ModelAndView("admin/emptylist", "au", null);
        return mv;
    }

    public static String getEmptyListPage () {
        return "admin/emptylist";
    }

    public static ModelAndView getExceptionMV () {
        ModelAndView mv = new ModelAndView("admin/exception", "au", null);
        return mv;
    }

    public static String getExceptionPage () {
        return "admin/exception";
    }

    public static ModelAndView getMaintenanceMV () {
        ModelAndView mv = new ModelAndView("admin/maintenance", "au", null);
        return mv;
    }

    public static String getMaintenancePage () {
        return "admin/maintenance";
    }
/*
    public static int getSeqNumber(String name) {

        JTUtils acu = new JTUtils();

        return acu.seqnumService.getSeqNumber(name);
    }

    public static String getContactEmail(int id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(ContactEmail, new Object [] { id });
    }

    public static String getContactEmail(String id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(ContactEmail, new Object [] { id });
    }

    public static String getContactPhone(int id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(ContactPhone, new Object [] { id });
    }

    public static String getContactPhone(String id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(ContactPhone, new Object [] { id });
    }

    public static String getLASTFIRST(int id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(LASTFIRST, new Object [] { id });
    }

    public static String getLASTFIRST(String id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(LASTFIRST, new Object [] { id });
    }

    public static String getFIRSTLAST(int id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(FIRSTLAST, new Object [] { id });
    }

    public static String getFIRSTLAST(String id) {
        JTUtils acu = new JTUtils();
        return acu.antsDBService.getFieldValue(FIRSTLAST, new Object [] { id });
    }
*/
    public static boolean isMaintain() {

        JTUtils acu = new JTUtils();

        List<String> evemails = new ArrayList<String>();
//        List<TaskEmail> evemails = 
//                acu.evemailService.getEventEmailList(JTConstants.MAINTAIN_COND, new Object[] { acu.siteDom });

        if (evemails.size() > 0) {
        logger.info(acu.siteDom + " is under maintenence.");
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + acu.siteDom + " is under maintenence.");
            return true;
        } else {
        logger.info(acu.siteDom + " is NOT under maintenence.");
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + acu.siteDom + " is NOT under maintenence.");
            return false;
        }
    }

    public static boolean siteRestricted() {

        JTUtils acu = new JTUtils();

        if (acu.appHost.equalsIgnoreCase("Local"))
        return false;

        boolean antsadmin = acu.siteApp.equalsIgnoreCase("antsadmin");
        boolean siirtOnly = 
                (acu.siteApp.equalsIgnoreCase("antsclient-siirt") ? acu.appHost.equalsIgnoreCase("Prod") : false);

//        boolean restrict = (isMaintain() || siirtOnly);

        logger.info(acu.siteApp + " Is SIIRT Only? " + siirtOnly);
        logger.info(acu.siteDom + " Is ANTS Admin? " + antsadmin);

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + acu.siteApp + " Is SIIRT Only? " + siirtOnly);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + acu.siteDom + " Is ANTS Admin? " + antsadmin);

        return (antsadmin || siirtOnly);
    }

    public static boolean emailAction(String reqHost, SimpleMailMessage msg) 
            throws Exception {

        JTUtils acu = new JTUtils();

//        if (reqHost == null) 
//            reqHost = "";

//        if ((reqHost != null) && (reqHost.indexOf("129.6.26") >= 0)) {
//        if ((reqHost != null) && acu.allowipsService.hasIP(reqHost, "SCAN", acu.siteApp)) {
        if ((reqHost != null) && checkRemoteAddr(reqHost, "SCAN")) {
            msg.setTo(StringUtils.tokenizeToStringArray(acu.admin, EmailSep2));
//            msg.setTo(new String [] { "Lee, Jason <Jason.Lee@nist.gov>", 
//                "Antonishek, John K. <john.antonishek@nist.gov>" });
            
//            acu.mailSender.send(msg);
            System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nThe Email has not been sent due to scan discovery from reqHost");

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nEmail Subject: " + msg.getSubject());
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nEmail Message:\n" + msg.getText());

        logger.info("The Email has not been sent due to scan discovery from reqHost");
        logger.info("Email Subject: " + msg.getSubject());
        logger.info("Email Message:\n" + msg.getText());

        } else {
            acu.mailSender.send(msg);
            System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nThe Email has been sent");
        }

        return true;
    }

    public static boolean checkRemoteAddr(String reqHost) {

        JTUtils acu = new JTUtils();

        if (reqHost == null) 
            return false;

        String[] allowArray = null;
        List<String> allowList = null;

        logger.info("Allow List: " + acu.allow);
        logger.info("Remote Address: " + reqHost);

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nAllow List: " + acu.allow);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nRemote Address: " + reqHost);

        if ((acu.allow != null) && (acu.allow.length() > 0)) 
        allowArray = StringUtils.tokenizeToStringArray(acu.allow, JTConstants.SEPS);

        if (allowArray != null) {
        allowList = Arrays.asList(allowArray);

        for (String n : allowArray){
        logger.info("Allow address: " + n);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nAllow address: " + n);
        if (reqHost.equalsIgnoreCase(n)) {
        logger.info(reqHost + " is allowed for access.");
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + reqHost + " is allowed for access.");
            return true;
        } else {
        logger.info(reqHost + " is NOT allowed for access.");
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + reqHost + " is NOT allowed for access.");
            return false;
        }
        }

        return false;
        } else {
        logger.info("allowArray is null: " + allowArray);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nallowArray is null: " + allowArray);
            return true;
        }
    }

    public static boolean checkRemoteAddr(String reqHost, String accessType) {

        JTUtils acu = new JTUtils();

        if ((reqHost == null) || (reqHost.length() == 0))
            return false;

        logger.info("Remote Address: " + reqHost);
        logger.info("Access Type: " + accessType + " (Default is WEB)");

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nRemote Address: " + reqHost);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nAccess Type: " + accessType + " (Default is WEB)");

        if ((accessType == null) || (accessType.length() == 0))
            accessType = "WEB";

        if (true) {
//        if (acu.allowipsService.hasIP(reqHost, accessType, acu.siteApp)) {
        logger.info(reqHost + " is allowed for accessType " + accessType);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + reqHost + " is allowed for accessType " + accessType);
            return true;
        } else {
        logger.info(reqHost + " is NOT allowed for accessType " + accessType);
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + reqHost + " is NOT allowed for accessType " + accessType);
            return false;
        }
    }

    public static boolean isEmailException(Exception ex, 
            HttpServletRequest request, HttpSession session) {

            boolean sent = (ex.getMessage().indexOf("User unknown") >= 0);
            System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nThe Email has " + (sent ? "been partially" : "not been") + " sent");
//            ex.printStackTrace();            
//            if (sent)
            sendException(request, session, ex);

            return sent;
//            return (ex.getMessage().indexOf("User unknown") >= 0);
    }
/*
    public static TaskEmail checkEmailException(TaskEmail ae, Exception ex, 
            HttpServletRequest request, HttpSession session) {

            // simply log it and go on...
//            System.err.println(ex.getMessage());
            ae.setIsSent((ex.getMessage().indexOf("User unknown") >= 0));
            System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nThe Email has " + 
                   ((ae.getIsSent()) ? "been partially" : "not been") + " sent");
//            ex.printStackTrace();            
//            if (ae.getIsSent())
            sendException(request, session, ex);

            return ae;
    }

    public static TaskEmail sendEvEmail(TaskEmail ae, List<String> to, 
//    public static EventEmail sendEvEmail(EventEmail ae, AdminUser nu, List<String> to, 
            HttpServletRequest request, HttpSession session) {

        boolean go = true;
        boolean sent = false;
        JTUtils acu = new JTUtils();

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(acu.jtMailTemplate);

        List<String> sl = null;
        StringBuilder sb = new StringBuilder();
        Date start = null;
        Date evend = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm a z");

        if (to.size() > 0) {
        ae.setSendto(StringUtils.collectionToDelimitedString(to, EmailSep));
        msg.setTo(StringUtils.toStringArray(to));
        } else {
        go = false;
        }

        try{
        start = sdf.parse(ae.getEventStart());
        evend = sdf.parse(ae.getEventEnd());
        } catch(Exception ex) {
            ex.printStackTrace();            
        }

        sdf = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        String eventDate = (start == null) ? "" : sdf.format(start);

//        sdf = new SimpleDateFormat("HH:mm a z");
        sdf = new SimpleDateFormat("h:mm a z");
        String tStart = (start == null) ? "" : sdf.format(start);
        String tEvend = (evend == null) ? "" : sdf.format(evend);

//        ae.setSubject(String.format(acu.evemSubject, ae.getEventType(), eventDate));
        ae.setSubject(String.format(acu.evemSubject, eventDate));
        msg.setSubject(ae.getSubject());

//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\n" + msg.getSubject());
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\n" + sb.toString());

        sb.append("\n");
        sb.append(String.format(acu.evemBody1, ae.getEventType().toLowerCase(), 
                tStart, tEvend, eventDate));
//        sb.append(String.format(acu.evemBody1, ae.getEventType(), ae.getEventStart(), 
//                ae.getEventEnd(), eventDate));

        sb.append("\n\n");
        sb.append(ae.getDetails());

        sb.append("\n\n");
        sb.append(acu.iTACText);
        sb.append("\n\n");
        sb.append(acu.iTACHead);
        sb.append("\n");
        sb.append(acu.iTACPhone);
        sb.append("\n");
        sb.append(acu.iTACEmail);
        sb.append("\n");
        sb.append(acu.iTACURL);
        sb.append("\n");

        if (acu.appHost.equalsIgnoreCase("Dev") || acu.appHost.equalsIgnoreCase("Local")) {
        ae.setSubject("TEST: " + ae.getSubject());
        msg.setSubject("TEST: " + msg.getSubject());
        sb.insert(0, "\nThis is a TEST email.  Please do not respond or reply.\n\n");
        }

        ae.setMessage(sb.toString());
//        msg.setText(sb.toString());
        msg.setText(ae.getMessage());

//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSend To in ae: '" + ae.getSendTo() + "'");
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSend To in msg: '" + msg.getTo().toString() + "'");

//        ae.setIsSent(sent);
        if (go) {
        try{
//            acu.mailSender.send(msg);
//            ae.setIsSent(true);
            sent = emailAction(request.getRemoteHost(), msg);
            ae.setIsSent(sent);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent");
        }
        catch(Exception ex) {
            // simply log it and go on...
//            System.err.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\n" + ex.getMessage());
            ae = checkEmailException(ae, ex, request, session);
        }
        }

        acu.evemailService.insertEventEmail(ae);

        return ae;
    }

    public static boolean resendEvEmail(TaskEmail ae, HttpServletRequest request, HttpSession session) {

        boolean go = true;
        boolean sent = false;
        JTUtils acu = new JTUtils();

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(acu.jtMailTemplate);

//        String s = null;
//        List<String> sl = null;
        msg.setSubject(ae.getSubject());
        msg.setTo(StringUtils.tokenizeToStringArray(ae.getSendto(), EmailSep));
        msg.setText(ae.getMessage());

//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSend To in ae: '" + ae.getSendTo() + "'");
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSend To in msg: '" + msg.getTo().toString() + "'");

        try{
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\n" + msg.getSubject());
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\n" + sb.toString());
//            acu.mailSender.send(msg);
//            ae.setIsSent(true);
            sent = emailAction(request.getRemoteHost(), msg);
            ae.setIsSent(sent);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent");
        }
        catch(Exception ex) {
            // simply log it and go on...
//            System.err.println(ex.getMessage());
            ae = checkEmailException(ae, ex, request, session);
        }

        acu.evemailService.updsentEventEmail(ae.getID(), sent);

        return ae.getIsSent();
    }

    public static boolean remindEvEmail(TaskEmail ae, HttpServletRequest request, HttpSession session) {

        boolean go = true;
        boolean sent = false;
        JTUtils acu = new JTUtils();

        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(acu.jtMailTemplate);

//        String s = null;
//        List<String> sl = null;
        msg.setSubject("REMINDER: " + ae.getSubject());
        msg.setTo(StringUtils.tokenizeToStringArray(ae.getSendto(), EmailSep));
        msg.setText(ae.getMessage());

//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSend To in ae: '" + ae.getSendTo() + "'");
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nSend To in msg: '" + msg.getTo().toString() + "'");

        try{
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\n" + msg.getSubject());
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\n" + sb.toString());
//            acu.mailSender.send(msg);
//            ae.setIsSent(true);
            sent = emailAction(request.getRemoteHost(), msg);
            ae.setIsSent(sent);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent");
        }
        catch(Exception ex) {
            // simply log it and go on...
//            System.err.println(ex.getMessage());
            ae = checkEmailException(ae, ex, request, session);
        }
//        acu.evemailService.updsentEventEmail(ae.getID(), sent);

        return ae.getIsSent();
    }

    public static boolean sendPersonStatus(String username, PersonStatus ps, 
            HttpServletRequest request, HttpSession session) {

//        ModelAndView mv = new ModelAndView("main/exception");
        boolean sent = false;
        JTUtils acu = new JTUtils();

        if (ps == null) 
        ps = new PersonStatus();
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(acu.exMailTemplate);

        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }

        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        sb.append("Login User: " + ps.getLastFirst() + "(" + username + ")\n\n");

        sb.append("Remote Host: " + request.getRemoteHost() + "\n");
        sb.append("Remote Address: " + request.getRemoteAddr() + "\n");
        sb.append("Request URI: " + request.getRequestURI() + "\n");
        sb.append("Request URL: " + request.getRequestURL().toString() + "\n\n");
//        sb.append("Request URI (Attribute): " + requestUri + "\n");
        sb.append("Servlet Name (Attribute): " + servletName + "\n\n");

        sb.append("Application Host: " + acu.appHost + "\n\n");
        sb.append("ANTS Application: " + acu.siteApp + "\n\n");

        sb.append("Person Status:\n\n");
        sb.append("Person isActive? " + ps.getIsActive() + "\n");
        sb.append("Person isPending? " + ps.getIsPending() + "\n");
        sb.append("Person isSuspended? " + ps.getIsSuspended() + "\n");
        sb.append("Person IT Account Enabled? " + ps.isITAccountEnabled() + "\n\n");

        msg.setText(sb.toString());
        msg.setSubject(acu.loginSubject);
        msg.setTo(StringUtils.tokenizeToStringArray(acu.antsAdmin, EmailSep2));

        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\n" + msg.getSubject());
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\n" + sb.toString());

        try{
//            acu.mailSender.send(msg);
            sent = emailAction(request.getRemoteHost(), msg);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent");
        }
        catch(Exception ex) {
            // simply log it and go on...
//            System.err.println(ex.getMessage());            
            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nThe Email has not been sent");
            ex.printStackTrace();            
        }

        return sent;
    }
*/
    public static ModelAndView sendException(HttpServletRequest request, 
            HttpSession session, Exception e) {

        ModelAndView mv = new ModelAndView("admin/exception");
//        mv.addObject("name", e.getClass().getSimpleName());
//        mv.addObject("message", e.getMessage());
        JTUtils acu = new JTUtils();

//        AdminUser nu = (AdminUser) session.getAttribute("user");
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(acu.exMailTemplate);

        String servletName = (String) request
                .getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }

        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

//        sb.append("Login User: " + nu.getLastFirst() + "(" + nu.getUsername() + ")\n\n");

        sb.append("Remote Host: " + request.getRemoteHost() + "\n");
        sb.append("Remote Address: " + request.getRemoteAddr() + "\n");
        sb.append("Request URI: " + request.getRequestURI() + "\n");
        sb.append("Request URL: " + request.getRequestURL().toString() + "\n\n");
//        sb.append("Request URI (Attribute): " + requestUri + "\n");
        sb.append("Servlet Name (Attribute): " + servletName + "\n\n");

        sb.append("Application Host: " + acu.appHost + "\n\n");
        sb.append("ANTS Application: " + acu.siteApp + "\n\n");

        sb.append("Cause: " + e.getCause() + "\n\n");
        sb.append("Exception Name: " + e.getClass().getSimpleName() + "\n\n");
        sb.append("Message:\n" + e.getMessage() + "\n\n");

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        sb.append("Exception Stack Trace: " + "\n");
        sb.append(sw.toString());

        msg.setText(sb.toString());

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + msg.getSubject());
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + sb.toString());

        try{
//            acu.mailSender.send(msg);
            boolean sent = false;
            sent = emailAction(request.getRemoteHost(), msg);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent");
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

    public static ModelAndView sendErrorCode(HttpServletRequest request, 
            HttpSession session, Integer statusCode) {

        ModelAndView mv = new ModelAndView("admin/exception");
//        mv.addObject("name", e.getClass().getSimpleName());
//        mv.addObject("message", e.getMessage());
        JTUtils acu = new JTUtils();

//        AdminUser nu = (AdminUser) session.getAttribute("user");
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(acu.exMailTemplate);

        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

//        sb.append("Login User: " + nu.getLastFirst() + "(" + nu.getUsername() + ")\n\n");

        sb.append("Remote Host: " + request.getRemoteHost() + "\n");
        sb.append("Remote Address: " + request.getRemoteAddr() + "\n");
        sb.append("Request URI: " + request.getRequestURI() + "\n");
        sb.append("Request URL: " + request.getRequestURL().toString() + "\n\n");
//        sb.append("Request URI (Attribute): " + requestUri + "\n");

        sb.append("Application Host: " + acu.appHost + "\n\n");
        sb.append("ANTS Application: " + acu.siteApp + "\n\n");

        sb.append("Error Code: " + statusCode + "\n\n");

        msg.setText(sb.toString());

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + msg.getSubject());
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + sb.toString());

        try{
//            acu.mailSender.send(msg);
            boolean sent = false;
            sent = emailAction(request.getRemoteHost(), msg);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nThe Email has been sent");
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

    public static boolean sendIntercept(HttpServletRequest request, HttpSession session, 
            StringBuilder sb2, String badrequestPage) {

//        ModelAndView mv = new ModelAndView("main/exception");
//        mv.addObject("name", e.getClass().getSimpleName());
//        mv.addObject("message", e.getMessage());
        JTUtils acu = new JTUtils();

//        AdminUser nu = (AdminUser) session.getAttribute("user");
        // Create a thread safe "copy" of the template message and customize it
        SimpleMailMessage msg = new SimpleMailMessage(acu.intMailTemplate);

        boolean sent = false;

        String requestUri = (String) request
                .getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n");

//        sb.append("Login User: " + nu.getLastFirst() + "(" + nu.getUsername() + ")\n\n");

        sb.append("Remote Host: " + request.getRemoteHost() + "\n");
        sb.append("Remote Address: " + request.getRemoteAddr() + "\n");
        sb.append("Request URI: " + request.getRequestURI() + "\n");
        sb.append("Request URL: " + request.getRequestURL().toString() + "\n\n");
//        sb.append("Request URI (Attribute): " + requestUri + "\n");

        sb.append("Application Host: " + acu.appHost + "\n\n");
        sb.append("ANTS Application: " + acu.siteApp + "\n\n");

        sb.append("Security Interceptor Message: " + "\n");
        sb.append(sb2.toString());

        msg.setText(sb.toString());

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + msg.getSubject());
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\n" + sb.toString());

        try{
//            acu.mailSender.send(msg);
//        msg.setSubject(msg.getSubject() + " - " + 
//                new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()));
            sent = emailAction(request.getRemoteHost(), msg);
//            System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//"\nThe Email has been sent");
        }
        catch(Exception ex) {
            // simply log it and go on...
//            System.err.println(ex.getMessage());            
            System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                    "\nThe Email has not been sent");
            ex.printStackTrace();            
        }

//        ModelAndView mv = new ModelAndView("redirect:" + badrequestPage, "antsview", null);
        return sent;
    }

}

/*

*/
