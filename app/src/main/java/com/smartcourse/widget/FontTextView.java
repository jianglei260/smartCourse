package com.smartcourse.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontTextView extends TextView
{
	Context context;
	Typeface fontIcon;
	public FontTextView(Context context,AttributeSet attrs)
	{
		super(context,attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}

	@Override
	public void setTypeface(Typeface tf)
	{
		// TODO Auto-generated method stub
		super.setTypeface(tf);
	}

	private void init()
	{
		// TODO Auto-generated method stub
		fontIcon=Typeface.createFromAsset(context.getAssets(), "ionicons.ttf");
		setTypeface(fontIcon);
	}

}
