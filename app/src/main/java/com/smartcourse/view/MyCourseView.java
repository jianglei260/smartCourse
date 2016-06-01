package com.smartcourse.view;

import com.example.smartcourse.R;
import com.smartcourse.activity.AddCourse;
import com.smartcourse.activity.FirstActivity;
import com.smartcourse.db.DBHelper;
import com.smartcourse.utils.DateUtil;
import com.smartcourse.widget.StyleButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MyCourseView extends LinearLayout implements View.OnClickListener {
    private Context context;
    private AlertDialog aboutDialog, infoDialog;
    private String role;
    private TextView roleText;

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
        titleBarView.setRightImageVisible(true);
        titleBarView.setRightImage(R.drawable.ic_add_white);
        titleBarView.setRightImageOnClick(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, AddCourse.class);
                context.startActivity(intent);
            }
        });

        LayoutParams tilteBarLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.title_bar_height));
        addView(titleBarView, tilteBarLayoutParams);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = layoutParams.rightMargin = layoutParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_top_margin);
        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.title_bar_height);
        layoutParams.gravity = Gravity.CENTER;
        LinearLayout infoLayout = new LinearLayout(getContext());
        infoLayout.setBackgroundColor(getResources().getColor(R.color.white));
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams infoParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        infoParams.leftMargin = layoutParams.rightMargin = layoutParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_top_margin);
        infoParams.height = getResources().getDimensionPixelOffset(R.dimen.style_button_height);
        infoParams.gravity = Gravity.CENTER;

        TextView nameText = new TextView(getContext());
        SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName", null);
        role = preferences.getString("role", "学生");
        LayoutParams nameParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        nameParams.gravity = Gravity.CENTER;
        nameParams.weight = 1;
        nameParams.leftMargin = layoutParams.leftMargin;
        nameText.setGravity(Gravity.CENTER);
        if (userName != null)
            nameText.setText("当前用户:" + userName);

        roleText = new TextView(getContext());
        roleText.setText(role);
        roleText.setGravity(Gravity.CENTER);
        LayoutParams roleParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        roleParams.gravity = Gravity.CENTER;
        roleParams.weight = 1;

        roleParams.rightMargin = layoutParams.leftMargin;
        ImageView icon = new ImageView(getContext());
        icon.setImageResource(R.drawable.user);
        int width = getResources().getDimensionPixelOffset(R.dimen.icon_height);
        LayoutParams iconParams = new LayoutParams(width, width);
        iconParams.gravity = Gravity.CENTER;
        iconParams.weight = 1;
        infoLayout.addView(icon, iconParams);
        infoLayout.addView(nameText, nameParams);
        infoLayout.addView(roleText, roleParams);
        roleText.setOnClickListener(this);
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

        StyleButton exit = new StyleButton(context);
        exit.setText("退出");
        exit.setAlpha(1.0f);
        exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("退出登录");
                String msg = "是否确定退出登录?";
                builder.setMessage(msg);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        aboutDialog.dismiss();
                        doExit();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                aboutDialog = builder.create();
                aboutDialog.show();
            }
        });


        addView(infoLayout, infoParams);
        addView(myInfo, layoutParams);
        addView(about, layoutParams);
        addView(exit, layoutParams);
    }

    private void doExit() {
        SharedPreferences preference = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        preference.edit().clear().commit();
        Intent intent = new Intent(getContext(), FirstActivity.class);
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();
    }

    private String[] roles = new String[]{"学生", "老师"};

    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("选择角色");
        builder.setMultiChoiceItems(roles, getChecked(), new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                setRole(i);
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    private void setRole(int position) {
        SharedPreferences preference = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        preference.edit().putString("role", roles[position]).commit();
        role = roles[position];
        roleText.setText(role);
    }

    private boolean[] getChecked() {
        boolean[] checked = new boolean[roles.length];
        for (int i = 0; i < roles.length; i++) {
            if (roles[i].equals(role))
                checked[i] = true;
        }
        return checked;
    }
}
