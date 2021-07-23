/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.codes.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.itech.javatest.codes.model.Codes;
import com.itech.javatest.codes.dao.CodesDAO;
import com.itech.javatest.codes.service.CodesService;

/**
 *
 * @author jhl
 */
@Service
public class CodesServiceImpl implements CodesService {

    @Autowired
    @Qualifier("codesDao")
    CodesDAO codesDao;

    @Override
    public Codes getCodes(String condition) {
        return codesDao.getCodes(condition);
    }

    @Override
    public Codes getCodes(Codes c) {
        return codesDao.getCodes(c);
    }

    @Override
    public List<Codes> getCodesList(String condition) {
        return codesDao.getCodesList(condition);
    }

    @Override
    public List<Codes> getCodesList(Codes c) {
        return codesDao.getCodesList(c);
    }

    @Override
    public List<Codes> getAllActiveCodes() {
        return codesDao.getAllActiveCodes();
    }

    @Override
    public List<Codes> getAllCodes() {
        return codesDao.getAllCodes();
    }

    @Override
    public List<Codes> insertCodes(Codes c) {
        return codesDao.insertCodes(c);
    }

    @Override
    public List<Codes> updateCodes(Codes c) {
        return codesDao.updateCodes(c);
    }

    @Override
    public List<Codes> deleteCodes(int id) {
        return codesDao.deleteCodes(id);
    }

    @Override
    public List<Codes> deleteCodes(Codes c) {
        return codesDao.deleteCodes(c);
    }
    
}
