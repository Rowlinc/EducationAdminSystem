package com.morgan.aduAdmSys.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DBHelper {
    private static final Properties prop=new Properties();
    private static final Logger logger= LoggerFactory.getLogger(DBHelper.class);
//    获取连接
    public static Connection getConnection(){
        try(InputStream input= Files.newInputStream(Paths.get("src/main/resources/dbconfig.properties"))) {
            prop.load(input);
        } catch (IOException e) {
            logger.error("配置文件加载失败");
        }
        //读取配置文件中的属性
        String url = prop.getProperty("db_url");
        String user = prop.getProperty("db_user");
        String password = prop.getProperty("db_password");
        Connection conn = null;
        try {
             conn= DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            logger.error("数据库连接失败");
        }
        return conn;
    }
//    释放资源
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if(conn!=null) {
                conn.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
