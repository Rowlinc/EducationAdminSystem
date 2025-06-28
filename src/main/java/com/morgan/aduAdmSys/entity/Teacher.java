package com.morgan.aduAdmSys.entity;

//教师信息管理表
public class Teacher {
    private int id;
    private String name;
    private String password;

    public Teacher(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Teacher() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "老师----" +
                "|教师号:" + id +
                "|名字:'" + name  +
                "|密码:" + password  +
                '|';
    }
}
