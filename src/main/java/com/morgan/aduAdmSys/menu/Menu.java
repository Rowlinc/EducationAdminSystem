package com.morgan.aduAdmSys.menu;

import com.morgan.aduAdmSys.entity.Student;
import com.morgan.aduAdmSys.entity.Teacher;
import com.morgan.aduAdmSys.function.AdministratorManager;
import com.morgan.aduAdmSys.function.StudentManager;
import com.morgan.aduAdmSys.function.TeacherManager;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
        menu();
    }
    public static void menu() throws NoSuchAlgorithmException {
        Scanner scanner=new Scanner(System.in);
        StudentManager studentManager=new StudentManager();
        TeacherManager teacherManager=new TeacherManager();
        AdministratorManager administratorManager=new AdministratorManager();
        String choice;
        while(true){
            System.out.println("|================================================|");
            System.out.println("|1.学生端");
            System.out.println("|================================================|");
            System.out.println("|2.教师端");
            System.out.println("|================================================|");
            System.out.println("|3.行政端");
            System.out.println("|================================================|");
            System.out.print("|请选择你的账号类型:");
            choice=scanner.nextLine();
            switch (choice) {
                case "1": {
                    Student student;
                    while (true) {
                        student = studentManager.login();
                        if (student!=null) {
                            StudentMenu.menu(student);
                        } else {
                            System.out.println("|是否继续尝试登录学生端(yes/no)?");
                            String choice1 = scanner.nextLine();
                            System.out.println("|================================================|");
                            if (choice1.equalsIgnoreCase("no")) {
                                break;
                            } else if (!choice1.equalsIgnoreCase("yes")) {
                                System.out.println("|WARNING:非法输入,已自动为你退出");
                                System.out.println("|================================================|");
                            }
                        }
                    }
                    break;
                }
                case "2": {
                    Teacher teacher;
                    while (true) {
                        teacher = teacherManager.login();
                        if (teacher!=null) {
                            TeacherMenu.menu(teacher);
                        } else {
                            System.out.println("|是否继续尝试登录教师端(yes/no)?");
                            String choice1 = scanner.nextLine();
                            System.out.println("|================================================|");
                            if (choice1.equalsIgnoreCase("no")) {
                                break;
                            } else if (!choice1.equalsIgnoreCase("yes")) {
                                System.out.println("|WARNING:非法输入,已自动为你退出");
                                System.out.println("|================================================|");
                            }
                        }
                    }
                    break;
                }
                case "3": {
                    while (true) {
                        if (administratorManager.login()) {
                            AdministratorMenu.menu();
                        } else {
                            System.out.println("|是否继续尝试登录行政端(yes/no)?");
                            String choice1 = scanner.nextLine();
                            System.out.println("|================================================|");
                            if (choice1.equalsIgnoreCase("no")) {
                                break;
                            } else if (!choice1.equalsIgnoreCase("yes")) {
                                System.out.println("|WARNING:非法输入,已自动为你退出");
                                System.out.println("|================================================|");
                            }
                        }
                    }
                    break;
                }
                default:
                    System.out.println("你的输入不正确,请重新输入:");
                    System.out.println("|================================================|");
                    break;
            }
        }

    }
}
