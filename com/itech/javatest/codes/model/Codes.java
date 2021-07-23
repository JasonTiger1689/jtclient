/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itech.javatest.codes.model;

import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 *
 * @author jhl
 */
@XmlRootElement
public class Codes {
    
    int id;
    String name;
    String code;
    String comment;
    String description;
    String dispOrder;
    String grpName;
    String enterby;
    String enterdate;
    String status;
    String statusdate;

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDispOrder(String dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getDispOrder() {
        return dispOrder;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    public String getEnterby() {
        return enterby;
    }

    public void setEnterdate(String enterdate) {
        this.enterdate = enterdate;
    }

    public String getEnterdate() {
        return enterdate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusdate(String statusdate) {
        this.statusdate = statusdate;
    }

    public String getStatusdate() {
        return statusdate;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    
}
