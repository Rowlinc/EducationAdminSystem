package com.morgan.aduAdmSys.function;

import com.morgan.aduAdmSys.dao.*;
import com.morgan.aduAdmSys.entity.*;
import com.morgan.aduAdmSys.utils.Encrypt;
import com.morgan.aduAdmSys.utils.Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Scanner;

public class TeacherManager {
    Scanner scanner=new Scanner(System.in);
    TeacherDao teacherDao=new TeacherDao();
    StudentDao studentDao=new StudentDao();
    ScheduleDao scheduleDao=new ScheduleDao();
    CourseDao courseDao=new CourseDao();
    OrderClassroomDao orderClassroomDao=new OrderClassroomDao();
    ChooseCourseDao chooseCourseDao=new ChooseCourseDao();
    AttendanceDao attendanceDao=new AttendanceDao();
    ClassroomDao classroomDao=new ClassroomDao();
    //    根据id修改教师密码
    public void changeTeacherPassword(Teacher teacher) throws NoSuchAlgorithmException {
        String password= Util.checkPassword(teacher.getPassword());
        if(password==null){
            System.out.println("修改失败");
            return;
        }
        teacherDao.updatePasswordById(teacher.getId(), password);
    }
    //    根据教师id查询课程表
    public void selectScheduleByTeacherId(int teacherId){
        if(!courseDao.isExistTeacher(teacherId)){
            System.out.println("您当前并未授课");
            return;
        }
        if(!scheduleDao.isExist(courseDao.selectCourseByTeacherId(teacherId).getId())){
            System.out.println("未查询到您的课表");
            return;
        }
        System.out.println("|================================================|");
        List<Schedule> list=scheduleDao.selectScheduleByTeacherId(teacherId);
        for(Schedule schedule:list){
            System.out.println("|"+schedule);
            System.out.println("|================================================|");
        }
    }
//    给学生录入成绩
    public void addScoreToStudent(int teacherId){
        ChooseCourse chooseCourse=new ChooseCourse();
        if(!courseDao.isExistTeacher(teacherId)){
            System.out.println("|================================================|");
            System.out.println("您当前并未授课");
            return;
        }
        Course course=courseDao.selectCourseByTeacherId(teacherId);
        int score,studentId;
        double averageScore=0;
        System.out.println("|================================================|");
        System.out.print("|请输入学生id:");
        studentId=scanner.nextInt();
        scanner.nextLine();
        if(!chooseCourseDao.isExist(studentId,course.getId())){
            System.out.println("|================================================|");
            System.out.println("|未查找到此学生");
            return;
        }
        Student student=studentDao.selectStudentById(studentId);
        chooseCourse.setStudentId(student.getId());
        System.out.println("|================================================|");
        System.out.print("|请输入平时成绩:");
        while(true){
            score=scanner.nextInt();
            scanner.nextLine();
            if(score<0||score>100){
                System.out.print("非法成绩,是否重新输入(yes/no)?");
                if(!scanner.nextLine().equalsIgnoreCase("yes"))return;
                else {
                    System.out.print("请输入平时成绩:");
                }
            }else {
                break;
            }
        }
        averageScore+=(score*0.1);
        System.out.println("|================================================|");
        chooseCourse.setNormalScore(score);
        System.out.print("|请输入实验成绩:");
        while(true){
            score=scanner.nextInt();
            scanner.nextLine();
            if(score<0||score>100){
                System.out.print("非法成绩,是否重新输入(yes/no)?");
                if(!scanner.nextLine().equalsIgnoreCase("yes"))return;
                else {
                    System.out.print("请输入实验成绩:");
                }
            }else {
                break;
            }
        }
        averageScore+=(score*0.2);
        System.out.println("|================================================|");
        chooseCourse.setExperimentScore(score);
        System.out.print("|请输入阶段成绩:");
        while(true){
            score=scanner.nextInt();
            scanner.nextLine();
            if(score<0||score>100){
                System.out.print("非法成绩,是否重新输入(yes/no)?");
                if(!scanner.nextLine().equalsIgnoreCase("yes"))return;
                else {
                    System.out.print("请输入阶段成绩:");
                }
            }else {
                break;
            }
        }
        averageScore+=(score*0.3);
        System.out.println("|================================================|");
        chooseCourse.setStageScore(score);
        System.out.print("|请输入期末成绩:");
        while(true){
            score=scanner.nextInt();
            scanner.nextLine();
            if(score<0||score>100){
                System.out.print("非法成绩,是否重新输入(yes/no)?");
                if(!scanner.nextLine().equalsIgnoreCase("yes"))return;
                else {
                    System.out.print("请输入期末成绩:");
                }
            }else {
                break;
            }
        }
        averageScore+=(score*0.4);
        System.out.println("|================================================|");
        chooseCourse.setTestScore(score);
        chooseCourse.setAverageScore(averageScore);
        chooseCourseDao.updateChooseCourseById(chooseCourse,teacherId);
        System.out.println("|================================================|");
    }
//    预约教室
    public void OrderClassroom(int teacherId) {
        OrderClassroom orderClassroom = new OrderClassroom();
        int num;
        String timestamp;
        System.out.println("|================================================|");
        System.out.print("|请输入要预约的教室号:");
        num = scanner.nextInt();
        scanner.nextLine();
        if (!classroomDao.isExist(num)){
            System.out.println("该教室不存在");
            return;
        }
        orderClassroom.setClassroomId(num);
        System.out.println("|================================================|");
        System.out.print("|请输入你要预约的起始时间(YYYY-MM-DD HH:MM:SS.fff):");
        timestamp=Util.getTimestampFormatString();
        orderClassroom.setStringStartTime(timestamp);
        System.out.println("|================================================|");
        System.out.print("|请输入你要预约的结束时间(YYYY-MM-DD HH:MM:SS.fff):");
        timestamp=Util.getTimestampFormatString();
        orderClassroom.setStringEndTime(timestamp);
        orderClassroom.setTeacherId(teacherId);
        orderClassroomDao.insertOrderClassroom(orderClassroom);
        System.out.println("|================================================|");
    }
    //    登录
    public Teacher login() throws NoSuchAlgorithmException {
        System.out.println("|================================================|");
        System.out.print("|请输入你的教师编号:");
        int teacherId=scanner.nextInt();
        scanner.nextLine();
        if (!teacherDao.isExist(teacherId)) {
            System.out.println("|================================================|");
            System.out.println("|WARNING:教师编号不存在");
            System.out.println("|================================================|");
            return null;
        }else{
            Teacher teacher=teacherDao.selectTeacherById(teacherId);
            System.out.print("|请输入密码:");
            if(Encrypt.encryptMD5(scanner.nextLine()).equals(teacher.getPassword())){
                System.out.println("|登录成功!");
                System.out.println("|================================================|");
                return teacher;
            }else {
                System.out.println("|WARNING:密码不正确");
                System.out.println("|================================================|");
                return null;
            }
        }
    }
//    上班打卡
    public void addComeTime(int teacherId){
        boolean result=attendanceDao.addStartById(teacherId);
        if(result){
            System.out.println("|================================================|");
            System.out.println("|打卡成功");
            System.out.println("|================================================|");
        }else {
            System.out.println("|================================================|");
            System.out.println("|打卡失败");
            System.out.println("|================================================|");
        }
    }
    //    下班打卡
    public void addLeaveTime(int teacherId){
        boolean result=attendanceDao.addEndById(teacherId);
        if(result){
            System.out.println("|================================================|");
            System.out.println("|打卡成功");
            System.out.println("|================================================|");
        }else {
            System.out.println("|================================================|");
            System.out.println("|打卡失败");
            System.out.println("|================================================|");
        }
    }
}
