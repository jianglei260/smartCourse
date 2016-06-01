package com.smartcourse.utils;

import java.util.Calendar;

import com.smartcourse.net.HttpRequestManager;
import com.smartcourse.net.HttpRequestTask;
import com.smartcourse.net.HttpTaskListenner;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class DateUtil {
    private String url = "http://jwc.cuit.edu.cn/";

    public int getWeek(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
        int diffWeek = getdiffWeek(context);
        Calendar calendar = Calendar.getInstance();
        int normalWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        if (diffWeek > 0) {
            return normalWeek - diffWeek;

        }
        return -1;
    }

    public int getdiffWeek(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
        if (Calendar.getInstance().get(Calendar.MONTH) > 9) {
            getWeekFromInternet(context);
            return -1;
        }

        if (sharedPreferences.getBoolean("catched", false) == false) {
            getWeekFromInternet(context);
            return -1;
        }
        return sharedPreferences.getInt("configWeek", 0) - sharedPreferences.getInt("schoolWeek", 0);
    }

    public void setWeek(int week, Context context, boolean config) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("week", week).commit();
        Calendar calendar = Calendar.getInstance();
        int normalWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        sharedPreferences.edit().putBoolean("catched", config).commit();
        sharedPreferences.edit().putInt("configWeek", normalWeek).commit();
        sharedPreferences.edit().putInt("schoolWeek", week).commit();
    }

    public void getWeekFromInternet(final Context context) {
        HttpRequestTask httpRequestTask = new HttpRequestTask(url, new HttpTaskListenner() {

            @Override
            public void onSuccess(String result) {
                // TODO Auto-generated method stub
                Document document = Jsoup.parse(result);
                Element element = document.getElementById("date_head");
                Element href = element.getAllElements().first();
                String text = href.text();
                String week = text.substring(text.indexOf("第") + 1, text.indexOf("周", 3));
                Log.d("week", week);
                int weekNumber = 0;
                try {
                    weekNumber = Integer.valueOf(week);
                } catch (Exception e) {
                    weekNumber = 0;
                }

                setWeek(weekNumber, context, weekNumber == 0 ? false : true);
            }

            @Override
            public void onFailed() {
                // TODO Auto-generated method stub
                Log.d("week", "error");
                Toast.makeText(context, "获取星期数失败", Toast.LENGTH_SHORT);
            }
        });
        HttpRequestManager.request(httpRequestTask);
    }
}
