/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.codes.dao.impl;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.itech.javatest.utils.JTUtils;

import com.itech.javatest.codes.model.Codes;
import com.itech.javatest.codes.dao.CodesDAO;
import com.itech.javatest.utils.db.impl.JTJDBCImpl;

/**
 *
 * @author jhl
 */
public class CodesDAOImpl extends JTJDBCImpl implements CodesDAO {

    @Override
    public Codes getCodes(String condition) {

        Codes c = null;
//        String condLow = null;
        if ((condition != null) && (condition.length() > 0)) {
            c = (Codes) this.jdbcTemplate.queryForObject(
                "select * from codes where " + condition, new CodesMapper());
        }

        return c;
    }

    @Override
    public Codes getCodes(Codes c) {
        
        StringBuilder sb = new StringBuilder();
        
        if (c.getID() > 0)
            sb.append("ID = " + c.getID());
        
        return (sb.length() > 0) ? this.getCodes(sb.toString()) : null;
    }

    @Override
    public List<Codes> getCodesList(String condition) {

        String where = "";
        String condLow = null;
        List<Codes> c = null;
        if ((condition != null) && (condition.length() > 0))
        condLow = condition.toLowerCase();

        System.out.println("select index = " + condLow.indexOf("select"));
        System.out.println("condLow = " + condLow);

        if ((condLow.indexOf("select") == 0) && (condLow.indexOf("from") > 8)) 
//        if (JTConstants.SQLSELECT.matcher(condition).matches()) 
            c = this.jdbcTemplate.query(condition, new CodesMapper());
        else {
            where = "where " + condition;
//            where = "where " + condition + " ORDER BY DESCRIPTION ";
        c = this.jdbcTemplate.query(
                "select * from codes " + where, new CodesMapper());
        }

//        List<Codes> c = new ArrayList<Codes>();
//        c.addAll(c1);
        return c;
    }

    @Override
    public List<Codes> getCodesList(Codes c) {

        StringBuilder sb = new StringBuilder();
        sb.append("UPPER(STATUS) = 'ACTIVE' ");
        
        if ((c.getName() != null) && (c.getName().length() > 0))
            sb.append("and NAME = '" + c.getName() + "' ");

//        List<Codes> c1 = new ArrayList<Codes>();
//        c1.addAll(this.getCodesList(sb.toString()));
//        return c1;
        return this.getCodesList(sb.toString());
    }

    @Override
    public List<Codes> getAllActiveCodes() {
//        List<Codes> c1 = new ArrayList<Codes>();
//        c1.addAll(this.getCodesList(new Codes()));
//        return c1;
        return this.getCodesList(new Codes());
    }

    @Override
    public List<Codes> getAllCodes() {
//        List<Codes> c1 = new ArrayList<Codes>();
//        c1.addAll(this.getCodesList(""));
//        return c1;
        return this.getCodesList("");
    }

    @Override
    public List<Codes> insertCodes(Codes c) {
        
        int rows = this.jdbcTemplate.update(
            "insert into codes " + 
            "(name,code,comment,description,disporder,grpname,enterby,enterdate,status,statusdate) " + 
            "values (NULLIF(?,''), NULLIF(?,''), NULLIF(?,''), NULLIF(?,''), NULLIF(?,''), " + 
                    "NULLIF(?,''), NULLIF(?,''), now(), 'ACTIVE', now())",
                    c.getName(),
                    c.getCode(),
                    c.getComment(),
                    c.getDescription(),
                    c.getDispOrder(),
                    c.getGrpName(),
                    c.getEnterby());
        
//        List<Codes> c1 = new ArrayList<Codes>();
//        c1.addAll(this.getCodesList(c));
//        return c1;
        return this.getCodesList(new Codes());
    }

    @Override
    public List<Codes> updateCodes(Codes c) {
        
        int rows = this.jdbcTemplate.update(
            "update codes set name = NULLIF(?,''), code = NULLIF(?,''), comment = NULLIF(?,''), " + 
            "description = NULLIF(?,''), disporder = NULLIF(?,''), grpname = NULLIF(?,'') where id = ?",
                    c.getName(),
                    c.getCode(),
                    c.getComment(),
                    c.getDescription(),
                    c.getDispOrder(),
                    c.getGrpName(),
                    c.getID());
        
//        List<Codes> c1 = new ArrayList<Codes>();
//        c1.addAll(this.getCodesList(c));
//        return c1;
        return this.getCodesList(new Codes());
    }

    @Override
    public List<Codes> deleteCodes(int id) {

        Codes c = this.getCodes("ID = " + id);
        int rows = this.jdbcTemplate.update(
                "update codes set STATUS = 'INACTIVE', STATUSDATE = NOW() " + 
                "where ID = ?",
                    id);

//        List<Codes> c1 = new ArrayList<Codes>();
//        c1.addAll(this.getCodesList(c));
//        return c1;
        return this.getCodesList(new Codes());
    }

    @Override
    public List<Codes> deleteCodes(Codes c) {
        
        int rows = this.jdbcTemplate.update(
                "update codes set STATUS = 'INACTIVE', STATUSDATE = NOW() " + 
                "where ID = ?",
                    c.getID());

//        List<Codes> c1 = new ArrayList<Codes>();
//        c1.addAll(this.getCodesList(c));
//        return c1;
        return this.getCodesList(new Codes());
    }

    private final class CodesMapper implements RowMapper<Codes> {

        @Override
        public Codes mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Codes c = new Codes();
            c.setID(rs.getInt("ID"));
            c.setName(rs.getString("NAME"));
            c.setCode(rs.getString("CODE"));
            c.setComment(rs.getString("COMMENT"));
            c.setDescription(rs.getString("DESCRIPTION"));
            c.setDispOrder(rs.getString("DISPORDER"));
            c.setGrpName(rs.getString("GRPNAME"));
            c.setEnterby(rs.getString("ENTERBY"));
            c.setEnterdate(rs.getString("ENTERDATE"));
            c.setStatus(rs.getString("STATUS"));
            c.setStatusdate(JTUtils.processDT(rs.getString("STATUSDATE")));

            return c;
        }
    }
    
}
