package com.example.hjh.paper.Bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by HJH on 2016/4/2.
 */
/*
UserID——用户ID【int自增】
UserName——用户名【string】
UserPassword——用户密码【string】
College——用户学院【string】
Grade——用户年级【string】
 UserClass——用户班级【string】
 */
public class User extends BmobObject {
    private String UserName;
    private String UserPassword;
    private String College;
    private String Grade;
    private String UserClass;


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getCollege() {
        return College;
    }

    public void setCollege(String college) {
        College = college;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        this.Grade = grade;
    }

    public String getUserClass() {
        return UserClass;
    }

    public void setUserClass(String userClass) {
        UserClass = userClass;
    }

}
