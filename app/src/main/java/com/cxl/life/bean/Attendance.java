package com.cxl.life.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by cxl on 2017/9/12.
 * 考勤
 */

public class Attendance extends DataSupport {
//    @Column(unique = true)  唯一性
    private String dateTime;//时间
    private String name;//姓名
    private String number;//编号
    private int state = 0;//状态 0是正常 1是迟到  2是早退

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
