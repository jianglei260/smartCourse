package com.example.smartcourse;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.smartcourse.activity.MainActivity;


import android.content.Context;
import android.os.Bundle;
import android.os.Message;

public class Location {
	Context context;
	LocationClientOption option;
	public LocationClient mLocationClient;
	public MyLocationListener mLocationListener=new MyLocationListener();
	public Location(Context context)
	{
		this.context=context;
	}
	public void getPosition(){
		mLocationClient=new LocationClient(context);
		option=new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setScanSpan(2000);
		option.setCoorType("bd09ll");
		option.setIsNeedAddress(true);
		option.setNeedDeviceDirect(true);
		mLocationClient.registerLocationListener(mLocationListener);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		if(mLocationClient!=null&&mLocationClient.isStarted())
			mLocationClient.requestLocation();
		
	}
	class MyLocationListener implements BDLocationListener{

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			Double latitude,longtitude;
			latitude=location.getLatitude();
			longtitude=location.getLongitude();

			Message msg=new Message();
			Bundle b=new Bundle();
			b.putDouble("latitude", latitude);
			b.putDouble("longtitude", longtitude);
			msg.setData(b);
			MainActivity.handler.sendMessage(msg);
			}
		}
}
