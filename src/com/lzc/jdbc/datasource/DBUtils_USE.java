package com.lzc.jdbc.datasource;

import com.lzc.jdbc.utils.JDBCUtilsDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @title: JDBCUtilsByDruid_USE
 * @Author luozouchen
 * @Date: 2022/10/26 0:36
 * @Version 1.0
 * 使用dbutils工具类
 */
public class DBUtils_USE {
    //使用dbutils工具类 +druid完成对表的crud操作
    @Test
    public void testQueryMany() throws Exception {//返回结果是多行的情况
        //1.得到 连接 Druid
        Connection connection = JDBCUtilsDruid.getConnection();
        //2.使用DButils类和接口，先引入dbutils jar文件
        //3.创建queryrunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关的方法，返回Arraylist结果集
//        String sql = "select * from actor where id >=?";
        String sql = "select id,name from actor where id >=?";

        //解读
        //（1）query方法就是执行sql语句，得到resultset ---- 封装到---- arraylist集合中
        //（2) 返回集合
        //（3）connection 连接
        //（4）sql:执行的sql语句
        //（5）new BeanListHandler<>(Actor.class) 在将resultset->actor-》封装到Arraylist
        //底层使用反射机制，去获取actor类的属性，然后进行封装
        //（6）1.就是给sql语句中的？赋值的，可以有多个值，因为是可变参数object... params
        //（7）底层得到的resultset，会在query关闭，关闭preparedstatement
        /**
         * 分析 queryRunner.query 方法:
         * public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params) throws
         SQLException {
         * PreparedStatement stmt = null;//定义 PreparedStatement
         * ResultSet rs = null;//接收返回的 ResultSet
         * Object result = null;//返回 ArrayList
         *
         * try {
         * stmt = this.prepareStatement(conn, sql);//创建 PreparedStatement
         * this.fillStatement(stmt, params);//对 sql 进行 ? 赋值
         * rs = this.wrap(stmt.executeQuery());//执行 sql,返回 resultset
         * result = rsh.handle(rs);//返回的 resultset --> arrayList[result] [使用到反射，对传入 class 对象
         处理]
         * } catch (SQLException var33) {
         * this.rethrow(var33, sql, params);
         * } finally {
         * try {
         * this.close(rs);//关闭 resultset
         * } finally {
         * this.close((Statement)stmt);//关闭 preparedstatement 对象
         * }
         * }
         *
         * return result;
         * }
         */
        List<Actor> list =
                queryRunner.query(connection, sql, new BeanListHandler<>(Actor.class), 1);
        System.out.println("输出集合信息");
        for (Actor actor : list) {
            System.out.println(actor);
        }
        //释放资源
        JDBCUtilsDruid.close(null, null, connection);

    }

    //演示 apache-dbutils + druid 完成 返回的结果是单行记录(单个对象)
    @Test
    public void testQuerySingle() throws SQLException {
        //1.得到 连接 Druid
        Connection connection = JDBCUtilsDruid.getConnection();

        //2.使用DButils类和接口，先引入dbutils jar文件
        //3.创建queryrunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关的方法，返回单个对象
//        String sql = "select * from actor where id >=?";
        String sql = "select id,name from actor where id =?";
        // 因为我们返回的单行记录<--->单个对象 , 使用的 Hander 是 BeanHandle
        Actor actor = queryRunner.query(connection, sql, new BeanHandler<>(Actor.class), 7);
        System.out.println(actor);
        JDBCUtilsDruid.close(null, null, connection);

    }

    //演示 apache-dbutils + druid 完成查询结果是单行单列-返回的就是 object
    @Test
    public void testScalar() throws SQLException {
        //1.得到 连接 Druid
        Connection connection = JDBCUtilsDruid.getConnection();

        //2.使用DButils类和接口，先引入dbutils jar文件
        //3.创建queryrunner
        QueryRunner queryRunner = new QueryRunner();
        //4.就可以执行相关的方法，返回单行单列，返回的就是object
//        String sql = "select * from actor where id >=?";
        String sql = "select name from actor where id =?";
        //老师解读： 因为返回的是一个对象, 使用的 handler 就是 ScalarHandler
        Object query = queryRunner.query(connection, sql, new ScalarHandler(), 4);
        System.out.println(query);
        JDBCUtilsDruid.close(null, null, connection);

    }


    //演示 apache-dbutils + druid 完成 dml (update, insert ,delete)
    @Test
    public void testDML() throws SQLException {
        Connection connection = JDBCUtilsDruid.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        //这里组织sql 完成 update,insert,delete
//        String sql = "update actor set name=? where id =?";
//        String sql = "insert into actor values (null,?,?,?,?)";
        String sql = "delete from actor where id=?";
        //(1) 执行 dml 操作是 queryRunner.update()
        //(2) 返回的值是受影响的行数 (affected:
        //修改
//        int affectedRow = queryRunner.update(connection, sql, "张三丰", 4);
        //添加
//        int affectedRow = queryRunner.update(connection, sql, "lzc", "男", "2000-06-21", "110");
        //删除
        int affectedRow = queryRunner.update(connection, sql, 5);
        System.out.println(affectedRow > 0 ? "执行成功" : "执行没有影响到表");
        JDBCUtilsDruid.close(null, null, connection);

    }
}
