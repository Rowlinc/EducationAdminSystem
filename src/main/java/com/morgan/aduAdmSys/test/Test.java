package com.morgan.aduAdmSys.test;


import com.morgan.aduAdmSys.entity.Student;
import com.morgan.aduAdmSys.function.AdministratorManager;
import com.morgan.aduAdmSys.function.StudentManager;
import com.morgan.aduAdmSys.function.TeacherManager;

public class Test {
    public static void main(String[] args) {
        AdministratorManager administratorManager = new AdministratorManager();
        StudentManager studentManager = new StudentManager();
        Student student = new Student(2023211454, "宋一鸣", "Songyiming123@", "男", 1);
        while(true){
//            Teacher teacher = new Teacher()
            TeacherManager teacherManager = new TeacherManager();
//            teacherManager.changeTeacherPassword(,"Songyiming123@");
            studentManager.deleteChooseCourse(2023211454);
        }

    }
}
