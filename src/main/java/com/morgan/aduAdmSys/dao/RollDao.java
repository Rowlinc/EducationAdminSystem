package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.Roll;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class RollDao {
    private static final Logger logger= LoggerFactory.getLogger(RollDao.class);
//    添加学籍
    public boolean insertRoll(Roll roll){
        Connection conn= DBHelper.getConnection();
        String sql="insert into Roll values(?,?,?,?,?)";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,roll.getStudentId());
            ps.setString(2,roll.getStatus());
            ps.setDate(3,roll.getDateEntranceTime());
            ps.setDate(4,roll.getDateGraduateTime());
            ps.setDate(5,roll.getDateLeaveTime());
            if(ps.executeUpdate()>0){
                System.out.println("添加成功");
            }else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            logger.error("添加失败");
            return false;
        }finally {
            DBHelper.close(conn,ps,null);
        }
        return true;
    }
//    修改学籍信息
    public void updateRoll(Roll roll){
        Connection conn= DBHelper.getConnection();
        String sql="{call manage_student_roll(?,?,?,?,?,?)}";
        try(CallableStatement stmt=conn.prepareCall(sql)) {
            stmt.setInt(1,roll.getStudentId());
            stmt.setString(2,roll.getStatus());
            stmt.setDate(3,roll.getDateEntranceTime());
            stmt.setDate(4,roll.getDateGraduateTime());
            stmt.setDate(5,roll.getDateLeaveTime());
            stmt.registerOutParameter(6, Types.VARCHAR);
            stmt.execute();
            System.out.println(stmt.getString(6));
        } catch (SQLException e) {
            logger.error("修改失败");
        }finally {
            DBHelper.close(conn,null,null);
        }
    }
//    检查当前学号的学籍是否存在
    public boolean isExist(int studentId){
        return Util.isExist("roll",studentId,"s_id");
    }
//    删除学籍
    public void deleteRoll(int studentId){
        Connection conn=DBHelper.getConnection();
        String sql="delete from Roll where s_id=?";
        PreparedStatement ps;
        try{
            ps= conn.prepareStatement(sql);
            ps.setInt(1,studentId);
            if(ps.executeUpdate()>0){
                System.out.println("删除成功");
            }else {
                System.out.println("删除失败");
            }
        } catch (SQLException e) {
            logger.error("删除失败");
        }
    }
}
