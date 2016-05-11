package com.example.hjh.paper.Home.Download;

import java.util.ArrayList;

/**
 * Created by HJH on 2016/4/22.
 */
public class download_file {
    private String filename;
    private String filepath;
    private int progress;//下载进度，总-100
    private String file_id;
    private int state;//状态：下载中，下载完成，下载失败————————0;1;2
    public static int state_downloading=0;
    public static int state_complete=1;
    public static int state_download_faile=2;
    private static ArrayList<download_file> downloading_FileList= new ArrayList<>();
    private static ArrayList<download_file> downloaded_FileList= new ArrayList<>();

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public ArrayList<download_file> getdownloadingist(){
        return downloading_FileList;
    }

    public ArrayList<download_file> getDownloaded_FileList(){
        return downloaded_FileList;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
