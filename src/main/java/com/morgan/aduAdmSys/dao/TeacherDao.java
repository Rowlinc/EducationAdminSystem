package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.Teacher;
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

public class TeacherDao {
    private static final Logger logger= LoggerFactory.getLogger(TeacherDao.class);
    public Teacher selectTeacherByName(String name) {
        Teacher teacher=new Teacher();
        Connection conn= DBHelper.getConnection();
        String sql="select * from teacher where t_name=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,name);
            rs=ps.executeQuery();
            if(rs.next()){
                teacher.setId(rs.getInt("t_id"));
                teacher.setName(rs.getString("t_name"));
                teacher.setPassword(rs.getString("t_password"));
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
        }finally {
            DBHelper.close(conn,ps,rs);
        }
        return teacher;
    }
    //    根据学号查询教师信息
    public Teacher selectTeacherById(int id) {
        Teacher teacher=null;
        Connection conn = DBHelper.getConnection();
        String sql = "select * from teacher where t_id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()){
                teacher=new Teacher();
                teacher.setId(rs.getInt("t_id"));
                teacher.setName(rs.getString("t_name"));
                teacher.setPassword(rs.getString("t_password"));
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
            return null;
        } finally {
            DBHelper.close(conn, ps, rs);
        }
        return teacher;
    }
    //    往表中添加教师数据
    public void insertTeacher(Teacher teacher) throws NoSuchAlgorithmException {
        Connection conn=DBHelper.getConnection();
        String sql="insert into teacher values(?,?,?)";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,teacher.getId());
            ps.setString(2,teacher.getName());
            ps.setString(3, Encrypt.encryptMD5(teacher.getPassword()));
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
    }
    //    按教师编号从表中删除数据
    public void deleteTeacherById(int id){
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps=null;
        String sql="delete from teacher where t_id=?";
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
    public void updateTeacherById(int id,Teacher teacher) throws NoSuchAlgorithmException {
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps=null;
        String sql="update teacher set t_id=?,t_name=?,t_password=? where t_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,teacher.getId());
            ps.setString(2,teacher.getName());
            ps.setString(3,Encrypt.encryptMD5(teacher.getPassword()));
            ps.setInt(4,id);
            if(ps.executeUpdate()>0){
                System.out.println("\n修改成功");
            }else {
                System.out.println("修改失败");
            }
        } catch (SQLException e) {
            logger.error("\n修改失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
//    修改密码
    public void updatePasswordById(int id,String password) throws NoSuchAlgorithmException {
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps=null;
        String sql="update teacher set t_password=? where t_id=?";
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
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
//    查询教师是否存在
    public boolean isExist(int teacherId){
        return Util.isExist("teacher",teacherId,"t_id");
    }
}
