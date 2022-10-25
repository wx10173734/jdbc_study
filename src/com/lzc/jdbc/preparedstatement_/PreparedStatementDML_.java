package com.lzc.jdbc.preparedstatement_;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

/**
 * @title: PreparedStatementDML_
 * @Author luozouchen
 * @Date: 2022/10/25 21:17
 * @Version 1.0
 * 演示preparestatement 使用Dml语句
 */
public class PreparedStatementDML_ {
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
        //3得到prepareStatement
        //3.1组织sql,sql 语句？相当于占位符
//        String sql = "insert into admin values (?,?)";
//        String sql = "update admin set pwd = ? where name =?";
        String sql = "delete from admin where name = ?";
        //3.2prepareStatement 对象实现了prepareStatement接口的实现类对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        //3.3给？赋值
        preparedStatement.setString(1, admin_name);
//        preparedStatement.setString(2, admin_name);

        int rows = preparedStatement.executeUpdate();
        System.out.println(rows>0 ? "执行成功":"执行失败");

        preparedStatement.close();
        connection.close();
    }

}
