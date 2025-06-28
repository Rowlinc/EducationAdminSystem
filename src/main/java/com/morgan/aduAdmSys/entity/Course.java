package com.morgan.aduAdmSys.entity;

//课程信息管理表
public class Course {
    private int id;
    private String name;
    private int teacherId;
    private int maxStudents;
    private int currentEnrollment;
    private int credits;

    public Course() {
    }

    public Course(int id, String name, int teacherId, int maxStudents, int currentEnrollment, int credits) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.maxStudents = maxStudents;
        this.currentEnrollment = currentEnrollment;
        this.credits = credits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public int getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(int currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "课程信息----" +
                "|课程号:" + id +
                "|课程名:" + name +
                "|授课教师id:" + teacherId +
                '|';
    }
}
