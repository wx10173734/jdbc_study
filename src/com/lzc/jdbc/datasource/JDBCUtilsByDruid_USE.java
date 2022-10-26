package com.lzc.jdbc.datasource;

import com.lzc.jdbc.utils.JDBCUtils;
import com.lzc.jdbc.utils.JDBCUtilsDruid;
import org.junit.Test;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        String sql = "select * from actor where id >=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtilsDruid.getConnection();
            System.out.println(connection.getClass());//运行类型
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
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
            JDBCUtilsDruid.close(resultSet, preparedStatement, connection);
        }
    }


    //使用老师的土方法来解决resuleset封装到arraylist集合中
    @Test
    public void testSelectToArrayList() {
        System.out.println("使用德鲁伊方式 完成");
        Connection connection = null;
        String sql = "select * from actor where id >=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Actor> list = new ArrayList<>();
        try {
            connection = JDBCUtilsDruid.getConnection();
            System.out.println(connection.getClass());//运行类型
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String sex = resultSet.getString("sex");
                Date borndate = resultSet.getDate("borndate");
                String phone = resultSet.getString("phone");
//                System.out.println(id + "\t" + name + "\t" + sex + "\t" + borndate + "\t" + phone);
                //把得到的resultset记录 封装到actor对象，放入到list集合
                list.add(new Actor(id, name, sex, borndate, phone));
            }
            System.out.println("list集合数据=" + list);
            for (Actor actor : list) {
                System.out.println(actor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCUtilsDruid.close(resultSet, preparedStatement, connection);
        }
        //因为arraylist 和connection 没有任何关联，所以该集合可以复用

    }
}
