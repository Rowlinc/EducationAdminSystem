package com.morgan.aduAdmSys.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//学籍管理表
//校验格式
public class Roll {
    private String status;
    private int studentId;
    private Date entranceTime;
    private Date graduateTime;
    private Date leaveTime;

    public Roll() {
    }

    public Roll(String status, int studentId, String entranceTime, String graduateTime, String leaveTime) {
        this.status = status;
        this.studentId = studentId;
        this.entranceTime = Date.valueOf(entranceTime);
        this.graduateTime = Date.valueOf(graduateTime);
        this.leaveTime = Date.valueOf(leaveTime);
    }

    //    格式必须为yyyy-MM-dd
    public void setStringEntranceTime(String entranceTime){
        this.entranceTime=Date.valueOf(entranceTime);
    }

    public void setDateEntranceTime(Date entranceTime){
        this.entranceTime=entranceTime;
    }

    public Date getDateEntranceTime() {
        return entranceTime;
    }

    public String getStringEntranceTime(){
        LocalDate localDate=entranceTime.toLocalDate();
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDateGraduateTime() {
        return graduateTime;
    }

    public String getStringGraduateTime(){
        LocalDate localDate=graduateTime.toLocalDate();
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setStringGraduateTime(String graduateTime) {
        this.graduateTime = Date.valueOf(graduateTime);
    }

    public void setDateGraduateTime(Date graduateTime){
        this.graduateTime=graduateTime;
    }

    public Date getDateLeaveTime() {
        return leaveTime;
    }

    public String getStringLeaveTime(){
        LocalDate localDate=leaveTime.toLocalDate();
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setStringLeaveTime(String leaveTime) {
        this.leaveTime = Date.valueOf(leaveTime);
    }

    public void setDateLeaveTime(Date leaveTime){
        this.leaveTime=leaveTime;
    }

    @Override
    public String toString() {
        return "学籍信息----" +
                "|状态:" + status  +
                "|学生号:" + studentId +
                "|入学时间:" + entranceTime +
                "|毕业时间:" + graduateTime +
                "|退休时间:" + leaveTime +
                '|';
    }
}
