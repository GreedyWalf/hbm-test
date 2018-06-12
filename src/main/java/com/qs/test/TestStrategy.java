package com.qs.test;

import com.qs.entity.Customer;
import com.qs.entity.Order;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class TestStrategy {

    public static void main(String[] args) {
//        testBatchSize();
//        testBatchSize2();
//        rest("0.01");
        rest("1");
    }

    /**
     * 在一对多关系下，一的一方批量获取多的一方数据，批量查询时，在一的一方set配置中添加batch-size属性可以提高查询性能；
     */
    private static void testBatchSize(){
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        //获取所有的customer对象
        List<Customer> customers = session.createCriteria(Customer.class).list();
        System.out.println(customers.size());

        //获取所有customer关联的order对象（注意：结合Customer.hbm.xml配置文件看，<set batch-size='3'>，通过batch-size属性多条select语句合并为select..in..）
        for (Customer customer : customers) {
            System.out.println(customer.getOrderSet());
        }

        transaction.commit();
    }

    /**
     * 在多对一关系下，多的一方批量获取一的一方对象，批量查询时，在多的一方class配置中添加batch-size属性可以提高查询性能；
     */
    private static void testBatchSize2() {
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<Order> orders = session.createCriteria(Order.class).list();
        System.out.println(orders.size());

        for (Order order : orders) {
            System.out.println(order.getCustomer());
        }
        transaction.commit();
    }

    public static int rest(Object obj){
        if(obj!=null && !obj.equals("")){
            return Integer.parseInt(obj.toString());
        }else{
            return 0;
        }
    }
}
