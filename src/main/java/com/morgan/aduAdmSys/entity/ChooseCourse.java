package com.morgan.aduAdmSys.entity;

public class ChooseCourse {
    private int studentId;
    private int normalScore;
    private int experimentScore;
    private int stageScore;
    private int testScore;
    private int courseId;
    private double averageScore;

    public ChooseCourse() {
    }

    public ChooseCourse(int studentId, int experimentScore, int normalScore, int stageScore, int testScore, int courseId,double averageScore) {
        this.studentId = studentId;
        this.experimentScore = experimentScore;
        this.normalScore = normalScore;
        this.stageScore = stageScore;
        this.testScore = testScore;
        this.courseId = courseId;
        this.averageScore=averageScore;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getNormalScore() {
        return normalScore;
    }

    public void setNormalScore(int normalScore) {
        this.normalScore = normalScore;
    }

    public int getExperimentScore() {
        return experimentScore;
    }

    public void setExperimentScore(int experimentScore) {
        this.experimentScore = experimentScore;
    }

    public int getStageScore() {
        return stageScore;
    }

    public void setStageScore(int stageScore) {
        this.stageScore = stageScore;
    }

    public int getTestScore() {
        return testScore;
    }

    public void setTestScore(int testScore) {
        this.testScore = testScore;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public String toString() {
        return "选课信息----" +
                "|学生号:" + studentId +
                "|平时成绩:" + normalScore +
                "|实验成绩:" + experimentScore +
                "|阶段成绩:" + stageScore +
                "|期末成绩:" + testScore +
                "|课程号:" + courseId +
                "|总评成绩:" + averageScore +
                '|';
    }
}
