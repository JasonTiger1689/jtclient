/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.controller;

import java.io.*;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ConcurrentModificationException;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.itech.javatest.utils.*;

import com.itech.javatest.security.ldap.ADConnection;
import com.itech.javatest.security.ldap.NISTLDAPAuthenticationProvider;

import com.itech.javatest.codes.model.Codes;
import com.itech.javatest.codes.dao.CodesDAO;
import com.itech.javatest.codes.service.CodesService;

/**
 *
 * @author jhl
 */
@Controller
@RequestMapping("/admin.htm")
@Configurable(autowire = Autowire.BY_TYPE)
public class AdminController {

    protected static Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    @Qualifier("sessionRegistry")
//    @Resource(name="sessionRegistry")
    SessionRegistryImpl sessionRegistry;
//    private SessionRegistryImpl sessionRegistry;

    @Autowired
    @Qualifier("codesService")
    CodesService codesService;

//    @Value( "${site.appHost}" )
    @Value( "#{siteProps['site.appHost']}" )
    String appHost;

//    @Value( "${site.siteApp}" )
    @Value( "#{siteProps['site.siteApp']}" )
    String siteApp;

//    @Value( "${site.siteDom}" )
    @Value( "#{siteProps['site.siteDom']}" )
    String siteDom;

    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
    }

    @RequestMapping(params="actor=mainmenu")
    public ModelAndView mainmenu() {

        ModelAndView mv = JTUtils.getMainMenuMV();

        return mv;
//        return new ModelAndView("admin/mainmenu", "codes", codesService.getAllActiveCodes());
    }

    @RequestMapping(params="actor=notAuth")
    public ModelAndView getNotAuth(HttpServletRequest request, HttpSession session) {
//        if (!AntsAdminUtil.verifySession(session))
//        return AntsAdminUtil.getDeniedPage();

        ModelAndView mv = JTUtils.getNotAuthMV();
//        ModelAndView mv = new ModelAndView("admin/notAuth", "codes", null);

        return mv;
    }

    @RequestMapping(params="actor=badrequest")
    public ModelAndView badrequest(HttpServletRequest request, HttpSession session) {
//        if (!AntsAdminUtil.verifySession(session))
//        return AntsAdminUtil.getDeniedPage();

        ModelAndView mv = JTUtils.getBadRequestMV();
//        ModelAndView mv = new ModelAndView("admin/badrequest", "codes", null);

        return mv;
    }

    @RequestMapping(params="actor=showMaintenance")
    public ModelAndView showMaintenance(HttpServletRequest request, HttpSession session) {
//    public String showMaintenance(HttpServletRequest request, HttpSession session) {
//        if (!AntsAdminUtil.verifySession(session))
//        return AntsAdminUtil.getDeniedPage();

//        TaskEmail evemail = new TaskEmail();
        String evemail = "The site is under maintenance now and will be back to working soon.  Thanks for your patience.";

        List<String> evemails = new ArrayList<String>();
//        List<EventEmail> evemails = 
//                evemailService.getEventEmailList(AntsAdminConstants.MAINTAIN_COND, new Object[] { antsDom });

        if ((evemails != null) && (evemails.size() > 0)) {
            evemail = evemails.get(0);
        }

        ModelAndView mv = JTUtils.getMaintenanceMV();
//        ModelAndView mv = new ModelAndView("admin/maintenance", "evemail", evemail);

        return mv;
//        return "admin/maintenance";
    }

    @RequestMapping(params="actor=handleError")
    public ModelAndView handleError(HttpServletRequest request, HttpSession session, Exception e) {
//        if (!AntsAdminUtil.verifySession(session))
//        return AntsAdminUtil.getDeniedPage();

        // Analyze the Error/Exception
        Throwable throwable = (Throwable) request
                .getAttribute("javax.servlet.error.exception");
        Exception exception = (Exception) request
                .getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
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

        if (exception == null) {
            JTUtils.sendErrorCode(request, session, statusCode);
        } else {
            JTUtils.sendException(request, session, exception);
        }

        ModelAndView mv = JTUtils.getExceptionMV();
//        ModelAndView mv = new ModelAndView("admin/exception", "antsview", null);
//        ModelAndView mv = new ModelAndView("main/exception", "antsview", null);
//        mv.addObject("ANTSID_STATUS", codesService.getCodesList("NAME = 'ANTSID_STATUS' AND status = 'ACTIVE' "));
//        mv.addObject("ASSET_TYPE", codesService.getCodesList("NAME = 'ASSET_TYPE' AND status = 'ACTIVE' "));
//        mv.addObject("SOURCE", codesService.getCodesList("NAME = 'SOURCE' AND status = 'ACTIVE' "));

        return mv;
    }

    /**
     * Handles and retrieves the login JSP page
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(params="actor=getLogin")
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String getLoginPage(ModelMap model, HttpServletRequest request, HttpSession session) {
//    public String getLoginPage(ModelMap model, HttpServletRequest request, HttpSession session) {
    public ModelAndView getLoginPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        logger.debug("Received request to show login page");

        List<String> evemails = null;
//        List<TaskEmail> evemails = 
//                evemailService.getEventEmailList(JTConstants.SCH_EVEM_COND, new Object[] { antsDom });

        ModelAndView mv = JTUtils.getMainMenuMV();
//        ModelAndView mv = JTUtils.getLoginMV();
//        ModelAndView mv = new ModelAndView("admin/login", "evemails", evemails);
        mv.addObject("hasEvent", ((evemails != null) && (evemails.size() > 0)));

        saveSession(request, session);

//        return "auth/login";
//        return "admin/login";
        return mv;
    }

    /**
     * Handles and retrieves the login JSP page
     * 
     * @param model
     * @param request
     * @param session
     * @return the name of the JSP page
     */
    @RequestMapping(params="actor=login", method=RequestMethod.POST)
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String checkLogin(ModelMap model, HttpServletRequest request, HttpServletResponse response, 
//           HttpSession session) {
    public ModelAndView checkLogin(ModelMap model, HttpServletRequest request, HttpSession session) {
        logger.debug("Received request to check login info");

        ModelAndView mv = JTUtils.getLoginMV();
//        ModelAndView mv = new ModelAndView("auth/login", "antsview", null);
//        ModelAndView mv = new ModelAndView("admin/login", "antsview", null);

        boolean go = true;
        String username = request.getParameter("j_username");
        String password = request.getParameter("j_password");
        StringBuilder sb = new StringBuilder();
        ADConnection adc = new ADConnection();

        if ((username == null) || (username.length() == 0))
            sb.append("Username cannot be empty\n");
        if ((password == null) || (password.length() == 0))
            sb.append("Password cannot be empty\n");
/*
        if (antsProps != null) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nutil:properties antsProps is not null");
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nhtml.title from util:properties antsProps = " + antsProps.getProperty("html.title"));
        } else {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nutil:properties antsProps is null");
        }

        if (siteProps != null) {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nutil:properties siteProps is not null");
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nsite.antsApp from util:properties siteProps = " + siteProps.getProperty("site.antsApp"));
        } else {
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nutil:properties siteProps is null");
        }
*/
        int pid = -1;
        String userDG = null;
        session.setAttribute("username", username);

//        if (!appHost.equalsIgnoreCase("Local"))
//        go = adc.authenticate(username, password);
/*        
        List<String> ol = adminService.getGroupList(username);
        if ((ol != null) && (ol.size() > 0))
        userDG = StringUtils.collectionToDelimitedString(ol, ",");
*/
        if (sb.length() == 0) {
        if (go) {
//            AdminUser au = adminService.loadUserByUsername(username);
//            if (au == null) {
//        mv.addObject("error", "User Not Found in Database!");
//        model.put("error", "User Not Found in Database!");

//        PersonStatus ps = adminService.getPersonStatus("NIST_PRIMARY_USERNAME = '" + username + "' ");
//        AntsAdminUtil.sendPersonStatus(username, ps, request, session);

//        adbService.saveLogin(pid, username, "", userDG, 
//                "", "User Not Found", request.getRemoteAddr());

//        return mv;
//        return "auth/login";
//        return "auth.htm?actor=getLogin";
//            }

        ServletContext sc = session.getServletContext();
//        sc.declareRoles("ROLE_USER", "ROLE_ANONYMOUS");
//        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, AUTHORITIES);
//        auth.setAuthenticated(true);
//        SecurityContextHolder.getContext().setAuthentication(auth);

//        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);

        if (SecurityContextHolder.getContext() != null) {
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSecurityContextHolder.getContext() is not null");

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, AUTHORITIES);

        try {
//        auth.setAuthenticated(true);
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nAuthentication auth.setAuthenticated(true) is successful");
        } catch (Exception ex) {
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nAuthentication auth.setAuthenticated(true) is not successful");
        ex.printStackTrace();
        }
        SecurityContextHolder.getContext().setAuthentication(auth);

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSecurityContextHolder.getContext().getAuthentication() is not null");
//        username = SecurityContextHolder.getContext().getAuthentication().getName();
//        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
        }
        }

//            adbService.saveLogin(au.getPeopleID(), username, au.getOuNumber(), au.getDomainGroup(), 
//                    au.getOuNumber(), "Login", request.getRemoteAddr());

//        JTUtils.addSessionInfo(session);

        return new ModelAndView("redirect:admin.htm?actor=mainmenu", "antsview", null);
//        return new MainController().searchForm(request, session);
//        return new ModelAndView("main.htm?actor=searchForm", "antsview", null);
        } else {

//        adbService.saveLogin(pid, username, "", userDG, 
//                "", "Login Fail", request.getRemoteAddr());

//            model.put("error", "You have entered an invalid username or password!");
        mv.addObject("error", "You have entered an invalid username or password!");
        return mv;
//        return "auth/login";
        }
        } else {
//            model.put("error", sb.toString());
        mv.addObject("error", sb.toString());
        return mv;
//        return "auth/login";
        }

    }

    public boolean saveSession(HttpServletRequest request, HttpSession session) {

            session.setAttribute("login", true);
            session.setAttribute("visiting", true);

            session.setAttribute("lang", "ENG");
            session.setAttribute("page", "mainmenu");

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        session.setAttribute("today", sdf.format(today));
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        session.setAttribute("today2", sdf.format(today));
        sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        session.setAttribute("todayDT", sdf.format(today));

//        String verDate = evemailService.getLastVerDate(antsDom);
//        session.setAttribute("verDate", verDate);
//        Integer verMajor = evemailService.getLastVerMajor(antsDom);
//        session.setAttribute("verMajor", verMajor);
//        Integer verMinor = evemailService.getLastVerMinor(antsDom);
//        session.setAttribute("verMinor", verMinor);
//        String verURL = "evemail.htm?actor=popvh&antsApp=" + antsDom;
//        session.setAttribute("verURL", verURL);

        return true;
    }

    public boolean deleteSession(HttpServletRequest request, HttpSession session) {

        session.removeAttribute("user");
        session.removeAttribute("login");
        session.removeAttribute("username");

        return true;
    }

    /**
     * Handles and retrieves the login JSP page
     * 
     * @param model
     * @param request
     * @param session
     * @return the name of the JSP page
     */
    @RequestMapping(params="actor=loginError")
