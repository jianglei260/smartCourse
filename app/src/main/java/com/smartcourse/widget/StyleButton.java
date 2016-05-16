package com.smartcourse.widget;

import com.example.smartcourse.R;

import android.content.Context;
import android.widget.Button;

public class StyleButton extends Button
{
	private Context context;

	public StyleButton(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	private void init()
	{
		// TODO Auto-generated method stub
		setBackgroundResource(R.drawable.itemselected);
//		getLayoutParams().width=context.getResources().getDimensionPixelSize(R.dimen.style_button_widght);
//		getLayoutParams().height=context.getResources().getDimensionPixelSize(R.dimen.style_button_height);
	}
}
