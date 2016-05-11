package com.example.hjh.paper.Bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by HJH on 2016/4/2.
 */
/*
TeacherID——教工号【int自增】
TeacherName——姓名【string】
TeacherCollege——学院【string】
TeacherSubject——学科【string】
 */
public class Teacher extends BmobObject {
    private String TeacherName;
    private String TeacherCollege;
    private String TeacherSubject;

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public String getTeacherCollege() {
        return TeacherCollege;
    }

    public void setTeacherCollege(String teacherCollege) {
        TeacherCollege = teacherCollege;
    }

    public String getTeacherSubject() {
        return TeacherSubject;
    }

    public void setTeacherSubject(String teacherSubject) {
        TeacherSubject = teacherSubject;
    }
}
