package com.smartcourse.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smartcourse.bean.CourseItem;
import com.smartcourse.db.DBHelper;
import com.smartcourse.utils.DateUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseReader {
    Context context;
    Calendar calendar;

    public CourseReader(Context context) {
        this.context = context;
        calendar = Calendar.getInstance();
    }

    public List<CourseItem> getTodayCourse() {
        DBHelper helper = DBHelper.getInstance(context);
        Cursor c = helper.query(DBHelper.COURSE_TBL_NAME);
        List<CourseItem> courseList = new ArrayList<CourseItem>();
        CourseItem courseItem;
        DateUtil dateUtil = new DateUtil();
        int week = dateUtil.getWeek(context);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int weekdayCol = c.getColumnIndex("weekday");
            if (c.getInt(weekdayCol) == (weekday - 1)) {
                int startWeekCol = c.getColumnIndex("startweek");
                int endWeekCol = c.getColumnIndex("endweek");
                if (c.getInt(startWeekCol) <= week && c.getInt(endWeekCol) >= week) {
                    courseItem = new CourseItem();
                    int nameCol = c.getColumnIndex("name");
                    int teacherCol = c.getColumnIndex("teacher");
                    int classRoomCol = c.getColumnIndex("classRoom");
                    int hourCol = c.getColumnIndex("hour");
                    int flagCol = c.getColumnIndex("flag");
                    int id = c.getColumnIndex("_id");
                    courseItem.setClassRoom(c.getString(classRoomCol));
                    String hour = String.valueOf(2 * (c.getInt(hourCol)) - 1) + String.valueOf(2 * (c.getInt(hourCol)));
                    courseItem.setHour(hour);
                    courseItem.setEndWeek(c.getInt(endWeekCol));
                    courseItem.setSatrtWeek(c.getInt(startWeekCol));
                    courseItem.setName(c.getString(nameCol));
                    courseItem.setTeacher(c.getString(teacherCol));
                    courseItem.setWeekday(c.getInt(weekdayCol));
                    courseItem.setFlag(c.getInt(flagCol));
                    courseItem.setId(c.getInt(id));
                    courseList.add(courseItem);
                }
            }

        }
        c.close();
        return courseList;

    }

    public List<CourseItem> getAllCourse() {
        DBHelper helper = DBHelper.getInstance(context);
        SQLiteDatabase database = helper.getReadableDatabase();
        List<CourseItem> list = new ArrayList<CourseItem>();
        CourseItem courseItem;
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 4; j++) {
                Cursor c = database.rawQuery("SELECT * FROM coursetb where weekday = ? and hour = ?", new String[]{String.valueOf(i), String.valueOf(j)});

                if (c.getCount() < 1) {
                    list.add(null);
                } else {
                    c.moveToFirst();
                    int weekdayCol = c.getColumnIndex("weekday");

                    int startWeekCol = c.getColumnIndex("startweek");
                    int endWeekCol = c.getColumnIndex("endweek");
                    courseItem = new CourseItem();
                    int nameCol = c.getColumnIndex("name");
                    int teacherCol = c.getColumnIndex("teacher");
                    int classRoomCol = c.getColumnIndex("classRoom");
                    int hourCol = c.getColumnIndex("hour");
                    int flagCol = c.getColumnIndex("flag");
                    String classroom = c.getString(classRoomCol);
                    courseItem.setClassRoom(classroom);
                    String hour = String.valueOf(2 * (c.getInt(hourCol)) - 1) + String.valueOf(2 * (c.getInt(hourCol)));
                    courseItem.setHour(hour);
                    courseItem.setEndWeek(c.getInt(endWeekCol));
                    courseItem.setSatrtWeek(c.getInt(startWeekCol));
                    courseItem.setName(c.getString(nameCol));
                    courseItem.setTeacher(c.getString(teacherCol));
                    courseItem.setWeekday(c.getInt(weekdayCol));
                    courseItem.setFlag(c.getInt(flagCol));
                    list.add(courseItem);

                }

            }

        }

        return list;
    }

}
