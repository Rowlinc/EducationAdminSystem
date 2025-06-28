package com.morgan.aduAdmSys.entity;

import java.sql.Time;

//课程表
//校验格式
public class Schedule {
    private int courseId;
    private int classId;
    private int weekday;
    private Time startTime;
    private Time endTime;
    private int classroomId;

    public Schedule() {
    }

    public Schedule(int courseId, int classId, int weekday, String startTime, String endTime,int classroomId) {
        this.courseId = courseId;
        this.classId = classId;
        this.weekday = weekday;
        this.startTime=Time.valueOf(startTime);
        this.endTime=Time.valueOf(endTime);
        this.classroomId = classroomId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

//    把date获取为date
    public Time getTimeStartTime() {
        return startTime;
    }

//    把date获取为string
    public String getStringStartTime(){
        return startTime.toString();
    }
//    把String赋值date
    public void setStringStartTime(String startTime) {
        this.startTime=Time.valueOf(startTime);
    }

//    把date赋值date
    public void setTimeStartTime(Time startTime){
        this.startTime=startTime;
    }

    //    把date获取为date
    public Time getTimeEndTime() {
        return endTime;
    }

    //    把date获取为string
    public String getStringEndTime(){
        return endTime.toString();
    }
    //    把String赋值date
    public void setStringEndTime(String endTime) {
        this.endTime=Time.valueOf(endTime);
    }

    //    把date赋值date
    public void setTimeEndTime(Time endTime){
        this.endTime=endTime;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    @Override
    public String toString() {
        return "课程表----" +
                "|课程号:" + courseId +
                "|班级号:" + classId +
                "|日期:周" + weekday +
                "|时间:" + startTime +"-"+endTime+
                "|教室号:" + classroomId +
                '|';
    }
}
