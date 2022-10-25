package com.lzc.jdbc.statement_;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

/**
 * @title: Statement_
 * @Author luozouchen
 * @Date: 2022/10/25 20:37
 * @Version 1.0
 * 演示statement sql注入问题
 */
public class Statement_ {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //让用户输入管理员名和密码
        System.out.print("请输入管理员的名字");
        String admin_name = scanner.nextLine(); //next(): 当接收到 空格或者 '就是表示结束
        System.out.print("请输入管理员的密码");
        String admin_pwd = scanner.nextLine();// 老师说明，如果希望看到 SQL 注入，这里需要用 nextLine
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
        String sql = "select name,pwd from admin where name = '" + admin_name + "' and pwd= '" + admin_pwd + "'   ";
        //执行给定的 SQL 语句，该语句返回单个 ResultSet 对象
        ResultSet resultSet = statement.executeQuery(sql);
        //使用while循环 取出数据
        if (resultSet.next()) {//如果查询到一条记录，则说明该管理员存在
            System.out.println("恭喜，登录成功");
        } else {
            System.out.println("对不起，登陆失败");
        }

        resultSet.close();
        statement.close();
        connection.close();

    }
}
