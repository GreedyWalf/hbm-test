package com.qs.test;

import com.qs.entity.IdentifyCard;
import com.qs.entity.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestOne2OnePrimary {
    private static Session session = new Configuration().configure().buildSessionFactory().openSession();


    public static void main(String[] args) {
//        testSave();
//        testFind("4028811063ec66410163ec6647c50000");
//        testFind2("4028811063ec66410163ec6647c50000");
        testSave2();
    }

    /**
     * 测试一对一主键单向关联保存
     */
    public static void testSave(){
        Transaction transaction = session.beginTransaction();
        IdentifyCard identifyCard = new IdentifyCard("身份证");
        User user = new User("zhangsan");
        identifyCard.setUser(user);


        user.setIdentifyCard(identifyCard);
        session.save(user);

        //user的主键中引用了IdentifyCard的主键作为外键关联，这里保存实体只要保存user即可；
//        session.save(identifyCard);
        transaction.commit();


//        Hibernate: insert into t_identify_card (CARD_NAME, CARD_ID) values (?, ?)
//        Hibernate: insert into t_user (USER_NAME, USER_ID) values (?, ?)

    }

    /**
     * 测试一对一主键单向关联 查询
     */
    public static void testFind(String userId){
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User where userId=:userId");
        query.setString("userId", userId);
        User user = (User) query.list().get(0);
        System.out.println(user.getUserName());
        System.out.println(user.getIdentifyCard().getCardName());
        transaction.commit();


//        Hibernate: select user0_.USER_ID as USER_ID1_8_, user0_.USER_NAME as USER_NAM2_8_ from t_user user0_ where user0_.USER_ID=?
//        Hibernate: select identifyca0_.CARD_ID as CARD_ID1_3_0_, identifyca0_.CARD_NAME as CARD_NAM2_3_0_ from t_identify_card identifyca0_ where identifyca0_.CARD_ID=?

    }


    /**
     * 测试一对一主键双向关联 保存实体
     */
    public static void testSave2(){
        Transaction transaction = session.beginTransaction();
        User user = new User("李四");
        IdentifyCard identifyCard = new IdentifyCard("教师证");

        //双向关联可以双方都各自设置对方对象的属性关联（实际测试时，按照单向关联设置属性，保存也可以，即：下面的设置user属性值可以省略）
        user.setIdentifyCard(identifyCard);
//        identifyCard.setUser(user);

        //只要保存user即可,identifyCard保存可以省略
        session.save(user);
//        session.save(identifyCard);
        transaction.commit();


//        Hibernate: insert into t_identify_card (CARD_NAME, CARD_ID) values (?, ?)
//        Hibernate: insert into t_user (USER_NAME, USER_ID) values (?, ?)
    }

    /**
     * 测试一对一主键双向关联 查询
     * @param cardId
     */
    public static void testFind2(String cardId){
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from IdentifyCard where cardId=:cardId");
        query.setString("cardId", cardId);
        IdentifyCard identifyCard = (IdentifyCard) query.list().get(0);
        System.out.println(identifyCard.getCardName());
        System.out.println(identifyCard.getUser().getUserName());
        System.out.println(identifyCard.getUser().getIdentifyCard());
        System.out.println(identifyCard.getUser().getIdentifyCard().getUser().getUserName());
        transaction.commit();


//        Hibernate: select identifyca0_.CARD_ID as CARD_ID1_3_, identifyca0_.CARD_NAME as CARD_NAM2_3_ from t_identify_card identifyca0_ where identifyca0_.CARD_ID=?
//        Hibernate: select user0_.USER_ID as USER_ID1_8_0_, user0_.USER_NAME as USER_NAM2_8_0_ from t_user user0_ where user0_.USER_ID=?
    }
}
