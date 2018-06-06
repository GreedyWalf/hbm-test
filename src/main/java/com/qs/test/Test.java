package com.qs.test;

import com.qs.entity.Customer;
import com.qs.entity.Order;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.*;

import java.util.*;

public class Test {

    public static void main(String[] args) {
//        save();
//        testFind();
//        testFind2();
//        testFind3();
//        testFind4();
//        testFind5();
//        testFind6();
//        testFind7();
//        testFind8();
//        testFind9();

//        test();

//        saveForOne2Many();

        getById("4028829263cedcbd0163cedcc14a0001");
    }

    public static void test(){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        System.out.println(session);
    }


    /**
     * 测试设置主控方cascade="save-update"，完成order、customer两个实体保存；
     */
    private static void save() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        //先保存customer
        Transaction transaction = session.beginTransaction();
        Customer customer = new Customer("zhangsan");
        //Order作为主控方，配置中使用cascade级联保存和更新操作，这里可以不用对关联对象customer进行保存操作，hibernate会自动保存的；
        //session.save(customer);

        //将customer对象关联在order对象中，再保存order对象
        Order order = new Order("D20180403", customer);
        Order order2 = new Order("D20180403-2", customer);
        session.save(order);
        session.save(order2);

        transaction.commit();
    }


    /**
     * 测试hibernate保存数据（在一的一方维护多的一方的数据）
     *
     * 注意观察输出的sql，先插入一条customer记录，在插入两条order记录，在将两条order记录关联的customer_id更新掉；
     */
    private static void saveForOne2Many(){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction ts = session.beginTransaction();
        Customer customer = new Customer();
        customer.setName("张三丰");
        session.save(customer);


        Order order = new Order();
        order.setOrderNumber("11111");

        Order order2 = new Order();
        order2.setOrderNumber("22222");


        Set<Order> orders = new HashSet<Order>();
        orders.add(order);
        orders.add(order2);

        customer.setOrders(orders);
        session.save(order);
        session.save(order2);
        ts.commit();
    }

    /**
     * 测试配置映射关系的lazy属性作用
     */
    private static void getById(String customerId){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction ts = session.beginTransaction();
        Customer customer = (Customer) session.get(Customer.class, customerId);
        System.out.println(customer.getName());
        //设置lazy="true"表示启用在执行这个语句之前，不会向数据库发送sql，当需要获取order表数据时，再发送sql；
        System.out.println(customer.getOrders());
        ts.commit();
    }


    /**
     * 查询order列表，hibernate会同时将关联的customer数据也查出来，封装数据在order类中定义的customer属性中；
     */
    private static void testFind() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Order where customer.id=:customerId");
        query.setString("customerId", "4028810e628be81801628be81a570000");
        List orderList = query.list();
        transaction.commit();
    }


    /**
     * 使用criteria查询Order数据
     */
    private static void testFind2() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Order.class, "o");
        List orderList = criteria.list();
        transaction.commit();
    }


    /**
     * 使用criteria条件查询，or、in、like
     */
    private static void testFind3() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();
        Transaction transaction = session.beginTransaction();

        //使用orderNumber='' or orderNumber=''
        Criteria criteria = session.createCriteria(Order.class, "o");
        List orders = criteria.add(Restrictions.or(
                Restrictions.eq("orderNumber", "D20180403"),
                Restrictions.eq("orderNumber", "D20180403-2")
        )).list();

        //使用in('','')
        criteria = session.createCriteria(Order.class);
        List<String> orderNumbers = Arrays.asList("D20180403", "D20180403-2");
        orders = criteria.add(Restrictions.in("orderNumber", orderNumbers)).list();

        //使用like %orderNumber%
        criteria = session.createCriteria(Order.class);
        orders = criteria.add(Restrictions.like("orderNumber", "%2018%")).list();
        transaction.commit();
    }


    /**
     * 使用criteria排序，order by
     */
    private static void testFind4() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Order.class);
        List orderList = criteria.addOrder(org.hibernate.criterion.Order.desc("id")).list();
        transaction.commit();
    }


    /**
     * 关联查询，Order表中维护了外键customerId关联查询Customer
     * <p>
     * select * from t_order o
     * inner join t_customer c on o.customer_id=c.id
     * where o.order_number='D20180403'
     */
    private static void testFind5() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Order.class, "o");
        List orderList = criteria.createAlias("customer", "c").add(
                Restrictions.eq("o.orderNumber", "D20180403")
        ).list();

        System.out.println(orderList);
        transaction.commit();
    }


    /**
     * 关联查询的另一种方式，sql和上面测试类似；
     */
    private static void testFind6() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Order.class, "o");
        List result = criteria.createCriteria("customer", "c")
                .add(Restrictions.eq("c.name", "小红"))
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP)
                .list();
        Iterator iterator = result.iterator();

        List<Order> orderList = new ArrayList<Order>(result.size());
        Set<Customer> customerList = new HashSet<Customer>(result.size());
        while (iterator.hasNext()) {
            Map entityMap = (Map) iterator.next();
            Order order = (Order) entityMap.get("o");
            Customer customer = (Customer) entityMap.get("c");
            orderList.add(order);
            customerList.add(customer);
        }

        transaction.commit();
    }


    /**
     * Projections投影，使用聚合函数，比如max(order_number)
     */
    private static void testFind7() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Order.class, "o");
        String orderNumber = (String) criteria.setProjection(Projections.max("o.orderNumber")).list().get(0);

        criteria = session.createCriteria(Order.class,"o");
        List orderList = criteria.add(Restrictions.eq("o.orderNumber", orderNumber)).list();
        transaction.commit();
    }

    /**
     * 使用Projections可以指定查询表中的某些字段，而不是全部字段
     */
    private static void testFind8(){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Order.class, "o");
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("orderNumber"));
        projectionList.add(Projections.property("id"));
        List result = criteria.setProjection(projectionList).list();
        transaction.commit();
    }

    /**
     * 使用Disjunction、Conjunction查询  就是拼接or、and
     */
    private static void testFind9(){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(Order.class);

        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.like("orderNumber", "%2018%"))
                .add(Restrictions.like("orderNumber","%D%"));

        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq("orderNumber","D20180403-2"))
                .add(Restrictions.like("orderNumber","%-2%"));

//        List list = criteria.add(conjunction).add(disjunction).list();

        List orders = criteria.add(Restrictions.eqOrIsNull("orderNumber","D20180403"))
                .add(Restrictions.or(conjunction, disjunction)).list();
        transaction.commit();
    }
}
