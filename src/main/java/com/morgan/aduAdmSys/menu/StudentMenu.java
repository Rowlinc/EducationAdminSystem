package com.morgan.aduAdmSys.menu;
import com.morgan.aduAdmSys.entity.Student;
import com.morgan.aduAdmSys.function.StudentManager;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class StudentMenu {
    public static void menu(Student student) throws NoSuchAlgorithmException {
        StudentManager studentManager=new StudentManager();
        Scanner scanner=new Scanner(System.in);
        while(true){
            System.out.println("|================================================|");
            System.out.println("|                     1.修改密码                   ");
            System.out.println("|================================================|");
            System.out.println("|                     2.个人信息查询               ");
            System.out.println("|================================================|");
            System.out.println("|                     3.选课信息与成绩          ");
            System.out.println("|================================================|");
            System.out.println("|                     4.选课                    ");
            System.out.println("|================================================|");
            System.out.println("|                     5.退课                      ");
            System.out.println("|================================================|");
            System.out.println("|                     6.课程表                   ");
            System.out.println("|================================================|");
            System.out.println("|                     7.考试时间                ");
            System.out.println("|================================================|");
            System.out.print("请选择要使用的功能:");
            String choice=scanner.nextLine();
            switch (choice){
                case "1":
                    studentManager.changeStudentPassword(student);
                    break;
                case "2":
                    System.out.println(student);
                    break;
                case "3":
                    studentManager.selectChooseCourse(student.getId());
                    break;
                case "4":
                    studentManager.chooseMyCourse(student.getId());
                    break;
                case "5":
                    studentManager.deleteChooseCourse(student.getId());
                    break;
                case "6":
                    studentManager.selectScheduleById();
                    break;
                case "7":
                    studentManager.selectExamByClassId(student.getClassId());
                    break;
                default:
                    System.out.println("非法输入");
                    break;
            }
        }
    }
}


