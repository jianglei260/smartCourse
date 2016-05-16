package com.smartcourse.view;

import com.example.smartcourse.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class MyListView extends LinearLayout implements View.OnClickListener
{
	private Context context;
	private LayoutParams layoutParams;
	private OnItemClickListener clickListener;
	private Adapter adapter;

	public MyListView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initUI();
	}

	private void initUI()
	{
		setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_item_top_margin);
		layoutParams.rightMargin = layoutParams.leftMargin;
		layoutParams.topMargin = layoutParams.leftMargin;
	}

	public void setAdapter(BaseAdapter adapter)
	{
		this.adapter = adapter;
		int size = adapter.getCount();
		View convertView = null;
		for (int i = 0; i < size; i++)
		{
			addView(adapter.getView(i, convertView, (ViewGroup) getRootView()), layoutParams);
		}
	}

	public void setOnItemClickListener(OnItemClickListener clickListener)
	{
		int count = getChildCount();
		this.clickListener = clickListener;
		for (int i = 0; i < count; i++)
		{
			View itemView = getChildAt(i);
			itemView.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub

		clickListener.onItemClick(this.indexOfChild(v));
	}

	public interface OnItemClickListener
	{
		public void onItemClick(int position);
	}
}
