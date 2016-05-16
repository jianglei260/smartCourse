package com.example.smartcourse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.smartcourse.activity.MainActivity;
import com.smartcourse.bean.CourseItem;

/**
 * Created by ASUS-1 on 2015/9/8.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        CourseItem courseItem = new CourseItem(bundle.getString("name"), bundle.getString("teacher"), bundle.getString("classRoom"), bundle.getString("hour"), bundle.getInt("startWeek"),
                bundle.getInt("endWeek"), bundle.getInt("weekday"), bundle.getInt("flag"));
        Log.d("alarm start", courseItem.getName());
        notify(context, courseItem);
    }

    public void notify(Context context, CourseItem courseItem) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_course_logo, "今天的课程", System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.vibrate = new long[]{1000, 500};
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(context, "今天第" + courseItem.getHour() + "节的课程", "课程：" + courseItem.getName() + "教室：" + courseItem.getClassRoom(), pendingIntent);
        notificationManager.notify(Integer.parseInt(courseItem.getHour()), notification);
        Log.d("alarm notify", courseItem.getName());
    }
}
