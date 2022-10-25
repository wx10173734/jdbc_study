package com.lzc.jdbc.myjdbc;

/**
 * @title: OracleJdbcImpl
 * @Author luozouchen
 * @Date: 2022/10/25 16:50
 * @Version 1.0
 */
public class OracleJdbcImpl implements JdbcInterface {
    @Override
    public Object getConnection() {
        System.out.println("得到了oracle的连接");
        return null;
    }

    @Override
    public void crud() {
        System.out.println("完成oracle的增删改查");
    }

    @Override
    public void close() {
        System.out.println("关闭oracle连接");
    }
}
