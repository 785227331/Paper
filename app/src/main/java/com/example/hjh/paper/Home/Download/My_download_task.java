package com.example.hjh.paper.Home.Download;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.hjh.paper.Bmob.Paper;
import com.example.hjh.paper.SQL_Operate.DAO;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HJH on 2016/4/24.
 */
public class My_download_task extends AsyncTask {
    //需要下载的任务
    private download_file file;
    private Context context;

    public My_download_task(download_file file,Context context){
        Log.i("abc","添加了任务："+file.getFilename());
        this.file = file;
        this.context = context;
        Message msg = new Message();
        msg.what=1;
        Download_Control.mainHandler.sendMessage(msg);
    }
    //该方法将在执行后台耗时操作前被调用。通常方法用于完成一些初始化准备工作，比如在界面上显示进度等
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //后台线程将要完成的任务；该方法可以调用publishProgress（.....）方法更新任务的执行进度
    @Override
    protected Object doInBackground(Object[] params) {
        Log.i("abc","开始执行任务");
        //将下载任务的信息加入到list 和 数据库中
        //放入数据库前先检查一下数据库中是否有同样的文件
        DAO db = new DAO(context);
        if(db.getDate(file.getFile_id())==null)//如果数据库中没有该id的文件则加入到数据库中；若有则不加
            //加入到数据库中
            db.add(file);
        //加入到list中
        DownLoad_list.getList().add(file);

        //开始下载任务,根据id进行下载
        final File savefile = new File(file.getFilepath()+file.getFilename());
        //1、先获取BmobFile对象实例
        BmobQuery<Paper> bmobQuery = new BmobQuery<Paper>();
        bmobQuery.addWhereEqualTo("objectId",file.getFile_id());
        Log.i("abc","id:"+file.getFile_id());
        Log.i("abc","path:"+savefile.getAbsolutePath());
        Log.i("abc","path:"+savefile.getPath());
        bmobQuery.findObjects(context, new FindListener<Paper>() {
            @Override
            public void onSuccess(List list) {
                final Paper paper = (Paper) list.get(0);
                Log.i("abc","size:"+list.size());
                BmobFile downloadfile = paper.getPaperFile();
                if (downloadfile != null) {
                    downloadfile.download(context, savefile, new DownloadFileListener() {
                        @Override
                        public void onStart() {
                            toast("开始下载！");
                            super.onStart();
                        }
                        @Override
                        public void onSuccess(String s) {
                            toast(paper.getPaperFileName() + "下载成功！");
                            //下载完成后，更新数据库中文件状态（state）
                            DAO dao = new DAO(context);
                            file.setState(download_file.state_complete);
                            Log.i("abc","id:"+file.getFile_id()+" state"+file.getState());
                            dao.modify(file);
                        }
                        //更新下载进度,progress--下载完成度百分比，total--总下载大小
                        @Override
                        public void onProgress(Integer progress, long total) {
                            Log.i("abc", "下载进度：" + progress + "," + total);
                            if(progress==100){
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = file.getFile_id();
                                Download_Control.mainHandler.sendMessage(msg);
                            }
                            else {
                                Message msg = new Message();
                                msg.what = 2;
                                msg.arg1 = progress;
                                msg.obj = file.getFile_id();
                                Download_Control.mainHandler.sendMessage(msg);
                            }
                        }
                        @Override
                        public void onFailure(int i, String s) {
                            toast(paper.getPaperFileName() + "下载失败！");
                            //下载s失败后，更新数据库中文件状态（state）
                            DAO dao = new DAO(context);
                            file.setState(download_file.state_download_faile);
                            dao.modify(file);
                        }
                    });
                }
                else{
                    Log.i("abc","file为空");
                }
            }
            @Override
            public void onError(int i, String s) {
                //找不到对应id的文件
                Log.i("abc","");
                //下载s失败后，更新数据库中文件状态（state）
                DAO dao = new DAO(context);
                file.setState(download_file.state_download_faile);
                dao.modify(file);
            }
        });
        return null;
    }

    //在 doInBackground 方法中调用一个 publishProgress(Progress)方法更新任务进度后，将触发该方法
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    //当doInBackground（）完成后，系统会自动调用 该方法，并将doInBackground（）方法的返回值传给该方法
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }



    private void downloadfile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
    }

    public download_file getFile() {
        return file;
    }

    private void toast(String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
