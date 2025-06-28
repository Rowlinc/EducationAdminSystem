package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.Classroom;
import com.morgan.aduAdmSys.utils.DBHelper;
import com.morgan.aduAdmSys.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClassroomDao {
    private static final Logger logger= LoggerFactory.getLogger(ClassroomDao.class);
    //    添加一个教室
    public void insertClassroom(Classroom classroom){
        Connection conn= DBHelper.getConnection();
        String sql="insert into classroom(c_id) values(?)";
        PreparedStatement ps=null;
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,classroom.getId());
            if(ps.executeUpdate()>0){
                System.out.println("添加成功");
            }else {
                System.out.println("添加失败");
            }
        } catch (SQLException e) {
            logger.error("教室添加失败");
        }finally {
            DBHelper.close(conn,ps,null);
        }
    }
    //    查询教室是否存在
    public boolean isExist(int classroomId) {
        return Util.isExist("classroom", classroomId, "c_id");
    }
}