//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String getLoginErrorPage(ModelMap model, HttpServletRequest request, HttpSession session) {
    public ModelAndView getLoginErrorPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        logger.debug("Received request to show login Error page");

        // Add an error message to the model if login is unsuccessful
        // The 'error' parameter is set to true based on the when the authentication has failed. 
        // We declared this under the authentication-failure-url attribute inside the spring-security.xml
        /* See below:
        <form-login 
        login-page="/krams/auth/login" 
        authentication-failure-url="/krams/auth/login?error=true" 
        default-target-url="/krams/main/common"/>
        */

        ModelAndView mv = JTUtils.getLoginMV();
//        ModelAndView mv = new ModelAndView("admin/login", "antsview", null);

        // Assign an error message
//        model.put("error", "You have entered an invalid username or password!");
        mv.addObject("error", "You have entered an invalid username or password!");
//        return "auth/login";
        return mv;
    }

    /**
     * Handles and retrieves the logout JSP page. This is shown whenever a regular user
     * tries to access an admin only page.
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(params="actor=logout")
//    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getLogoutPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        logger.debug("Received request to show logout page");

        String username = (String)session.getAttribute("username");
//        AdminUser nu = (AdminUser)session.getAttribute("user");

//        adbService.saveLogin(nu.getPeopleID(), username, nu.getOuNumber(), nu.getDomainGroup(), 
//                    nu.getOuNumber(), "Logout", request.getRemoteAddr());

        deleteSession(request, session);

//        JTUtils.removeSessionInfo(session);

        session.invalidate();
        model.remove("user");

        // This will resolve to /WEB-INF/jsp/denied.jsp
//        return "auth/logout";
//        return "admin/logout";
        return JTUtils.getLogoutPage();
    }

    /**
     * Handles and retrieves the logout JSP page. This is shown whenever a regular user
     * tries to access an admin only page.
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(params="actor=timeout")
//    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getTimeoutPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        logger.debug("Received request to show session timeout page");

        int id = -1;
        String ouN = null, dg = null;
        String ouL = null;
        String username = (String)session.getAttribute("username");
//        AdminUser au1 = (AdminUser)session.getAttribute("user");
//        AdminUser au2 = (AdminUser)model.get("user");
/*
        if (au2 != null) {
            id = au2.getPeopleID();
            dg = au2.getDomainGroup();
            ouN = au2.getOuNumber();
            ouL = au2.getOuNumber();
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nModel AdminUser is not null");
        }
        
        if (au1 != null) {
            id = au1.getPeopleID();
            dg = au1.getDomainGroup();
            ouN = au1.getOuNumber();
            ouL = au1.getOuNumber();
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nSession AdminUser is not null");
        }
*/
        if (username == null) {
            username = "SpringSecurity";
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nusername is null when Session Timeout");
        }

