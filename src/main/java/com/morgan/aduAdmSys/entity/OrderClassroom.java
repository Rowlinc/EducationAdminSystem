package com.morgan.aduAdmSys.entity;

import java.sql.Timestamp;

//预约教室信息
public class OrderClassroom {
    private int classroomId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int teacherId;

    public OrderClassroom() {
    }

    public OrderClassroom(int classroomId, String startTime,String endTime, int teacherId) {
        this.classroomId = classroomId;
        this.startTime=Timestamp.valueOf(startTime);
        this.endTime=Timestamp.valueOf(endTime);
        this.teacherId=teacherId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public Timestamp getTimestampStartTime() {
        return startTime;
    }

    public String getStringStartTime(){
        return startTime.toString();
    }

    public void setStringStartTime(String startTime) {
        this.startTime = Timestamp.valueOf(startTime);
    }

    public void setTimestampStartTime(Timestamp startTime){
        this.startTime=startTime;
    }

    public Timestamp getTimestampEndTime() {
        return endTime;
    }

    public String getStringEndTime(){
        return endTime.toString();
    }

    public void setStringEndTime(String endTime) {
        this.endTime = Timestamp.valueOf(endTime);
    }

    public void setTimestampEndTime(Timestamp endTime){
        this.endTime=endTime;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "教室预约----" +
                "|教室号:" + classroomId +
                "|使用时间:" + startTime +"-"+endTime+
                "|班级号:" + teacherId +
                '|';
    }
}
