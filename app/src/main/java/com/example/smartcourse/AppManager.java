package com.example.smartcourse;

import android.content.Context;

public class AppManager
{
	private static AppManager appManager;
	private Context context;

	public static AppManager getInstance()
	{
		if (appManager == null)
			appManager = new AppManager();
		return appManager;
	}

	public void setContext(Context context)
	{
		this.context = context;
	}

	public Context getContext()
	{
		return this.context;
	}
}
