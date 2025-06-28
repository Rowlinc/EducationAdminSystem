package com.morgan.aduAdmSys.entity;

//教室信息
public class Classroom {
    private int id;
    private int capacity;

    public Classroom() {
    }

    public Classroom(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "教室信息----" +
                "|教室号:" + id +
                "|教室容量:" + capacity +
                '|';
    }
}
