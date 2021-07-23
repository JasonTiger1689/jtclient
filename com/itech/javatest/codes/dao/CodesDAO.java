/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.codes.dao;

import java.util.List;
import javax.sql.DataSource;

import com.itech.javatest.codes.model.Codes;

/**
 *
 * @author jhl
 */
public interface CodesDAO {

    public void setDataSource(DataSource dataSource);

    public Codes getCodes(String condition);

    public Codes getCodes(Codes c);

//    public List<Codes> getCodesListSQL(String sql);

    public List<Codes> getCodesList(String condition);

    public List<Codes> getCodesList(Codes c);

    public List<Codes> getAllActiveCodes();

    public List<Codes> getAllCodes();

    public List<Codes> insertCodes(Codes c);

    public List<Codes> updateCodes(Codes c);

    public List<Codes> deleteCodes(int id);

    public List<Codes> deleteCodes(Codes c);
    
}
