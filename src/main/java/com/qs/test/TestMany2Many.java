package com.qs.test;


import com.qs.entity.Course;
import com.qs.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestMany2Many {

    public static void main(String[] args) {
//        testSave();
        testSave2();
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
}
