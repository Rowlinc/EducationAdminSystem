package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.utils.DBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AttendanceDao {
    private static final Logger logger= LoggerFactory.getLogger(AttendanceDao.class);
//    上班打卡
    public boolean addStartById(int teacherId){
        boolean result=false;
        Connection conn = DBHelper.getConnection();
        String sql = "insert into Attendance(t_id) values(?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, teacherId);
            if(ps.executeUpdate()>0){
                result=true;
            }
        } catch (SQLException e) {
            logger.error("");
        } finally {
            DBHelper.close(conn, ps, null);
        }
        return result;
    }
//    下班打卡
    public boolean addEndById(int teacherId){
        boolean result=false;
        Connection conn = DBHelper.getConnection();
        String sql = "update Attendance set t_leave_time=CURRENT_TIMESTAMP where t_id=? and a_date=CURRENT_DATE";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, teacherId);
            if(ps.executeUpdate()>0){
                result=true;
            }
        } catch (SQLException e) {
            logger.error("");
        } finally {
            DBHelper.close(conn, ps, null);
        }
        return result;
    }
}
