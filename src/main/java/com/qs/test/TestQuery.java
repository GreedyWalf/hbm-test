package com.qs.test;

import com.qs.entity.Customer;
import com.qs.entity.Order;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;

import java.util.List;

/**
 * 测试hibernate的几种查询数据方式
 */
public class TestQuery {
    private static Session session = new Configuration().configure().buildSessionFactory().openSession();

    public static void main(String[] args) {
//        query("40288f0d63ed98df0163ed98e1830000");

//        queryByHql();
//        queryByHql("40288f0d63ed98df0163ed98e1830000");
//        queryByHqlLimt2();
//        queryByQbc("40288f0d63ed98df0163ed98e1830000");
//        queryByQbc2("40288f0d63ed98df0163ed98e1830000");
//        queryByQbc3("40288f0d63ed98df0163ed98e1830000");
        queryByQbc4("40288f0d63ed98df0163ed98e1830000");
//        queryBySql("40288f0d63ed98df0163ed98e1830000");
    }


    /**
     * 方式1：OID检索方式
     * 方式2：导航对象视图检索方式（通过已经检索出来的对象，获取关联属性对象的数据）
     */
    private static void query(String customerId) {
        Transaction transaction = session.beginTransaction();

        //通过session.get()/load()方式查询数据
        Customer customer = (Customer) session.get(Customer.class, customerId);
        System.out.println(customer.getCustomerName());
        System.out.println(customer.getOrderSet().size());

        //备注：上面get方法从数据中获取到数据，并存在session的一级缓存中，这里的load方法会先查询一级缓存中有没有符合查询的记录，没有再
        //从数据库中查询
        Customer customer2 = (Customer) session.load(Customer.class, customerId);
        System.out.println(customer2.getCustomerName());
        System.out.println(customer2.getOrderSet().size());
        transaction.commit();
    }


    /**
     * 方式3：HQL（Hibernate Query Language）检索方式
     * <p>
     * 查询全部的customer记录
     */
    private static void queryByHql() {
        Transaction transaction = session.beginTransaction();
        List<Customer> customers = session.createQuery("from Customer").list();
        System.out.println(customers.size());
        System.out.println(customers.get(0).getCustomerName());
        transaction.commit();
    }

    /**
     * 根据customerId获取customer记录
     */
    private static void queryByHql(String customerId) {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Customer where customerId=:customerId");
        query.setString("customerId", customerId);
        List list = query.list();
        Customer customer = (list != null && list.size() > 0) ? (Customer) list.get(0) : null;
        System.out.println(customer);
        if (customer != null) {
            System.out.println(customer.getCustomerName());
        }

        transaction.commit();
    }

    /**
     * 使用HQL查询从第2条数据开始查询2条记录 (支持分页)
     */
    private static void queryByHqlLimt2() {
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Customer");
        query.setFirstResult(1);
        query.setMaxResults(2);
        List<Customer> list = query.list();

        //相当于是mysql的 limit 1,2;
        System.out.println(list.get(0).getCustomerName());
        System.out.println(list.get(1).getCustomerName());
        transaction.commit();

//        Hibernate: select customer0_.customer_id as customer1_1_, customer0_.customer_name as customer2_1_ from t_customer customer0_ limit ?, ?

    }


    /**
     * 方式4：QBC查询(Query By Criteria)：面向对象查询方式
     */
    private static void queryByQbc(String customerId) {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class);
//        Customer customer = (Customer) criteria.add(Restrictions.eq("customerId", customerId)).uniqueResult();

        Customer customer = (Customer) criteria.add(
                Restrictions.and(
                        Restrictions.eq("customerId", customerId),
                        Restrictions.eq("customerName", "张三")
                )
        ).uniqueResult();
        System.out.println(customer);
        System.out.println(customer.getCustomerName());
        transaction.commit();
    }

    /**
     * 使用Qbc进行条件查询
     */
    private static void queryByQbc2(String customerId) {
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Customer.class);

        List<Customer> customers = criteria.add(
                Restrictions.or(
                        Restrictions.and(
                                Restrictions.eq("customerName", "张三"),
                                Restrictions.eq("customerId", customerId)
                        ),
                        Restrictions.eq("customerName", "李四"))
        ).list();

        System.out.println(customers);
        System.out.println(customers.size());
        System.out.println(customers.get(0).getCustomerName());
        System.out.println(customers.get(1).getCustomerName());
        transaction.commit();
    }


    /**
     * 使用Qbc的离线查询
     */
    private static void queryByQbc3(String customerId) {
        //定义离线查询（session关闭情况下可以使用，当session连接时，在执行查询）
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);
        detachedCriteria.add(
                Restrictions.or(
                        Restrictions.and(
                                Restrictions.eq("customerName", "张三"),
                                Restrictions.eq("customerId", customerId)
                        ),
                        Restrictions.eq("customerName", "李四")
                )
        );

        //获取session
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        //获取在线的criteria，在执行sql获取结果
        Criteria executableCriteria = detachedCriteria.getExecutableCriteria(session);
        List<Customer> customers = executableCriteria.list();
        System.out.println(customers);
        System.out.println(customers.size());
        System.out.println(customers.get(0).getCustomerName());
        System.out.println(customers.get(1).getCustomerName());
    }

    /**
     * QBC的or、and使用
     */
    private static void queryByQbc4(String customerId) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class);

        //使用Disjunction拼接or语句
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq("customerName", "李四"));
        disjunction.add(Restrictions.and(
                Restrictions.eq("customerName", "张三"),
                Restrictions.eq("customerId", customerId)
        ));

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(disjunction);
        conjunction.add(Restrictions.like("customerName","%张%"));

//        detachedCriteria.add(disjunction);
        detachedCriteria.add(conjunction);

        //获取session
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        //获取在线的criteria，在执行sql获取结果
        Criteria executableCriteria = detachedCriteria.getExecutableCriteria(session);
        List<Customer> customers = executableCriteria.list();
        System.out.println(customers);
        System.out.println(customers.size());
        System.out.println(customers.get(0).getCustomerName());
    }

    /**
     * 方式5：本地sql检索方式
     */
    private static void queryBySql(String customerId) {
        Transaction transaction = session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("select * FROM t_customer where customer_id=:customerId");
        sqlQuery.setString("customerId", customerId);

        //查询出来的结果集需要绑定实体类，才可以强转成实体对象
        Customer customer = (Customer) sqlQuery.addEntity(Customer.class).uniqueResult();
        System.out.println(customer);
        System.out.println(customer.getCustomerName());
        transaction.commit();
    }
}
