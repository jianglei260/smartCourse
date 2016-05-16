package com.smartcourse.view;

import com.example.smartcourse.R;
import com.smartcourse.activity.AddCourse;
import com.smartcourse.db.DBHelper;
import com.smartcourse.utils.DateUtil;
import com.smartcourse.widget.StyleButton;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MyCourseView extends LinearLayout {
    private Context context;
    private AlertDialog aboutDialog, infoDialog;

    public MyCourseView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        initUI();
    }

    @SuppressLint("NewApi")
    private void initUI() {
        // TODO Auto-generated method stub
        setOrientation(VERTICAL);
        TitleBarView titleBarView = new TitleBarView(context);
        titleBarView.setTitle("我的课程表");
        titleBarView.setLeftImageVisible(false);
        titleBarView.setRightImageVisible(false);

        LayoutParams tilteBarLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.title_bar_height));
        addView(titleBarView, tilteBarLayoutParams);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = layoutParams.rightMargin = layoutParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_top_margin);

        StyleButton addCourse = new StyleButton(context);
        addCourse.setText("添加课程");
        addCourse.setAlpha(100f);
        addCourse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(context, AddCourse.class);
                context.startActivity(intent);
            }
        });

        StyleButton myInfo = new StyleButton(context);
        myInfo.setText("我的信息");
        myInfo.setAlpha(1.0f);
        myInfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("我的信息");
                Cursor c = DBHelper.getInstance(context).query(DBHelper.USER_TBL_NAME);
                c.moveToFirst();
                String pro = c.getString(c.getColumnIndex("pro"));
                String grade = c.getString(c.getColumnIndex("grade"));
                String mClass = c.getString(c.getColumnIndex("class"));
                DateUtil dateUtil = new DateUtil();
                String msg = "专业：" + pro + "\n" + "年级：" + grade + "\n" + "班级：" + mClass + "\n" + "周数：第" + dateUtil.getWeek(context) + "周";
                builder.setMessage(msg);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        infoDialog.dismiss();
                    }
                });
                infoDialog = builder.create();
                infoDialog.show();
            }
        });

        StyleButton about = new StyleButton(context);
        about.setText("关于");
        about.setAlpha(1.0f);
        about.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("关于");
                String msg = "这是一款帮助成都信息工程大学师生快捷查询课程表的软件";
                builder.setMessage(msg);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        aboutDialog.dismiss();
                    }
                });
                aboutDialog = builder.create();
                aboutDialog.show();
            }
        });

        addView(addCourse, layoutParams);
        addView(myInfo, layoutParams);
        addView(about, layoutParams);
    }

}
