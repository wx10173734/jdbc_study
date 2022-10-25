package com.lzc.jdbc.myjdbc;

/**
 * 规定的jdbc接口(方法)
 */
public interface JdbcInterface {
    //链接
    public Object getConnection();

    //crud
    public void crud();

    //关闭连接
    public void close();
}
