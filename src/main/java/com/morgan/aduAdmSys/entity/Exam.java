package com.morgan.aduAdmSys.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//考试信息
//校验格式
public class Exam {
    private int courseId;
    private int classroomId;
    private int num;
    private Date date;
    private int classId;

    public Exam() {
    }

    public Exam(int classroomId, int courseId, int num, String date, int classId) {
        this.classroomId = classroomId;
        this.courseId = courseId;
        this.num = num;
        this.date = Date.valueOf(date);
        this.classId = classId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public Date getDateDate() {
        return date;
    }

    public String getStringDate(){
        LocalDate localDate=date.toLocalDate();
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public void setStringDate(String date) {
        this.date = Date.valueOf(date);
    }

    public void setDateDate(Date date){
        this.date=date;
    }

    @Override
    public String toString() {
        return "考试信息----" +
                "|课程号:" + courseId +
                "|教室号:" + classroomId +
                "|课节:" + num +
                "|日期:" + date +
                "|班级号:" + classId +
                '|';
    }
}
