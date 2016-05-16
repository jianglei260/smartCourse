package com.example.smartcourse;

import com.smartcourse.task.Task;
import com.smartcourse.task.TaskExecutor;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TaskService extends Service {
    private IBinder binder = new TaskServiceBinder();
    private TaskExecutor executor;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("service", "onBind");
        AlarmTaskManager.getInstance().setTodayAlarm(this);
        return binder;
    }

    public void executeTask(Task task) {
        executor.addTask(task);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        executor = TaskExecutor.getInstance();
        Log.d("service", "create");
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.d("service", "onDestroy");
    }

    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
        Log.d("service", "onLowMemory");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        MyApplication.getMyApplication().setTaskService(this);
        Log.d("service", "onStart");
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // TODO Auto-generated method stub
        Log.d("service", "onUnbind");
        return super.onUnbind(intent);
    }

    public class TaskServiceBinder extends Binder {
        public TaskService getTaskService() {
            return TaskService.this;
        }
    }

}
