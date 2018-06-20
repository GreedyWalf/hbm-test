package com.qs.test;

import com.qs.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;
import java.util.List;

public class TestN1 {
    private static Session session = new Configuration().configure().buildSessionFactory().openSession();

    public static void main(String[] args) {
//        test();
//        test2();
//        test3();
//        test4();
//        test5();
        test6();
//        test7();
    }


    /**
     * 测试list()
     */
    private static void test() {
        Transaction transaction = session.beginTransaction();
        //通过list方法，hibernate会将所有的数据查询出来，发出一条select语句
        List<Customer> customers = session.createQuery("from Customer").setMaxResults(3).list();

        Iterator<Customer> iterator = customers.iterator();
        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            System.out.println(customer.getCustomerName());
        }

        transaction.commit();
    }

    /**
     * 测试iterate()
     */
    private static void test2() {
        Transaction transaction = session.beginTransaction();
        //通过list方法，hibernate会将所有的数据查询出来，发出一条select语句
        Iterator<Customer> iterator = session.createQuery("from Customer").setMaxResults(3).iterate();

        while (iterator.hasNext()) {
            Customer customer = iterator.next();
            System.out.println(customer.getCustomerName());
        }

        transaction.commit();
    }


    /**
     * 测试一级缓存、二级缓存
     * session未关闭
     * session关闭后，再开启一个session查询
     */
    private static void test3() {
        Session session = HibernateUtils.openSession();
        List customers = session.createQuery("from Customer").setMaxResults(3).list();
        session.close();
        session = HibernateUtils.openSession();
        Customer customer = (Customer) session.load(Customer.class, "40288f0d63ed98df0163ed98e1830000");
        System.out.println(customer);
    }

    /**
     * 测试投影查询结果是否存储在缓存（一级、二级缓存）中？
     * <p>
     * 测试结果：一级、二级缓存中存储的都是实体对象，部分属性数据不会被存储到缓存中
     */
    private static void test4() {
        Session session = HibernateUtils.openSession();
        List list = session.createQuery("select customerName from Customer").setMaxResults(3).list();
        System.out.println(list.size());

        session.close();
        session = HibernateUtils.openSession();
        String customerName = (String) session.createQuery("select customerName from Customer where customerName='张三'").uniqueResult();
    }


    /**
     * 使用二级缓存解决N+1问题
     */
    private static void test5() {
        Session session = HibernateUtils.openSession();
        List<Customer> customers = session.createQuery("from Customer").setMaxResults(3).list();

        for (Customer customer : customers) {
            System.out.println(customer.getCustomerName());
        }

        session.close();

        session = HibernateUtils.openSession();
        Iterator<Customer> iterator = session.createQuery("from Customer").iterate();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getCustomerName());
        }

        session.close();
    }

    /**
     * 使用查询缓存，缓存同样的查询sql(查询缓存也是SessionFactory级别的)
     * <p>
     * 查询缓存生效的条件：
     * 1、只有当 HQL 查询语句完全相同时，连参数设置都要相同，此时查询缓存才有效；
     * 2、查询时候添加setCacheable(true)
     * 3、在解决N+1问题上，如果开启查询缓存需要结合二级缓存一起配置使用；（查询缓存缓存的对象的ID）
     */
    private static void test6() {
        Session session = HibernateUtils.openSession();
        List<Customer> customers = session.createQuery("from Customer").setCacheable(true).setMaxResults(3).list();
        session.close();

        session = HibernateUtils.openSession();
        customers = session.createQuery("from Customer").setCacheable(true).setMaxResults(3).list();
        session.close();
    }

    /**
     * 测试查询缓存是否可以缓存对象的属性？
     * 1、开启二级缓存，开启查询缓存；
     * 2、前后查询的sql语句要一致，使用query.setCacheable(true);
     */
    private static void test7() {
        Session session = HibernateUtils.openSession();
        List<String> customerNames = session.createQuery("select customerName from Customer")
                .setCacheable(true).list();
        System.out.println(customerNames);
        session.close();

        session = HibernateUtils.openSession();
        customerNames = session.createQuery("select customerName from Customer")
                .setCacheable(true).list();
        System.out.println(customerNames);
    }
}
