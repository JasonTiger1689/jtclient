/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.aspect;

import java.io.*;
import java.util.*;

import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author jhl
 */
@Aspect
public class AspectJTLogger {

    private static final Logger logger = Logger.getLogger(AspectJTLogger.class);

    protected DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
//	@Around("execution(* com.mkyong.customer.bo.CustomerBo.addCustomerAround(..))")
/*
        public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {

            logger.info("******");

            logger.info("logAround() is running!");
            logger.info("Data Source = " + dataSource);

            joinPoint.proceed();
            
            logSave(joinPoint);
            logger.info("******");

	}
*/
        
//	@After("execution(* com.mkyong.customer.bo.CustomerBo.addCustomer(..))")
    public void logAfter(JoinPoint joinPoint) throws Throwable {
	
        logger.info("******");

        logger.info("logAfter() is running!");
        logger.info("Data Source = " + dataSource);

        logSave(joinPoint);
        logger.info("******");

    }

    public void logSave(JoinPoint joinPoint) throws Throwable {

        int count = 0;
        try {
            count = this.jdbcTemplate.update(
                    "insert into mbl_request_log (request_log_id, class_name, method_name, " + 
                    "request_parameters, date_created) " + 
                    "values (mbl_request_log_seq.nextval, ?, ?, ?, sysdate)", 
                    new Object[] { joinPoint.getTarget().getClass().getSimpleName(), 
                        joinPoint.getSignature().getName(), 
                        ToStringBuilder.reflectionToString(joinPoint.getArgs()) });

            if (count > 0)
                logger.info("MBL_REQUEST_LOG record inserted " + count + " rows.");

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.info(sw);
        }
    }
    
}
