package com.example.hjh.paper.Home;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjh.paper.Bmob.User;
import com.example.hjh.paper.Init_Spinner;
import com.example.hjh.paper.Bmob.Online_User;
import com.example.hjh.paper.R;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by HJH on 2016/4/20.
 */
public class Modify_User_Information extends AppCompatActivity {
    private Spinner college;
    private Spinner _class;
    private Spinner year;
    private ArrayAdapter<String> adapter;

    private TextView user_name;
    private TextView user_old_password;
    private TextView user_new_password;
    private Button button;
    private Online_User online_user = Online_User.getUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_user_information);
        Log.i("abc","mosdfdsefsdfwsesfsdfsd");
        init_toolbar();
        user_name = (TextView) findViewById(R.id.change_username);
        user_name.setText(online_user.getUserName());
        user_new_password = (TextView) findViewById(R.id.change_new_userpassword);
        user_old_password = (TextView) findViewById(R.id.change_old_userpassword);
        init_button();
    }

    private void init_button() {
        button = (Button) findViewById(R.id.change_btn_finish);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断旧密码是否真确
                if(user_old_password.getText().toString().equals(online_user.getUserPassword())){
                    //判断新密码是否符合要求，符合则更新数据
                    String s = user_new_password.getText().toString();
                    Pattern p = Pattern.compile("\\d{8,}+");
                    Matcher m = p.matcher(s);
                    //新密码符合规则
                    if(m.matches()){
                        //步骤：修改数据，根据id更新数据
                        final User user = new User();
                        user.setUserName(user_name.getText().toString());//用户名
                        user.setUserPassword(user_new_password.getText().toString());//用户密码
                        user.setCollege(college.getSelectedItem().toString());//用户学院
                        user.setGrade(year.getSelectedItem().toString());//用户年级
                        user.setUserClass(college.getSelectedItem().toString());//用户班级
                        user.update(Modify_User_Information.this,online_user.getObjectId(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                toast("更新成功！");
                                Modify_User_Information.this.finish();
                                //确定更新成功后
                                online_user.setUserName(user.getUserName());
                                online_user.setUserPassword(user.getUserPassword());
                                online_user.setCollege(user.getCollege());
                                online_user.setGrade(user.getGrade());
                                online_user.setUserClass(user.getUserClass());
                                online_user.setLogin_Type(Online_User.user_model);
                            }
                            @Override
                            public void onFailure(int i, String s) {
                                toast("更新失败！");
                            }
                        });
                    }
                    else{
                        toast("新密码不符合要求！请输入8位数字的密码！");
                    }
                }
                else{
                    toast("原密码错误！");
                }
            }
        });
    }

    private void init_toolbar() {
        //设置toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.change_toolbar);
        toolbar.setTitle("注册");
        //将onMenuItemClick监听者设置给toolbar
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init_spinner();
        //设置完成菜单按钮,
        Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("abc","菜单");
                return false;
            }
        };
        // Menu item click 的监听事件一样要设定在 setSupportActionBar 才有作用
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    private void init_spinner() {
        init_college();
        init_class();
        init_year();
    }

    private void init_college() {
        college = (Spinner) findViewById(R.id.change_college);
        Resources res =getResources();
        String[] college_name = res.getStringArray(R.array.college_name);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,college_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        college.setAdapter(adapter);
        //添加事件Spinner事件监听
        college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("abc", "college");
                //获取选择的项的值
                String sInfo = parent.getItemAtPosition(position).toString();
                setUserClass(sInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //设置默认值
        college.setVisibility(View.VISIBLE);
    }

    private void setUserClass(String sInfo) {
        Init_Spinner is = new Init_Spinner(sInfo, this, _class, R.array.computer_class_name);//参数最后的id需要改为_class的数组id
        is.init(Init_Spinner.change);
    }

    private void init_class(){
        _class = (Spinner) findViewById(R.id.change_userclass);
        Resources res =getResources();
        String[] _class_name = res.getStringArray(R.array.computer_class_name);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,_class_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        _class.setAdapter(adapter);
        //设置默认值
        _class.setVisibility(View.VISIBLE);
    }
    private void init_year(){
        year = (Spinner) findViewById(R.id.change_usergrade);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
        String date=sdf.format(new java.util.Date());
        Log.i("abc",date);
        int _year = Integer.parseInt(date);
        String[] year_name = new String[9];
        int j=0;
        for(int i=_year-4;i<=_year+4;i++){
            String a = i+"";
            year_name[j++] = a;
            Log.i("abc",a);
        }

        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,year_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        year.setAdapter(adapter);
        year.setSelection(0);
        //设置默认值
        year.setVisibility(View.VISIBLE);
    }

    private void toast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

}
