package com.qs.test;

import com.qs.entity.Customer;
import com.qs.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Set;

public class testMany2One {
    private static Session session = new Configuration().configure().buildSessionFactory().openSession();

    public static void main(String[] args) {
        testSave();
    }


    /**
     * 测试双向关联，数据保存
     */
    private static void testSave() {
        Transaction transaction = session.beginTransaction();

        Customer customer = new Customer("王五");
        Order order = new Order("555");
        Order order2 = new Order("666");

        order.setCustomer(customer);
        order2.setCustomer(customer);


        session.save(customer);
        session.save(order);
        session.save(order2);

        transaction.commit();

//        Hibernate: insert into T_CUSTOMER (CUSTOMER_NAME, CUSTOMER_IS) values (?, ?)
//        Hibernate: insert into t_order (ORDER_NUMBER, CUSTOMER_ID, ORDER_ID) values (?, ?, ?)
//        Hibernate: insert into t_order (ORDER_NUMBER, CUSTOMER_ID, ORDER_ID) values (?, ?, ?)
    }

    /**
     * 测试双向关联，数据保存
     */
    private static void testSave2() {
        Transaction transaction = session.beginTransaction();

        Order order = new Order("111");
        Order order2 = new Order("222");

        Set<Order> orderSet = new HashSet<Order>(2);
        orderSet.add(order);
        orderSet.add(order2);

        Customer customer = new Customer("张三");
        customer.setOrderSet(orderSet);

        session.save(customer);

        session.save(order);
        session.save(order2);
        transaction.commit();


//        Hibernate: insert into t_order (ORDER_NUMBER, CUSTOMER_ID, ORDER_ID) values (?, ?, ?)
//        Hibernate: insert into t_order (ORDER_NUMBER, CUSTOMER_ID, ORDER_ID) values (?, ?, ?)
//        Hibernate: insert into T_CUSTOMER (CUSTOMER_NAME, CUSTOMER_IS) values (?, ?)

    }
}
