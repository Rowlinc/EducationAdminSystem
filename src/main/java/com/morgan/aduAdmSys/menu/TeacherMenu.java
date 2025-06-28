package com.morgan.aduAdmSys.menu;

import com.morgan.aduAdmSys.entity.Teacher;
import com.morgan.aduAdmSys.function.TeacherManager;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class TeacherMenu {
    public static void menu(Teacher teacher) throws NoSuchAlgorithmException {
        TeacherManager teacherManager = new TeacherManager();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("|================================================|");
            System.out.println("|                     1.修改密码                   ");
            System.out.println("|================================================|");
            System.out.println("|                     2.个人信息查询               ");
            System.out.println("|================================================|");
            System.out.println("|                     3.学生成绩录入          ");
            System.out.println("|================================================|");
            System.out.println("|                     4.教室预约                    ");
            System.out.println("|================================================|");
            System.out.println("|                     5.课程表                   ");
            System.out.println("|================================================|");
            System.out.println("|                     6.上班打卡             ");
            System.out.println("|================================================|");
            System.out.println("|                     7.下班打卡             ");
            System.out.println("|================================================|");
            System.out.print("请选择要使用的功能:");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    teacherManager.changeTeacherPassword(teacher);
                    break;
                case "2":
                    System.out.println(teacher);
                    break;
                case "3":
                    teacherManager.addScoreToStudent(teacher.getId());
                    break;
                case "4":
                    teacherManager.OrderClassroom(teacher.getId());
                    break;
                case "5":
                    teacherManager.selectScheduleByTeacherId(teacher.getId());
                    break;
                case "6":
                    teacherManager.addComeTime(teacher.getId());
                    break;
                case "7":
                    teacherManager.addLeaveTime(teacher.getId());
                    break;
                default:
                    System.out.println("非法输入");
                    break;
            }
        }
    }
}
