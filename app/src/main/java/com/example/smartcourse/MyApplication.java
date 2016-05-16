package com.example.smartcourse;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by ASUS-1 on 2015/9/8.
 */
public class MyApplication extends Application {
    private TaskService taskService;
    private static MyApplication myApplication;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            taskService = ((TaskService.TaskServiceBinder) service).getTaskService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            taskService = null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        Intent intent = new Intent(this, TaskService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unbindService(serviceConnection);
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

}
