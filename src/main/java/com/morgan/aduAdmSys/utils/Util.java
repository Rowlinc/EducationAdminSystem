package com.morgan.aduAdmSys.utils;

import com.morgan.aduAdmSys.dao.ExamDao;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Util {
    static Scanner scanner = new Scanner(System.in);
    public static final int MAX_CLASS_COUNT = 60;
    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_PASSWORD = "Xxy1013@";
    public static final String DEFAULT_PASSWORD = "Songyiming123@";
    public static final int MAX_COURSE_CAPACITY=100;
    public static final int MIN_COURSE_CAPACITY=10;
    public static final int MIN_COURSE_CREDIT=1;
    public static final int MAX_COURSE_CREDIT=15;
    public static String checkPassword(String oldPassword) throws NoSuchAlgorithmException {
        Scanner sc = new Scanner(System.in);
        String newPassword;
        System.out.print("请输入新密码:");
        while (true) {
            newPassword = sc.nextLine();
            if (newPassword.equalsIgnoreCase("y")) {
                return null;
            } else if (Encrypt.encryptMD5(newPassword).equals(oldPassword)) {
                System.out.print("新密码与原密码重复,请重新输入,输入y退出:");
            } else if (!checkPasswordPattern(newPassword)) {
                System.out.print("密码格式错误,请重新输入,格式为8-16位数字、大小写字母、@,输入y退出:");
            } else {
                return newPassword;
            }
        }
    }

    //    检查密码格式是否正确
    public static boolean checkPasswordPattern(String password) {
        if (password.length() < 8 || password.length() > 16) return false;
        else {
            boolean checkNum = false, checkSpecial = false, checkBigLetter = false, checkSmallLetter = false;
            for (char c : password.toCharArray()) {
                if (c < 48 || (c > 57 && c < 64) || (c > 90 && c < 97) || c > 122) return false;
                else if (c < 58) checkNum = true;
                else if (c == 64) checkSpecial = true;
                else if (c < 91) checkBigLetter = true;
                else checkSmallLetter = true;
                if (checkNum && checkBigLetter && checkSmallLetter && checkSpecial) break;
            }
            return checkNum && checkBigLetter && checkSmallLetter && checkSpecial;
        }
    }

    //    检查是否存在(一个参数)
    public static boolean isExist(String tableName, int id, String colName) {
        Connection conn = DBHelper.getConnection();
        PreparedStatement ps = null;
        String sql = "select count(*)  from " + tableName + " where " + colName + "=?";
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("|查询失败");
        } finally {
            DBHelper.close(conn, ps, rs);
        }
        return false;
    }

    //    检查是否存在(两个参数)
    public static boolean isExist(String tableName, int id1, int id2, String colName1, String colName2) {
        Connection conn = DBHelper.getConnection();
        PreparedStatement ps = null;
        String sql = "select count(*)  from " + tableName + " where "+colName1+"=? and "+colName2+"=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id1);
            ps.setInt(2,id2);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("|查询失败");
        } finally {
            DBHelper.close(conn, ps, null);
        }
        return false;
    }

//    Date的格式化校验
    public static String getDateFormatString() {
        // 1. 定义日期格式模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // 严格模式，拒绝非法日期（如2月30日）
        String date;
        boolean result = false;
        while (true) {
            date = scanner.nextLine();
            System.out.println("|================================================|");
            try {
                // 2. 解析日期字符串
                Date date1 = sdf.parse(date);
                // 3. 重新格式化并对比（避免自动修正，如1999-02-30会被转为1999-03-02）
                String formattedDate = sdf.format(date1);
                result = date.equals(formattedDate);
            } catch (Exception e) {
                // 格式错误或日期非法时保持false
            }
            if (result) {
                break;
            }else {
                System.out.println("非法的日期格式，请重新输入！");
            }
        }
        return date;
    }
//    Time的格式化校验
    public static String getTimeFormatString() {
        // 1. 定义日期格式模板
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setLenient(false); // 严格模式，拒绝非法日期（如2月30日）
        String time;
        boolean result = false;
        while (true) {
            time = scanner.nextLine();
            System.out.println("|================================================|");
            try {
                Date date = sdf.parse(time);
                // 转换为java.sql.Time类型（仅保留时分秒，日期部分设为1970-01-01）
                Time sqlTime = new Time(date.getTime());
                // 校验时间合法性（与原始字符串对比）
                String formatted = sdf.format(sqlTime);
                result = time.equals(formatted);
            } catch (Exception e) {
                // 格式错误或日期非法时保持false
            }
            if (result) {
                break;
            }else {
                System.out.print("|非法的日期格式，请重新输入:");
            }
        }
        return time;
    }
//    Timestamp的格式化校验
    public static String getTimestampFormatString() {
        // 1. 定义时间戳格式模板（包含日期、时分秒、毫秒）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setLenient(false); // 严格模式，拒绝非法值
        String timestamp;
        boolean result = false;
        while (true) {
            timestamp = scanner.nextLine();
            System.out.println("|================================================|");
            try {
                // 2. 解析时间戳字符串
                Date date = sdf.parse(timestamp);

                // 3. 转换为java.sql.Timestamp并重新格式化
                Timestamp sqlTimestamp = new Timestamp(date.getTime());
                String formatted = sdf.format(sqlTimestamp);

                // 4. 对比原始字符串与格式化结果（避免自动修正非法值）
                result = timestamp.equals(formatted);
            } catch (Exception e) {
                // 格式错误或日期非法时保持false
            }
            if (result) {
                break;
            }else {
                System.out.println("非法的日期格式，请重新输入！");
            }
        }
        return timestamp;
    }
//    姓名校验
    public static String getName() {
        String name;
        while(true){
            name = scanner.nextLine();
            if(name.isEmpty() ||name.length()>10) {
                System.out.print("|请输入正确的姓名,是否重新输入(yes/no)?");
//                忽略大小写的匹配
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return null;
                } else {
                    System.out.print("|请重新输入姓名:");
                }
            }else {
                break;
            }
        }
        return name;
    }
//    性别校验
    public static String getGender() {
        String sex;
        while(true){
            sex=scanner.nextLine();
            if(sex.equals("男")||sex.equals("女")){
                break;
            }else {
                System.out.print("|非法输入,是否重新输入(yes/no)?");
                if(scanner.nextLine().equalsIgnoreCase("no")){
                    return null;
                }else {
                    System.out.print("|请重新输入性别:");
                }
            }
        }
        return sex;
    }
//    考试的课节合法性与重复校验
    public static int checkClass(String date,int classId) {
        ExamDao examDao = new ExamDao();
        int examNum;
        while (true) {
            examNum = scanner.nextInt();
            scanner.nextLine();
            if (examNum < 1 || examNum > 12 || examNum % 2 == 0) {
                System.out.print("起始课节只能为奇数且只能在1-12之间,是否重新输入(yes/no)?");
                if (scanner.nextLine().equalsIgnoreCase("no")) {
                    return 0;
                } else {
                    System.out.print("|请重新输入课节:");
                }
            } else {
                if (examDao.isExistTime(examNum, date,classId)) {
                    System.out.println("该时间段已有考试安排，是否重新输入(yes/no)?");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        return 0;
                    } else {
                        System.out.print("|请重新输入课节:");
                    }
                } else {
                    break;
                }
            }
        }
        return examNum;
    }
}
