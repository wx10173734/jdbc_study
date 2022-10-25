package com.lzc.jdbc.datasource;

import com.lzc.jdbc.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @title: ConQuestion
 * @Author luozouchen
 * @Date: 2022/10/25 23:32
 * @Version 1.0
 */
public class ConQuestion {
    //代码 连接Mysql 5000次
    @Test
    public void testCon(){
        //看看连接-关闭 Connect 会耗用多久
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            //使用传统jdbc方式，得到连接
            Connection connection = JDBCUtils.getConnection();
            //做一些工作，比如得到 preparedStatement 发送sql
            //....
            //关闭
            JDBCUtils.close(null,null,connection);
        }
        long end = System.currentTimeMillis();
        System.out.println("传统方式5000次 耗时"+(end-start));//传统方式5000次67340
    }
}
