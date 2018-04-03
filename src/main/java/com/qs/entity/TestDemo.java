package com.qs.entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestDemo {

    public static void main(String[] args) {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Customer customer = new Customer();
        customer.setName("qinyupeng");
        customer.setMarried(false);
        customer.setSex('m');
        String id = (String) session.save(customer);
        customer.setName("秦玉鹏");
        transaction.commit();
        System.out.println(id);
    }
}
