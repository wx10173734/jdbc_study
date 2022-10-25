package com.lzc.jdbc.jdbcConn;

import com.mysql.cj.jdbc.Driver;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @title: jdbcConn
 * @Author luozouchen
 * @Date: 2022/10/25 17:33
 * @Version 1.0
 * 分析java连接Mysql的5种方式
 */
public class jdbcConn {
    //方式1
    @Test
    public void connect01() throws SQLException {
        Driver driver = new Driver();
        String url = "jdbc:mysql://localhost:3306/jdbc_study";
        //将用户名和密码放入到properties对象中
        Properties properties = new Properties();
        properties.setProperty("user", "root");//用户
        properties.setProperty("password", "123");//密码
        Connection connect = driver.connect(url, properties);
        System.out.println(connect);
    }

    //方式2
    @Test
    public void connect02() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //使用反射加载Driver类,动态加载，更加的灵活，减少依赖性
        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        String url = "jdbc:mysql://localhost:3306/jdbc_study";
        //将用户名和密码放入到properties对象中
        Properties properties = new Properties();
        properties.setProperty("user", "root");//用户
        properties.setProperty("password", "123");//密码
        Connection connect = driver.connect(url, properties);
        System.out.println(connect);

    }

    //方式3 使用DriverManager 替代driver进行统一管理
    @Test
    public void connect03() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        //使用反射加载Driver
        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        //创建url user password
        String url = "jdbc:mysql://localhost:3306/jdbc_study";
        String user = "root";
        String password = "123";

        DriverManager.registerDriver(driver);//注册driver驱动
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式 4: 使用 Class.forName 自动完成注册驱动，简化代码
    //这种方式获取连接是使用的最多，推荐使用
    @Test
    public void connect04() throws ClassNotFoundException, SQLException {
        //使用反射加载Driver类

        /*
        源码: 1. 静态代码块，在类加载时，会执行一次. 2. DriverManager.registerDriver(new Driver());
        3. 因此注册 driver 的工作已经完成
        static {
        try {
        DriverManager.registerDriver(new Driver());
        } catch (SQLException var1) {
        throw new RuntimeException("Can't register driver!");
        }
        }
        */

        Class.forName("com.mysql.cj.jdbc.Driver");
        //创建url user password
        String url = "jdbc:mysql://localhost:3306/jdbc_study";
        String user = "root";
        String password = "123";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    //方式 5 , 在方式 4 的基础上改进，增加配置文件，让连接 mysql 更加灵活
    @Test
    public void connect05() throws IOException, ClassNotFoundException, SQLException {
        //通过 Properties 对象获取配置文件的信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        //获取相关的值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        Class.forName(driver);//建议写上
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

    @Test
    public void test_Connect() throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);

        String sql = "insert into actor values(null,'罗邹晨','男','1970-11-11','110')";
        Statement statement = connection.createStatement();
        int rows =0;
        for (int i = 0; i < 5; i++) {
            rows = statement.executeUpdate(sql);
        }
        System.out.println(rows > 0 ? "成功" : "失败");

    }
}
