package com.morgan.aduAdmSys.dao;

import com.morgan.aduAdmSys.entity.OrderClassroom;
import com.morgan.aduAdmSys.utils.DBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class OrderClassroomDao {
    private static final Logger logger= LoggerFactory.getLogger(OrderClassroomDao.class);
//    添加预约信息
    public void insertOrderClassroom(OrderClassroom orderClassroom) {
        Connection conn = DBHelper.getConnection();
        String sql = "{call sp_book_classroom(?,?,?,?,?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, orderClassroom.getClassroomId());
            stmt.setTimestamp(2, orderClassroom.getTimestampStartTime());
            stmt.setTimestamp(3, orderClassroom.getTimestampEndTime());
            stmt.setInt(4, orderClassroom.getTeacherId());
            stmt.registerOutParameter(5, Types.VARCHAR);
            stmt.execute();
            System.out.println(stmt.getString(5));
        } catch (SQLException e) {
            logger.error("\n预约失败");
        } finally {
            DBHelper.close(conn, null, null);
        }
    }
}
