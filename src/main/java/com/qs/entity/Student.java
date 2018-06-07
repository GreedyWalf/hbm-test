package com.qs.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Student implements Serializable {

    private String studentId;
    private String studentName;

    //一个学生可以选多门课程，这里维护Course的set集合
    private Set<Course> courseSet = new HashSet<Course>();

    public Set<Course> getCourseSet() {
        return courseSet;
    }

    public void setCourseSet(Set<Course> courseSet) {
        this.courseSet = courseSet;
    }

    public Student(){

    }

    public Student(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
