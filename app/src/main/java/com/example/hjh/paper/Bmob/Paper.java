package com.example.hjh.paper.Bmob;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by HJH on 2016/4/2.
 */
/*
PaperID——试卷号【int自增】
PaperYear——年份【int】
PaperName——学科【sting】
PaperTeacher——出题教师【string】
PaperClass——年级/班级【string】
PaperFile——文件【file】
PaperFileName——文件名【string】
 */
public class Paper extends BmobObject {
    private Integer PaperYear;
    private String PaperCollege;
    private String PaperName;
    private String PaperTeacher;
    private String PaperClass;
    private String PaperFileName;
    private BmobFile PaperFile;

    public Paper(Integer PaperYear, String PaperCollege,String PaperName,String PaperTeacher,
            String PaperClass,String PaperFileName,BmobFile PaperFile){
        this.PaperYear = PaperYear;
        this.PaperCollege = PaperCollege;
        this.PaperName = PaperName;
        this.PaperTeacher = PaperTeacher;
        this.PaperClass = PaperClass;
        this.PaperFileName = PaperFileName;
        this.PaperFile = PaperFile;
    }

    public String getPaperCollege() {
        return PaperCollege;
    }

    public void setPaperCollege(String paperCollege) {
        PaperCollege = paperCollege;
    }

    public String getPaperFileName() {return PaperFileName;}

    public void setPaperFileName(String paperFileName) {PaperFileName = paperFileName;}

    public Integer getPaperYear() {
        return PaperYear;
    }

    public void setPaperYear(Integer paperYear) {
        PaperYear = paperYear;
    }

    public String getPaperName() {
        return PaperName;
    }

    public void setPaperName(String paperName) {
        PaperName = paperName;
    }

    public String getPaperTeacher() {
        return PaperTeacher;
    }

    public void setPaperTeacher(String paperTeacher) {
        PaperTeacher = paperTeacher;
    }

    public String getPaperClass() {
        return PaperClass;
    }

    public void setPaperClass(String paperClass) {
        PaperClass = paperClass;
    }

    public BmobFile getPaperFile() {
        return PaperFile;
    }

    public void setPaperFile(BmobFile paperFile) {
        PaperFile = paperFile;
    }
}
