/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.utils.db;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author jhl
 */
public interface JTJDBC {

    public void setDataSource(DataSource dataSource);

    public DataSource getDataSource();

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate);

    public JdbcTemplate getJdbcTemplate();
    
}
