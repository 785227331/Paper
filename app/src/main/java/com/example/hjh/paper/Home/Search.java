package com.example.hjh.paper.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hjh.paper.Bmob.Paper;
import com.example.hjh.paper.Home.Download.Download_Control;
import com.example.hjh.paper.Home.Download.download_file;
import com.example.hjh.paper.Init_Spinner;
import com.example.hjh.paper.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HJH on 2016/4/7.
 */
public class Search extends Fragment {
    private View searchview;
    private LinearLayout layout;
    private LinearLayout loading_pb;

    //listview
    private ListView listview_result;
    private int lastItem;//监听是否到底部
    private ArrayList<Map<String, Object>> date = new ArrayList<>();
    private SimpleAdapter simpleAdapter;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    private Button btn_search;
    private String PaperCollege = "计算机科学学院";//初始默认值
    private String PaperName = null;
    private String PaperTeacher = null;
    private String PaperClass = null;

    private Spinner college;
    private Spinner subject;
    private Spinner teacher;
    private Spinner paper_class;

    //上拉加载
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多
    private int limit = 10;        // 每页的数据是10条
    private int curPage = 0;        // 当前页的编号，从0开始

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchview = inflater.inflate(R.layout.search, container, false);
        //初始化搜索对话框的布局
        layout = (LinearLayout) getLayoutInflater(Bundle.EMPTY).inflate(R.layout.search_item, null);
        //加载动画
        loading_pb = (LinearLayout) searchview.findViewById(R.id.search_loading);
        loading_pb.setVisibility(View.INVISIBLE);

        college = (Spinner) layout.findViewById(R.id.search_paper_college);
        subject = (Spinner) layout.findViewById(R.id.search_paper_name);
        teacher = (Spinner) layout.findViewById(R.id.search_paper_teacher);
        paper_class = (Spinner) layout.findViewById(R.id.search_paper_class);

