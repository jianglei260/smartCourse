package com.smartcourse.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.smartcourse.AlarmTaskManager;
import com.example.smartcourse.AppManager;
import com.example.smartcourse.Location;
import com.example.smartcourse.R;
import com.example.smartcourse.R.id;
import com.example.smartcourse.R.layout;
import com.example.smartcourse.R.menu;
import com.example.smartcourse.TaskService;
import com.example.smartcourse.TaskService.TaskServiceBinder;
import com.smartcourse.bean.CourseItem;
import com.smartcourse.parser.CourseReader;
import com.smartcourse.task.Task;
import com.smartcourse.utils.DateUtil;
import com.smartcourse.utils.TimeUtil;
import com.smartcourse.view.MyCourseView;
import com.smartcourse.view.TodayListView;
import com.smartcourse.view.ViewBaseAdapter;
import com.smartcourse.view.WeekListView;
import com.smartcourse.view.WeekView;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private TextView todayList, weekList, me, todayClass;
	private ViewPager viewPager;
	private List<View> pageList;
	public static Handler handler;
	private int last;
	private TodayListView todayListView;
	private Context context;
	private List<CourseItem> courseList;
	private List<CourseItem> todayCourseList;
	private int textColor, textSelectedColor;
	private TaskService service;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		context = this;
		AppManager.getInstance().setContext(context);

		CourseReader parser = new CourseReader(getApplicationContext());
		todayCourseList = parser.getTodayCourse();
		courseList = parser.getAllCourse();

		textColor = getResources().getColor(R.color.textColor);
		textSelectedColor = getResources().getColor(R.color.blue);

		initUI();
		initViewPager();

	}

	private void initViewPager()
	{
		// TODO Auto-generated method stub
		viewPager.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int arg0)
			{
				// TODO Auto-generated method stub
				switch (arg0)
				{
				case 0:
					todayList.setTextColor(textSelectedColor);
					break;
				case 1:
					weekList.setTextColor(textSelectedColor);
					break;
				case 2:
					me.setTextColor(textSelectedColor);
					break;
				}
				switch (last)
				{
				case 0:
					todayList.setTextColor(textColor);
					break;
				case 1:
					weekList.setTextColor(textColor);
					break;
				case 2:
					me.setTextColor(textColor);
					break;
				}

				last = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub

			}
		});

	}

	public void initUI()
	{
		Typeface iconfont = Typeface.createFromAsset(getAssets(), "ionicons.ttf");
		todayList = (TextView) findViewById(R.id.todayList);
		weekList = (TextView) findViewById(R.id.weekList);
		me = (TextView) findViewById(R.id.me);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		todayList.setTypeface(iconfont);
		weekList.setTypeface(iconfont);
		me.setTypeface(iconfont);
		ColorStateList color = weekList.getTextColors();

		pageList = new ArrayList<View>();

		todayListView = new TodayListView(context, todayCourseList);
		pageList.add(todayListView);

		WeekView weekView = new WeekView(context, courseList);
		pageList.add(weekView);

		MyCourseView myCourseView = new MyCourseView(context);
		pageList.add(myCourseView);
		viewPager.setAdapter(new MyPagerAdapter(pageList));
		viewPager.setCurrentItem(0);
		todayList.setOnClickListener(new MyOnclickListener(0));
		weekList.setOnClickListener(new MyOnclickListener(1));
		me.setOnClickListener(new MyOnclickListener(2));
		todayList.setTextColor(textSelectedColor);
		weekList.setTextColor(textColor);
		me.setTextColor(textColor);
	}

	class MyOnclickListener implements View.OnClickListener
	{
		int status;

		public MyOnclickListener(int status)
		{
			this.status = status;

		}

		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(status);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_settings:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), AddCourse.class);
			startActivity(intent);
			break;

		}
		return super.onOptionsItemSelected(item);

	}

	class MyPagerAdapter extends PagerAdapter
	{
		List<View> mViewList;

		public MyPagerAdapter(List<View> viewList)
		{
			mViewList = viewList;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return mViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			// TODO Auto-generated method stub
			return arg0 == (arg1);
		}

		public void destroyItem(View arg0, int arg1, Object arg2)
		{
			((ViewPager) arg0).removeView(mViewList.get(arg1));
		}

		public Object instantiateItem(View arg0, int arg1)
		{
			((ViewPager) arg0).addView(mViewList.get(arg1), 0);

			return mViewList.get(arg1);

		}

	}

	public void onStop()
	{
		super.onStop();
		// System.exit(0);
	}
}
