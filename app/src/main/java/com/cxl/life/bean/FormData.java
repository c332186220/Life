package com.cxl.life.bean;

/**
 * Created by cxl on 2017/9/30.
 * 表单数据
 */

public class FormData {

    private String padId; //主键id
    private String LOOP_NAME; //回路名称
    private String FIRST_VALUE=""; //第一个值
    private String SECOND_VALUE=""; //第二个值
    private String LIGHT_GRADE; //光级
    private String PART; //所属分块
    private String OID; //记录表主键
    private String TYPE; //字段类型1：一个值；2两个值；3：两个值加加光级（上方显示）；4：三个值 ; 5：一个值加光级（上方显示）
    private int SORT; //排序字段
    private int FID; //灯光回路字段信息表主键id
    private String FIRST_NAME; //第一个值得名称，比如：电压
    private String SECOND_NAME; //第二个值得名称，比如：电流
    private String isInScope = "0";//-1 低于 0 在范围内  1 高于

    public String getPadId() {
        return padId;
    }

    public void setPadId(String padId) {
        this.padId = padId;
    }

    public String getLOOP_NAME() {
        return LOOP_NAME;
    }

    public void setLOOP_NAME(String LOOP_NAME) {
        this.LOOP_NAME = LOOP_NAME;
    }

    public String getFIRST_VALUE() {
        return FIRST_VALUE;
    }

    public void setFIRST_VALUE(String FIRST_VALUE) {
        this.FIRST_VALUE = FIRST_VALUE;
    }

    public String getSECOND_VALUE() {
        return SECOND_VALUE;
    }

    public void setSECOND_VALUE(String SECOND_VALUE) {
        this.SECOND_VALUE = SECOND_VALUE;
    }

    public String getLIGHT_GRADE() {
        return LIGHT_GRADE;
    }

    public void setLIGHT_GRADE(String LIGHT_GRADE) {
        this.LIGHT_GRADE = LIGHT_GRADE;
    }

    public String getPART() {
        return PART;
    }

    public void setPART(String PART) {
        this.PART = PART;
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public int getSORT() {
        return SORT;
    }

    public void setSORT(int SORT) {
        this.SORT = SORT;
    }

    public int getFID() {
        return FID;
    }

    public void setFID(int FID) {
        this.FID = FID;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public String getSECOND_NAME() {
        return SECOND_NAME;
    }

    public void setSECOND_NAME(String SECOND_NAME) {
        this.SECOND_NAME = SECOND_NAME;
    }

    public String getIsInScope() {
        return isInScope;
    }

    public void setIsInScope(String isInScope) {
        this.isInScope = isInScope;
    }
}
