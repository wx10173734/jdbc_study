package com.lzc.jdbc.datasource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Properties;

/**
 * @title: Druid_
 * @Author luozouchen
 * @Date: 2022/10/26 0:21
 * @Version 1.0
 * 测试druid的使用
 */
public class Druid_ {

    @Test
    public void testDruid() throws Exception {
        //1.创建properties
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\druid.properties"));
        //2.创建一个指定参数的数据库连接池 Druid连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection = dataSource.getConnection();
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("druid连接池时间"+(end-start));//druid连接池时间987


    }
}
