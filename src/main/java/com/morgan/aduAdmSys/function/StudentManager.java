package com.morgan.aduAdmSys.function;

import com.morgan.aduAdmSys.dao.*;
import com.morgan.aduAdmSys.entity.*;
import com.morgan.aduAdmSys.utils.Encrypt;
import com.morgan.aduAdmSys.utils.Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentManager {
    StudentDao studentDao=new StudentDao();
    ChooseCourseDao chooseCourseDao=new ChooseCourseDao();
    ScheduleDao scheduleDao=new ScheduleDao();
    CourseDao courseDao=new CourseDao();
    Scanner scanner=new Scanner(System.in);
    ExamDao examDao=new ExamDao();
//    根据id修改学生密码
    public void changeStudentPassword(Student student) throws NoSuchAlgorithmException {
        String password= Util.checkPassword(student.getPassword());
        if(password==null){
            System.out.println("修改失败");
            return;
        }
        studentDao.updatePasswordById(student.getId(), password);
    }
//    根据id查询学生基本信息
    public void selectStudentMessage(int studentId){
        Student student = studentDao.selectStudentById(studentId);
        System.out.println(student);
    }
//    根据id查询学生选课信息和成绩
    public void selectChooseCourse(int studentId){
        if(!chooseCourseDao.isExist(studentId)){
            System.out.println("|当前无选课信息");
            return;
        }
        Map<String, ChooseCourse> map=chooseCourseDao.selectChooseCourseById(studentId);
        int count=0;
        for(Map.Entry<String, ChooseCourse> entry:map.entrySet()) {
            count++;
            System.out.println("|================================================|");
            System.out.println("|课程" + count + ":" + entry.getKey());
            System.out.println("|================================================|");
            ChooseCourse chooseCourse = entry.getValue();
            System.out.println("|课程id:"+chooseCourse.getCourseId());
            System.out.println("|================================================|");
            if (chooseCourse.getExperimentScore()==0) {
                System.out.println("|平时成绩:未结课");
                System.out.println("|================================================|");
                System.out.println("|实验成绩:未结课");
                System.out.println("|================================================|");
                System.out.println("|阶段成绩:未结课");
                System.out.println("|================================================|");
                System.out.println("|期末成绩:未结课");
                System.out.println("|================================================|");
            } else {
                System.out.println("|平时成绩:" + chooseCourse.getNormalScore());
                System.out.println("|================================================|");
                System.out.println("|实验成绩:" + chooseCourse.getExperimentScore());
                System.out.println("|================================================|");
                System.out.println("|阶段成绩:" + chooseCourse.getStageScore());
                System.out.println("|================================================|");
                System.out.println("|期末成绩:" + chooseCourse.getTestScore());
                System.out.println("|================================================|");
                System.out.println("|总评成绩:" + chooseCourse.getAverageScore());
                System.out.println("|================================================|");
            }
        }
    }
//    选课
    public void chooseMyCourse(int studentId){
        Map<String, Course> map = courseDao.selectCourse();
        System.out.println("|================================================|");
        System.out.println("|以下是可选课程:");
        for (Map.Entry<String, Course> entry:map.entrySet()){
            System.out.println("|================================================|");
            System.out.println("|"+entry.getKey());
            System.out.println("|================================================|");
            System.out.println("|"+entry.getValue());
        }
        System.out.println("|================================================|");
        System.out.print("|请输入课程号选课:");
        int courseId;
        while(true){
            courseId=scanner.nextInt();
            scanner.nextLine();
            if(!courseDao.isExist(courseId)){
                System.out.print("|该课程不存在,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("yes")){
                    System.out.print("|请重新输入要选的课程号:");
                }else {
                    return;
                }
            }else {
                if (chooseCourseDao.isExist(studentId,courseId)){
                    System.out.println("你已经选过该课程,已为您自动退出");
                    return;
                }else {
                    break;
                }
            }
        }
        System.out.println("|================================================|");
        chooseCourseDao.insertChooseCourse(studentId,courseId);
    }
//    退课
    public void deleteChooseCourse(int studentId){
        System.out.println("|================================================|");
        System.out.println("|这是你的选课信息:");
        System.out.println("|================================================|");
        selectChooseCourse(studentId);
        System.out.print("|请输入你要退课的id:");
        int courseId;
        while(true){
            courseId=scanner.nextInt();
            scanner.nextLine();
            if (chooseCourseDao.isExist(studentId,courseId)){
                break;
            }else {
                System.out.print("|你没有选这门课,是否重新输入?(yes/no)");
                if(scanner.nextLine().equalsIgnoreCase("yes")){
                    System.out.print("|请重新输入要退课的id:");
                }else {
                    return;
                }
            }
        }
        System.out.println("|================================================|");
        chooseCourseDao.deleteChooseCourseById(studentId,courseId);
        System.out.println("|================================================|");
    }
//    根据课程id查询课程表
    public void selectScheduleById(){
        System.out.println("|================================================|");
        System.out.print("|请输入要查询课程表的课程id:");
        int courseId=scanner.nextInt();
        if (!scheduleDao.isExist(courseId)) {
            System.out.println("|没有查询到这门课程的课程表,请向任课老师确认是否排课");
            return;
        }
        System.out.println("|================================================|");
        List<Schedule> list=scheduleDao.selectScheduleById(courseId);
        for(Schedule schedule:list){
            System.out.println("|"+schedule);
            System.out.println("|================================================|");
        }
    }
//    根据学生的班级id查询考试
    public void selectExamByClassId(int classId){
        System.out.println("|================================================|");
        if(!examDao.isExist(classId)){
            System.out.println("您当前无考试安排");
            System.out.println("|================================================|");
            return;
        }
        System.out.println("|您有以下考试安排:");
        System.out.println("|================================================|");
        List<Exam> list=examDao.selectExamByClassId(classId);
        for(Exam exam:list){
            System.out.println(exam);
            System.out.println("|================================================|");
        }
    }
//    登录
    public Student login() throws NoSuchAlgorithmException {
        System.out.println("|================================================|");
        System.out.print("|请输入你的学号:");
        int studentId=scanner.nextInt();
//        nextInt紧接着是换行符,需要先nextLine一次读掉换行符
        scanner.nextLine();
        Student student;
        if (!studentDao.isExist(studentId)) {
            System.out.println("|================================================|");
            System.out.println("|WARNING:学号不存在");
            System.out.println("|================================================|");
            return null;
        }else{
            student=studentDao.selectStudentById(studentId);
            System.out.println("|================================================|");
            return getStudent(student);
        }
    }

    private Student getStudent(Student student) throws NoSuchAlgorithmException {
        System.out.print("|请输入密码:");
        if(Encrypt.encryptMD5(scanner.nextLine()).equals(student.getPassword())){
            System.out.println("|登录成功!");
            System.out.println("|================================================|");
            return student;
        }else {
            System.out.println("|WARNING:密码不正确");
            System.out.println("|================================================|");
            return null;
        }
    }
}
