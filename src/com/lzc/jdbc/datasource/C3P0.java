package com.lzc.jdbc.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @title: C3P0
 * @Author luozouchen
 * @Date: 2022/10/25 23:56
 * @Version 1.0
 * 演示c3p0的使用
 */
public class C3P0 {
    //方式1：相关参数，在程序中指定user,url,password等
    @Test
    public void testC3P0_01() throws Exception {
        //1.创建一个数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        //2.通过配置文件获取相关信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        //读取相关的属性值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");
        //给数据源 comboPooledDataSource 设置相关参数
        //注意:连接管理是由 comboPooledDataSource 来管理
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);

        //设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);
        //最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
        //测试连接池的效率，测试连接mysql 5000次的操作
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection = comboPooledDataSource.getConnection();//这个方法就是从datasource接口实现的
//            System.out.println("连接ok");
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("c3p0连接池时间"+(end-start));//c3p0连接池时间906

    }

    //第二种方式 使用配置文件模板来完成
    @Test
    public void testC3P0_02() throws SQLException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("hello");
        //测试连接池的效率，测试连接mysql 5000次的操作
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection = comboPooledDataSource.getConnection();
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("c3p0 第二种方式连接池时间"+(end-start));//c3p0连接池时间768

    }
}
