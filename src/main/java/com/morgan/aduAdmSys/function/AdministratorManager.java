package com.morgan.aduAdmSys.function;

import com.morgan.aduAdmSys.dao.*;
import com.morgan.aduAdmSys.entity.*;
import com.morgan.aduAdmSys.utils.Util;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Scanner;

public class AdministratorManager {
    Scanner scanner = new Scanner(System.in);
    MyClassDao myClassDao = new MyClassDao();
    StudentDao studentDao = new StudentDao();
    TeacherDao teacherDao = new TeacherDao();
    RollDao rollDao = new RollDao();
    CourseDao courseDao = new CourseDao();
    ExamDao examDao = new ExamDao();
    ClassroomDao classroomDao = new ClassroomDao();
    ScheduleDao scheduleDao = new ScheduleDao();
    ChooseCourseDao chooseCourseDao = new ChooseCourseDao();
    //    添加老师
    public void insertTeacher() throws NoSuchAlgorithmException {
        Teacher teacher = new Teacher();
        System.out.println("|================================================|");
        System.out.print("|请输入教师编号:");
        int teacherId;
        while (true) {
            teacherId = scanner.nextInt();
            scanner.nextLine();
            if (teacherId < 0) {
                System.out.println("非法输入,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.print("请重新输入编号:");
                }
            } else {
                if (teacherDao.isExist(teacherId)) {
                    System.out.print("该编号已存在,是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return;
                    } else {
                        System.out.print("请重新输入编号:");
                    }
                } else {
                    break;
                }
            }
        }
        teacher.setId(teacherId);
        System.out.println("|================================================|");
        System.out.print("|请输入教师姓名:");
        String name = Util.getName();
        if (name == null) {
            return;
        }
        teacher.setName(name);
        teacher.setPassword(Util.DEFAULT_PASSWORD);
        System.out.println("|================================================|");
        teacherDao.insertTeacher(teacher);
    }
    //    添加班级
    public void insertClass() {
        MyClass myClass = new MyClass();
        System.out.println("|================================================|");
        System.out.print("|请输入班级号:");
        int classId;
        while (true) {
            classId = scanner.nextInt();
            scanner.nextLine();
            if (classId < 0) {
                System.out.println("非法输入,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.print("请重新输入编号:");
                }
            } else {
                if (myClassDao.isExist(classId)) {
                    System.out.print("该班级已存在,是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return;
                    } else {
                        System.out.print("请重新输入编号:");
                    }
                } else {
                    break;
                }
            }
        }
        myClass.setId(classId);
        System.out.println("|================================================|");
        myClassDao.insertClass(myClass);
    }
    //    添加教室
    public void insertClassroom() {
        Classroom classroom = new Classroom();
        System.out.println("|================================================|");
        System.out.print("|请输入教室号:");
        int classroomId;
        while (true) {
            classroomId = scanner.nextInt();
            scanner.nextLine();
            if (classroomId < 0) {
                System.out.println("非法输入,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.print("请重新输入编号:");
                }
            } else {
                if (classroomDao.isExist(classroomId)) {
                    System.out.print("该教室已存在,是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return;
                    } else {
                        System.out.print("请重新输入编号:");
                    }
                } else {
                    break;
                }
            }
        }
        classroom.setId(classroomId);
        System.out.println("|================================================|");
        classroomDao.insertClassroom(classroom);
    }
    //    修改学生信息
    public void updateStudent() throws NoSuchAlgorithmException {
        Student student = new Student();
        Integer studentId = getStudentId();
        if (studentId == null) return;
        if (setStudent(studentId, student)) return;
        studentDao.updateStudentById(student);
    }
    //    修改教师信息
    public void updateTeacher() throws NoSuchAlgorithmException {
        Teacher teacher = new Teacher();
        Integer teacherId = getTeacherId();
        if (teacherId == null) return;
        System.out.print("|请输入新的教师编号:");
        int teacherId1;
        while (true) {
            teacherId1 = scanner.nextInt();
            scanner.nextLine();
            if (teacherId1 < 0) {
                System.out.println("非法输入,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.print("请重新输入编号:");
                }
            } else {
                if (teacherDao.isExist(teacherId1) && teacherId1 != teacherId) {
                    System.out.print("该编号已存在,是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return;
                    } else {
                        System.out.print("请重新输入编号:");
                    }
                } else {
                    break;
                }
            }
        }
        teacher.setId(teacherId1);
        System.out.println("|================================================|");
        System.out.print("|请输入教师姓名:");
        String name = Util.getName();
        if (name == null) {
            return;
        }
        teacher.setName(name);
        System.out.println("|================================================|");
        System.out.print("|请输入密码:");
        teacher.setPassword(Util.DEFAULT_PASSWORD);
        System.out.println("|================================================|");
        teacherDao.updateTeacherById(teacherId, teacher);
    }
    //    删除教师
    public void deleteTeacher() {
        Integer teacherId = getTeacherId();
        if (teacherId == null) return;
        teacherDao.deleteTeacherById(teacherId);
    }
    //    添加学籍信息
    public void insertRoll() throws NoSuchAlgorithmException {
        Roll roll = new Roll();
        System.out.println("|================================================|");
        System.out.print("|请输入学生学号:");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        boolean result=rollDao.isExist(studentId);
        if(result){
            System.out.print("该学生学籍已存在,是否覆盖(yes/no)?");
            String choice=scanner.nextLine();
            if(!choice.equalsIgnoreCase("yes")){
                return;
            }
        }
        if (studentId < 0) {
            System.out.println("非法输入,是否重新输入(yes/no)?");
            if (scanner.nextLine().equalsIgnoreCase("no")) {
                return;
            } else {
                System.out.print("请重新输入编号:");
            }
        } else {
            roll.setStudentId(studentId);
        }
        System.out.println("|================================================|");
        System.out.print("|请输入学生学业状态(在读/休学):");
        String status;
        while(true){
            status=scanner.nextLine();
            System.out.println("|================================================|");
            if(status.equals("在读")||status.equals("休学")){
                break;
            }else {
                System.out.println("|错误的状态,请重新输入:");
            }
        }
        roll.setStatus(status);
        System.out.print("|请输入学生入学时间(以yyyy-MM-dd形式):");
        String date=Util.getDateFormatString();
        roll.setStringEntranceTime(date);
        System.out.println("|================================================|");
        System.out.println("|请输入学生毕业时间(以yyyy-MM-dd形式):");
        date=Util.getDateFormatString();
        roll.setStringGraduateTime(date);
        System.out.println("|================================================|");
        if (result) {
            rollDao.updateRoll(roll);
        } else {
            result = rollDao.insertRoll(roll);
            if (result) {
                insertStudent(roll.getStudentId());
            }
        }
    }
    //    修改学籍信息
    public void updateRoll() {
        Roll roll = new Roll();
        Integer studentId = getStudentId();
        if (studentId == null) return;
        roll.setStudentId(studentId);
        System.out.println("|================================================|");
        System.out.print("|请输入学生学业状态(在读/休学/退学):");
        String status;
        while(true){
            status=scanner.nextLine();
            System.out.println("|================================================|");
            if(status.equals("在读")||status.equals("休学")||status.equals("退学")){
                break;
            }else {
                System.out.println("|错误的状态,请重新输入:");
            }
        }
        roll.setStatus(status);
        String date;
        if(!status.equals("退学")){
            System.out.println("|================================================|");
            System.out.print("|请输入学生入学时间(以yyyy-MM-dd形式):");

            date = Util.getDateFormatString();
            roll.setStringEntranceTime(date);
            System.out.println("|================================================|");
            System.out.print("|请输入学生毕业时间(以yyyy-MM-dd形式):");
            date = Util.getDateFormatString();
            roll.setStringGraduateTime(date);
        }else {
            System.out.println("|================================================|");
            System.out.print("|请输入学生退学时间(以yyyy-MM-dd形式):");
            date = Util.getDateFormatString();
            roll.setStringLeaveTime(date);
            myClassDao.deleteStudentInClass(studentDao.selectStudentById(roll.getStudentId()).getClassId());
            Map<String,ChooseCourse> map=chooseCourseDao.selectChooseCourseById(roll.getStudentId());
            for(Map.Entry<String,ChooseCourse> entry:map.entrySet()){
                courseDao.deleteStudentInCourse(entry.getValue().getCourseId());
            }
        }
        rollDao.updateRoll(roll);
    }
    //    添加课程
    public void addCourse(){
        Course course=new Course();
        System.out.println("|================================================|");
        System.out.print("|请输入课程编号:");
        int courseId;
        while(true) {
            courseId = scanner.nextInt();
            scanner.nextLine();
            if (courseId < 0) {
                System.out.print("|非法输入,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.print("|请重新输入编号:");
                }
            } else {
                if (courseDao.isExist(courseId)) {
                    System.out.print("|该课程已存在,是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return;
                    } else {
                        System.out.print("|请重新输入编号:");
                    }
                } else {
                    break;
                }
            }
        }
        if (getCourse(course, courseId)) return;
        courseDao.insertCourse(course);
    }
    //    查看当前课程列表
    public void selectCourse(){
        Map<String,Course> map = courseDao.selectCourse();
        System.out.println("|================================================|");
        System.out.println("|以下是可选课程:");
        for (Map.Entry<String, Course> entry:map.entrySet()){
            System.out.println("|================================================|");
            System.out.println("|任课老师:"+entry.getKey());
            System.out.println("|================================================|");
            System.out.println("|"+entry.getValue());
        }
        System.out.println("|================================================|");
    }
    //    修改课程
    public void updateCourse(){
        Course course=new Course();
        System.out.println("|================================================|");
        System.out.print("|请输入要修改的课程的编号:");
        int courseId;
        while(true){
            courseId = scanner.nextInt();
            scanner.nextLine();
            if(!courseDao.isExist(courseId)){
                System.out.print("该课程不存在,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("请重新输入课程编号:");
                }
            }else {
                break;
            }
        }
        System.out.println("|================================================|");
        System.out.print("|请输入新的课程编号:");
        int courseId1;
        while(true) {
            courseId1 = scanner.nextInt();
            scanner.nextLine();
            if (courseId1 < 0) {
                System.out.println("非法输入,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.print("请重新输入编号:");
                }
            } else {
                if (courseDao.isExist(courseId1)&&courseId1!= courseId) {
                    System.out.print("该课程已存在,是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return;
                    } else {
                        System.out.print("请重新输入课程编号:");
                    }
                } else {
                    break;
                }
            }
        }
        if (getCourse(course, courseId1)) return;
        courseDao.updateCourseById(courseId,course);
    }
    //    删除课程
    public void deleteCourse(){
        System.out.println("|================================================|");
        System.out.print("|请输入要删除的课程的编号:");
        int courseId;
        while(true){
            courseId = scanner.nextInt();
            scanner.nextLine();
            if(!courseDao.isExist(courseId)){
                System.out.print("该课程不存在,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("请重新输入课程编号:");
                }
            }else {
                break;
            }
        }
        courseDao.deleteCourseById(courseId);
    }
    //    添加考试计划
    public void insertExamArrange(){
        Exam exam=new Exam();
        System.out.println("|================================================|");
        System.out.print("|请输入要考试的课程编号:");
        int courseId;
        while(true){
            courseId=scanner.nextInt();
            scanner.nextLine();
            if(!courseDao.isExist(courseId)){
                System.out.print("|没有查询到该课程信息,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("|请重新输入课程编号:");
                }
            }else {
                break;
            }
        }
        exam.setCourseId(courseId);
        System.out.println("|================================================|");
        System.out.print("|请输入教室号:");
        int classroomId;
        while(true){
            classroomId=scanner.nextInt();
            scanner.nextLine();
            if(!classroomDao.isExist(classroomId)){
                System.out.print("|没有查询到教室,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("|请重新输入教室编号:");
                }
            }else {
                break;
            }
        }
        exam.setClassroomId(classroomId);
        System.out.println("|================================================|");
        System.out.print("|请输入考试日期(以yyyy-MM-dd形式):");
        String date=Util.getDateFormatString();
        exam.setStringDate(date);
        System.out.println("|================================================|");
        System.out.print("|请输入开始课节:");
        int examNum=Util.checkClass(date,classroomId);
        if (examNum==0){
            return;
        }
        exam.setNum(examNum);
        System.out.println("|================================================|");
        System.out.print("|请输入班级号:");
        int classId;
        while(true){
            classId =scanner.nextInt();
            scanner.nextLine();
            if(!myClassDao.isExist(classId)){
                System.out.print("|没有查询到该班级,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("|请重新输入班级编号:");
                }
            }else {
                break;
            }
        }
        exam.setClassId(classId);
        examDao.insertExam(exam);
    }
     //    删除考试安排
    public void deleteExamArrange(){
        System.out.println("|================================================|");
        System.out.print("|请输入要取消的考试的课程编号:");
        int courseId;
        while(true){
            courseId = scanner.nextInt();
            scanner.nextLine();
            if(!examDao.isExistCourse(courseId)){
                System.out.print("|该课程当前没有考试信息,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("|请重新输入课程编号:");
                }
            }else {
                break;
            }
        }
        examDao.deleteExam(courseId);
    }
//    添加课程安排
    public void addSchedule(){
        Schedule schedule=new Schedule();
        String time;
        System.out.println("|================================================|");
        System.out.print("|请输入要添加的课程编号:");
        int courseId;
        while(true){
            courseId=scanner.nextInt();
            scanner.nextLine();
            if(!courseDao.isExist(courseId)){
                System.out.print("|没有查询到该课程信息,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("|请重新输入课程编号:");
                }
            }else {
                break;
            }
        }
        schedule.setCourseId(courseId);
        System.out.println("|================================================|");
        System.out.print("|请输入班级编号:");
        int classId;
        while(true){
            classId = scanner.nextInt();
            scanner.nextLine();
            if(!myClassDao.isExist(classId)){
                System.out.println("|================================================|");
                System.out.print("|没有查询到该班级信息,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.println("|================================================|");
                    System.out.print("|请重新输入班级编号:");
                }
            } else {
                break;
            }
        }
        schedule.setClassId(classId);
        System.out.println("|================================================|");
        System.out.print("|请输入教室号:");
        int classroomId;
        while(true) {
            classroomId = scanner.nextInt();
            scanner.nextLine();
            if (!classroomDao.isExist(classroomId)) {
                System.out.println("|================================================|");
                System.out.print("|该教室不存在,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.println("|================================================|");
                    System.out.print("|请重新输入教室编号:");
                }
            } else {
                break;
            }
        }
        schedule.setClassroomId(classroomId);
        System.out.println("|================================================|");
        System.out.print("|请输入上课天(周几):");
        int weekday;
        while(true){
            weekday=scanner.nextInt();
            if(weekday>=1&&weekday<=7){
                break;
            }else {
                System.out.println("|================================================|");
                System.out.print("|非法输入,请输入按数字1-7填入上课日:");
            }
        }
        schedule.setWeekday(weekday);
        scanner.nextLine();
        System.out.println("|================================================|");
        System.out.print("|请以标准格式输入课程开始时间(HH:mm:ss):");
        time=Util.getTimeFormatString();
        schedule.setStringStartTime(time);
        System.out.println("|================================================|");
        System.out.print("|请以标准格式输入课程结束时间(HH:mm:ss):");
        time=Util.getTimeFormatString();
        schedule.setStringEndTime(time);
        scheduleDao.insertSchedule(schedule);
    }
//    删除课程安排 
    public void deleteSchedule(){
        Schedule schedule=new Schedule();
        System.out.println("|================================================|");
        System.out.print("|请输入课程安排的课程编号:");
        int courseId;
        while(true){
            courseId = scanner.nextInt();
            scanner.nextLine();
            if(!courseDao.isExist(courseId)){
                System.out.print("|该课程不存在,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return;
                }else {
                    System.out.print("|请重新输入课程编号:");
                }
            }else {
                break;
            }
        }
        schedule.setCourseId(courseId);
        System.out.println("|================================================|");
        System.out.print("|请输入课程安排的班级号:");
        int classId;
        while(true) {
            classId = scanner.nextInt();
            scanner.nextLine();
            if (!myClassDao.isExist(classId)) {
                System.out.print("|该班级不存在,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return;
                } else {
                    System.out.print("|请重新输入编号:");
                }
            } else {
                break;
            }
        }
        schedule.setClassId(classId);
        System.out.println("|================================================|");
        System.out.print("|请输入课程安排的上课日:");
        int weekday;
        while(true){
            weekday=scanner.nextInt();
            if(weekday>=1&&weekday<=7){
                break;
            }else {
                System.out.print("|非法输入,请输入按数字1-7填入上课日:");
            }
        }
        schedule.setWeekday(weekday);
        scheduleDao.deleteSchedule(schedule);
    }
    //    登录
    public boolean login(){
        boolean result=false;
        System.out.println("|================================================|");
        System.out.print("|请输入管理员用户名:");
        String adminName=scanner.nextLine();
        if (!adminName.equals(Util.ADMIN_NAME)) {
            System.out.println("|================================================|");
            System.out.println("|WARNING:用户名错误");
            System.out.println("|================================================|");
        }else{
            System.out.print("|请输入密码:");
            String password=scanner.nextLine();
            System.out.println("|================================================|");
            if(password.equals(Util.ADMIN_PASSWORD)){
                System.out.println("|登录成功!");
                System.out.println("|================================================|");
                result=true;
            }else {
                System.out.println("|WARNING:密码不正确");
                System.out.println("|================================================|");
            }
        }
        return result;
    }
    //    生成学生信息
    private boolean setStudent(int studentId, Student student) {
        student.setId(studentId);
        System.out.println("|================================================|");
        System.out.print("|请输入学生姓名:");
        String name = Util.getName();
        if (name == null) {
            return true;
        }
        student.setName(name);
        System.out.println("|================================================|");
        System.out.print("|请输入学生性别:");
        String sex = Util.getGender();
        if (sex == null) return true;
        student.setSex(sex);
        student.setPassword(Util.DEFAULT_PASSWORD);
        System.out.println("|================================================|");
        System.out.print("|请输入班级id:");
        while (true) {
            int classId = scanner.nextInt();
            scanner.nextLine();
            if ((!myClassDao.isExist(classId))) {
                System.out.println("|================================================|");
                System.out.print("不存在该班级,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return true;
                } else {
                    System.out.print("|请重新输入班级id:");
                }
            } else {
                if (myClassDao.classIsFull(classId)) {
                    System.out.println("|================================================|");
                    System.out.print("该班级已满员,是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return true;
                    } else {
                        System.out.print("|请重新输入班级id:");
                    }
                } else {
                    student.setClassId(classId);
                    break;
                }
            }
        }
        System.out.println("|================================================|");
        return false;
    }
    //    删除学生
    private void deleteStudent() {
        System.out.println("|================================================|");
        System.out.println("|请输入要删除学生的学号:");
        int studentId = scanner.nextInt();
        System.out.println("|================================================|");
        studentDao.deleteStudentById(studentId);
    }
    //    添加学生
    private void insertStudent(int studentId) throws NoSuchAlgorithmException {
        Student student = new Student();
        if (setStudent(studentId, student)) return;
        studentDao.insertStudent(student);
    }
    //    获取学生id
    private Integer getStudentId() {
        System.out.println("|================================================|");
        System.out.print("|请输入学生学号:");
        int studentId;
        while (true) {
            studentId = scanner.nextInt();
            scanner.nextLine();
            if (!studentDao.isExist(studentId)) {
                System.out.print("该学生不存在,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return null;
                } else {
                    System.out.print("请重新输入学号:");
                }
            } else {
                break;
            }
        }
        return studentId;
    }
    //    获取教师id
    private Integer getTeacherId() {
        System.out.println("|================================================|");
        System.out.print("|请输入教师编号:");
        int teacherId;
        while (true) {
            teacherId = scanner.nextInt();
            scanner.nextLine();
            if (!teacherDao.isExist(teacherId)) {
                System.out.print("该教师不存在,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return null;
                } else {
                    System.out.print("请重新输入编号:");
                }
            } else {
                break;
            }
        }
        System.out.println("|================================================|");
        return teacherId;
    }
    //    获取Course对象
    private boolean getCourse(Course course, int courseId) {
        course.setId(courseId);
        System.out.println("|================================================|");
        System.out.print("|请输入课程名:");
        String name= Util.getName();
        if(name==null){
            return true;
        }
        course.setName(name);
        System.out.println("|================================================|");
        System.out.print("|请输入授课老师编号:");
        int teacherId;
        while(true){
            teacherId=scanner.nextInt();
            scanner.nextLine();
            if(!teacherDao.isExist(teacherId)){
                System.out.print("|未查询到该编号教师信息,是否继续输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return true;
                }else {
                    System.out.print("|请重新输入教师编号:");
                }
            }else {
                break;
            }
        }
        course.setTeacherId(teacherId);
        System.out.println("|================================================|");
        System.out.print("|请输入课程最大容量:");
        int capacity;
        while(true){
            capacity=scanner.nextInt();
            scanner.nextLine();
            if(capacity<Util.MIN_COURSE_CAPACITY||capacity>Util.MAX_COURSE_CAPACITY){
                System.out.print("|课程容量只能在"+Util.MIN_COURSE_CAPACITY+"-"+Util.MAX_COURSE_CAPACITY+"之间,是否继续输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return true;
                }else {
                    System.out.print("|请重新输入:");
                }
            }else {
                break;
            }
        }
        course.setMaxStudents(capacity);
        System.out.println("|================================================|");
        System.out.print("|请输入课程学分:");
        int credits;
        while(true){
            credits=scanner.nextInt();
            scanner.nextLine();
            if(credits<Util.MIN_COURSE_CREDIT||credits>Util.MAX_COURSE_CREDIT){
                System.out.print("|课程学分只能在"+Util.MIN_COURSE_CREDIT+"-"+Util.MAX_COURSE_CREDIT+"之间,是否继续输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return true;
                }else {
                    System.out.print("|请重新输入:");
                }
            }else {
                break;
            }
        }
        course.setCredits(credits);
        System.out.println("|================================================|");
        return false;
    }
}
