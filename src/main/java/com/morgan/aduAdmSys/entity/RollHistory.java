package com.morgan.aduAdmSys.entity;

import java.sql.Date;

// 学籍历史记录实体类
public class RollHistory {
    private int studentId;
    private String studentStatus;
    private Date entranceTime;
    private Date graduateTime;
    private Date leaveTime;

    public RollHistory() {
    }

    public RollHistory(int studentId, Date entranceTime, String studentStatus, Date graduateTime, Date leaveTime) {
        this.studentId = studentId;
        this.entranceTime = entranceTime;
        this.studentStatus = studentStatus;
        this.graduateTime = graduateTime;
        this.leaveTime = leaveTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Date getEntranceTime() {
        return entranceTime;
    }

    public void setEntranceTime(Date entranceTime) {
        this.entranceTime = entranceTime;
    }

    public Date getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(Date graduateTime) {
        this.graduateTime = graduateTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    @Override
    public String toString() {
        return "学籍变更记录----" +
                "|学生学号:" + studentId +
                "|状态:" + studentStatus +
                "|入学时间:" + entranceTime +
                "|应毕业时间:" + graduateTime +
                "|退学时间:" + leaveTime +
                '|';
    }
}
