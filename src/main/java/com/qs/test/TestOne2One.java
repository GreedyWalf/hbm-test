package com.qs.test;

import com.qs.entity.IDCard;
import com.qs.entity.IdentifyCard;
import com.qs.entity.Person;
import com.qs.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestOne2One {

    public static void main(String[] args) {
//        test();

        testSave();
//        getUserByCardId("4028829263cee7e90163cee823000000");
//        getIdentifyCardByUserId("4028829263cee7e90163cee823000000");


//        one2OneFkave();
//        findPersonByCardId("40288e9163d465760163d4657a390000");
//        findCardByPersonId("40288e9163d465760163d4657a4e0001");
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
    public static void getIdentifyCardByUserId(String userId) {
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


    /*****--------测试单向一对一外键关联------------******/
    public static void one2OneFkave() {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        IDCard idCard = new IDCard("准考证");
        Person person = new Person("李四");
        person.setIdCard(idCard);

        session.save(idCard);
        session.save(person);
        transaction.commit();
    }


    /**
     * 测试双向一对一外键关联（通过IDCard实体获取关联的Person实体）
     */
    public static void findPersonByCardId(String cardId) {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        IDCard idCard = (IDCard) session.get(IDCard.class, cardId);
        System.out.println(idCard.getCardName());
        System.out.println(idCard.getPerson().getPersonName());
        transaction.commit();
    }

    /**
     * 测试双向一对一外键关联（通过Person实体获取关联的IdCard实体）
     */
    public static void findCardByPersonId(String personId) {
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Person person = (Person) session.get(Person.class, personId);
        System.out.println(person.getPersonName());
        System.out.println(person.getIdCard().getCardName());
        transaction.commit();
    }

}
