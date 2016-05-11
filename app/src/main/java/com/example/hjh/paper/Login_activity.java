package com.example.hjh.paper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hjh.paper.Bmob.Online_User;
import com.example.hjh.paper.Bmob.User;
import com.example.hjh.paper.Home.Home_activity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HJH on 2016/4/4.
 */
public class Login_activity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText username ;
    private EditText userpassword;
    private String name;
    private String password;
    private long exitTime = 0;//最后一次点击返回键的时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        if(intent.getIntExtra("flag",0)==1) {
            toolbar.setNavigationIcon(R.drawable.back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //返回个人中心
                    onBackPressed();
                }
            });
        }
        username = (EditText) findViewById(R.id.username);
        userpassword = (EditText) findViewById(R.id.userpassword);

    }


    public void doClick(View view) {
        switch (view.getId()){
            case R.id.txt_register:{
                Log.i("abc","register");
                Intent intent = new Intent(this,Register_activity.class);
                startActivity(intent);
                break;
            }
            case R.id.login_noName:{ //匿名登录
                Log.i("abc","login");
                finish();
                Intent intent = new Intent(this,Home_activity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_login:{//用户登录
                Log.i("abc","login");
                login();
                break;
            }
        }
    }

    private void login() {
        //用户登录信息检查
        name = username.getText().toString();
        password = userpassword.getText().toString();
        Pattern p1 = Pattern.compile("\\d{11}+");
        Pattern p2 = Pattern.compile("\\d{8,}+");
        Matcher m1 = p1.matcher(name);
        Matcher m2 = p2.matcher(password);
        if(m1.matches() && m2.matches()){//先进行本地的格式检查
            BmobQuery<User> query = new BmobQuery<>();
            query.addWhereEqualTo("UserName",name);
            query.findObjects(this, new FindListener<User>() {
                @Override
                public void onSuccess(List<User> list) {
                    if(list.size()!=0){//返回结果不为空
                        User u = list.get(0);//进行密码对比，密码匹配则进行登录，保存登录信息
                        if(u.getUserPassword().equals(password)){
                            //保存登录信息
                            Online_User user = Online_User.getUser();
                            user.setObjectId(u.getObjectId());//id
                            user.setUserName(u.getUserName());//用户名
                            user.setUserPassword(u.getUserPassword());//用户密码
                            user.setCollege(u.getCollege());//用户学院
                            user.setGrade(u.getGrade());//用户年级
                            user.setUserClass(u.getUserClass());//用户班级
                            user.setLogin_Type(Online_User.user_model);//用户登陆模式
                            toast("登录成功！");

                            //一秒后跳转
                            final Intent intent = new Intent(Login_activity.this,Home_activity.class);
                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    finish();
                                    startActivity(intent); //执行
                                }
                            };
                            timer.schedule(task, 1000 * 1); //1秒后
                        }
                        else {
                            //学号找到了，但是密码不对
                            toast("学号和密码不对，请重新检查！");
                        }
                    }
                    else {
                        toast("该学号还没注册！");
                    }
                }
                @Override
                public void onError(int i, String s) {
                    toast("请检查网络！");
                }
            });
        }
        else{
            toast("学号或密码的格式不对！");
        }
    }

    private void toast(String s){
        Toast.makeText(Login_activity.this,s,Toast.LENGTH_SHORT).show();
    }


    //监听返回键，实现双击退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
