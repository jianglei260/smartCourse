package com.smartcourse.activity;

import com.smartcourse.bean.CourseItem;
import com.smartcourse.view.CourseDetialView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class CourseDetialActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Bundle bundle = getIntent().getExtras();

		CourseItem course = new CourseItem(bundle.getString("name"), bundle.getString("teacher"), bundle.getString("classRoom"), bundle.getString("hour"), bundle.getInt("startWeek"),
				bundle.getInt("endWeek"), bundle.getInt("weekday"), bundle.getInt("flag"));
		setContentView(new CourseDetialView(this, course));
	}
}
