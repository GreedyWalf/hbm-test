package com.qs.test;

import com.qs.entity.IDCard;
import com.qs.entity.Person;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class TestOne2OneForeign {
    private static Session session = new Configuration().configure().buildSessionFactory().openSession();

    public static void main(String[] args) {
        testSave();
    }


    /**
     * 测试一对一外键单向关联 保存实体
     */
    private static void testSave(){
        Transaction transaction = session.beginTransaction();
        Person person = new Person("张三");
        IDCard idCard = new IDCard("身份证");
        person.setIdCard(idCard);

        session.save(idCard);
        session.save(person);
        transaction.commit();


//        Hibernate: insert into t_idcard (CARD_NAME, CARD_ID) values (?, ?)
//        Hibernate: insert into t_person (PERSON_NAME, CARD_ID, PERSON_ID) values (?, ?, ?)

    }
}
