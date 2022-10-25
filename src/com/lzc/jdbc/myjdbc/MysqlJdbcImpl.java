package com.lzc.jdbc.myjdbc;

/**
 * @title: MysqlJdbcImpl
 * @Author luozouchen
 * @Date: 2022/10/25 16:48
 * @Version 1.0
 * mysql数据库实现了Jdbc接口
 */
public class MysqlJdbcImpl implements JdbcInterface {
    @Override
    public Object getConnection() {
        System.out.println("得到了mysql的连接");
        return null;
    }

    @Override
    public void crud() {
        System.out.println("完成mysql的增删改查");
    }

    @Override
    public void close() {
        System.out.println("关闭Mysql连接");
    }
}
