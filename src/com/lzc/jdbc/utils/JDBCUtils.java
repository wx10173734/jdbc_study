package com.lzc.jdbc.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @title: JDBCUtils
 * @Author luozouchen
 * @Date: 2022/10/25 21:47
 * @Version 1.0
 * 这是一个工具类，完成Mysql的连接和关闭资源
 */
public class JDBCUtils {
    //定义相关的属性（4）个，因为只需要1份，做出Static
    private static String user;//用户名
    private static String password;//密码
    private static String url;//url
    private static String driver;//驱动名

    //在static代码块去初始化
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\mysql.properties"));
            //读取相关的属性值
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");
        } catch (Exception e) {
            //在实际开发中，我们可以这样处理
            //1.将编译异常转成运行异常
            //2.这时调用者，可以选择捕获该异常，也可以选择默认处理该异常，比较方便
            throw new RuntimeException(e);
        }
    }

    //连接数据库，返回connection
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //关闭相关资源
    /*
        1.resultset 结果集
        2.statement或者preparestatement
        3.connect
        4.如果需要关闭资源，就传入对象，否则传入Null
     */
    public static void close(ResultSet set, Statement statement, Connection connection) {
        //判断是否为null
        try {
            if (set != null){
                set.close();
            }
            if (statement != null){
                statement.close();
            }
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
