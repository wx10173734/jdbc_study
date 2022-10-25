package com.lzc.jdbc.resultset_;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

/**
 * @title: ResultSet_
 * @Author luozouchen
 * @Date: 2022/10/25 20:17
 * @Version 1.0
 * 演示select语句返回 resultSet,并取出结果
 */
public class ResultSet_ {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");
        Class.forName(driver);

        //得到连接
        Connection connection = DriverManager.getConnection(url, user, password);
        //得到statement
        Statement statement = connection.createStatement();
        String sql = "select id,name,sex,borndate from actor";
        //执行给定的 SQL 语句，该语句返回单个 ResultSet 对象

        ResultSet resultSet = statement.executeQuery(sql);
        //使用while循环 取出数据
        while (resultSet.next()) { //让光标向后移动，如果没有更多行，则返回false
            int id = resultSet.getInt(1);//获取该行的第1列
            String name = resultSet.getString(2);
            String sex = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            System.out.println(id + "\t" + name + "\t" + sex + "\t" + date);
        }

        resultSet.close();
        statement.close();
        connection.close();

    }
}
