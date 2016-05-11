package com.example.hjh.paper.Bmob;

/**
 * Created by HJH on 2016/4/20.
 */
public class Online_User extends User {
    /*
        UserName：用户名
        UserPassword：用户密码
        College：用户学院
        Grade：用户年级
        UserClass：用户班级
        Login_Type：用户登陆模式
    */

    private static Online_User Online_User = new Online_User();
    private boolean Login_Type;//登陆类型：true--用户登录；false--匿名登陆
    public static boolean user_model = true;
    public static boolean Anonymous_model = false;

    public static Online_User getUser(){
        return Online_User;
    }

    public boolean isLogin_Type() {
        return Login_Type;
    }

    public void setLogin_Type(boolean login_Type) {
        Login_Type = login_Type;
    }

    public void delet(){//将登录信息置空
        Online_User.setUserName(null);
        Online_User.setUserPassword(null);
        Online_User.setCollege(null);
        Online_User.setGrade(null);
        Online_User.setUserClass(null);
        Online_User.Login_Type=false;

    }

}
