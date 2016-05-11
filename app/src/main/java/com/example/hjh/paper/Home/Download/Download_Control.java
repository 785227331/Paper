package com.example.hjh.paper.Home.Download;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.example.hjh.paper.R;
import com.example.hjh.paper.SQL_Operate.DAO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by HJH on 2016/4/10.
 */
public class Download_Control extends Fragment{
    //创建一个异步任务列表，用于保存下载文件任务，key-fileid
    public static Map<String,AsyncTask> map_task = new HashMap<>();
    public static Map<String,ProgressBar> map_pb = new HashMap<>();
    public static Download_Control download_control = new Download_Control();
    private static Download_adapter simpleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View downloadview = inflater.inflate(R.layout.download_control,container,false);
        return downloadview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setlistview();

        super.onActivityCreated(savedInstanceState);
    }
    public void setlistview(){
        //创建时就显示listview
        ListView download_list = (ListView) getActivity().findViewById(R.id.listView_download);
        SimpleAdapter simpleAdapter = getAdapter();
        download_list.setAdapter(simpleAdapter);

    }

    private Download_adapter getAdapter() {

        simpleAdapter = new Download_adapter(getContext(),getDate(),R.layout.download_item,
                new String[]{"name","path","id","state","img"},new int[]{R.id.download_filename,
                R.id.download_path,R.id.download_id,R.id.download_state,R.id.btn_sign});
        return simpleAdapter;
    }

    private LinkedList<Map<String, Object>> getDate() {
        //获取数据应该从数据库中获取，My_download_task中的任务只是，本次软件开启后新建立的任务
        LinkedList<Map<String,Object>> linkedList = new LinkedList<>();
        LinkedList<download_file> db_file_list ;

        DAO dao = new DAO(getContext());
        db_file_list = dao.getAllDate();
        for (download_file file : db_file_list) {
            Map<String,Object>  map = new HashMap<>();
            map.put("name",file.getFilename());
            map.put("path",file.getFilepath());
            map.put("id",file.getFile_id());
            int state = file.getState();
            switch (state){
                case 0://下载中
                    map.put("state","下载中");
                    map.put("img",R.drawable.stop);
                    break;
                case 1://下载完成
                    map.put("state","下载完成");
                    map.put("img",R.drawable.finish);
                    break;
                case 2://下载失败
                    map.put("state","下载失败");
                    map.put("img",R.drawable.begin);
                    break;
            }
            linkedList.add(map);
        }
        return linkedList;
    }

    public void NewDownload(download_file file){
        My_download_task task = new My_download_task(file,getContext());
        task.execute();
        map_task.put(file.getFile_id(),task);
    }
    /**
     * Message:
     *   what:1-建立下载；2-下载中，进度更新；3-下载完成
     *   arg1：当前下载进度
     */
    public static Handler mainHandler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg!=null) {
                if (msg.what == 1) {
                    Download_Control.download_control.setlistview();
                    Log.i("abc","msg1");
                } else if (msg.what == 2) {
                        //当进度为100时，完成下载
                        Log.i("abc", msg.obj + "————id");
                        map_pb.get(msg.obj).setProgress(msg.arg1);
                    }
            }
            super.handleMessage(msg);
        }
    };
}
