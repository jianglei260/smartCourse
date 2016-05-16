package com.smartcourse.view;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class ViewBaseAdapter<T> extends BaseAdapter
{
	protected List<T> lists;
	public ViewBaseAdapter(List<T> lists)
	{
		this.lists=lists;
	}
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public T getItem(int position)
	{
		// TODO Auto-generated method stub
		return lists.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public abstract View  getView(int position, View convertView, ViewGroup parent);

}
