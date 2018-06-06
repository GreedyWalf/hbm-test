package com.qs.test;

import com.qs.entity.IdentifyCard;
import com.qs.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestOne2One {

    public static void main(String[] args) {
//        test();

//        testSave();
        getUserByCardId("4028829263cee7e90163cee823000000");
        getIdentifyCardByUserId("4028829263cee7e90163cee823000000");
    }

    public static void test() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();
        System.out.println("session->>" + session);
    }

    /**
     * 单向一对一主键关联
     * <p>
     * User表中单向主键关联了IdentifyCard表，这里保存两个表的数据
     */
    public static void testSave() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        IdentifyCard identifyCard = new IdentifyCard("身份证");
        User user = new User("张三");
        identifyCard.setUser(user);
        user.setIdentifyCard(identifyCard);


        session.save(user);
        //session.save(identifyCard);  //这里可以省略

        transaction.commit();
    }

    /**
     * 测试双向一对一主键关联，根据cardId获取到IdentifyCard实体，在通过IdentifyCard实体获取到关联的User对象；
     */
    public static void getUserByCardId(String cardId) {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        IdentifyCard identifyCard = (IdentifyCard) session.get(IdentifyCard.class, cardId);
        System.out.println(identifyCard.getCardName());
        User user = identifyCard.getUser();
        System.out.println(user.getUserName());
        transaction.commit();
    }

    /**
     * 测试双向一对一主键关联，根据UserId获取到主控方User表实体，在通过user对象属性获取identifyCard对象；
     */
    public static void getIdentifyCardByUserId(String userId){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        User user = (User) session.get(User.class, userId);
        System.out.println(user.getUserName());
        System.out.println(user.getIdentifyCard());
        transaction.commit();
    }
}