        btn_search = (Button) searchview.findViewById(R.id.search);
        listview_result = (ListView) searchview.findViewById(R.id.list_paper);
        listview_result.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        //添加一个底部view：显示正在加载
                        loading_pb.setVisibility(View.VISIBLE);
                        //加载数据
                        queryData(curPage,STATE_MORE);
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1;
            }
        });

        //初始化对话框选项，以及对话框
        init();
        setDialogView();

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_dialog();
            }
        });
        return searchview;
    }

    private void show_dialog() {
        alertDialog.show();
    }

    private void setDialogView() {
        builder = new AlertDialog.Builder(getContext())
                //设置标题
                .setTitle("搜索选项设置")
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //搜索操作,返回结果——文件名，组成链表，显示在lisview中
                        Log.i("abc", "search");
                        //添加一个底部view：显示正在加载
                        loading_pb.setVisibility(View.VISIBLE);

                        queryData(curPage,STATE_REFRESH);

                        alertDialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消搜索，不做任何处理
                        Log.i("abc", "cancle_search");
                        alertDialog.dismiss();
                    }
                });
        alertDialog = builder.create();
    }

    private void init() { //初始化搜索选项的 可选项目
        init_college();//先选学院
        init_subject("计算机科学学院", layout.getContext());//根据学院给出
        init_teacher("计算机科学学院", layout.getContext());//老师选项也是根据学院给出
        init_paperclass("计算机科学学院", layout.getContext());//根据学院给出
    }

    private void init_paperclass(String sInfo, Context context) {
        Init_Spinner is = new Init_Spinner(sInfo, context, paper_class, R.array.computer_class_name);//参数最后的id需要改为class的数组id
        is.init(Init_Spinner.search);
    }

    private void init_teacher(String sInfo, Context context) {
        Init_Spinner is = new Init_Spinner(sInfo, context, teacher, R.array.computer_class_name);//参数最后的id需要改为teacher的数组id
        is.init(Init_Spinner.search);
    }

    private void init_subject(String sInfo, Context context) {
        Init_Spinner is = new Init_Spinner(sInfo, context, subject, R.array.computer_class_name);//参数最后的id需要改为subject的数组id
        is.init(Init_Spinner.search);
    }

    private void init_college() {
        Resources res = getResources();
        String[] college_name = res.getStringArray(R.array.college_name);
        //将可选内容与ArrayAdapter连接起来
        ArrayAdapter adapter = new ArrayAdapter<String>(layout.getContext(), android.R.layout.simple_spinner_item, college_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (college == null)
            Log.e("abc", "college");
        if (adapter == null)
            Log.e("abc", "adapter");
        //将adapter 添加到spinner中
        college.setAdapter(adapter);
        //添加事件Spinner事件监听
        college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //点击后，更新学科选项，并设置保存选择的值
                PaperCollege = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //设置初始选择值，
        //设置默认值
        college.setVisibility(View.VISIBLE);
    }

    private void queryData(final int page ,final int actionType){
        BmobQuery<Paper> query = new BmobQuery<Paper>();
        // 如果是加载更多
        if(actionType == STATE_MORE){
            // 跳过之前页数并去掉重复数据
            query.setSkip(page * limit);
        }
        // 设置每页数据个数,默认是10个
        query.setLimit(limit);
        //添加查询条件
        query.addWhereEqualTo("PaperCollege", PaperCollege);
        //获取选中项
        PaperName = subject.getSelectedItem().toString();
        PaperTeacher = teacher.getSelectedItem().toString();
        PaperClass = paper_class.getSelectedItem().toString();
        //判断是否有选择条件
        if (!PaperName.equals("无条件")) {
            query.addWhereEqualTo("PaperName", PaperName);
        }
        if (!PaperTeacher.equals("无条件")) {
            query.addWhereEqualTo("PaperTeacher", PaperTeacher);
        }
        if (!PaperClass.equals("无条件")) {
            query.addWhereEqualTo("PaperClass", PaperClass);
        }
        Log.i("abc", "PaperCollege--" + PaperCollege + "PaperName--" + PaperName + "PaperTeacher--" + PaperTeacher + "PaperClass--" + PaperClass);
        //查询，并获取结果
        query.findObjects(getContext(), new FindListener<Paper>() {
            @Override
            public void onSuccess(List<Paper> list) {
                Log.i("abc", "一共有多少条数据：" + list.size());
                if (list.size() == 0) {//如果该条件下找不任何数据，则提示“找不到相关文档”
                    Toast.makeText(searchview.getContext(), "对不起，找不到相关文档！", Toast.LENGTH_SHORT).show();
                    //加载完毕后，隐藏底部view
                    loading_pb.setVisibility(View.GONE);
                    return;
                }
                if(actionType == STATE_REFRESH){
                    curPage = 0;
                    date.clear();
                }
                for (Paper paper : list) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    Log.i("abc", "数据名：" + paper.getPaperFileName());
                    //添加数据
                    map.put("img", R.drawable.word);
                    map.put("filename", paper.getPaperFileName());
                    map.put("college", paper.getPaperCollege());
                    map.put("name", paper.getPaperName());
                    map.put("teacher", paper.getPaperTeacher());
                    map.put("year", paper.getPaperYear());
                    map.put("id",paper.getObjectId());
                    date.add(map);
                    Log.i("abc", "PaperCollege--" + paper.getPaperCollege() + "PaperName--" + paper.getPaperName() + "PaperTeacher--" + paper.getPaperTeacher() + "PaperClass--" + paper.getPaperYear());
                }
                curPage++;
                //加载完毕后，隐藏底部view
                loading_pb.setVisibility(View.GONE);
                //设置适配器
                simpleAdapter = new SimpleAdapter(searchview.getContext(), date, R.layout.search_result_listview,
                        new String[]{"img", "filename", "college", "name", "teacher", "year","id"},
                        new int[]{R.id.img_list, R.id.result_paper_filename, R.id.result_paper_college,
                                R.id.result_paper_name, R.id.result_paper_teacher, R.id.result_paper_year,R.id.file_id}){
                    //对每个view进行设置
                    @Override
                    public View getView(final int position, View convertView, ViewGroup parent) {
                        //记录位置
                        final int index = position;
                        //获取view对象
                        View v= super.getView(position, convertView, parent);
                        //在获取并初始化需要设置的控件
                        Button download = (Button) v.findViewById(R.id.btn_download);
                        //给button设置标记，标记---当前view的位置
                        download.setTag(position);
                        //给button添加事件
                        download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //根据位置信息，获取文件信息，并加入下载队列进行下载
                                Map<String, Object> map = date.get(position);
//                                Log.i("abc","name:"+map.get("filename")+"id:"+map.get("id")+"---path:"+getContext().getApplicationContext().getCacheDir()+"/bmob/");
                                download_file file = new download_file();
                                //默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
                                file.setFile_id(map.get("id").toString());
                                file.setFilename(map.get("filename").toString());
                                file.setFilepath(getContext().getApplicationContext().getCacheDir()+"/bmob/");
                                file.setProgress(0);
                                file.setState(download_file.state_downloading);

                                //交给Download_Control 开启下载任务
                                Download_Control.download_control.NewDownload(file);
                                //开启后切换到下载管理中心
                                Home_activity.setposition(1);

                            }
                        });
                        return v;
                    }
                };
                listview_result.setAdapter(simpleAdapter);
            }
            @Override
            public void onError ( int i, String s){
                //加载完毕后，隐藏底部view
                loading_pb.setVisibility(View.GONE);
            }
        });
    }
}