//        session.removeAttribute("user");
//        session.removeAttribute("login");
//        session.removeAttribute("username");
        deleteSession(request, session);

        session.invalidate();
        model.remove("user");

//        adbService.saveLogin(id, username, ouN, dg, 
//                    ouL, "Session Timeout", request.getRemoteAddr());
        // This will resolve to /WEB-INF/jsp/denied.jsp
//        return "auth/logout";
//        return "admin/timeout";
        return JTUtils.getTimeoutPage();
    }

    /**
     * Handles and retrieves the denied JSP page. This is shown whenever a regular user
     * tries to access an admin only page.
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(params="actor=denied")
//    @RequestMapping(value = "/denied", method = RequestMethod.GET)
    public String getDeniedPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        logger.debug("Received request to show access denied page");

        // This will resolve to /WEB-INF/jsp/denied.jsp
//        return "auth/denied";
//        return "admin/denied";
        return JTUtils.getDeniedPage();
    }

    /**
     * Handles and retrieves list of logged-in users as JSP view
     * 
     * @return the name of the JSP page
     */
    @RequestMapping(params="actor=getViewSessions")
//    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getViewSessions(Model model, HttpServletRequest request, HttpSession session) {
//    public String viewSessions(Model model, HttpServletRequest request, HttpSession session) {

        if (!JTUtils.verifySession(session))
        return JTUtils.getDeniedMV();

