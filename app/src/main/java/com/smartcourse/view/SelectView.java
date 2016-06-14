package com.smartcourse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.example.smartcourse.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianglei on 16/5/24.
 */
public class SelectView extends RelativeLayout {
    private TitleBarView titleBarView;
    private ListView listView;
    public static final String NAME = "name";
    private SimpleAdapter simpleAdapter;

    public SelectView(Context context) {
        super(context);
        initView();
    }

    public SelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.select, null);
        titleBarView = (TitleBarView) view.findViewById(R.id.titleBar);
        listView = (ListView) view.findViewById(R.id.list);
        addView(view);
    }

    public void setTitle(String text) {
        titleBarView.setTitle(text);
    }

    public void setDataList(List<String> list) {
        simpleAdapter = new SimpleAdapter(getContext(), getMapList(list), R.layout.select_item, new String[]{NAME}, new int[]{R.id.text});
        listView.setAdapter(simpleAdapter);
    }

    public void setBackClickListener(OnClickListener listener) {
        titleBarView.setLeftImageOnClick(listener);
    }

    public void setListItemClickListener(AdapterView.OnItemClickListener listener) {
        listView.setOnItemClickListener(listener);
    }

    private List<Map<String, String>> getMapList(List<String> list) {
        List<Map<String, String>> mapList = new ArrayList<>(list.size());
        for (String name : list) {
            Map<String, String> map = new HashMap<>();
            map.put(NAME, name);
            mapList.add(map);
        }
        return mapList;
    }
}
