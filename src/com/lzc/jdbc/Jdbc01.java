package com.lzc.jdbc;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @title: Jdbc01
 * @Author luozouchen
 * @Date: 2022/10/25 16:58
 * @Version 1.0
 * 这是第一个Jdbc程序，完成简单的操作
 */
public class Jdbc01 {
    public static void main(String[] args) throws SQLException {
        //1.注册驱动
        Driver driver = new Driver();//创建Driver对象
        //2.得到连接
        // 老师解读
        //(1) jdbc:mysql:// 规定好表示协议，通过 jdbc 的方式连接 mysql
        //(2) localhost 主机，可以是 ip 地址
        //(3) 3306 表示 mysql 监听的端口
        //(4) hsp_db02 连接到 mysql dbms 的哪个数据库
        //(5) mysql 的连接本质就是前面学过的 socket 连接
        String url = "jdbc:mysql://localhost:3306/jdbc_study";
        //将用户名和密码放入到properties对象中
        Properties properties = new Properties();
        properties.setProperty("user", "root");//用户
        properties.setProperty("password", "123");//密码

        Connection connect = driver.connect(url, properties);

        //3.执行sql
//        String sql = "insert into actor values(null,'罗邹晨','男','1970-11-11','110')";
//        String sql = "update  actor set name='周星驰' where id =1";
        String sql = "delete from actor where id =1";
        //statement 用于执行静态 SQL 语句并返回其生成的结果的对象
        Statement statement = connect.createStatement();
        int rows = statement.executeUpdate(sql);//如果是dml语句，返回的就是影响行数
        System.out.println(rows > 0 ? "成功" : "失败");

        //4.关闭连接资源
        statement.close();
        connect.close();
    }
}
