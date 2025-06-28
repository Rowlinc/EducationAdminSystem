package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.Exam;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDao {
    private static final Logger logger= LoggerFactory.getLogger(ExamDao.class);
//    根据班级id查询考试安排
    public List<Exam> selectExamByClassId(int classId){
        List<Exam> list=new ArrayList<>();
        Exam exam=new Exam();
        Connection conn= DBHelper.getConnection();
        String sql="select * from Exam where class_id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,classId);
            rs=ps.executeQuery();
            while(rs.next()){
                exam.setCourseId(rs.getInt("c_id"));
                exam.setClassroomId(rs.getInt("classroom_id"));
                exam.setNum(rs.getInt("e_time"));
                exam.setDateDate(rs.getDate("e_date"));
                exam.setClassId(rs.getInt("class_id"));
                list.add(exam);
            }
        } catch (SQLException e) {
            logger.error("\n查询失败");
        }finally {
            DBHelper.close(conn,ps,rs);
        }
        return list;
    }
//    添加考试安排
    public void insertExam(Exam exam) {
        Connection conn = DBHelper.getConnection();
        String sql = "insert into Exam values(?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, exam.getCourseId());
            ps.setInt(2, exam.getClassroomId());
            ps.setInt(3, exam.getNum());
            ps.setDate(4, exam.getDateDate());
            ps.setInt(5, exam.getClassId());
            if(ps.executeUpdate()>0){
                System.out.println("添加成功");
            }else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            logger.error("添加失败");
        } finally {
            DBHelper.close(conn, ps, null);
        }
    }
//    删除考试安排
    public void deleteExam(int courseId){
        Connection conn = DBHelper.getConnection();
        String sql = "delete from exam where c_id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,courseId);
            if(ps.executeUpdate()>0){
                System.out.println("删除成功");
            }else {
                System.out.println("删除失败");
            }
        } catch (SQLException e) {
            logger.error("删除失败");
        } finally {
            DBHelper.close(conn, ps, null);
        }
    }
//    根据班级id查询考试安排
    public boolean isExist(int classId){
        return Util.isExist("exam",classId,"class_id");
    }
//    根据课程id查询考试安排
    public boolean isExistCourse(int courseId){
        return Util.isExist("exam",courseId,"c_id");
    }
//    根据时间查询考试安排
    public boolean isExistTime(int examNum,String date,int classroomId){
        Connection conn = DBHelper.getConnection();
        PreparedStatement ps = null;
        String sql = "select count(*)  from exam where e_time=? and e_date=? and classroom_id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, examNum);
            ps.setDate(2, Date.valueOf(date));
            ps.setInt(3, classroomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("\n查询失败");
        } finally {
            DBHelper.close(conn, ps, null);
        }
        return false;
    }
}