//        AdminUser au1 = (AdminUser) session.getAttribute("user");

//        if (!au1.getIsSIIRT())
//        return new ModelAndView("admin/notAuth", "antsview", null);

        logger.info("Received request to show sessions page");
/*
        List<Object> prins = sessionRegistry.getAllPrincipals();

        boolean go = true;
        AdminUser au = null;
        AdminSessionInfo sesInfo = null;
        SessionInformation sessionInfo = null;
        HashMap<String, AdminSessionInfo> sesMap = null;

        ServletContext sc = session.getServletContext();
        sesMap = (HashMap) sc.getAttribute("sesMap");
        if (sesMap != null) {
        while (go) {
        try {
        for (String key : sesMap.keySet()) {
        sessionInfo = sessionRegistry.getSessionInformation(key);
        if (sessionInfo == null) {
        sesMap.remove(key);
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AdminController sessionRegistry.getSessionInformation(" + key + ") is null");
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AdminController sessionRegistry.getSessionInformation(" + key + ") is removed");
        logger.info("In AdminController sessionRegistry.getSessionInformation(" + key + ") is null");
        logger.info("In AdminController sessionRegistry.getSessionInformation(" + key + ") is removed");
        } else {
        sesInfo = sesMap.get(key);
        au = sesInfo.getUser();
        if (sessionInfo.isExpired()) {
        sesMap.remove(key);
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AdminController sessionRegistry.getSessionInformation(" + key + ") is expired");
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nIn AdminController sessionRegistry.getSessionInformation(" + key + ") is removed");
        logger.info("In AdminController sessionRegistry.getSessionInformation(" + key + ") is expired");
        logger.info("In AdminController sessionRegistry.getSessionInformation(" + key + ") is removed");
        }
        }
        }
        go = false;
        } catch (ConcurrentModificationException cme) {
        StringWriter sw = new StringWriter();
        cme.printStackTrace(new PrintWriter(sw));
        StringBuilder sb = new StringBuilder();
        sb.append("Exception Stack Trace: " + "\n");
        sb.append(sw.toString());

        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nConcurrentModificationException happened. Try again.");
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\n" + sb.toString());
        logger.info("ConcurrentModificationException happened. Try again.");
        logger.info(sb.toString());
        } catch (Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        StringBuilder sb = new StringBuilder();
        sb.append("Exception Stack Trace: " + "\n");
        sb.append(sw.toString());;

        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nException " + e.getClass().getSimpleName() + " happened. Try again.");
        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\n" + sb.toString());
        logger.info("Exception " + e.getClass().getSimpleName() + " happened. Try again.");
        logger.info(sb.toString());
        }
        }
        }
        sc.setAttribute("sesMap", sesMap);

        int pSize = 0, sSize = 0;
        if (prins != null)
        pSize = prins.size();
        if (sesMap != null)
        sSize = sesMap.size();

        logger.info("Total logged-in users in sessionRegistry: " + pSize);
        logger.info("Total logged-in sessions in ServletContext: " + sSize);
//        logger.info("Total logged-in users: " + sessionRegistry.getAllPrincipals().size());
//     logger.debug("Total logged-in users: " + sessionRegistry.getAllPrincipals().size());
        logger.info("List of logged-in users in sessionRegistry: ");
//     logger.debug("List of logged-in users: ");
        for (Object username: prins) {
//        for (Object username: sessionRegistry.getAllPrincipals()) {
            logger.info(username);
//            logger.debug(username);
        logger.info("Total sessions including expired ones in sessionRegistry: " + 
               sessionRegistry.getAllSessions(username, true).size());
        logger.info("Total sessions in sessionRegistry: " + 
               sessionRegistry.getAllSessions(username, false).size());
        }
//        logger.info("Total sessions including expired ones: " + 
//     logger.debug("Total sessions including expired ones: " + 
//               sessionRegistry.getAllSessions(sessionRegistry.getAllPrincipals().get(0), true).size());
//        logger.info("Total sessions: " + 
//     logger.debug("Total sessions: " + 
//               sessionRegistry.getAllSessions(sessionRegistry.getAllPrincipals().get(0), false).size());

        ModelAndView mv = new ModelAndView("admin/viewSessions", "sesMap", sesMap);

        List<SessionInformation> sesList = new ArrayList<SessionInformation>();
        for (Object prin : prins) {
        sesList.addAll(sessionRegistry.getAllSessions(prin, false));
        }

        List<AdminSessionInfo> sesList1 = new ArrayList<AdminSessionInfo>();
        if (sesMap != null) {
        for (String key : sesMap.keySet()) {
        sesList1.add(sesMap.get(key));
        }
        }

        // Attach to model list of users and granted authorities
        model.addAttribute("prins", prins);
        model.addAttribute("totPrins", prins.size());
        model.addAttribute("sesList", sesList);
        model.addAttribute("sesListSize", sesList.size());

//        model.addAttribute("sesList1", sesList1);
//        model.addAttribute("sesList1Size", sesList1.size());
*/

        ModelAndView mv = JTUtils.getViewSessionsMV();
//        ModelAndView mv = new ModelAndView("admin/viewSessions", "sesMap", sesMap);
        // This will resolve to /WEB-INF/jsp/userspage.jsp
        return mv;
//        return "userspage";
 }
    
}
