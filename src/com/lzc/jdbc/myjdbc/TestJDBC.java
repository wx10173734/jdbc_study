package com.lzc.jdbc.myjdbc;

/**
 * @title: TestJDBC
 * @Author luozouchen
 * @Date: 2022/10/25 16:49
 * @Version 1.0
 */
public class TestJDBC {
    public static void main(String[] args) {
        //完成对mysql操作
        JdbcInterface jdbcInterface = new MysqlJdbcImpl();
        jdbcInterface.getConnection();//通过接口来调用实现类(动态绑定机制)
        jdbcInterface.crud();
        jdbcInterface.close();

        //完成对oracle操作
        JdbcInterface oracleJdbc = new OracleJdbcImpl();
        oracleJdbc.getConnection();//通过接口来调用实现类(动态绑定机制)
        oracleJdbc.crud();
        oracleJdbc.close();
    }
}
