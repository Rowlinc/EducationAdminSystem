package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.Student;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Encrypt;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDao {
    private static final Logger logger= LoggerFactory.getLogger(StudentDao.class);
//    根据名字查询学生信息
    public Student selectStudentByName(String name) {
        Student student=new Student();
        Connection conn= DBHelper.getConnection();
        String sql="select * from student where s_name=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,name);
            rs=ps.executeQuery();
            if(rs.next()){
                student.setId(rs.getInt("s_id"));
                student.setName(rs.getString("s_name"));
                student.setSex(rs.getString("s_sex"));
                student.setPassword(rs.getString("s_password"));
                student.setClassId(rs.getInt("s_class_id"));
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
        }finally {
            DBHelper.close(conn,ps,rs);
        }
        return student;
    }
    //    根据学号查询学生信息
    public Student selectStudentById(int id) {
        Student student=null;
        Connection conn = DBHelper.getConnection();
        String sql = "select s_id,s_name,s_sex,s_password,s_class_id from student where s_id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            student = new Student();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                student=new Student();
                student.setId(rs.getInt("s_id"));
                student.setName(rs.getString("s_name"));
                student.setSex(rs.getString("s_sex"));
                student.setPassword(rs.getString("s_password"));
                student.setClassId(rs.getInt("s_class_id"));
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
        } finally {
            DBHelper.close(conn, ps, rs);
        }
        return student;
    }
//    往表中添加数据
    public void insertStudent(Student student) throws NoSuchAlgorithmException {
        Connection conn=DBHelper.getConnection();
        String sql="insert into student values(?,?,?,?,?)";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,student.getId());
            ps.setString(2,student.getName());
            ps.setString(3,student.getSex());
            ps.setString(4, Encrypt.encryptMD5(student.getPassword()));
            ps.setInt(5,student.getClassId());
            if(ps.executeUpdate()>0){
                System.out.println("\n添加成功");
            }else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            logger.error("\n添加失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
        MyClassDao myClassDao =new MyClassDao();
        myClassDao.addStudentInClass(student.getClassId());
    }
//    按学号从表中删除数据
    public void deleteStudentById(int id){
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps=null;
        String sql="delete from student where s_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,id);
            if(ps.executeUpdate()>0){
                System.out.println("\n删除成功");
            }else {
                System.out.println("删除失败");
            }
        } catch (SQLException e) {
            logger.error("\n删除失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
//    修改数据
    public void updateStudentById(Student student) throws NoSuchAlgorithmException {
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps;
        String sql="update student set s_name=?,s_sex=?,s_password=?,s_class_id=? where s_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,student.getName());
            ps.setString(2,student.getSex());
            ps.setString(3,Encrypt.encryptMD5(student.getPassword()));
            ps.setInt(4,student.getClassId());
            ps.setInt(5,student.getId());
            if(ps.executeUpdate()>0){
                System.out.println("\n修改成功");
            }else {
                System.out.println("修改失败");
            }
        } catch (SQLException e) {
            logger.error("\n修改失败");
        }
    }
//    修改密码
    public void updatePasswordById(int id,String password) throws NoSuchAlgorithmException {
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps=null;
        String sql="update student set s_password=? where s_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,Encrypt.encryptMD5(password));
            ps.setInt(2,id);
            if(ps.executeUpdate()>0){
                System.out.println("\n修改成功");
            }else {
                System.out.println("修改失败");
            }
        } catch (SQLException e) {
            logger.error("\n修改失败");
        }
    }
//    确定学生是否存在
    public boolean isExist(int studentId){
        return Util.isExist("student",studentId,"s_id");
    }
}
