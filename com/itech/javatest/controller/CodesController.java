/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Configurable;

import com.itech.javatest.codes.model.Codes;
import com.itech.javatest.codes.dao.CodesDAO;
import com.itech.javatest.codes.service.CodesService;

/**
 *
 * @author jhl
 */
@Controller
@RequestMapping("/codes.htm")
@Configurable(autowire = Autowire.BY_TYPE)
public class CodesController {

    @Autowired
    @Qualifier("codesService")
    CodesService codesService;

    @RequestMapping(params="actor=view")
    public ModelAndView viewCodes(@RequestParam Integer ID) {
        return new ModelAndView("codes/editcodes", "code", codesService.getCodes("ID = " + ID));
    }

    @RequestMapping(params="actor=listName")
    public ModelAndView listCodes(@RequestParam String name) {
        return new ModelAndView("codes/listcodes", "codes", 
                codesService.getCodesList("NAME = '" + name + "' and STATUS = 'ACTIVE'")).addObject("codename", name);
    }

    @RequestMapping(params="actor=listCodes")
    public ModelAndView listCodes(@ModelAttribute("codes") List<Codes> c) {
        return new ModelAndView("codes/listcodes", "codes", c);
    }

    @RequestMapping(params="actor=listActive")
    public ModelAndView listActiveCodes() {
        return new ModelAndView("codes/listcodes", "codes", codesService.getAllActiveCodes());
    }

    @RequestMapping(params="actor=listAll")
    public ModelAndView listAllCodes() {
        return new ModelAndView("codes/listcodes", "codes", codesService.getAllCodes());
    }

/*
    @RequestMapping(params="actor=addCodes")
    public ModelAndView addCodes() {
        return new ModelAndView("codes/addcodes", "code", new Codes());
    }
*/

    @RequestMapping(params="actor=addCodes")
    public ModelAndView addCodes() {
        return new ModelAndView("codes/editcodes", "code", new Codes());
    }

    @RequestMapping(params="actor=Insert")
    public ModelAndView insertCodes(@ModelAttribute("code") Codes c) throws Exception {
        codesService.insertCodes(c);
//        return new ModelAndView("redirect:codes.htm?actor=listCodes", "codes", codesService.insertCodes(c));
//        return this.listActiveCodes();
        return this.listCodes(c.getName());
    }
    
    @RequestMapping(params="actor=editCodes")
    public ModelAndView editCodes(@RequestParam Integer ID) {
        return new ModelAndView("codes/editcodes", "code", codesService.getCodes("ID = " + ID));
    }

    @RequestMapping(params="actor=Update")
    public ModelAndView updateCodes(@ModelAttribute("code") Codes c) throws Exception {
        codesService.updateCodes(c);
//        return new ModelAndView("redirect:codes.htm?actor=listCodes", "codes", codesService.updateCodes(c));
//        return this.listActiveCodes();
        return this.listCodes(c.getName());
    }

    @RequestMapping(params="actor=Delete")
    public ModelAndView deleteCodes(@ModelAttribute("code") Codes c) throws Exception {
        codesService.deleteCodes(c);
//        return new ModelAndView("redirect:codes.htm?actor=listCodes", "codes", codesService.deleteCodes(c));
//        return this.listActiveCodes();
        return this.listCodes(c.getName());
    }
    
}
