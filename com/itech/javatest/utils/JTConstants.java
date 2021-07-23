/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.utils;

import java.util.regex.Pattern;

/**
 *
 * @author jhl
 */
public class JTConstants {

    public static String SEPS = ",; ";

    public static String DELIM = ";";

    public static Pattern DELIMITERS1 = Pattern.compile(",\\s*|;\\s*");
//    String delimiters = "\\s+|,\\s*|\\.\\s*";

    public static Pattern DELIMITERS2 = Pattern.compile("\\s+|,\\s*|;\\s*");
//    String delimiters = "\\s+|,\\s*|\\.\\s*";

    public static String OUTDATETIME = "yyyy-MM-dd HH:mm:ss a z";

    public static final Pattern SQLSELECT = Pattern.compile("\\s*SELECT\\s+.+\\s+FROM\\s+.*", 
           Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public final static String TIMESTAMPS = 
"TIMESTAMP <= DATE_SUB(NOW(), INTERVAL (%d) DAY) ";

    public final static String TIMESTAMPE = 
"TIMESTAMP >= DATE_SUB(NOW(), INTERVAL (%d) DAY)) ";

    public static final String PERSON_LF = 
           "SELECT lastfirst FROM nist.personinfo WHERE PEOPLE_ID = ";
    public static final String PERSON_LF_PA = 
           "SELECT lastfirst FROM nist.personinfo WHERE PEOPLE_ID = ? ";
    public static final String PERSON_FL = 
           "SELECT FIRSTLAST FROM nist.personinfo WHERE PEOPLE_ID = ";
    public static final String PERSON_FL_PA = 
           "SELECT FIRSTLAST FROM nist.personinfo WHERE PEOPLE_ID = ? ";
    public static final String PEOPLE_LIST = 
           "SELECT people_id as VALUE, lastfirst as TEXT FROM nist.personinfo ";

    public final static String GETCODES = 
          "NAME = ? AND status = 'ACTIVE' ";

    public final static String GETCODES_CO = 
          "NAME = ? AND status = 'ACTIVE' ORDER BY CODE ";

    public final static String GETCODES_DO = 
          "NAME = ? AND status = 'ACTIVE' ORDER BY DISPORDER ";

    public final static String ANTSAPP_COND = 
          "NAME = 'ANTSAPP' AND status = 'ACTIVE' ";

    public final static String LOGIN_EVENT_COND = 
          "NAME = 'LOGIN_EVENT' AND status = 'ACTIVE' ";

    public final static String EVEMTYPE_COND = 
          "NAME = 'EVEMTYPE' AND status = 'ACTIVE' ";

    public final static String VERSTYPE_COND = 
          "NAME = 'VERSTYPE' AND status = 'ACTIVE' ";

    public static String ASPECTSERVICES = 
"SELECT DISTINCT(CLASSNAME) AS SERVICENAME FROM nist.aspectlog ";

    public static String SCH_EVEM_COND = 
"((ACTIVE <=> 'true') AND !(COMPLETE <=> 'true') AND (ANTSAPP = ?)) ";

    public static String MAINTAIN_COND = 
"((ACTIVE <=> 'true') AND !(COMPLETE <=> 'true') AND ((EVENTSTART <= NOW()) AND (NOW() <= EVENTEND)) AND (ANTSAPP = ?)) ";

    public static String DEL_SEQNUM_COND = 
"(!(STATUS <=> 'ACTIVE')) ";

    public final static String SOURCE_COND = 
          "NAME = 'SOURCE' AND status = 'ACTIVE' ";

    public final static String LOCATION_COND = 
          "NAME = 'LOCATION' AND status = 'ACTIVE' ";

    public final static String TAG_TYPE_COND = 
          "NAME = 'TAG_TYPE' AND status = 'ACTIVE' ";

    public final static String ASSET_TYPE_COND = 
          "NAME = 'ASSET_TYPE' AND status = 'ACTIVE' ";

}

/*

    public final static String SSP_LIST = 
          "SELECT distinct(PLANID) FROM nist.ssplan WHERE SSP_ACTIVE <=> 'true' ORDER BY PLANID ASC ";

    public final static String SSP_LIST_DIV = 
          "SELECT distinct(PLANID) FROM nist.ssplan WHERE SSP_ACTIVE <=> 'true' ";

    public final static String ASSIGN_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE = 'ASSIGNED' ";

    public final static String EU_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'ASSIGNED' AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'ASSIGNED' ";

    public final static String EU_STATUS_COND_NOT = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'ASSIGNED' AND CODE != ";

    public final static String OU_STATUS_COND_TR = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'SIIRT|SECURITY|UNCLAIMED|APPROVED|TEAM' ";

    public final static String OU_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'SIIRT|SECURITY|UNCLAIMED|APPROVED|TRANSFERRED|TEAM' AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'SIIRT|SECURITY|UNCLAIMED|APPROVED|TRANSFERRED|TEAM' ";
//          "NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'SIIRT|NETWORK|UNCLAIMED|APPROVED'";
//          "NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'SIIRT|ANTS|NETWORK'";

    public final static String OU_STATUS_COND_NOT = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'SIIRT|SECURITY|UNCLAIMED|APPROVED|TRANSFERRED|TEAM' AND CODE != ";

    public final static String DT_STATUS_COND_TR = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'OU|SIIRT|NETWORK|REN|EXCESSED|UNCLAIMED|APPROVED' ";

    public final static String DT_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'OU|SIIRT|NETWORK|REN|EXCESSED|UNCLAIMED|APPROVED|TRANSFERRED' AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'OU|SIIRT|SECURITY|APPROVED|TRANSFERRED|REJECT' ";

    public final static String DT_STATUS_COND_NOT = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'OU|SIIRT|NETWORK|REN|EXCESSED|UNCLAIMED|APPROVED|TRANSFERRED' AND CODE != ";

    public final static String NT_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND (CODE REGEXP 'NETWORK|ASSIGNED' AND CODE NOT REGEXP 'SECURITY|NETWORKED') AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND (CODE REGEXP 'NETWORK|ASSIGNED' AND CODE NOT REGEXP 'SECURITY') ";

    public final static String NT_STATUS_COND_NOT = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND (CODE REGEXP 'NETWORK|ASSIGNED' AND CODE NOT REGEXP 'SECURITY|NETWORKED') AND CODE != ";

    public final static String AA_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'ASSIGNED' AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'ASSIGNED' ";

    public final static String NS_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND (CODE REGEXP 'NETWORK|ASSIGNED' AND CODE NOT REGEXP 'TEAM|NETWORKED') AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND (CODE REGEXP 'NETWORK|ASSIGNED' AND CODE NOT REGEXP 'TEAM') ";

    public final static String CCF_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'SIIRT|SECURITY|TEAM' AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'REJECT' ";

    public final static String SIIRT_STATUS_COND_TR = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'OU|SECURITY|APPROVED|TEAM' ";

    public final static String SIIRT_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'OU|SECURITY|APPROVED|TRANSFERRED|TEAM' AND CODE != ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE NOT REGEXP 'OU|SECURITY|UNCLAIMED|APPROVED|TRANSFERRED|TEAM' ";

    public final static String EMAIL_STATUS_COND = 
"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'ASSIGNED|APPROVED|TRANSFERRED|REJECT|REN' AND CODE != 'UNASSIGNED' ";
//"NAME = 'ANTSID_STATUS' AND STATUS = 'ACTIVE' AND CODE REGEXP 'ASSIGNED|APPROVED|TRANSFERRED|REJECT|REN' AND CODE NOT REGEXP 'UNASSIGNED' AND CODE != ";

    public final static String OUDG_STATUS_COND = 
          "NAME = 'OUDG' AND status = 'ACTIVE' ";

    public final static String ANTSID_STATUS_COND = 
          "NAME = 'ANTSID_STATUS' AND status = 'ACTIVE' ";

    public final static String OUDEL_LEVEL_COND = 
          "NAME = 'OUDELEGATE_LEVEL' AND status = 'ACTIVE' ";

//    public static final String OS_LIST = 
//           "SELECT ostype as VALUE, ostype as TEXT FROM nist.ostype ";
    public static final String OS_ID = 
           "SELECT ostype_id FROM nist.ostype ";

*/
