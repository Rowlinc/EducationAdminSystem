package com.morgan.aduAdmSys.entity;

//学生信息管理表
public class Student {
    private int id;
    private String name;
    private String sex;
    private String password;
    private int classId;

    public Student() {
    }

    public Student(int id, String name, String password, String sex, int classId) {
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.password = password;
        this.sex = sex;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "学生信息----" +
                "|学生号:" + id +
                "|学生姓名:" + name  +
                "|学生性别:" + sex  +
                "|密码:" + password  +
                "|班级号:" + classId +
                '|';
    }
}
