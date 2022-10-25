package com.lzc.jdbc.utils;

import org.junit.Test;

import java.sql.*;

/**
 * @title: JDBCUtils_Use
 * @Author luozouchen
 * @Date: 2022/10/25 21:58
 * @Version 1.0
 * 该类演示如何使用JDBCUtils 工具类，完成dml和select
 */
public class JDBCUtils_Use {


    @Test
    public void testDML() {//insert,update,delete
        //1.得到连接
        Connection connection = null;
        //2.组织一个sql
        String sql = "update actor set name=? where id =?";
        //3.创建preparestatement
        PreparedStatement preparedStatement = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            //给占位符赋值
            preparedStatement.setString(1, "周星驰");
            preparedStatement.setInt(2, 4);
            //执行
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭资源
            JDBCUtils.close(null, preparedStatement, connection);
        }

    }

    @Test
    public void testSelect() {
        Connection connection = null;
        String sql = "select * from actor where id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,5);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                Date borndate = resultSet.getDate("borndate");
                String phone = resultSet.getString("phone");
                System.out.println(id + "\t" + name + "\t" + sex + "\t" + borndate + "\t" + phone);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(resultSet,preparedStatement,connection);
        }
    }
}
