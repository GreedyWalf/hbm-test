package com.qs.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Course implements Serializable {

    private String courseId;
    private String courseName;

    //一门课可以有多个学员选择，这里维护student的set集合
    private Set<Student> studentSet = new HashSet<Student>();

    public Set<Student> getStudentSet() {
        return studentSet;
    }

    public void setStudentSet(Set<Student> studentSet) {
        this.studentSet = studentSet;
    }

    public Course(){

    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
