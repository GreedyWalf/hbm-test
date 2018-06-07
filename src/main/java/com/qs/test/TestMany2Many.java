package com.qs.test;


import com.qs.entity.Course;
import com.qs.entity.Student;
import com.qs.entity.StudentCourseRel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class TestMany2Many {

    public static void main(String[] args) {
//        testSave();
//        testSave2();
        testSave3();
    }

    /**
     * 测试多对多关联保存
     */
    public static void testSave(){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Student student = new Student("张三");
        Student student2 = new Student("李四");

        Course course = new Course("化学");
        Course course2 = new Course("物理");

        student.getCourseSet().add(course);
        student.getCourseSet().add(course2);

        student2.getCourseSet().add(course);
        student2.getCourseSet().add(course2);

        session.save(course);
        session.save(course2);
        session.save(student);
        session.save(student2);
        transaction.commit();
    }

//    Hibernate: insert into t_course (COURSE_NAME, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_course (COURSE_NAME, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_student (STUDENT_NAME, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_student (STUDENT_NAME, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_student_course (STUDENT_ID, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_student_course (STUDENT_ID, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_student_course (STUDENT_ID, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_student_course (STUDENT_ID, COURSE_ID) values (?, ?)



    /**
     * 测试多对多关联保存
     */
    public static void testSave2(){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Student student = new Student("张三");
        Student student2 = new Student("李四");

        Course course = new Course("化学");
        Course course2 = new Course("物理");

        course.getStudentSet().add(student);
        course.getStudentSet().add(student2);
        course2.getStudentSet().add(student);
        course2.getStudentSet().add(student2);

        session.save(student);
        session.save(student2);
        session.save(course);
        session.save(course2);
        transaction.commit();
    }

//    Hibernate: insert into t_student (STUDENT_NAME, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_student (STUDENT_NAME, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_course (COURSE_NAME, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_course (COURSE_NAME, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_student_course (COURSE_ID, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_student_course (COURSE_ID, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_student_course (COURSE_ID, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_student_course (COURSE_ID, STUDENT_ID) values (?, ?)



    /**
     * 将两个表多对多关联改成两个多对一关联（不用hibernate帮我们维护中间表，我们自己手动维护）
     */
    public static void testSave3(){
        Session session = new Configuration()
                .configure()
                .buildSessionFactory()
                .openSession();

        Transaction transaction = session.beginTransaction();
        Student student = new Student("张三");
        Student student2 = new Student("李四");

        Course course = new Course("化学");
        Course course2 = new Course("物理");

        StudentCourseRel rel = new StudentCourseRel();
        rel.setCourse(course);
        rel.setStudent(student);
        rel.setCreateTime(new Date());

        StudentCourseRel rel2 = new StudentCourseRel();
        rel2.setCourse(course2);
        rel2.setStudent(student);
        rel2.setCreateTime(new Date());

        StudentCourseRel rel3 = new StudentCourseRel();
        rel3.setCourse(course);
        rel3.setStudent(student2);
        rel3.setCreateTime(new Date());

        StudentCourseRel rel4 = new StudentCourseRel();
        rel4.setCourse(course2);
        rel4.setStudent(student2);
        rel4.setCreateTime(new Date());

        session.save(student);
        session.save(student2);
        session.save(course);
        session.save(course2);
        session.save(rel);
        session.save(rel2);
        session.save(rel3);
        session.save(rel4);
        transaction.commit();
    }


//    Hibernate: insert into t_student (STUDENT_NAME, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_student (STUDENT_NAME, STUDENT_ID) values (?, ?)
//    Hibernate: insert into t_course (COURSE_NAME, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_course (COURSE_NAME, COURSE_ID) values (?, ?)
//    Hibernate: insert into t_student_course (CREATE_TIME, STUDENT_ID, COURSE_ID, REL_ID) values (?, ?, ?, ?)
//    Hibernate: insert into t_student_course (CREATE_TIME, STUDENT_ID, COURSE_ID, REL_ID) values (?, ?, ?, ?)
//    Hibernate: insert into t_student_course (CREATE_TIME, STUDENT_ID, COURSE_ID, REL_ID) values (?, ?, ?, ?)
//    Hibernate: insert into t_student_course (CREATE_TIME, STUDENT_ID, COURSE_ID, REL_ID) values (?, ?, ?, ?)
}
