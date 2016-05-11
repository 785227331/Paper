package com.example.hjh.paper;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by HJH on 2016/4/18.
 */
public class Init_Spinner {
    private String sInfo;
    private Context context;
    private Spinner spinner;
    private TextView spinner_text;
    private int id;
    private ArrayAdapter adapter;

    public static boolean search = true;
    public static boolean upload = false;
    public static boolean regist = false;
    public static boolean change = false;


    public Init_Spinner(String sInfo, Context context, Spinner spinner,int id){
        this.sInfo = sInfo;
        this.context = context;
        this.spinner = spinner;
        this.id = id;
    }
    public void init(final boolean type){//type:true--搜索类型，0位是null；false--上传类型，0位的数组的第一个
        Resources res = context.getResources();
        String[] temp;
        switch(sInfo){
            case "计算机科学学院":{
                temp = res.getStringArray(R.array.computer_class_name);
                break;
            }
//            case "体育学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "化学与环境工程学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "医学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "外语学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
//            case "教育学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "数学与信息科学学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "文学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "旅游与地理学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "经济管理学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "美术学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "英东生命科学学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "音乐学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "韶州师范学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "英东食品农业科学与工程学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }case "英东农业科学与工程学院":{
//                temp = res.getStringArray(R.array.computer_class_name);
//                break;
//            }
            default:
                temp = new String[1];
                temp[0] = new String("暂时无选项");
                break;
        }
        Log.i("abc",sInfo);
        String[] _class_name;
        if(type){//搜索类型的，在数组头加个null
            _class_name = new String[temp.length+1];
            _class_name[0] = "无条件";
            for(int i=1;i<=temp.length;i++)
                _class_name[i] = temp[i-1];
        }
        else
            _class_name = temp;
        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,_class_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);
        //设置默认值
        spinner.setVisibility(View.VISIBLE);
    }
}
