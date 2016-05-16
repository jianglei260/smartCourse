package com.smartcourse.view;

import java.util.List;

import com.example.smartcourse.R;
import com.smartcourse.activity.CourseDetialActivity;
import com.smartcourse.bean.CourseItem;
import com.smartcourse.view.MyListView.OnItemClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TodayListView extends LinearLayout {
    private Context context;
    private MyListView listView;
    private ListAdapter adapter;
    private List<CourseItem> couseList;
    private Paint paint;

    public TodayListView(Context context, List<CourseItem> list) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.couseList = list;
        initUI();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

    }

    @SuppressLint("NewApi")
    private void initUI() {
        // TODO Auto-generated method stub
        setBackgroundResource(R.drawable.week_bg);
        setOrientation(VERTICAL);
        TitleBarView titleBarView = new TitleBarView(context, null);
        titleBarView.setTitle("今日课程");
        titleBarView.setLeftImageVisible(false);
        titleBarView.setRightImageVisible(false);
        Log.d("layout_with", String.valueOf(titleBarView.getWidth()));

        LayoutParams tilteBarLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.title_bar_height));
        addView(titleBarView, tilteBarLayoutParams);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.rightMargin = layoutParams.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_item_top_margin);
        listView = new MyListView(context);
        adapter = new ListAdapter(couseList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                // TODO Auto-generated method stub
                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(context, CourseDetialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", couseList.get(position).getName());
                bundle.putString("teacher", couseList.get(position).getTeacher());
                bundle.putString("classRoom", couseList.get(position).getClassRoom());
                bundle.putString("hour", couseList.get(position).getHour());
                bundle.putInt("satrtWeek", couseList.get(position).getSatrtWeek());
                bundle.putInt("endWeek", couseList.get(position).getEndWeek());
                bundle.putInt("weekday", couseList.get(position).getWeekday());
                bundle.putInt("flag", couseList.get(position).getFlag());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        addView(listView, layoutParams);
        if (couseList.size() == 0) {
            TextView textView = new TextView(context);
            textView.setText("今天没有任何课程，可以去做别的事情哦！");
            textView.setBackgroundResource(R.drawable.textstyle);
            LayoutParams layoutParams2 = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.title_bar_height));
            layoutParams2.topMargin = layoutParams2.leftMargin = layoutParams2.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_item_top_margin);
            addView(textView, layoutParams2);
        }

    }

    public void setList(List<CourseItem> couseList) {
        this.couseList = couseList;
    }

    public void setOnItemClicked(OnItemClickListener listenner) {
        listView.setOnItemClickListener(listenner);
    }

    public class ListAdapter extends ViewBaseAdapter<CourseItem> {
        ViewHolder viewHolder;

        public ListAdapter(List<CourseItem> lists) {
            super(lists);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = LinearLayout.inflate(context, R.layout.mlist, null);
                viewHolder = new ViewHolder();
                viewHolder.courseName = (TextView) convertView.findViewById(R.id.nameItem);
                viewHolder.teacher = (TextView) convertView.findViewById(R.id.teacherItem);
                viewHolder.time = (TextView) convertView.findViewById(R.id.timeItem);
                viewHolder.classRoom = (TextView) convertView.findViewById(R.id.classItem);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (getItem(position) != null) {
                CourseItem courseItem = getItem(position);
                viewHolder.courseName.setText(courseItem.getName());
                viewHolder.teacher.setText(courseItem.getTeacher());
                viewHolder.time.setText(" 第" + courseItem.getHour() + "节课");
                viewHolder.classRoom.setText(courseItem.getClassRoom());
            }

            return convertView;
        }
    }

    class ViewHolder {
        TextView courseName;
        TextView teacher;
        TextView time;
        TextView classRoom;
    }

}
