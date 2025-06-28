package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.ChooseCourse;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ChooseCourseDao {
    private static final Logger logger= LoggerFactory.getLogger(ChooseCourseDao.class);
//    查询某个学生的选课信息
    public Map<String,ChooseCourse> selectChooseCourseById(int studentId) {
        Map<String, ChooseCourse> map=new HashMap<>();
        ChooseCourse chooseCourse=new ChooseCourse();
        Connection conn= DBHelper.getConnection();
        String sql="select course.c_name,chooseCourse.normal_score,chooseCourse.experiment_score,chooseCourse.stage_score,chooseCourse.test_score,chooseCourse.c_id,chooseCourse.average_score from course inner join chooseCourse on course.c_id=chooseCourse.c_id where chooseCourse.s_id=?";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,studentId);
            rs=ps.executeQuery();
            String key;
            while(rs.next()){
                key=rs.getString("c_name");
                chooseCourse.setNormalScore(rs.getInt("normal_score"));
                chooseCourse.setExperimentScore(rs.getInt("experiment_score"));
                chooseCourse.setStageScore(rs.getInt("stage_score"));
                chooseCourse.setTestScore(rs.getInt("test_score"));
                chooseCourse.setAverageScore(rs.getDouble("average_score"));
                chooseCourse.setCourseId(rs.getInt("c_id"));
                map.put(key,chooseCourse);
            }
        } catch (SQLException e) {
            logger.error("查询失败");
        }finally {
            DBHelper.close(conn,ps,rs);
        }
        return map;
    }
//    通过pgsql函数进行选课
    public void insertChooseCourse(int studentId,int courseId) {
        Connection conn = DBHelper.getConnection();
        String sql = "{call sp_student_enroll_course(?,?,?)}";
        try(CallableStatement stmts=conn.prepareCall(sql)) {
            stmts.setInt(1,studentId);
            stmts.setInt(2,courseId);
            stmts.registerOutParameter(3,Types.VARCHAR);
            stmts.execute();
            System.out.println(stmts.getString(3));
        } catch (SQLException e) {
            logger.error("\n添加失败");
        } finally {
            DBHelper.close(conn, null, null);
        }
    }
//    删除选课
    public void deleteChooseCourseById(int studentId,int courseId){
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps=null;
        String sql="delete from chooseCourse where s_id=? and c_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,studentId);
            ps.setInt(2,courseId);
            if(ps.executeUpdate()>0){
                System.out.println("|删除成功");
            }else {
                System.out.println("|删除失败");
            }
        } catch (SQLException e) {
            logger.error("|删除失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
    //    录入成绩
    public void updateChooseCourseById(ChooseCourse chooseCourse,int teacherId){
        Connection conn=DBHelper.getConnection();
        PreparedStatement ps;
        String sql="update ChooseCourse set normal_score=?,experiment_score=?,stage_score=? ,test_score=? where s_id=? and c_id=(select c_id from course where t_id=?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,chooseCourse.getNormalScore());
            ps.setInt(2,chooseCourse.getExperimentScore());
            ps.setInt(3,chooseCourse.getStageScore());
            ps.setInt(4,chooseCourse.getTestScore());
            ps.setInt(5,chooseCourse.getStudentId());
            ps.setInt(6,teacherId);
            if(ps.executeUpdate()>0){
                System.out.println("|录入成功");
            }else {
                System.out.println("|录入失败");
            }
        } catch (SQLException e) {
            logger.error("|录入失败");
        }
    }
//    该学生是否存在选课信息是否存在
    public boolean isExist(int studentId) {
        return Util.isExist("chooseCourse",studentId,"s_id");
    }
//    该学生该选课信息是否存在
    public boolean isExist(int studentId,int courseId){
        return Util.isExist("chooseCourse",studentId,courseId,"s_id","c_id");
    }
}
