package com.smartcourse.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.smartcourse.MyApplication;

/**
 * Created by jianglei on 16/6/1.
 */
public class UserUtil {
    public static String getUserName() {
        SharedPreferences preferences = MyApplication.getMyApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName", null);
        return userName;
    }

    public static String getRole() {
        SharedPreferences preferences = MyApplication.getMyApplication().getSharedPreferences("user", Context.MODE_PRIVATE);
        String role = preferences.getString("role", null);
        return role;
    }
}
