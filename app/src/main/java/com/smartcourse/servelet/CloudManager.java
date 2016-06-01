package com.smartcourse.servelet;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.smartcourse.AppManager;
import com.smartcourse.bean.CloudSaveBean;
import com.smartcourse.bean.CourseItem;
import com.smartcourse.utils.UserUtil;

public class CloudManager {
    private static CloudManager cloudManager;

    public static CloudManager getInstance() {
        if (cloudManager == null)
            cloudManager = new CloudManager();
        return cloudManager;
    }

    private CloudManager() {
        AVOSCloud.initialize(AppManager.getInstance().getContext(), "70i9lkmu9ei6gyczxoroq2m75razvgnpy1l0r2mqspnjazeg", "2f6861jutfonafsvwzx4zu2p8peuiizg185apkamte6s50e2");
    }

    public void saveToCloud(CloudSaveBean cloudSaveBean, SaveCallback callback) {
        AVObject avObject = new AVObject("CourseInfo");
        avObject.put("userClass", cloudSaveBean.getUserClass());
        avObject.put("courseName", cloudSaveBean.getCourseName());
        avObject.put("userPro", cloudSaveBean.getUserPro());
        avObject.put("teacher", cloudSaveBean.getTeacher());
        avObject.put("info", cloudSaveBean.getInfo());
        avObject.put("userName", cloudSaveBean.getUserName());
        avObject.put("role", UserUtil.getRole());
        avObject.saveInBackground(callback);
    }

    public void getInfoFromCloud(CloudSaveBean cloudSaveBean, FindCallback findCallback) {
        AVQuery<AVObject> avQuery = new AVQuery<AVObject>("CourseInfo");
        avQuery.whereEqualTo("userClass", cloudSaveBean.getUserClass());
        avQuery.whereEqualTo("courseName", cloudSaveBean.getCourseName());
        avQuery.whereEqualTo("userPro", cloudSaveBean.getUserPro());
        avQuery.findInBackground(findCallback);
    }
}
