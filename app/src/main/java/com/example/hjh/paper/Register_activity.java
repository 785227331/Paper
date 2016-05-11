package com.example.hjh.paper;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hjh.paper.Bmob.Online_User;
import com.example.hjh.paper.Bmob.User;
import com.example.hjh.paper.Home.Home_activity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by HJH on 2016/4/4.
 */
public class Register_activity extends AppCompatActivity {
    private Spinner college;
    private Spinner _class;
    private Spinner year;
    private ArrayAdapter<String> adapter;

    private String college_select;
    private String _class_select;
    private String year_select;
    private EditText name;
    private EditText password;
    private String user_name=null;
    private String user_password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        init_spinner();

        //设置完成菜单按钮,
        Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("abc","菜单");
                return false;
            }
        };
        //设置对应的监听事件，对填写的内容，立即进行检测
        //————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
        name = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_userpassword);


    }

    private void init_spinner() {
        init_college();
        init_class();
        init_year();
    }

    private void init_college() {
        college = (Spinner) findViewById(R.id.college);
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
                college_select = sInfo;
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
        Resources res =getResources();
        String[] _class_name = null;
        switch(sInfo){
            case "计算机科学学院":{
                _class_name = res.getStringArray(R.array.computer_class_name);
                break;
            }
//            case "体育学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "化学与环境工程学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "医学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "外语学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "教育学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "数学与信息科学学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "文学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "旅游与地理学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "经济管理学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "美术学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "英东生命科学学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "音乐学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "韶州师范学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "英东食品农业科学与工程学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "英东农业科学与工程学院":{
//                _class_name = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
            default:
                _class_name = new String[1];
                _class_name[0] = new String("暂时无选项");
                break;
        }
        Log.i("abc",sInfo);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,_class_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        _class.setAdapter(adapter);
        //设置默认值
        _class.setVisibility(View.VISIBLE);

    }

    private void init_class(){
        _class = (Spinner) findViewById(R.id.userclass);
        Resources res =getResources();
        String[] _class_name = res.getStringArray(R.array.computer_class_name);
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,_class_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _class_select = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //将adapter 添加到spinner中
        _class.setAdapter(adapter);
        //设置默认值
        _class.setVisibility(View.VISIBLE);
    }
    private void init_year(){
        year = (Spinner) findViewById(R.id.usergrade);
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
        //添加事件Spinner事件监听
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("abc", "_class");
                year_select = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        year.setSelection(0);
        //设置默认值
        year.setVisibility(View.VISIBLE);
    }

    public void doClick(View view) {
        switch(view.getId()){
            case R.id.btn_finish:{
                register();
                break;
            }

        }
    }
    private void register(){
        //获取name和password的值，并判断是否符合要求
        user_name = name.getText().toString();
        user_password = password.getText().toString();
        Pattern p1 = Pattern.compile("\\d{11}+");
        Pattern p2 = Pattern.compile("\\d{8,}+");
        Matcher m1 = p1.matcher(user_name);
        Matcher m2 = p2.matcher(user_password);
        Log.i("abc","c--"+college_select+" cl--"+_class_select+" y--"+year_select+" n--"+user_name+" p--"+user_password);
        if(m1.matches() && m2.matches()){//学号和密码都符合要求
            //连接bmob，查询学号是否已经被注册
            BmobQuery<User> query = new BmobQuery<User>();
            query.addWhereEqualTo("UserName",user_name);
            query.findObjects(this, new FindListener<User>() {
                @Override
                public void onSuccess(List<User> list) {
                    Log.i("abc","查询对象list长度："+list.size());
                    if(list.size() == 0 ){//list为空表示无相同的对象
                        //验证后学号可用，写入信息
                        User user = new User();
                        user.setUserName(user_name);
                        user.setUserPassword(user_password);
                        user.setCollege(college_select);
                        user.setUserClass(_class_select);
                        user.setGrade(year_select);
                        //写入
                        user.save(Register_activity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(Register_activity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                //执行跳转
                                final Intent intent = new Intent(Register_activity.this,Home_activity.class);

                                //保存登录信息
                                Online_User user = Online_User.getUser();
                                user.setUserName(user.getUserName());//用户名
                                user.setUserPassword(user.getUserPassword());//用户密码
                                user.setCollege(user.getCollege());//用户学院
                                user.setGrade(user.getGrade());//用户年级
                                user.setUserClass(user.getUserClass());//用户班级
                                user.setLogin_Type(Online_User.user_model);//用户登陆模式

                                //设置延迟任务
                                Timer timer = new Timer();
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        startActivity(intent); //执行
                                    }
                                };
                                timer.schedule(task, 1000 * 1); //1秒后
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(Register_activity.this,"注册失败，请重新尝试！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else
                        //存在相同的学号
                        Toast.makeText(Register_activity.this,"该学号已被注册",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(Register_activity.this,"请检查网络！",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
        }
    }

}
