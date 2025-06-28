package com.morgan.aduAdmSys.menu;

import com.morgan.aduAdmSys.function.AdministratorManager;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class AdministratorMenu {
    public static void menu() throws NoSuchAlgorithmException {
        AdministratorManager administratorManager = new AdministratorManager();
        Scanner scanner = new Scanner(System.in);
        String choice;
        String choice1;
        while (true) {
            System.out.println("|================================================|");
            System.out.println("|                     1.学生                  ");
            System.out.println("|================================================|");
            System.out.println("|                     2.教师               ");
            System.out.println("|================================================|");
            System.out.println("|                     3.课程          ");
            System.out.println("|================================================|");
            System.out.println("|                     4.校园事务          ");
            System.out.println("|================================================|");
            System.out.print("|请选择要使用的模块:");
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    while (true) {
                        System.out.println("|================================================|");
                        System.out.println("|                     1.添加学生学籍                    ");
                        System.out.println("|================================================|");
                        System.out.println("|                     2.修改学生信息             ");
                        System.out.println("|================================================|");
                        System.out.println("|                     3.修改学生学籍          ");
                        System.out.println("|================================================|");
                        System.out.print("|请选择要使用的功能:");
                        choice1 = scanner.nextLine();
                        System.out.println("|================================================|");
                        switch (choice1) {
                            case "1":
                                administratorManager.insertRoll();
                                break;
                            case "2":
                                administratorManager.updateStudent();
                                break;
                            case "3":
                                administratorManager.updateRoll();
                                break;
                            default:
                                System.out.println("|非法输入");
                                break;
                        }
                        System.out.println("|================================================|");
                        System.out.println("|是否继续使用本模块(yes/no):");
                        String choice3 = scanner.nextLine();
                        if (choice3.equalsIgnoreCase("no")) {
                            break;
                        } else if (!choice3.equalsIgnoreCase("yes")) {
                            System.out.println("|================================================|");
                            System.out.println("|非法输入,已自动退出");
                            break;
                        }
                    }
                    break;
                case "2":
                    while (true) {
                        System.out.println("|================================================|");
                        System.out.println("|                     1.添加教师                  ");
                        System.out.println("|================================================|");
                        System.out.println("|                     2.修改教师信息               ");
                        System.out.println("|================================================|");
                        System.out.println("|                     3.删除教师          ");
                        System.out.println("|================================================|");
                        System.out.print("|请选择要使用的功能:");
                        choice1 = scanner.nextLine();
                        System.out.println("|================================================|");
                        switch (choice1) {
                            case "1":
                                administratorManager.insertTeacher();
                                break;
                            case "2":
                                administratorManager.updateTeacher();
                                break;
                            case "3":
                                administratorManager.deleteTeacher();
                                break;
                            default:
                                System.out.println("|非法输入");
                                break;
                        }
                        System.out.println("|================================================|");
                        System.out.println("|是否继续使用本模块(yes/no)?");
                        String choice3 = scanner.nextLine();
                        if (choice3.equalsIgnoreCase("no")) {
                            break;
                        } else if (!choice3.equalsIgnoreCase("yes")) {
                            System.out.println("|================================================|");
                            System.out.println("|非法输入,已自动退出");
                            break;
                        }
                    }
                    break;
                case "3":
                    while (true) {
                        System.out.println("|================================================|");
                        System.out.println("|                     1.查看当前课程列表                  ");
                        System.out.println("|================================================|");
                        System.out.println("|                     2.添加课程               ");
                        System.out.println("|================================================|");
                        System.out.println("|                     3.修改课程          ");
                        System.out.println("|================================================|");
                        System.out.println("|                     4.删除课程          ");
                        System.out.println("|================================================|");
                        System.out.println("|                     5.添加课程安排          ");
                        System.out.println("|================================================|");
                        System.out.println("|                     6.删除课程安排          ");
                        System.out.println("|================================================|");
                        System.out.println("|                     7.添加考试安排          ");
                        System.out.println("|================================================|");
                        System.out.println("|                     8.删除考试安排          ");
                        System.out.println("|================================================|");
                        System.out.print("|请选择要使用的功能:");
                        choice1 = scanner.nextLine();
                        System.out.println("|================================================|");
                        switch (choice1) {
                            case "1":
                                administratorManager.selectCourse();
                                break;
                            case "2":
                                administratorManager.addCourse();
                                break;
                            case "3":
                                administratorManager.updateCourse();
                                break;
                            case "4":
                                administratorManager.deleteCourse();
                                break;
                            case "5":
                                administratorManager.addSchedule();
                                break;
                            case "6":
                                administratorManager.deleteSchedule();
                                break;
                            case "7":
                                administratorManager.insertExamArrange();
                                break;
                            case "8":
                                administratorManager.deleteExamArrange();
                                break;
                            default:
                                System.out.println("|非法输入");
                                break;
                        }
                        System.out.println("|================================================|");
                        System.out.println("|是否继续使用本模块(yes/no)?");
                        String choice3 = scanner.nextLine();
                        if (choice3.equalsIgnoreCase("no")) {
                            break;
                        } else if (!choice3.equalsIgnoreCase("yes")) {
                            System.out.println("|================================================|");
                            System.out.println("|非法输入,已自动退出");
                            break;
                        }
                    }
                    break;
                case "4":
                    while (true) {
                        System.out.println("|================================================|");
                        System.out.println("|                     1.添加班级                    ");
                        System.out.println("|================================================|");
                        System.out.println("|                     2.添加教室             ");
                        System.out.println("|================================================|");
                        System.out.print("|请选择要使用的功能:");
                        choice1 = scanner.nextLine();
                        System.out.println("|================================================|");
                        switch (choice1) {
                            case "1":
                                administratorManager.insertClass();
                                break;
                            case "2":
                                administratorManager.insertClassroom();
                                break;
                            default:
                                System.out.println("|非法输入");
                                break;
                        }
                        System.out.println("|================================================|");
                        System.out.println("|是否继续使用本模块(yes/no):");
                        String choice3 = scanner.nextLine();
                        if (choice3.equalsIgnoreCase("no")) {
                            break;
                        } else if (!choice3.equalsIgnoreCase("yes")) {
                            System.out.println("|================================================|");
                            System.out.println("|非法输入,已自动退出");
                            break;
                        }
                    }
                    break;
                default:
                    System.out.println("|================================================|");
                    System.out.println("|非法输入");
                    break;
            }
        }
    }
}