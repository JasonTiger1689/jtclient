/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.interceptor;

import java.util.*;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itech.javatest.utils.*;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author jhl
 */
public class JTInterceptor extends HandlerInterceptorAdapter {

    static Logger logger = Logger.getLogger(JTInterceptor.class);

    String badrequestPage;

    public static Pattern[] patterns = new Pattern[]{
        // Detecting SQL Injection
        // Regex for detecting SQL Injection meta-characters
//        Pattern.compile(".*/((\\%3D)|(=))[^\\n]*((\\%27)|(\')|(\\-\\-)|(\\%3B)|(;)).*/i", 
        Pattern.compile(".*/((\\%3D)|(=))[^\\n]*((\\%27)|(\')|(\\-\\-)|(\\%3B)|(;)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for detecting SQL Injection with the UNION keyword
//        Pattern.compile(".*/((\\%27)|(\'))union.*/i", 
        Pattern.compile(".*/((\\%27)|(\'))union.*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for typical SQL Injection attack
//        Pattern.compile(".*/\\w*((\\%27)|(\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52)).*/i", 
        Pattern.compile(".*/\\w*((\\%27)|(\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for typical SQL Injection attack
        Pattern.compile(".*(\'|%27).(and|or).(\'|%27)|(\'|%27).%7C{0,2}|%7C{2}.*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for typical SQL Injection attack
        Pattern.compile(".*(/\\*)[ ,;(%20)(%27)(%7C)(%26)(%5E)(%2B)(%2D)(%25)]*.*(\\*/).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // SQL Key Word for typical SQL Injection attack
//        Pattern.compile("\\s*((select.*from)|(delete\\s+from)|(insert\\s+into)|(update.+set)).*/i", 
        Pattern.compile("\\s*((select\\s.*from\\s)|(delete\\s+from\\s)|(insert\\s+into\\s)|(update\\s.+set\\s)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile("\\s*((drop\\s+table)|(drop\\s+database)|(alter\\s+table)|(alter\\s+database)).*/i", 
        Pattern.compile("\\s*((drop\\s+table\\s)|(drop\\s+database\\s)|(alter\\s+table\\s)|(alter\\s+database\\s)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile("\\s*((create\\s+table)|(create\\s+user)).*/i", 
        Pattern.compile("\\s*((create\\s+table\\s)|(create\\s+user\\s)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile("\\s*((exec)|(shutdown)|(\\bor\\b)).*/i", 
//        Pattern.compile("\\s*((exec)\\s+).*", 
//                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile("\\s*((delete(%20)+from)|(insert(%20)+into)).*/i", 
        Pattern.compile("\\s*((delete(%20)+from)|(insert(%20)+into)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile("\\s*((drop(%20)+table)|(drop(%20)+database)|(alter(%20)+table)|(alter(%20)+database)).*/i", 
        Pattern.compile("\\s*((drop(%20)+table)|(drop(%20)+database)|(alter(%20)+table)|(alter(%20)+database)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile("\\s*((create(%20)+table)|(create(%20)+user)).*/i", 
        Pattern.compile("\\s*((create(%20)+table)|(create(%20)+user)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // SQL Key Word for typical SQL Injection attack
//        Pattern.compile(".*((select.*from)|(insert\\s+into)|(update.+set)|(delete\\s+from)).*/i", 
        Pattern.compile(".*((select\\s.*from\\s)|(insert\\s+into\\s)|(update\\s.+set\\s)|(delete\\s+from\\s)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*((drop\\s+table)|(drop\\s+database)|(alter\\s+table)|(alter\\s+database)).*/i", 
        Pattern.compile(".*((drop\\s+table\\s)|(drop\\s+database\\s)|(alter\\s+table\\s)|(alter\\s+database\\s)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*((create\\s+table)|(create\\s+user)).*/i", 
        Pattern.compile(".*((create\\s+table\\s)|(create\\s+user\\s)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*((group\\s+by)|(order\\s+by)|having).*/i", 
//        Pattern.compile(".*((group\\s+by)|(order\\s+by)|having).*", 
//                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*((exec)|(shutdown)|(\\bor\\b)).*/i", 
        Pattern.compile(".*((exec)\\s+).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*((insert(%20)+into)|(delete(%20)+from)).*/i", 
        Pattern.compile(".*((insert(%20)+into)|(delete(%20)+from)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*((drop(%20)+table)|(drop(%20)+database)|(alter(%20)+table)|(alter(%20)+database)).*/i", 
        Pattern.compile(".*((drop(%20)+table)|(drop(%20)+database)|(alter(%20)+table)|(alter(%20)+database)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*((create(%20)+table)|(create(%20)+user)).*/i", 
        Pattern.compile(".*((create(%20)+table)|(create(%20)+user)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for Command Line Injection attack
//        Pattern.compile(".*[`|~(%60)(%7C)(%7E)(!!)(%21!)(!%21)]+.*", 
//                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(".*:[\\s(%20)(%08)(%09)(%0A)(%0B)(%0D)]*('|\"|‘).*('|\"|‘).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Script fragments
//        Pattern.compile(".*<script>.*</script>.*/i", 
        Pattern.compile(".*<script>.*</script>.*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // src='...'
//        Pattern.compile(".*src[\r\n]*=[\r\n]*\\\'(.*?)\\\'.*/i", 
        Pattern.compile(".*src[\r\n]*=[\r\n]*\\\'(.*?)\\\'.*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
//        Pattern.compile(".*src[\r\n]*=[\r\n]*\\\"(.*?)\\\".*/i", 
        Pattern.compile(".*src[\r\n]*=[\r\n]*\\\"(.*?)\\\".*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // lonely script tags
//        Pattern.compile(".*</script>.*/i", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*</script>.*", Pattern.CASE_INSENSITIVE),
//        Pattern.compile(".*<script(.*)>.*/i", 
        Pattern.compile(".*<script(.*)>.*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // eval(...)
//        Pattern.compile(".*eval\\((.*?)\\).*/i", 
        Pattern.compile(".*eval\\((.*?)\\).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // expression(...)
//        Pattern.compile(".*expression\\((.*?)\\).*/i", 
        Pattern.compile(".*expression\\((.*?)\\).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // javascript:...
//        Pattern.compile(".*javascript:.*/i", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*javascript:.*", Pattern.CASE_INSENSITIVE),
        // vbscript:...
//        Pattern.compile(".*vbscript:.*/i", Pattern.CASE_INSENSITIVE),
        Pattern.compile(".*vbscript:.*", Pattern.CASE_INSENSITIVE),
        // onload(...)=...
//        Pattern.compile(".*onload(.*?)=.*/i", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(".*onload(.*?)=.*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // HTML links Regular Expression Pattern
        // HTML A tag Regular Expression Pattern
//        Pattern.compile(".*(?i)<a([^>]+)>(.+?)</a>.*/i", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(".*(?i)<a([^>]+)>(.+?)</a>.*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Extract HTML link Regular Expression Pattern
//        Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+)).*/i", 
        Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+)).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for "<img src" CSS attack
//        Pattern.compile(".*/((\\%3C)|<)((\\%69)|i|(\\%49))((\\%6D)|m|(\\%4D))((\\%67)|g|(\\%47))[^\n]+((\\%3E)|>).*/i", 
        Pattern.compile(".*/((\\%3C)|<)((\\%69)|i|(\\%49))((\\%6D)|m|(\\%4D))((\\%67)|g|(\\%47))[^\n]+((\\%3E)|>).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for simple CSS attack
//        Pattern.compile(".*/((\\%3C)|<)((\\%2F)|\\/)*[a-z0-9\\%]+((\\%3E)|>).*/i", 
        Pattern.compile(".*/((\\%3C)|<)((\\%2F)|\\/)*[a-z0-9\\%]+((\\%3E)|>).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Paranoid regex for CSS attacks
//        Pattern.compile(".*/((\\%3C)|<)[^\\n]+((\\%3E)|>).*/i", 
        Pattern.compile(".*/((\\%3C)|<)[^\\n]+((\\%3E)|>).*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Java Exception Injection
//        Pattern.compile(".*Exception in thread.*/i", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(".*Exception in thread.*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // XPath Abbreviated Syntax Injection
        Pattern.compile("(/(@?[\\w_?\\w:\\*]+(\\+\\])*)?)+", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // XPath Expanded Syntax Injection
        Pattern.compile("/?(ancestor(-or-self)?|descendant(-or-self)?|following(-sibling))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // HTTP, HTTPS, FTP, FTPS, FILE
//        Pattern.compile(".*?((www\\.)?).*?((\\.com)|(\\.net))", 
//                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // HTTP, HTTPS, FTP, FTPS, FILE
//        Pattern.compile(".*?(http:|https:|ftp:|ftps:|file:).*?", 
//                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Server-Side Include Injection
        Pattern.compile("<!--\\s*<!--(include|exec|echo|config|printenv)\\s+.*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    public static Pattern[] exceptions = new Pattern[]{
        // XPath Abbreviated Syntax Injection
        Pattern.compile("/([1-9]|[1-9][0-9]|1[0-1][0-9]|12[0-8])$", 
                Pattern.CASE_INSENSITIVE)
    };

    public void setBadrequestPage(String badrequestPage) {
        this.badrequestPage = badrequestPage;
    }

    public String getBadrequestPage() {
        return badrequestPage;
    }

    //before the actual handler will be executed
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
           Object handler) throws Exception {

        boolean sent = false;
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> paras = request.getParameterMap();
        Set<String> names = paras.keySet();
        String[] values = new String[] {};

        for (String n : names){
            values = paras.get(n);
        for (String v : values){
        if ((v != null) && (v.length() > 0)) {
            // Check IF matches a pattern
            for (Pattern p : patterns){
                if(p.matcher(v).matches()) {
                System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMatched Parameter Name: " + n);
                System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMatched Parameter Value: " + v);
                System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMatched Pattern: " + p.pattern());

                sb.append("\n");
                sb.append(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMatched Parameter Name: " + n);
                sb.append("\n");
                sb.append(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMatched Parameter Value: " + v);
                sb.append("\n");
                sb.append(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMatched Pattern: " + p.pattern());
                sb.append("\n");

            for (Pattern e : exceptions){
                if(e.matcher(v).matches()) {
                //Matches exception pattern
                System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nMatched Exception: " + e.pattern());
                return true;
                }
            }
                //Matches bad request pattern
                sent = JTUtils.sendIntercept(request, request.getSession(), sb, badrequestPage);
                response.sendRedirect(badrequestPage);
                return false;
                }
            }
        }
        }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, 
            HttpServletResponse response, Object handler, Exception ex) throws Exception {

//        long startTime = (Long) request.getAttribute("startTime");
        logger.info("Request URL: " + request.getRequestURL().toString());
//        logger.info("Request URL::" + request.getRequestURL().toString()
//                + ":: End Time=" + System.currentTimeMillis());
//        logger.info("Request URL::" + request.getRequestURL().toString()
//                + ":: Time Taken=" + (System.currentTimeMillis() - startTime));

        logger.info("resetSessionAccessTime(" + request.getSession().getId() + ") is updated");

        JTUtils.resetSessionAccessTime(request.getSession());
    }

/*

    public static Pattern[] patterns = new Pattern[]{
        // Detecting SQL Injection
        // Regex for detecting SQL Injection meta-characters
        Pattern.compile("/((\\%3D)|(=))[^\\n]*((\\%27)|(\')|(\\-\\-)|(\\%3B)|(;))/i", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for detecting SQL Injection with the UNION keyword
        Pattern.compile("/((\\%27)|(\'))union/ix", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for typical SQL Injection attack
        Pattern.compile("/\\w*((\\%27)|(\'))((\\%6F)|o|(\\%4F))((\\%72)|r|(\\%52))/ix", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for typical SQL Injection attack
        Pattern.compile("(\'|%27).(and|or).(\'|%27)|(\'|%27).%7C{0,2}|%7C{2}", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // SQL Key Word for typical SQL Injection attack
        Pattern.compile("[\\s]*((select.*?from)|(delete\\s*from)|(insert\\s*into)|(update.*?set))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("[\\s]*((drop\\s*table)|(drop\\s*database)|(alter\\s*table)|(create\\s*table))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("[\\s]*((exec)|(shutdown)|(\\bor\\b))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // SQL Key Word for typical SQL Injection attack
        Pattern.compile(".*?(select.*?from|insert\\s*into|update.*?set|delete\\s*from)", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(".*?((drop\\s*table)|(drop\\s*database)|(alter\\s*table)|(create\\s*table))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(".*?(group\\s*by|order\\s*by|having)", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(".*?((exec)|(shutdown)|(\\bor\\b))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Script fragments
        Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // src='...'
        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // lonely script tags
        Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // eval(...)
        Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // expression(...)
        Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // javascript:...
        Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
        // vbscript:...
        Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
        // onload(...)=...
        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // HTML links Regular Expression Pattern
        // HTML A tag Regular Expression Pattern
        Pattern.compile("(?i)<a([^>]+)>(.+?)</a>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Extract HTML link Regular Expression Pattern
        Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for "<img src" CSS attack
        Pattern.compile("/((\\%3C)|<)((\\%69)|i|(\\%49))((\\%6D)|m|(\\%4D))((\\%67)|g|(\\%47))[^\n]+((\\%3E)|>)/i", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Regex for simple CSS attack
        Pattern.compile("/((\\%3C)|<)((\\%2F)|\\/)*[a-z0-9\\%]+((\\%3E)|>)/ix", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Paranoid regex for CSS attacks
        Pattern.compile("/((\\%3C)|<)[^\\n]+((\\%3E)|>)/I", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Java Exception Injection
        Pattern.compile(".*Exception in thread.*", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // XPath Abbreviated Syntax Injection
        Pattern.compile("(/(@?[\\w_?\\w:\\*]+(\\+\\])*)?)+", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // XPath Expanded Syntax Injection
        Pattern.compile("/?(ancestor(-or-self)?|descendant(-or-self)?|following(-sibling))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // Server-Side Include Injection
        Pattern.compile("<!--\\s*<!--(include|exec|echo|config|printenv)\\s+.*", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // HTTP, HTTPS, FTP, FTPS, FILE
        Pattern.compile(".*?((www\\.)?).*?((\\.com)|(\\.net))", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // HTTP, HTTPS, FTP, FTPS, FILE
        Pattern.compile(".*?http:|https:|ftp:|ftps:|file:.*?", 
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };
*/

}
