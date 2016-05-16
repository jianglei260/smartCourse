package com.smartcourse.utils;

import java.util.Calendar;

import com.smartcourse.bean.ScreenBean;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

public class TimeUtil {
    private Context context;
    private int screenWidth, screenHeight;
    private WindowManager windowManager;
    private Calendar calendar;
    private int hour;
    private int min;
    private int weekday;

    public TimeUtil(Context context) {
        this.context = context;
        windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
        calendar = Calendar.getInstance();

        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        weekday = calendar.get(Calendar.DAY_OF_WEEK);
    }

    public ScreenBean getLocation() {
        ScreenBean screenBean = new ScreenBean();
        if (hour > 12)
            hour = hour - 2;
        float col = (float) (weekday - 1) / 5;
        float row = (float) (hour - 8) / 8;
        float x = col * (float) screenWidth - 50;
        float y = row * (float) (screenHeight) + 40;

        screenBean.setX((int) x);
        screenBean.setY((int) y);
        return screenBean;
    }

    public int getPassedIndex() {
        int index = 0;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        weekday = calendar.get(Calendar.DAY_OF_WEEK);
        int value = 0;

        if (hour > 18)
            value = 4;
        else if (hour > 16)
            value = 3;
        else if (hour > 12)
            value = 2;
        else if (value > 10)
            value = 1;
        index = (weekday - 2) * 4 + value;
        Log.d("passed index", "" + index);
        return index - 1;
    }

    public int getIndex() {
        int index = 0;
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        weekday = calendar.get(Calendar.DAY_OF_WEEK);

        int value = 0;
        if (hour >= 8 && hour < 10)
            value = 1;
        else if (hour > 10 && hour < 12)
            value = 2;
        else if (hour > 14 && hour < 16)
            value = 3;
        else if (hour > 16)
            value = 4;
        index = (weekday - 2) * 4 + value;
        Log.d("now index", "" + index);
        return index - 1;

    }

}
