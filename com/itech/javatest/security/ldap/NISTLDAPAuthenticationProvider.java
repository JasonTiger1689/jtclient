package com.itech.javatest.security.ldap;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Date;
import java.text.SimpleDateFormat;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

//import gov.nist.antsclient.utils.*;

//import gov.nist.antsadmin.admin.model.AdminUser;
//import gov.nist.antsadmin.admin.model.AdminRole;
//import gov.nist.antsadmin.admin.dao.AdminDAO;
//import gov.nist.antsadmin.admin.service.AdminService;
import com.itech.javatest.utils.JTConstants;

/**
 *
 * @author jhl
 */
@Component
@Configurable(autowire = Autowire.BY_TYPE)
public class NISTLDAPAuthenticationProvider implements AuthenticationProvider {

//    @Autowired
//    @Qualifier("adminService")
//    AdminService adminService;

// Another "injected" object. This allows us to use the information that's
	// part of any incoming request.
	// We could, for example, get header information, or the requestor's
	// address.
    @Context
    Request req;

    @Context
    HttpServletRequest request;
//    Request request;

/*
    @Value( "${ldap.authenticationMode}" )
    String authenticationMode;
    @Value( "${ldap.contextFactory}" )
    String contextFactory;
    @Value( "${ldap.providerURL}" )
    String providerURL;
    @Value( "${ldap.searchBase}" )
    String searchBase;
    @Value( "${ldap.suffix}" )
    String suffix;
    @Value( "${ldap.providerType}" )
    String providerType;
*/

    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
//        String username = request.getParameter("j_username");
//        String password = request.getParameter("j_password");

        Authentication auth = null;

        StringBuilder sb = new StringBuilder();

        ADConnection adc = new ADConnection();
//        adc.loadProperties(authenticationMode, contextFactory, providerURL, searchBase, suffix, providerType);

//        AdminUser nu = null;

        if ((username == null) || (username.length() == 0))
            sb.append("Username cannot be empty\n");
        if ((password == null) || (password.length() == 0))
            sb.append("Password cannot be empty\n");

        if (sb.length() == 0) {
        if (adc.authenticate(username, password)) {
//            nu = adminService.loadUserByUsername(username);
/*
        if (nu == null) {
            throw new BadCredentialsException("User Not Found in Database!");
        } else {

//        authentication.setAuthenticated(true);
//            model.put("user", nu);
//            session.setAttribute("user", nu);

//        Collection<? extends GrantedAuthority> authorities = nu.getAuthorities();
        GrantedAuthority [] ar = new GrantedAuthority[1];
        if (nu.getAuthorities() != null) {
        ar = nu.getAuthorities().toArray(ar);
        } else {
//        AdminRole a = new AdminRole();
//        a.setName("ROLE_USER");
        GrantedAuthority ga = new AdminRole();
        ((AdminRole)ga).setName("ROLE_USER");
//        ar = new GrantedAuthority[1];
        ar[0] = ga;
        }
//        List<AntsRole> roles = new ArrayList<AntsRole>();
//        List<GrantedAuthority> authorities = nu.getAuthorities();
//        List<GrantedAuthority> authorities = CollectionUtils.arrayToList(ar);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nInjected HttpServletRequest = '" + request + "'");
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nInjected Request = '" + req + "'");
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nInjected HttpServletRequest = '" + request.toString() + "'");
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nInjected Request = '" + req.toString() + "'");
//        HttpServletRequest req2 = (HttpServletRequest) req;
//        System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
//                "\nCasted HttpServletRequest = '" + req2.toString() + "'");

        auth = new UsernamePasswordAuthenticationToken(username, password, AUTHORITIES);
//        auth.setAuthenticated(true);

        if (SecurityContextHolder.getContext() != null) {
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSecurityContextHolder.getContext() is not null");
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        SecurityContextHolder.;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
        System.out.println(new SimpleDateFormat(JTConstants.OUTDATETIME).format(new Date()) + 
                "\nSecurityContextHolder.getContext().getAuthentication() is not null");
//        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
        }
        }
        }
*/
        } else {
            throw new BadCredentialsException("Invalid username and/or password!");
        }
//        } else {
//            model.put("error", sb.toString());
//        mv.addObject("error", sb.toString());
        }

        return auth;
//        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }

    @Override
//    public boolean supports(Class<?> arg0) {
    public boolean supports(Class<?> authentication) {
//        return true;

        if ( authentication == null ) return false;

        return Authentication.class.isAssignableFrom(authentication);
    }
    
}
