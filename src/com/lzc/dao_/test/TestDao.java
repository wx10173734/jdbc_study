package com.lzc.dao_.test;

import com.lzc.dao_.dao.ActorDao;
import com.lzc.dao_.domain.Actor;
import org.junit.Test;

import java.util.List;

/**
 * @title: TestDao
 * @Author luozouchen
 * @Date: 2022/10/26 16:25
 * @Version 1.0
 */
public class TestDao {
    //测试 ActorDAO 对 actor 表 crud 操作
    @Test
    public void testActorDAO() {
        ActorDao actorDao = new ActorDao();
        //1. 查询
        List<Actor> actors = actorDao.queryMulti("select * from actor where id >= ?", Actor.class, 1);
        System.out.println("===查询结果===");
        for (Actor actor : actors) {
            System.out.println(actor);
        }

        //2. 查询单行记录
        Actor actor = actorDao.querySingle("select * from actor where id =?", Actor.class, 4);
        System.out.println("====查询单行结果====");
        System.out.println(actor);
        //3. 查询单行单列
        Object o = actorDao.queryScalar("select name from actor where id = ?", 6);
        System.out.println("====查询单行单列值===");
        System.out.println(o);
        //4. dml 操作 insert ,update, delete
        int update = actorDao.update("insert into actor values(null, ?, ?, ?, ?)", "张无忌", "男", "2000-11-11", "999");
        System.out.println(update > 0 ? "执行成功" : "执行没有影响表");

    }





}
