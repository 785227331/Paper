package com.example.hjh.paper.SQL_Operate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hjh.paper.Home.Download.download_file;

import java.util.LinkedList;

/**
 * Created by HJH on 2016/4/22.
 */
public class DAO {
    private static final String DATABASENAME = "Download.db"; //数据库名称
    private DBOpenHelper helper = null;

    public DAO(Context context){
        helper = new DBOpenHelper(context,DATABASENAME);
    }
    public DAO(Context context,int verson){
        helper = new DBOpenHelper(context,DATABASENAME,verson);
    }

    //读取所有数据
    public LinkedList<download_file> getAllDate(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from download",null);

        LinkedList<download_file> list = new LinkedList<>();
        while (cursor.moveToNext()){
            download_file file = new download_file();
            file.setFilename(cursor.getString(cursor.getColumnIndex("filename")));
            file.setFilepath(cursor.getString(cursor.getColumnIndex("filepath")));
            file.setFile_id(cursor.getString(cursor.getColumnIndex("fileid")));
            file.setState(cursor.getInt(cursor.getColumnIndex("state")));
            list.add(file);
        }
        return list;
    }
    //读取/查找数据
    public download_file getDate(String file_id){
        // 取得数据库操作
        SQLiteDatabase db = helper.getReadableDatabase();
        // 用游标Cursor接收从数据库检索到的数据
        Cursor cursor = db.rawQuery("select * from download where fileid=?", new String[] { file_id });

        if(cursor.moveToFirst()==false)//没有找到对应的文件
            return null;
        download_file file = new download_file();
        file.setFilename(cursor.getString(cursor.getColumnIndex("filename")));
        file.setFilepath(cursor.getString(cursor.getColumnIndex("filepath")));
        file.setFile_id(cursor.getString(cursor.getColumnIndex("fileid")));
        file.setState(cursor.getInt(cursor.getColumnIndex("state")));

        // 记得关闭数据库操作
        db.close();
        return file;
    }

    //删除单个数据
    public boolean delete(String file_id){
        // 取得数据库操作
        SQLiteDatabase db = helper.getWritableDatabase();
        // 删除数据
        db.rawQuery("delete from download where fileid=?", new String[] { file_id });
        // 记得关闭数据库操作
        db.close();
        return true;
    }

    //添加单个数据
    public boolean add(download_file file){
        // 取得数据库操作
        SQLiteDatabase db = helper.getWritableDatabase();
        // 插入数据
        db.execSQL("insert into download (filename,filepath,fileid,state) values(?,?,?,?)", new Object[] { file.getFilename(), file.getFilepath(),file.getFile_id(),file.getState()});
        // 记得关闭数据库操作
        db.close();
        return true;
    }

    //修改单个数据
    public boolean modify(download_file file){
        // 取得数据库操作
        SQLiteDatabase db = helper.getWritableDatabase();
        // 插入数据
        db.execSQL("update download set state=? where fileid=?", new Object[] {file.getState(),file.getFile_id()});
        // 记得关闭数据库操作
        db.close();
        return true;
    }
}
