package com.smartcourse.utils;

import java.util.Calendar;

import com.smartcourse.net.HttpRequestManager;
import com.smartcourse.net.HttpRequestTask;
import com.smartcourse.net.HttpTaskListenner;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class DateUtil
{
	private String url = "http://jwc.cuit.edu.cn/";
	private String startChar = "第<span style=\"font-size:20pt;color:#FF0000\">";

	public int getWeek(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
		int diffWeek = getdiffWeek(context);
		Calendar calendar = Calendar.getInstance();
		int normalWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		if (diffWeek > 0)
		{
			return normalWeek - diffWeek;
		}
		return -1;
	}

	public int getdiffWeek(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
		if (sharedPreferences.getBoolean("catched", false) == false)
		{
			getWeekFromInternet(context);
			return -1;
		}
		return sharedPreferences.getInt("diffWeek", -1);
	}

	public void setWeek(int week, Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("date", Context.MODE_PRIVATE);
		sharedPreferences.edit().putInt("week", week).commit();
		Calendar calendar = Calendar.getInstance();
		int normalWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		sharedPreferences.edit().putBoolean("catched", true).commit();
		sharedPreferences.edit().putInt("normalWeek", normalWeek).commit();
		sharedPreferences.edit().putInt("diffWeek", normalWeek - week).commit();
	}

	public void getWeekFromInternet(final Context context)
	{
		HttpRequestTask httpRequestTask = new HttpRequestTask(url, new HttpTaskListenner()
		{

			@Override
			public void onSuccess(String result)
			{
				// TODO Auto-generated method stub
				int start = result.indexOf(startChar) + startChar.length();
				String week = result.substring(start, start + 2);
				Log.d("week", result);
				setWeek(Integer.valueOf(week), context);
			}

			@Override
			public void onFailed()
			{
				// TODO Auto-generated method stub
				Log.d("week", "error");
				Toast.makeText(context, "获取星期数失败", Toast.LENGTH_SHORT);
			}
		});
		HttpRequestManager.request(httpRequestTask);
	}
}
