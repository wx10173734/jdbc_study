package com.lzc.jdbc.transaction_;

import com.lzc.jdbc.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @title: Transaction_
 * @Author luozouchen
 * @Date: 2022/10/25 22:17
 * @Version 1.0
 * 演示jdbc中如何使用事务
 */
public class Transaction_ {
    //没有使用事务
    @Test
    public void noTransaction() {
        //操作转账业务
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "update account set balance = balance-100 where id =1";
        String sql2 = "update account set balance = balance+100 where id =2";
        try {
            connection = JDBCUtils.getConnection();//在默认情况下，connection 是默认自动提交
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();//执行第一条sql

            int i = 1 / 0;//抛出异常
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }

    //使用事务解决
    @Test
    public void transaction() {
        //操作转账业务
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "update account set balance = balance-100 where id =1";
        String sql2 = "update account set balance = balance+100 where id =2";
        try {
            connection = JDBCUtils.getConnection();//在默认情况下，connection 是默认自动提交
            //将connection 设置为不自动提交
            connection.setAutoCommit(false);//相当于开启了事务
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();//执行第一条sql

            //int i = 1 / 0;//抛出异常
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();

            //这里提交事务
            connection.commit();

        } catch (SQLException e) {
            //这里可以进行回滚，即撤销执行的sql
            //默认回滚到事务开始的状态
            System.out.println("执行发生了异常，撤销执行的sql");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        } finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }
}
