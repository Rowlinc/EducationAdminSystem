package com.morgan.aduAdmSys.entity;

//班级信息管理表
public class MyClass {
    private int id;
    private int studentNumber;

    public MyClass() {
    }

    public MyClass(int id, int studentNumber) {
        this.id = id;
        this.studentNumber = studentNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "班级信息----" +
                "|班级号:" + id +
                "|班级人数:" + studentNumber +
                '|';
    }
}
