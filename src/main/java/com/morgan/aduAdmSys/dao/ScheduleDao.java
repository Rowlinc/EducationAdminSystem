package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.Schedule;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleDao.class);
//    根据课程id查询课程表
    public List<Schedule> selectScheduleById(int courseId) {
        List<Schedule> list = new ArrayList<>();
        Schedule schedule;
        Connection conn = DBHelper.getConnection();
        String sql = "select * from schedule where c_id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();
            while (rs.next()) {
                schedule = new Schedule();
                schedule.setCourseId(rs.getInt("c_id"));
                schedule.setClassId(rs.getInt("class_id"));
                schedule.setWeekday(rs.getInt("s_weekday"));
                schedule.setTimeStartTime(rs.getTime("start_time"));
                schedule.setTimeEndTime(rs.getTime("end_time"));
                schedule.setClassroomId(rs.getInt("classroom_id"));
                list.add(schedule);
            }

        } catch (SQLException e) {
            logger.error("\n查询失败");
        } finally {
            DBHelper.close(conn, ps, rs);
        }
        return list;
    }
    //    根据教师id查询课程表
    public List<Schedule> selectScheduleByTeacherId(int teacherId) {
        List<Schedule> list = new ArrayList<>();
        Schedule schedule = new Schedule();
        Connection conn = DBHelper.getConnection();
        String sql = "select * from schedule where c_id=(select c_id from course where t_id=?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, teacherId);
            rs = ps.executeQuery();
            while (rs.next()) {
                schedule.setCourseId(rs.getInt("c_id"));
                schedule.setClassId(rs.getInt("class_id"));
                schedule.setWeekday(rs.getInt("s_weekday"));
                schedule.setTimeStartTime(rs.getTime("start_time"));
                schedule.setTimeEndTime(rs.getTime("end_time"));
                schedule.setClassroomId(rs.getInt("classroom_id"));
                list.add(schedule);
            }

        } catch (SQLException e) {
            logger.error("\n查询失败");
        } finally {
            DBHelper.close(conn, ps, rs);
        }
        return list;
    }
//    添加课程安排
    public void insertSchedule(Schedule schedule){
        Connection conn=DBHelper.getConnection();
        String sql="{call AddScheduleWithCheck (?,?,?,?,?,?,?)}";
        try(CallableStatement stmt=conn.prepareCall(sql)){
            stmt.setInt(1,schedule.getCourseId());
            stmt.setInt(2,schedule.getClassId());
            stmt.setInt(3,schedule.getWeekday());
            stmt.setTime(4,schedule.getTimeStartTime());
            stmt.setTime(5,schedule.getTimeEndTime());
            stmt.setInt(6,schedule.getClassroomId());
            stmt.registerOutParameter(7,Types.VARCHAR);
            stmt.execute();
            System.out.println(stmt.getString(7));
        }catch (SQLException e){
            logger.error("添加失败");
        }finally {
            DBHelper.close(conn,null,null);
        }
    }
//    删除课程安排
    public void deleteSchedule(Schedule schedule){
        Connection conn=DBHelper.getConnection();
        String sql="{call DeleteSchedule (?,?,?,?)}";
        try(CallableStatement stmt=conn.prepareCall(sql)){
            stmt.setInt(1,schedule.getCourseId());
            stmt.setInt(2,schedule.getClassId());
            stmt.setInt(3,schedule.getWeekday());
            stmt.registerOutParameter(4,Types.VARCHAR);
            stmt.execute();
            System.out.println(stmt.getString(4));
        }catch (SQLException e){
            logger.error("删除失败");
        }finally {
            DBHelper.close(conn,null,null);
        }
    }
//    课程安排是否存在
    public boolean isExist(int courseId){
        return Util.isExist("schedule",courseId,"c_id");
    }
}
