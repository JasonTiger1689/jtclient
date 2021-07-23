/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.utils.db.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.itech.javatest.utils.db.JTJDBC;

/**
 *
 * @author jhl
 */
@Repository
@Configurable(autowire = Autowire.BY_TYPE)
public class JTJDBCImpl implements JTJDBC {

    protected DataSource dataSource;
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    @Override
    public void setDataSource(@Qualifier("dataSource_JT") DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
}
