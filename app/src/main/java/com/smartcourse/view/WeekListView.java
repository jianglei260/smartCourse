package com.smartcourse.view;

import java.util.List;

import com.example.smartcourse.R;
import com.smartcourse.activity.CourseDetialActivity;
import com.smartcourse.bean.CourseItem;
import com.smartcourse.bean.ScreenBean;
import com.smartcourse.utils.DateUtil;
import com.smartcourse.utils.TimeUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SearchViewCompat.OnCloseListenerCompat;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WeekListView extends LinearLayout {
    private Context context;
    private LinearLayout[] layout = new LinearLayout[20];
    private LinearLayout rootView;
    private Paint paint;
    private List<CourseItem> couseList;

    public WeekListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

        this.context = context;
        setBackgroundResource(R.drawable.week_bg);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        rootView = (LinearLayout) LinearLayout.inflate(context, R.layout.weekcourseview, null);
        layout[0] = (LinearLayout) rootView.findViewById(R.id.week_course_1);
        layout[1] = (LinearLayout) rootView.findViewById(R.id.week_course_2);
        layout[2] = (LinearLayout) rootView.findViewById(R.id.week_course_3);
        layout[3] = (LinearLayout) rootView.findViewById(R.id.week_course_4);
        layout[4] = (LinearLayout) rootView.findViewById(R.id.week_course_5);
        layout[5] = (LinearLayout) rootView.findViewById(R.id.week_course_6);
        layout[6] = (LinearLayout) rootView.findViewById(R.id.week_course_7);
        layout[7] = (LinearLayout) rootView.findViewById(R.id.week_course_8);
        layout[8] = (LinearLayout) rootView.findViewById(R.id.week_course_9);
        layout[9] = (LinearLayout) rootView.findViewById(R.id.week_course_10);

        layout[10] = (LinearLayout) rootView.findViewById(R.id.week_course_11);
        layout[11] = (LinearLayout) rootView.findViewById(R.id.week_course_12);
        layout[12] = (LinearLayout) rootView.findViewById(R.id.week_course_13);
        layout[13] = (LinearLayout) rootView.findViewById(R.id.week_course_14);
        layout[14] = (LinearLayout) rootView.findViewById(R.id.week_course_15);
        layout[15] = (LinearLayout) rootView.findViewById(R.id.week_course_16);
        layout[16] = (LinearLayout) rootView.findViewById(R.id.week_course_17);
        layout[17] = (LinearLayout) rootView.findViewById(R.id.week_course_18);
        layout[18] = (LinearLayout) rootView.findViewById(R.id.week_course_19);
        layout[19] = (LinearLayout) rootView.findViewById(R.id.week_course_20);

        for (int i = 0; i < 20; i++) {
            layout[i].setOnClickListener(new CouseItemClickListenner(i));
        }
        addView(rootView);

    }

    public void setAdapter(List<CourseItem> lists) {
        View convertView = null;
        this.couseList = lists;
        for (int i = 0; i < lists.size(); i++) {
            CourseItem item = lists.get(i);
            if (item != null) {
                TextView courseName = (TextView) layout[i].findViewById(R.id.week_courseName);
                courseName.setText(item.getName());
                TextView classRoom = (TextView) layout[i].findViewById(R.id.week_classRoom);
                classRoom.setText(item.getClassRoom());
                TextView teacher = (TextView) layout[i].findViewById(R.id.week_teacher);
                teacher.setText(item.getTeacher());
                TextView weekStart = (TextView) layout[i].findViewById(R.id.week_startEnd);
                teacher.setText(String.valueOf(item.getSatrtWeek()) + "--" + String.valueOf(item.getEndWeek()) + "周");
                TextView weekFlag = (TextView) layout[i].findViewById(R.id.week_flag);
                switch (item.getFlag()) {
                    case 0:
                        break;
                    case 1:
                        weekFlag.setText("单周");
                        break;
                    case 2:
                        weekFlag.setText("双周");
                        break;
                }

            }

        }
    }

    class CouseItemClickListenner implements View.OnClickListener {
        private int position;

        public CouseItemClickListenner(int i) {
            // TODO Auto-generated constructor stub
            this.position = i;

        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.d("position", String.valueOf(couseList.size()));
            if (couseList.get(position) != null) {
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

            invalidate();
        }

    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    public void setPassedCourse(int index) {

        if (index > 0) {
            if (index >= 19)
                index = 19;
            for (int i = 0; i <= index; i++) {
                layout[i].setBackgroundResource(R.drawable.foot);
            }
        }

    }

    public void setNowCourse(int index) {
        if (index > 0) {
            if (index >= 19)
                index = 19;
            layout[index].setAlpha(0.6f);

        }
    }
}


