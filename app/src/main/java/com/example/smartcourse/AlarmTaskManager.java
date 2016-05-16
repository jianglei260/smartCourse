package com.example.smartcourse;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.smartcourse.activity.AddCourse;
import com.smartcourse.bean.CourseItem;
import com.smartcourse.parser.CourseReader;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlarmTaskManager {
    private static AlarmTaskManager alarmTaskManager;
    private List<CourseItem> todayList;
    private Context context;

    public static AlarmTaskManager getInstance() {
        if (alarmTaskManager == null)
            alarmTaskManager = new AlarmTaskManager();
        return alarmTaskManager;
    }

    private AlarmTaskManager() {

    }

    public void setTodayAlarm(Context context) {
        this.context = context;
        CourseReader courseReader = new CourseReader(context);
        todayList = courseReader.getTodayCourse();
        Calendar calendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        for (CourseItem courseItem : todayList) {
            int hour = Integer.parseInt(String.valueOf(courseItem.getHour().charAt(0)));
            Intent intent = new Intent(context, AlarmReceiver.class);
            switch (hour) {
                case 1:
                    calendar.set(Calendar.HOUR_OF_DAY, 8);
                    calendar.set(Calendar.MINUTE, 5);
                    calendar.set(Calendar.SECOND, 0);
                    intent.setAction("com.example.smartcourse.action.ALARM_FIRST_STARTED");
                    bindIntent(intent, courseItem);
                    setAlarm(calendar, courseItem, intent);
                    break;
                case 3:
                    calendar.set(Calendar.HOUR_OF_DAY, 10);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    intent.setAction("com.example.smartcourse.action.ALARM_SECONED_STARTED");
                    bindIntent(intent, courseItem);
                    setAlarm(calendar, courseItem, intent);
                    break;
                case 5:
                    calendar.set(Calendar.HOUR_OF_DAY, 13);
                    calendar.set(Calendar.MINUTE, 45);
                    calendar.set(Calendar.SECOND, 0);
                    intent.setAction("com.example.smartcourse.action.ALARM_THIRD_STARTED");
                    bindIntent(intent, courseItem);
                    setAlarm(calendar, courseItem, intent);
                    break;
                case 7:
                    calendar.set(Calendar.HOUR_OF_DAY, 15);
                    calendar.set(Calendar.MINUTE, 40);
                    calendar.set(Calendar.SECOND, 0);
                    intent.setAction("com.example.smartcourse.action.ALARM_FOURTH_STARTED");
                    bindIntent(intent, courseItem);
                    setAlarm(calendar, courseItem, intent);
                    break;
            }
        }
    }

    private void bindIntent(Intent intent, CourseItem courseItem) {
        Bundle bundle = new Bundle();
        bundle.putString("name", courseItem.getName());
        bundle.putString("teacher", courseItem.getTeacher());
        bundle.putString("classRoom", courseItem.getClassRoom());
        bundle.putString("hour", courseItem.getHour());
        bundle.putInt("satrtWeek", courseItem.getSatrtWeek());
        bundle.putInt("endWeek", courseItem.getEndWeek());
        bundle.putInt("weekday", courseItem.getWeekday());
        bundle.putInt("flag", courseItem.getFlag());
        intent.putExtras(bundle);
    }

    public void setAlarm(Calendar calendar, CourseItem courseItem, Intent intent) {
        if ((calendar.getTimeInMillis() + 30 * 60 * 1000) >= System.currentTimeMillis()) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Log.d("alarm seted", courseItem.getName());
        }
    }
}
