package com.itech.javatest.security.ldap;

//import gov.nist.nais.exception.AuthenticatorException;
//import gov.nist.nais.configuration.NaisAppProperties;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;
//import auth.v1a.types.*;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

/**
 *
 * @author jhl
 */
@Component
@Configurable(autowire = Autowire.BY_TYPE)
public class ADConnection {

	/*
	  GENS.providerType=AD
	  GENS.providerURL=ldaps://129.6.83.54
	  GENS.searchBase=OU=NISTUSERS,DC=GENDEV,DC=NIST,DC=GOV
	  GENS.suffix=@gendev.nist.gov
	  GENS.authenticationMode=simple
	  GENS.contextFactory=com.sun.jndi.ldap.LdapCtxFactory
	 */

	private static final Logger logger = Logger.getLogger(ADConnection.class);

	static final private String distinguishedName = "distinguishedName";
	static final private String loginQueryName = "sAMAccountName";
	static final private String displayName = "displayName";
	static final private String userAccountControl = "userAccountControl";
	static final private String mail = "mail";
	static final private String telephoneNumber = "telephoneNumber";
	static final private String ou = "ou";
	static final private String division = "division";
	static final private String roomNumber = "roomNumber";
	static final private String dirId = "employeeNumber";
	static final private String location = "l";
	static final private String memberOf = "memberOf";
	static final private String[] attrLst = {distinguishedName, displayName,userAccountControl,
		mail,telephoneNumber,roomNumber,ou,division,dirId,location,memberOf};
	static final private String AE_USER_NOT_FOUND = "AcceptSecurityContext error, data 525";
	static final private String AE_INVALID_CREDENTIALS = "AcceptSecurityContext error, data 52e";
	static final private String AE_TIME_RESTRICTION = "AcceptSecurityContext error, data 530";
	static final private String AE_PASSWORD_EXPIRED = "AcceptSecurityContext error, data 532";
	static final private String AE_ACCOUNT_DISABLED = "AcceptSecurityContext error, data 533";
	static final private String AE_ACCOUNT_EXPIRED = "AcceptSecurityContext error, data 701";
	static final private String AE_MUST_RESET_PASSWORD = "AcceptSecurityContext error, data 773";
	static final private String AE_ACCOUNT_LOCKED = "AcceptSecurityContext error, data 775";

//	private String authenticationMode = "simple";
//	private String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
//	private String providerURL = "ldaps://genldapdev.nist.gov";
//	private String providerURL = "ldaps://gendev.nist.gov";
//	private String providerURL = "ldap://gendev.nist.gov";
//	private String searchBase = "OU=NISTUSERS,DC=GENDEV,DC=NIST,DC=GOV";
//	private String suffix = "@gendev.nist.gov";
//	private String providerType = "AD";
/*
        private String authenticationMode;
	private String contextFactory;
	private String providerURL;
	private String searchBase;
	private String suffix;
	private String providerType;
*/
/*
//    @Value( "${ldap.authenticationMode}" )
    String authenticationMode;
//    @Value( "${ldap.contextFactory}" )
    String contextFactory;
//    @Value( "${ldap.providerURL}" )
    String providerURL;
//    @Value( "${ldap.searchBase}" )
    String searchBase;
//    @Value( "${ldap.suffix}" )
    String suffix;
//    @Value( "${ldap.providerType}" )
    String providerType;
*/
/*
*/
//  @Value( "#{siteProps['ldap.authenticationMode']}" )
    String authenticationMode;
//  @Value( "#{siteProps['ldap.contextFactory']}" )
    String contextFactory;
//  @Value( "#{siteProps['ldap.providerURL']}" )
    String providerURL;
//  @Value( "#{siteProps['ldap.searchBase']}" )
    String searchBase;
//  @Value( "#{siteProps['ldap.suffix']}" )
    String suffix;
//  @Value( "#{siteProps['ldap.providerType']}" )
    String providerType;

