package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.MyClass;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyClassDao {
    private static final Logger logger= LoggerFactory.getLogger(MyClassDao.class);
//    检查班级是否满员
    public boolean classIsFull(int classId){
        Connection conn= DBHelper.getConnection();
        String sql="select s_number from class where c_id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        int count=-1;
        try {
            ps= conn.prepareStatement(sql);
            ps.setInt(1,classId);
            rs=ps.executeQuery();
            if(rs.next()){
                count=rs.getInt("s_number");
            }
            if(count==-1){
                System.out.println("班级不存在");
                return true;
            }else if(count>= Util.MAX_CLASS_COUNT){
                System.out.println("该班级满员");
                return true;
            }else {
                return false;
            }
        } catch (SQLException e) {
            logger.error("查询失败");
        }finally {
            DBHelper.close(conn,ps,rs);
        }
        return true;
    }
//    往班级中添加一个人
    public void addStudentInClass(int classId){
        Connection conn= DBHelper.getConnection();
        String sql="update class set s_number=s_number+1 where c_id=?";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,classId);
            if(ps.executeUpdate()>0){
                System.out.println("班级人数修改成功");
            }else {
                System.out.println("班级人数修改失败");
            }
        } catch (SQLException e) {
            logger.error("班级人数修改失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
//    从班级中减少一个人
    public void deleteStudentInClass(int classId) {
        Connection conn = DBHelper.getConnection();
        String sql = "update class set s_number=s_number-1 where c_id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, classId);
            if (ps.executeUpdate() > 0) {
                System.out.println("班级人数修改成功");
            } else {
                System.out.println("班级人数修改失败");
            }
        } catch (SQLException e) {
            logger.error("班级人数修改失败");
        } finally {
            DBHelper.close(conn, ps, null);
        }
    }
//    添加一个班级
    public void insertClass(MyClass myClass){
        Connection conn= DBHelper.getConnection();
        String sql="insert into class(c_id) values(?)";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,myClass.getId());
            if(ps.executeUpdate()>0){
                System.out.println("班级添加成功");
            }else {
                System.out.println("班级添加失败");
            }
        } catch (SQLException e) {
            logger.error("班级添加失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
    //    查询班级是否存在
    public boolean isExist(int classId){
        return Util.isExist("class", classId, "c_id");
    }
}
