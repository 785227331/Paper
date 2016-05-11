package com.example.hjh.paper.Home.Download;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.hjh.paper.R;

import java.util.List;
import java.util.Map;

/**
 * Created by HJH on 2016/5/7.
 */
public class Download_adapter extends SimpleAdapter {
    private ProgressBar bar;
    private ImageButton imageButton;
    private TextView tv;
    private TextView fname;
    private TextView fid;
    private TextView fpath;

    private Context context;
    private int position;
    private List<? extends Map<String, ?>> list;
    private Map<String, ?> map;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public Download_adapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        list = data;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position=position;
        map = list.get(position);

        View v = super.getView(position, convertView, parent);
        bar = (ProgressBar) v.findViewById(R.id.progressbar);
        imageButton = (ImageButton) v.findViewById(R.id.btn_sign);
        tv = (TextView) v.findViewById(R.id.download_state);
        fname = (TextView) v.findViewById(R.id.download_filename);
        fid = (TextView) v.findViewById(R.id.file_id);
        fpath = (TextView) v.findViewById(R.id.download_path);

        if(map.get("state").equals("下载完成"))
            bar.setProgress(100);

        if(!Download_Control.map_pb.containsKey(map.get("id"))){
            Download_Control.map_pb.put(map.get("id")+"",bar);
        }
        return v;
    }

}
