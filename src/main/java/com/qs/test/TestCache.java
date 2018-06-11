package com.qs.test;

import com.qs.entity.Order;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * 测试只是点：
 *  session的get()和load()方法区别；
 *      1、get()方法获取不到数据返回null，load()抛出ClassNotFoundException异常；
 *      2、get()方法直接返回实体对象，load()方法返回代理对象；
 *      3、load()方法可以从二级缓存获取数据；
 *
 *  hibernate中对象的状态：
 *      1、调用save()、saveOrUpdate()、persist()方法后，游离对象变成持久化数据；（在数据库事务提交事，会判断持久化对象属性是否更改，如果更改了则会发出update语句进行更新操作；）
 *      2、调用load()、get()方法后可以获取持久化对象；
 *
 */
public class TestCache {
    public static void main(String[] args) {
//        test();
//        test2();
//        test3();
//        test4();
    }

    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    private static void test(){
        Session session = sessionFactory.openSession();
        String orderId = "4028810e628be81801628be81a690001";

        Order order = (Order) session.get(Order.class, orderId);
        System.out.println(order);
        Order order2  = (Order) session.load(Order.class, orderId);
        System.out.println(order2);

//        Order order = (Order) session.get( Order.class,"4028810e628be81801628be81a690001");
//        session.get(Order.class, "4028810e628be81801628be81a690001");
//        session.load(Order.class,"4028810e628be81801628be81a690001");
//        session.load(Order.class,"4028810e628be81801628be81a690001");
//        System.out.println(order);
    }


//    private static void test2(){
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        Order order = new Order();
//        order.setId("4028810e628be81801628be81a690001");
//        order = (Order) session.load(Order.class, order.getId());
//        order.setOrderNumber("ceshi_111");
//        transaction.commit();  //在提交事务时，hibernate会拿保存在session缓存中的order对象和这个order对象比较，如果不一致，则会发出update语句
//    }
//
//    private static void test3(){
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//
//        Order order = (Order) session.load(Order.class,"4028810e628be81801628be81a690001");
//        order.setId("4028810e628be81801628be81a690001");
//        session.saveOrUpdate(order);
//        Serializable serializable = session.save(order);
//        order.setOrderNumber("123_ceshi");
//        session.saveOrUpdate(order);
//        order.setOrderNumber("aaa");
//        session.update(order);
//        transaction.commit();
//    }

    private static void test4(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Order where id=:id");
        query.setParameter("id", "4028810e628be81801628be81a690001");
        List list = query.list();
        System.out.println(list);
        Order order = (Order) session.load(Order.class, "4028810e628be81801628be81a690001");
        System.out.println(order);
        transaction.commit();
    }
}
