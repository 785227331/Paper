package com.example.hjh.paper.SQL_Operate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HJH on 2016/4/22.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASEVERSION = 1;//数据库版本,大于0

    //在SQLiteOpenHelper的子类中必须有该构造函数
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //一下两个构造函数是为了方便使用创建的
    public DBOpenHelper(Context context,String name,int version){
        this(context,name,null,version);
    }
    public DBOpenHelper(Context context,String name){
        this(context,name, DATABASEVERSION);
    }



    //数据库被创建时调用，只调用一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        //主要用于创建数据库中的表，sql语句
        Log.i("abc","creat db");
        //添加表download，用于存放正在下载的文件的信息
        //id(主键/自增)，文件名，文件路径，状态（下载中-true/下载完-false）
        db.execSQL("create table download(id integer primary key autoincrement,filename varchar(50)," +
                "filepath varchar(50),fileid varchar(50),state integer)");
    }

    //当数据库被打开时调用
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //当数据库进行更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
