package com.lzc.jdbc.datasource;

import com.lzc.jdbc.utils.JDBCUtils;
import com.lzc.jdbc.utils.JDBCUtilsDruid;
import org.junit.Test;

import java.sql.*;

/**
 * @title: JDBCUtilsByDruid_USE
 * @Author luozouchen
 * @Date: 2022/10/26 0:36
 * @Version 1.0
 */
public class JDBCUtilsByDruid_USE {
    @Test
    public void testSelect() {
        System.out.println("使用德鲁伊方式 完成");
        Connection connection = null;
        String sql = "select * from actor where id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilsDruid.getConnection();
            System.out.println(connection.getClass());//运行类型
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
            JDBCUtilsDruid.close(resultSet,preparedStatement,connection);
        }
    }
}