        public ADConnection() {
		logger.debug("loaded ADConnection");
		loadProperties();
	}
	
	private void loadProperties () {
		
//	  authenticationMode = NaisAppProperties.getProperty("AD_authenticationMode", "simple");
//	  contextFactory = NaisAppProperties.getProperty("AD_contextFactory", "com.sun.jndi.ldap.LdapCtxFactory");
//	  providerURL = NaisAppProperties.getProperty("AD_providerURL", "ldaps://genldapdev.nist.gov");
//	  searchBase = NaisAppProperties.getProperty("AD_searchBase", "OU=NISTUSERS,DC=GENDEV,DC=NIST,DC=GOV");
//	  suffix = NaisAppProperties.getProperty("AD_suffix", "@gendev.nist.gov");
//	  providerType = NaisAppProperties.getProperty("AD_providerType", "AD");

	}
	
	public void loadProperties (String authenticationMode, String contextFactory, 
                String providerURL, String searchBase, String suffix, String providerType) {
		
	  this.authenticationMode = authenticationMode;
	  this.contextFactory = contextFactory;
	  this.providerURL = providerURL;
	  this.searchBase = searchBase;
	  this.suffix = suffix;
	  this.providerType = providerType;

	}

	private String setPrincipal (String pUsername) {
		return ( pUsername + suffix);
	}

