package com.example.hjh.paper.Home.Download;

import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by HJH on 2016/4/24.
 */
public class DownLoad_list {
    //对应文件
    private static LinkedList<download_file> downloaded_list = new LinkedList<>();
    //对应布局
    private static ArrayList<View> layout_list = new ArrayList<>();

    public static ArrayList<View> getLayout_list() {
        return layout_list;
    }
    public static LinkedList<download_file> getList() {
        return downloaded_list;
    }
}
