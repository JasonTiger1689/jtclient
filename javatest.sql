
CREATE SCHEMA `test` ;

use test;

DROP TABLE IF EXISTS `codes`;
CREATE TABLE `codes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(28) DEFAULT NULL,
  `code` varchar(168) DEFAULT NULL,
  `comment` varchar(268) DEFAULT NULL,
  `description` varchar(188) DEFAULT NULL,
  `disporder` int(11) DEFAULT NULL,
  `grpname` varchar(28) DEFAULT NULL,
  `enterby` varchar(18) DEFAULT NULL,
  `enterdate` datetime DEFAULT NULL,
  `status` varchar(8) DEFAULT NULL,
  `statusdate` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CODES_NAME` (`NAME`),
  KEY `CODES_NC` (`NAME`,`CODE`)
) ;

INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('APPREALM','admin',NULL,'admin',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('APPREALM','client',NULL,'client',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('BOOLEAN','TRUE',NULL,'True',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('BOOLEAN','FALSE',NULL,'False',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('COUNTRY','USA',NULL,'United States of America',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('GENDER','M',NULL,'Male',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('GENDER','F',NULL,'Female',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LANGUAGE','ENG',NULL,'English',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LANGUAGE','CHT',NULL,'繁體',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LANGUAGE','CHN',NULL,'简体',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LOGIN_EVENT','Login',NULL,'Login',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LOGIN_EVENT','Logout',NULL,'Logout',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LOGIN_EVENT','Visiting',NULL,'Visiting',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LOGIN_EVENT','Session Timeout',NULL,'Session Timeout',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LOGIN_EVENT','User Not Found',NULL,'User Not Found',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('LOGIN_EVENT','Login Fail',NULL,'Login Fail',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','OR',NULL,'Oregon',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','WA',NULL,'Washington',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','VA',NULL,'Virginia',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','SC',NULL,'South Carolina',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','NY',NULL,'New York',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','NJ',NULL,'New Jersey',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','MD',NULL,'Maryland',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','DC',NULL,'District of Columbia',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','CO',NULL,'Colorado',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATE','CA',NULL,'California',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATUS','ACTIVE',NULL,'Active',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('STATUS','INACTIVE',NULL,'Inactive',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('EVEMTYPE','SOFTWARE UPDATES',NULL,'Software Updates',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('EVEMTYPE','DATABASE REFRESH',NULL,'Database Refresh',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('VERSTYPE','NO',NULL,'No Change',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('VERSTYPE','MAJOR',NULL,'Major Change',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('VERSTYPE','MINOR',NULL,'Minor Change',NULL,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('IPACCESSTYPE','WEB',NULL,'Web Access',1,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());
INSERT INTO `codes` (`NAME`,`CODE`,`COMMENT`,`DESCRIPTION`,`DISPORDER`,`GRPNAME`,`ENTERBY`,`ENTERDATE`,`STATUS`,`STATUSDATE`) VALUES ('IPACCESSTYPE','SCAN',NULL,'App Scan',2,NULL,'SUPERMAN',NOW(),'ACTIVE',NOW());

DROP TABLE IF EXISTS `seqnum`;
CREATE TABLE `seqnum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seqname` varchar(68) DEFAULT NULL,
  `seqvalue` int(11) DEFAULT NULL,
  `description` varchar(168) DEFAULT NULL,
  `enterby` varchar(88) DEFAULT NULL COMMENT 'Person who enters this record',
  `enterdate` datetime DEFAULT NULL,
  `status` varchar(8) DEFAULT NULL,
  `status_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `SEQNUM_NAME_UN` (`seqname`),
  KEY `SEQNUM_NAME` (`seqname`)
) ;

DROP TABLE `allowips`;
CREATE TABLE `allowips` (
  `allowipsid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Table ID, Auto Increment',
  `ip` varbinary(16) NOT NULL COMMENT 'IP address, Start, can be IPv4 or IPv6',
  `ip2` varbinary(16) DEFAULT NULL COMMENT 'IP address, End, can be IPv4 or IPv6',
  `accesstype` varchar(18) DEFAULT NULL COMMENT 'Access Type of IP Address',
  `description` varchar(268) DEFAULT NULL COMMENT 'Description',
  `active` varchar(6) DEFAULT NULL COMMENT 'Is active?',
  `enterby` varchar(88) DEFAULT NULL COMMENT 'Enter By Person',
  `changedate` datetime DEFAULT NULL COMMENT 'Change DateTime',
  PRIMARY KEY (`allowipsid`),
  KEY `IPS_IP` (`ip`),
  KEY `IPS_AT` (`accesstype`),
  KEY `IPS_IP2` (`ip`,`ip2`)
) ;

CREATE OR REPLACE VIEW `ipsview` as 
SELECT ALLOWIPSID,INET6_NTOA(IP) AS IP,INET6_NTOA(IP2) AS IP2,ACCESSTYPE, 
DESCRIPTION,ACTIVE,ENTERBY, DATE_FORMAT(DATE(CHANGEDATE),'%m/%d/%Y') AS CHANGEDATE 
FROM allowips ;

DROP TABLE IF EXISTS `taskemail`;
CREATE TABLE `taskemail` (
  `taskemailid` int(11) NOT NULL AUTO_INCREMENT,
  `tasktype` varchar(68) DEFAULT NULL,
  `taskstart` datetime DEFAULT NULL,
  `taskend` datetime DEFAULT NULL,
  `sendby` varchar(88) DEFAULT NULL,
  `subject` varchar(268) DEFAULT NULL,
  `sendto` blob,
  `message` blob,
  `send_ts` datetime DEFAULT NULL,
  `active` varchar(6) DEFAULT NULL,
  `sent` varchar(6) DEFAULT NULL,
  `vertype` varchar(6) DEFAULT NULL,
  `vermajor` int(11) DEFAULT NULL,
  `verminor` int(11) DEFAULT NULL,
  `details` varchar(5568) DEFAULT NULL,
  `complete` VARCHAR(6) DEFAULT NULL,
  PRIMARY KEY (`taskemailid`),
  UNIQUE KEY `taskemailid_UNIQUE` (`taskemailid`),
  KEY `EE_ET` (`tasktype`),
  KEY `EE_DTS` (`taskstart`),
  KEY `EE_DTE` (`taskend`),
  KEY `EE_DTA` (`taskstart`,`taskend`),
  KEY `EE_SB` (`sendby`),
  KEY `EE_VT` (`vertype`)
) ;

CREATE OR REPLACE VIEW `teview` AS 
SELECT *, CONCAT(VERMAJOR, '.', LPAD(VERMINOR, 3, '0')) AS VERSION, DATE_FORMAT(TASKSTART,'%m/%d/%Y') AS TASKDATE, 
DATE_FORMAT(TASKSTART,'%m/%d/%Y %H:%i') AS STARTDT, DATE_FORMAT(TASKEND,'%m/%d/%Y %H:%i') AS ENDDT 
FROM taskemail 
;

DROP TABLE IF EXISTS jtuser;
CREATE TABLE `jtuser` (
  `jtuserid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Table ID, Auto Increment',
  `last_name` varchar(88) NOT NULL,
  `first_name` varchar(88) NOT NULL,
  `mid_name` varchar(128) NOT NULL,
  `alt_last_name` varchar(128) DEFAULT NULL,
  `alt_first_name` varchar(128) DEFAULT NULL,
  `isadmin` varchar(6) NOT NULL,
  `user_type` varchar(88) NOT NULL,
  `issuspend` varchar(6) NOT NULL,
  `isduepaid` varchar(6) NOT NULL,
  `member_type` varchar(88) NOT NULL,
  `member_date` datetime DEFAULT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `pusername` varchar(128) NOT NULL,
  `ppassword` varchar(168) NOT NULL,
  PRIMARY KEY (`jtuserid`),
  KEY `JTU_UT` (`user_type`),
  KEY `JTU_MT` (`member_type`)
) ;

CREATE OR REPLACE VIEW `jtuview` AS 
SELECT *, 
trim(concat_ws(', ',ifnull(alt_last_name,last_name),ifnull(alt_first_name,first_name))) as lastfirst, 
trim(concat_ws(' ',ifnull(alt_first_name,first_name),ifnull(alt_last_name,last_name))) as firstlast, 
concat_ws(' ',trim(concat_ws(' ',first_name,nullif(mid_name,'nmn'))),last_name) as firstmidlast 
FROM jtuser ;

DROP TABLE IF EXISTS jtemail;
CREATE TABLE `jtemail` (
  `jtemailid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Table ID, Auto Increment',
  `jtreferid` int(11) NOT NULL COMMENT 'Table Reference ID, Foreign Key',
  `refer_type` varchar(88) NOT NULL,
  `email_type` varchar(88) NOT NULL,
  `email_name` varchar(88) NOT NULL,
  `email_address` varchar(168) DEFAULT NULL,
  `isprimary` varchar(6) DEFAULT NULL,
  `enterby` varchar(88) DEFAULT NULL COMMENT 'Person who enters this record',
  `enterdate` datetime DEFAULT NULL,
  PRIMARY KEY (`jtemailid`),
  KEY `JTE_RID` (`jtreferid`),
  KEY `JTE_ET` (`email_type`)
) ;

DROP TABLE IF EXISTS jtphone;
CREATE TABLE `jtphone` (
  `jtphoneid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Table ID, Auto Increment',
  `jtreferid` int(11) NOT NULL COMMENT 'Table Reference ID, Foreign Key',
  `refer_type` varchar(88) NOT NULL,
  `phone_type` varchar(88) NOT NULL,
  `phone_name` varchar(88) NOT NULL,
  `phone_number` varchar(28) DEFAULT NULL,
  `isprimary` varchar(6) DEFAULT NULL,
  `enterby` varchar(88) DEFAULT NULL COMMENT 'Person who enters this record',
  `enterdate` datetime DEFAULT NULL,
  PRIMARY KEY (`jtphoneid`),
  KEY `JTP_RID` (`jtreferid`),
  KEY `JTP_PT` (`phone_type`)
) ;

DROP TABLE IF EXISTS jtaddress;
CREATE TABLE `jtaddress` (
  `jtaddressid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Table ID, Auto Increment',
  `jtreferid` int(11) NOT NULL COMMENT 'Table Reference ID, Foreign Key',
  `refer_type` varchar(88) NOT NULL,
  `address_type` varchar(88) NOT NULL,
  `address_name` varchar(88) NOT NULL,
  `address1` varchar(168) DEFAULT NULL,
  `address2` varchar(168) DEFAULT NULL,
  `address3` varchar(168) DEFAULT NULL,
  `cityname` varchar(168) DEFAULT NULL,
  `statename` varchar(168) DEFAULT NULL,
  `country` varchar(168) DEFAULT NULL,
  `isprimary` varchar(6) DEFAULT NULL,
  `enterby` varchar(88) DEFAULT NULL COMMENT 'Person who enters this record',
  `enterdate` datetime DEFAULT NULL,
  PRIMARY KEY (`jtaddressid`),
  KEY `JTA_RID` (`jtreferid`),
  KEY `JTA_PT` (`address_type`)
) ;

DROP TABLE IF EXISTS jtcompany;
CREATE TABLE `jtcompany` (
  `jtcompanyid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Table ID, Auto Increment',
  `jtreferid` int(11) NOT NULL COMMENT 'Table Reference ID, Foreign Key',
  `refer_type` varchar(88) NOT NULL,
  `company_type` varchar(88) NOT NULL,
  `company_name` varchar(88) NOT NULL,
  `address1` varchar(168) DEFAULT NULL,
  `address2` varchar(168) DEFAULT NULL,
  `address3` varchar(168) DEFAULT NULL,
  `cityname` varchar(168) DEFAULT NULL,
  `statename` varchar(168) DEFAULT NULL,
  `country` varchar(168) DEFAULT NULL,
  `isprimary` varchar(6) DEFAULT NULL,
  `issuspend` varchar(6) NOT NULL,
  `isduepaid` varchar(6) NOT NULL,
  `member_type` varchar(88) NOT NULL,
  `member_date` datetime DEFAULT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `enterby` varchar(88) DEFAULT NULL COMMENT 'Person who enters this record',
  `enterdate` datetime DEFAULT NULL,
  PRIMARY KEY (`jtcompanyid`),
  KEY `JTC_RID` (`jtreferid`),
  KEY `JTC_MT` (`member_type`),
  KEY `JTC_PT` (`company_type`)
) ;