	private DirContext bind (String pLogin, String pPassword) throws Exception
	{
		if ((pLogin == null)||(pLogin.equals(""))) throw (new AuthenticationException("999: Missing Username!"));
		if ((pPassword == null)||(pPassword.equals(""))) throw (new AuthenticationException("999: Missing Password!"));
		DirContext ctx = null;
		String principal = null;
		String status = null;
		principal = setPrincipal (pLogin);
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		env.put(Context.PROVIDER_URL,providerURL);
		env.put(Context.SECURITY_AUTHENTICATION, authenticationMode);
		env.put(Context.SECURITY_PRINCIPAL, principal);
		env.put(Context.SECURITY_CREDENTIALS, pPassword);

		try {
			ctx = new InitialDirContext(env);
			if( ctx == null ) {
				status = "Failed: null ctx returned!";
			}
		} catch( AuthenticationException ae ) {
                    ae.printStackTrace();
			String r = ae.getExplanation();
			if (r.indexOf(AE_USER_NOT_FOUND)>0) status = "525: User Not Found!";
			else if (r.indexOf(AE_INVALID_CREDENTIALS)>0) status = "52e: Invalid Credentials!";
			else if (r.indexOf(AE_TIME_RESTRICTION)>0) status = "530: Not permitted to logon at this time!";
			else if (r.indexOf(AE_PASSWORD_EXPIRED)>0) status = "532: Password Expired!";
			else if (r.indexOf(AE_ACCOUNT_DISABLED)>0) status = "533: Account Disabled!";
			else if (r.indexOf(AE_ACCOUNT_EXPIRED)>0) status = "701: Account Expired!";
			else if (r.indexOf(AE_MUST_RESET_PASSWORD)>0) status = "773: Must Reset Password!";
			else if (r.indexOf(AE_ACCOUNT_LOCKED)>0) status = "775: Account Locked!";
			else status = "999: AuthenticationException reason unknown" + r;
		} catch( NamingException ne ) {
			ne.printStackTrace();
			status = ne.toString();
		}
		if (status != null) throw (new AuthenticationException(status));
		return ctx;
	}  // bind
/*
   public String authenticate (String pLogin, String pPassword) {
     String status;
     try {
       DirContext ctx0;
       ctx0 = bind(pLogin, pPassword);
       ctx0.close();
       status = "000: OK";
     } catch(Exception e) {
       status = e.getMessage();
     }
     System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nADConnection.authenticate[" + pLogin + "]: return=[" + status + "]");
     return status;
   }
*/
//	public boolean authenticate (String pLogin, String pPassword) throws AuthenticatorException {
	public boolean authenticate (String pLogin, String pPassword) {
		boolean status;
		try {
			DirContext ctx0;
			ctx0 = bind(pLogin, pPassword);
			ctx0.close();
			status = true;
			logger.debug("ADConnection.authenticate[" + pLogin + "]: return=[" + status + "]");
		} catch(Exception e) {
                    e.printStackTrace();
			status = false;
			logger.debug("ADConnection.authenticate[" + pLogin + "]: return=[" + e.getMessage() + "]");
			//throw new AuthenticatorException();
		}
		return status;
	}

/*
   public UserProfile getProfile (String pLogin, String pPassword) {
     UserProfile uf;
     try {
       DirContext ctx0;
       ctx0 = bind(pLogin, pPassword);
       uf = getUserProfile(pLogin, ctx0);
       uf.setAuthenticated(true);
       uf.setMessage("Authenticated!");
       ctx0.close();
     } catch(Exception e) {
       uf = new UserProfile(); uf.setUsername(pLogin);
       uf.setAuthenticated(false);
       uf.setMessage(e.getMessage());
     }
//     System.out.println(new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nADConnection.getProfile[" + pLogin + "]: return=[" + uf.getMessage() + "]");
     return uf;
   }

   private UserProfile getUserProfile (String pLogin, DirContext pCtx) {
     UserProfile uf = new UserProfile(); uf.setUsername(pLogin);
     try {
       SearchControls ctls = new SearchControls();
       ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
       ctls.setReturningAttributes(attrLst);
       String searchStr = "(" + loginQueryName + "=" + pLogin + ")";
       NamingEnumeration answer = pCtx.search(searchBase,searchStr,ctls);
       if (answer.hasMore())
       {
         SearchResult sr = (SearchResult)answer.next();
         Attributes dnAttrs = sr.getAttributes();
         if (dnAttrs != null)
         {
           NamingEnumeration attrEnum = dnAttrs.getAll();
           while (attrEnum.hasMore())
           {
             BasicAttribute ba = (BasicAttribute)attrEnum.next();
             String id = ba.getID();
             String val = ba.toString().substring(id.length()+2);
             if (id.equals(distinguishedName)) uf.setDn(val);
             if (id.equals(mail)) uf.setMail(val);
             if (id.equals(displayName)) uf.setDisplayName(val);
             if (id.equals(telephoneNumber)) uf.setTelephone(val);
             if (id.equals(roomNumber)) uf.setOffice(val);
             if (id.equals(ou)) uf.setOu(val);
             if (id.equals(division)) uf.setDivision(val);
             if (id.equals(dirId)) uf.setDirId(val);
             if (id.equals(location)) uf.setLocation(val);

             if (id.equals(memberOf)) { // memberOf: multi value attribute
               NamingEnumeration valEnum = ba.getAll();
               String[] mVal = new String[ba.size()];
               for (int count=0; count<mVal.length; count++) {
                 mVal[count] = extractCn((String)valEnum.next());
               }
               uf.setMemberOf(mVal);
             }

           }
         }
       }
//     uf.setStatus("000: OK");
       return uf;
   //
   //  } catch( PartialResultException re ) {
   //     System.out.println (new SimpleDateFormat(AntsAdminConstants.OUTDATETIME).format(new Date()) + 
                "\nADConnection.getUserProfile: search failed=" + pLogin);
   //     uf.setStatus("999: User '" + pLogin + "' unknown!!!");
   //
     } catch (Exception ex) {
       uf.setAuthenticated(false);
       uf.setMessage("999: ADConnection.getUserProfileException:" + ex.toString());
       ex.printStackTrace();
     }
     return (uf);
   } //getUserProfile
*/
	private String extractCn(String pDn) {
		if (pDn.startsWith("cn=") || pDn.startsWith("CN="))
			return pDn.substring(3,pDn.indexOf(","));
		else
			return "";
	}
    
}
