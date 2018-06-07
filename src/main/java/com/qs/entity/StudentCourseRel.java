package com.qs.entity;

import java.io.Serializable;
import java.util.Date;

public class StudentCourseRel implements Serializable {

    private String relId;

    private String studentId;

    private String courseId;

    private Date createTime;

    //中间表相对于Student表来说，是多对一关系，在多的一方维护Student对象
    private Student student;
    //中间表相对于Course表来说，是多对一关系，在多的一方维护Course对象
    private Course course;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getRelId() {
        return relId;
    }

    public void setRelId(String relId) {
        this.relId = relId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
