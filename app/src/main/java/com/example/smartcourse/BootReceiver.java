package com.example.smartcourse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ASUS-1 on 2015/9/8.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (MyApplication.getMyApplication().getTaskService() == null) {
            Intent serviceIntent = new Intent(context, TaskService.class);
            context.startService(serviceIntent);
        }
    }
}
