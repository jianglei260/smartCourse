package com.smartcourse.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.example.smartcourse.R;
import com.smartcourse.view.SelectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ConfigActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private FrameLayout container;
    private SelectView[] views;
    private int current = 0;
    private float width;
    private String area, depart, pro, grade, mClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        container = (FrameLayout) findViewById(R.id.container);
        width = getResources().getDisplayMetrics().widthPixels;
        views = new SelectView[5];

        SelectView areaSelectView = new SelectView(this);
        areaSelectView.setTitle("选择校区");
        areaSelectView.setDataList(getStringList(R.array.area));
        views[0] = areaSelectView;

        SelectView departSelectView = new SelectView(this);
        departSelectView.setTitle("选择学院");
        views[1] = departSelectView;

        SelectView proSelectView = new SelectView(this);
        proSelectView.setTitle("选择专业");
        views[2] = proSelectView;

        SelectView gradeSelectView = new SelectView(this);
        gradeSelectView.setTitle("选择年级");
        views[3] = gradeSelectView;

        SelectView classSelectView = new SelectView(this);
        classSelectView.setTitle("选择班级");
        views[4] = classSelectView;
        container.addView(views[0]);
        configSelectView();
    }

    private List<String> getStringList(int id) {
        String[] strings = getResources().getStringArray(id);
        List<String> list = new ArrayList<>(strings.length);
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    private void configSelectView() {
        for (SelectView view : views) {
            view.setBackClickListener(this);
            view.setListItemClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if (current >= 1) {
            closeAnimator(views[current], views[current - 1]);
            container.removeView(views[current]);
            current--;
        } else {
            onBackPressed();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (current) {
            case 0:
                if (i == 0) {
                    views[1].setDataList(getStringList(R.array.depart_native));
                } else {
                    views[1].setDataList(getStringList(R.array.depart));
                }
                area = ((Map<String,String>) adapterView.getAdapter().getItem(i)).get("name");
                break;
            case 1:
                views[2].setDataList(getProList(i));
                depart = ((Map<String,String>) adapterView.getAdapter().getItem(i)).get("name");
                break;
            case 2:
                views[3].setDataList(getStringList(R.array.grade));
                pro =((Map<String,String>) adapterView.getAdapter().getItem(i)).get("name");
                break;
            case 3:
                views[4].setDataList(getStringList(R.array.mclass));
                grade = ((Map<String,String>) adapterView.getAdapter().getItem(i)).get("name");
                break;
            case 4:
                mClass = ((Map<String,String>) adapterView.getAdapter().getItem(i)).get("name");
                break;
        }
        loadNext();

    }

    private List<String> getProList(int index) {
        List<String> proList = new ArrayList<>();
        NativePro nativePro = new NativePro();
        switch (index) {
            case 0:
                proList.clear();
                for (int i = 0; i < nativePro.str0.length; i++) {
                    proList.add(nativePro.str0[i]);
                }
                break;
            case 1:
                proList.clear();
                for (int i = 0; i < nativePro.str1.length; i++) {
                    proList.add(nativePro.str1[i]);
                }
                break;
            case 2:
                proList.clear();
                for (int i = 0; i < nativePro.str2.length; i++) {
                    proList.add(nativePro.str2[i]);
                }
                break;
            case 3:
                proList.clear();
                for (int i = 0; i < nativePro.str3.length; i++) {
                    proList.add(nativePro.str3[i]);
                }
                break;
            case 4:
                proList.clear();
                for (int i = 0; i < nativePro.str4.length; i++) {
                    proList.add(nativePro.str4[i]);
                }
                break;
            case 5:
                proList.clear();
                for (int i = 0; i < nativePro.str5.length; i++) {
                    proList.add(nativePro.str5[i]);
                }
                break;
            case 6:
                proList.clear();
                for (int i = 0; i < nativePro.str6.length; i++) {
                    proList.add(nativePro.str6[i]);
                }
                break;
            case 7:
                proList.clear();
                for (int i = 0; i < nativePro.str7.length; i++) {
                    proList.add(nativePro.str7[i]);
                }
                break;
            case 8:
                proList.clear();
                for (int i = 0; i < nativePro.str8.length; i++) {
                    proList.add(nativePro.str8[i]);
                }
                break;
            case 9:
                proList.clear();
                for (int i = 0; i < nativePro.str9.length; i++) {
                    proList.add(nativePro.str9[i]);
                }
                break;
            case 10:
                proList.clear();
                for (int i = 0; i < nativePro.str10.length; i++) {
                    proList.add(nativePro.str10[i]);
                }
                break;
            case 11:
                proList.clear();
                for (int i = 0; i < nativePro.str11.length; i++) {
                    proList.add(nativePro.str11[i]);
                }
                break;
            case 12:
                proList.clear();
                for (int i = 0; i < nativePro.str12.length; i++) {
                    proList.add(nativePro.str12[i]);
                }
                break;
            case 13:
                proList.clear();
                for (int i = 0; i < nativePro.str13.length; i++) {
                    proList.add(nativePro.str13[i]);
                }
                break;
            case 14:
                proList.clear();
                for (int i = 0; i < nativePro.str14.length; i++) {
                    proList.add(nativePro.str14[i]);
                }
                break;
        }
        return proList;

    }

    private void loadNext() {
        if (current < 4) {
            openAnimator(views[current], views[++current]);
            container.addView(views[current]);
        } else {
            Intent intent = new Intent(this, ConfirmActivity.class);
            intent.putExtra("params", new String[]{area, depart, pro, grade, mClass});
            startActivity(intent);
        }

    }

    private void openAnimator(View currentview, View nextView) {
        ObjectAnimator openAnimator1 = ObjectAnimator.ofFloat(currentview, "translationX", 0, -width);
        ObjectAnimator openAnimator2 = ObjectAnimator.ofFloat(nextView, "translationX", width, 0);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(300);
        animationSet.play(openAnimator1).with(openAnimator2);
        animationSet.start();
    }

    private void closeAnimator(View currentView, View nextView) {
        ObjectAnimator closeAnimator1 = ObjectAnimator.ofFloat(currentView, "translationX", 0, width);
        ObjectAnimator closeAnimator2 = ObjectAnimator.ofFloat(nextView, "translationX", -width, 0);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.setDuration(300);
        animationSet.play(closeAnimator1).with(closeAnimator2);
        animationSet.start();
    }

}
