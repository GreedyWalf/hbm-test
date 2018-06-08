package com.qs.test;

import com.qs.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;

import java.util.*;

public class Test {

    private static Session session = new Configuration().configure().buildSessionFactory().openSession();

    public static void main(String[] args) {
//        testSave();
//        testSave();
//        testStatus();
        testStatus2();
    }

    /**
     * 测试数据保存，验证一级缓存和快照理论
     *
     * 1、session执行save方法后并不会立即发出insert语句，而是在事务提交的时候发出；
     * 2、session执行完save方法后，userId（主键）已经生成了，并被一同放在了一级缓存中；
     * 3、不要在事务提交前更新userId（主键），会抛出异常；在提交之前，无论更改userName属性多少次，只会发出一次update语句；
     */
    public static void testSave() {
        Transaction transaction = session.beginTransaction();
        User user = new User("zhangsan");
        //将user对象保存在一级缓存和快照中
        session.save(user);

        //执行完save方法后，user对象的主键userId已经生成，一同放在一级缓存中；
        System.out.println(user.getUserId());

        //更改了user对象的属性，将快照区中user属性值由"zhangsan"改成"张三"
        user.setUserName("张三");

        //不能更改主键值，会出现 org.hibernate.HibernateException异常
        //user.setUserId("402882de63d9966a0163d9966d2f0000");
        //事务提交时，对比一级缓存和快照中user对象值，返现userName属性不一致，然后发出update语句

//        session.flush();
        session.clear();
        transaction.commit();
    }


    /**
     * 测试对象从瞬时态 -> 持久态 -> 游离态
     */
    public static void testStatus(){
        Transaction transaction = session.beginTransaction();
        User user = new User("zhangsan");  //new一个兑现，为瞬时态
        session.save(user);  //这里变成持久态
        System.out.println(user.getUserId());
        System.out.println(user.getUserName());

        user.setUserName("李四"); //如果上面已经是持久态了，这里更改属性会将一级缓存中对象属性更改

        //下面的数据从一级缓存中获取的，看输出结果可以知道，确实将缓存中的userName属性值更改了
        User user2 = (User) session.get(User.class, user.getUserId());
        System.out.println(user2.getUserId());
        System.out.println(user2.getUserName()); //李四

        session.clear();
        user.setUserName("王五");  //将一级缓存中的数据清空，对象编程游离态，事务提交也不会发出update操作数据库了
        transaction.commit();
    }

    /**
     * 测试对象从持久态 -> 游离态
     */
    public static void testStatus2(){
        Transaction transaction = session.beginTransaction();
        //一级缓存中没有数据，先从数据库中查询，在保存在一级缓存中，此时获得的对象为持久态
        User user = (User) session.get(User.class, "");
        System.out.println(user.getUserId());

        //将对象从一级缓存和数据库中删除，快照中的对象也被删除
        session.delete(user);

        //从一级缓存和数据库中都获取不到数据，此时user对象为游离态
//        user = (User) session.get(User.class, "");
//        System.out.println(user.getUserId());
        transaction.commit();
    }
}
