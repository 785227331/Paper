package com.example.hjh.paper.Home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hjh.paper.Bmob.Paper;
import com.example.hjh.paper.FileSelector.CallbackBundle;
import com.example.hjh.paper.FileSelector.OpenFileDialog;
import com.example.hjh.paper.Init_Spinner;
import com.example.hjh.paper.Login_activity;
import com.example.hjh.paper.Bmob.Online_User;
import com.example.hjh.paper.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by HJH on 2016/4/10.
 */
public class Personal extends Fragment {
    private Dialog chosefile;
    private Dialog uploadfile;
    private LinearLayout layout;
    private String filepath;

    private ArrayAdapter adapter;

    //需要设置初始设置，没点击过时，就使用默认值
    private String PaperCollege = "计算机科学学院";//初始默认值
    private String PaperName = "软件工程";
    private String PaperTeacher = "软件工程";
    private String PaperClass = "软件工程";
    private int PaperYear;
    private String filename;
    private Dialog dialog;

    private Spinner college ;
    private Spinner subject ;
    private Spinner teacher ;
    private Spinner paper_class ;
    private TextView paper_name;
    private TextView paper_year ;
    private ImageButton year_up;
    private ImageButton year_down;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = (LinearLayout) getLayoutInflater(Bundle.EMPTY).inflate(R.layout.upload_file,null);
        return inflater.inflate(R.layout.personal,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //上传文件：打开文件选择器，选定文件后设置文件属性，确定后开始上传
        Button upload = (Button) getActivity().findViewById(R.id.btn_upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v.getId());
            }
        });
        Init_uploadfile(getActivity());
        //修改个人资料
        Button change = (Button) getActivity().findViewById(R.id.btn_change_personal);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否用户登录模式
                Online_User online_user = Online_User.getUser();
                if(online_user.isLogin_Type()){//true-用户登录模式
                    Intent intent = new Intent(getContext(),Modify_User_Information.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(),"请先进行登录！！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //退出登录：删除登录信息，返回登录页面
        Button landout = (Button) getActivity().findViewById(R.id.btn_landout);
        if(!Online_User.getUser().isLogin_Type()){//如果是匿名登录模式的，显示改为“登录”
            landout.setText("登录");
        }
        landout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除登录信息
                Online_User.getUser().delet();
                //关闭当前页面，并跳转到登录页面
                Personal.this.getActivity().finish();
                Intent intent = new Intent(Personal.this.getContext(), Login_activity.class);
                startActivity(intent);

            }
        });
    }

    private void Init_uploadfile(Context context) {
        AlertDialog.Builder builder= new AlertDialog.Builder(context)
                .setTitle("设置文件相关内容")
                .setView(layout)
                .setPositiveButton("确定上传", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定则开始上传，显示进度
                        File file = new File(filepath);//根据文件路径和文件名，创建文件对象
                        final BmobFile bmobFile = new BmobFile(file);
                        bmobFile.uploadblock(getContext(), new UploadFileListener() {
                            @Override
                            public void onSuccess() {
                                PaperName = subject.getSelectedItem().toString();
                                PaperTeacher = teacher.getSelectedItem().toString();
                                PaperClass = paper_class.getSelectedItem().toString();
                                //开始上传
                                // TODO Auto-generated method stub
                                Log.i("abc","success upload");
                                Paper paper = new Paper( PaperYear,  PaperCollege, PaperName, PaperTeacher,
                                        PaperClass, filename, bmobFile);
                                paper.save(getContext(), new SaveListener() {
                                    @Override
                                    public void onSuccess() {
                                        Log.i("abc","ok");
                                    }
                                    @Override
                                    public void onFailure(int i, String s) {
                                        Log.i("abc","no");
                                    }
                                });
                            }
                            @Override
                            public void onProgress(Integer arg0) {
                                // TODO Auto-generated method stub
                            }
                            @Override
                            public void onFailure(int arg0, String arg1) {
                                // TODO Auto-generated method stub
                                Log.i("abc","failed upload");
                            }
                        });
                        Log.i("abc",file.getPath());
                    }
                })
                .setNegativeButton("取消上传", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        uploadfile = builder.create();
        //设置各个spinner的初始值，文件名 在选择后再设置
        init();
        init_college();//先选学院
        init_subject("计算机科学学院",layout.getContext());//根据学院给出
        init_teacher("计算机科学学院",layout.getContext());//老师选项也是根据学院给出
        init_paperclass("计算机科学学院",layout.getContext());//根据学院给出
        init_paperyear();
    }

    private void init() {
        paper_name = (TextView) layout.findViewById(R.id.upload_filename);
        college = (Spinner) layout.findViewById(R.id.upload_file_college);
        subject = (Spinner) layout.findViewById(R.id.upload_file_name);
        teacher = (Spinner) layout.findViewById(R.id.upload_file_teacher);
        paper_class = (Spinner) layout.findViewById(R.id.upload_file_paperclass);
        paper_year = (TextView) layout.findViewById(R.id.upload_file_year);

        year_up = (ImageButton) layout.findViewById(R.id.btn_year_up);
        year_down = (ImageButton) layout.findViewById(R.id.btn_year_down);
    }

    private void init_paperyear() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        paper_year.setText(str);
        PaperYear = Integer.parseInt(str);

        year_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaperYear++;
                paper_year.setText(PaperYear+"");
            }
        });

        year_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaperYear--;
                paper_year.setText(PaperYear+"");
            }
        });
    }

    private void init_paperclass(String sInfo,Context context) {
        Init_Spinner is = new Init_Spinner(sInfo,context,paper_class,R.array.computer_class_name);//参数最后的id需要改为class的数组id
        is.init(Init_Spinner.upload);
    }
    private void init_teacher(String sInfo,Context context) {
        Init_Spinner is = new Init_Spinner(sInfo,context,teacher,R.array.computer_class_name);//参数最后的id需要改为teacher的数组id
        is.init(Init_Spinner.upload);
    }

    private void init_subject(String sInfo,Context context) {
        Init_Spinner is = new Init_Spinner(sInfo, context, subject, R.array.computer_class_name);//参数最后的id需要改为subject的数组id
        is.init(Init_Spinner.upload);
    }

    private void init_college(){

        Resources res = getResources();
        String[] college_name =res.getStringArray(R.array.college_name);
        //将可选内容与ArrayAdapter连接起来
        ArrayAdapter adapter = new ArrayAdapter<String>(layout.getContext(),android.R.layout.simple_spinner_item,college_name);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(college == null)
            Log.e("abc","college");
        if(adapter == null)
            Log.e("abc","adapter");
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


    private void showDialog(int id) {
        if(chosefile!=null){
            chosefile.show();
        }
        else{
            chosefile = onCreateDialog(id);
            chosefile.show();
        }

    }

    protected Dialog onCreateDialog(int id) {
        Map<String, Integer> images = new HashMap<String, Integer>();
        // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
        images.put(OpenFileDialog.sRoot, R.mipmap.ic_launcher);   // 根目录图标
        images.put(OpenFileDialog.sParent, R.mipmap.ic_launcher);    //返回上一层的图标
        images.put(OpenFileDialog.sFolder, R.mipmap.ic_launcher);   //文件夹图标
        images.put("doc", R.mipmap.ic_launcher);   //doc文件图标
        images.put("docx", R.mipmap.ic_launcher);   //docx文件图标
        images.put(OpenFileDialog.sEmpty, R.mipmap.ic_launcher);
        dialog = OpenFileDialog.createDialog(id, getActivity(), "打开文件", new CallbackBundle() {
            //回调时间
            @Override
            public void callback(Bundle bundle) {
                filepath = bundle.getString("path");//获取文件路径
                //打开文件上传页面：显示文件，选择文件的各个属性；
                filename = bundle.getString("name");//获取文件名
                paper_name.setText(filename);
                uploadfile.show();
            }
        }, ".doc;.docx;", images);
        return dialog;
    }

}
