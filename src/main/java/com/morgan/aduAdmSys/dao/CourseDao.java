package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.Course;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CourseDao {
    private static final Logger logger= LoggerFactory.getLogger(CourseDao.class);
    public Course selectCourseByName(String name) {
        Course course=new Course();
        Connection conn= DBHelper.getConnection();
        String sql="select * from course where c_name=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,name);
            rs=ps.executeQuery();
            if(rs.next()){
                course.setId(rs.getInt("c_id"));
                course.setName(rs.getString("c_name"));
                course.setTeacherId(rs.getInt("t_id"));
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
        }finally {
            DBHelper.close(conn,ps,rs);
        }
        return course;
    }
    //    根据授课老师编号查询课程信息
    public Course selectCourseByTeacherId(int teacherId) {
        Course course=new Course();
        Connection conn = DBHelper.getConnection();
        String sql = "select * from course where t_id=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, teacherId);
            rs = ps.executeQuery();
            if(rs.next()){
                course.setId(rs.getInt("c_id"));
                course.setName(rs.getString("c_name"));
                course.setTeacherId(rs.getInt("t_id"));
                course.setMaxStudents(rs.getInt("max_students"));
                course.setCurrentEnrollment(rs.getInt("current_enrollment"));
                course.setCredits(rs.getInt("credits"));
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
        } finally {
            DBHelper.close(conn, ps, rs);
        }
        return course;
    }
//    查询所有课程信息
    public Map<String,Course> selectCourse() {
        Map<String,Course> map=new HashMap<>();
        Connection conn = DBHelper.getConnection();
        String sql = "select teacher.t_name,course.c_name,course.max_students,course.current_enrollment,course.t_id,course.credits ,course.c_id from teacher inner join course on teacher.t_id=course.t_id";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                Course course=new Course();
                String key=rs.getString("t_name");
                course.setName(rs.getString("c_name"));
                course.setMaxStudents(rs.getInt("max_students"));
                course.setCurrentEnrollment(rs.getInt("current_enrollment"));
                course.setCredits(rs.getInt("credits"));
                course.setId(rs.getInt("c_id"));
                course.setTeacherId(rs.getInt("t_id"));
                map.put(key,course);
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
        } finally {
            DBHelper.close(conn, ps, rs);
        }
        return map;
    }
    //    往表中添加数据
    public void insertCourse(Course course){
        Connection conn=DBHelper.getConnection();
        String sql="insert into course(c_id,c_name,t_id,max_students,credits) values(?,?,?,?,?)";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,course.getId());
            ps.setString(2,course.getName());
            ps.setInt(3,course.getTeacherId());
            ps.setInt(4,course.getMaxStudents());
            ps.setInt(5,course.getCredits());
            if(ps.executeUpdate()>0){
                System.out.println("添加成功");
            }else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            logger.error("\n添加失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
    //    按课程号从表中删除数据
    public void deleteCourseById(int id){
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps=null;
        String sql="delete from course where c_id=?";
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
    public void updateCourseById(int id,Course course){
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps;
        String sql="update course set c_id=?,c_name=?,t_id=?,max_students=?,current_enrollment=?,credits=? where c_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,course.getId());
            ps.setString(2,course.getName());
            ps.setInt(3,course.getTeacherId());
            ps.setInt(4,course.getMaxStudents());
            ps.setInt(5,course.getCurrentEnrollment());
            ps.setInt(6,course.getCredits());
            ps.setInt(5,id);
            if(ps.executeUpdate()>0){
                System.out.println("\n修改成功");
            }else {
                System.out.println("修改失败");
            }
        } catch (SQLException e) {
            logger.error("\n修改失败");
        }
    }
//    课程是否存在
    public boolean isExist(int courseId){
        return Util.isExist("course",courseId,"c_id");
    }
//    查询老师是否有教课
    public boolean isExistTeacher(int teacherId){
        return Util.isExist("course",teacherId,"t_id");
    }
    //    从课程中减少一个人
    public void deleteStudentInCourse(int courseId) {
        Connection conn = DBHelper.getConnection();
        String sql = "update course set current_enrollment=current_enrollment-1 where c_id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, courseId);
            if (ps.executeUpdate() > 0) {
                System.out.println("课程人数修改成功");
            } else {
                System.out.println("课程人数修改失败");
            }
        } catch (SQLException e) {
            logger.error("课程人数修改失败");
        } finally {
            DBHelper.close(conn, ps, null);
        }
    }
}
