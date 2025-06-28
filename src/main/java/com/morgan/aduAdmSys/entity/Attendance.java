package com.morgan.aduAdmSys.entity;//package org.example.entity;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
////出勤信息
////记得校验输入格式
//public class Attendance {
//    private int teacherId;
//    private String arriveTime;
//    private String leaveTime;
////    private Date date;
//
//    public Attendance() {
//    }
//
//    public Attendance(int teacherId, String arriveTime, String leaveTime, String date) {
//        this.teacherId = teacherId;
//        this.arriveTime = Time.valueOf(arriveTime);
//        this.leaveTime = Time.valueOf(leaveTime);
//        this.date = Date.valueOf(date);
//    }
//
//    public int getTeacherId() {
//        return teacherId;
//    }
//
//    public void setTeacherId(int teacherId) {
//        this.teacherId = teacherId;
//    }
//
//    public Time getTimeArriveTime() {
//        return arriveTime;
//    }
//
//    public String getStringArriveTime(){
//        LocalTime localTime=arriveTime.toLocalTime();
//        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
//    }
//
//    public void setStringArriveTime(String arriveTime) {
//        this.arriveTime = Time.valueOf(arriveTime);
//    }
//
//    public void setTimeArriveTime(Time arriveTime){
//        this.arriveTime=arriveTime;
//    }
//
//    public Time getTimeLeaveTime() {
//        return leaveTime;
//    }
//
//    public String getStringLeaveTime(){
//        LocalTime localTime=leaveTime.toLocalTime();
//        return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
//    }
//
//    public void setStringLeaveTime(String leaveTime) {
//        this.leaveTime = Time.valueOf(leaveTime);
//    }
//
//    public void setTimeLeaveTime(Time leaveTime){
//        this.leaveTime=leaveTime;
//    }
//
//    public Date getDateDate() {
//        return date;
//    }
//
//    public String getStringDate(){
//        LocalDate localDate=date.toLocalDate();
//        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
//    }
//
//    public void setStringDate(String date) {
//        this.date = Date.valueOf(date);
//    }
//
//    public void setDateDate(Date date){
//        this.date=date;
//    }
//
//    @Override
//    public String toString() {
//        return "出勤{" +
//                "教师号:" + teacherId +
//                ", 上班时间:" + arriveTime +
//                ", 下班时间:" + leaveTime +
//                ", 日期:" + date +
//                '}';
//    }
//}
