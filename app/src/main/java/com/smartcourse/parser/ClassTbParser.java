package com.smartcourse.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.smartcourse.AppManager;
import com.smartcourse.db.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

public class ClassTbParser {
    Context context;
    DBHelper db;

    public ClassTbParser(Context context) {
        this.context = context;
    }

    public void parse(String src) {
        Document doc = Jsoup.parse(src);
        db = DBHelper.getInstance(context);
        db.delAll(DBHelper.COURSE_TBL_NAME);
        Elements trs = doc.select("tr");
        int startWeek, endWeek;
        for (int i = 3; i < trs.size() - 2; i++) {
            Element tr = trs.get(i);
            Elements tds = tr.select("td");
            for (int j = 1; j <= tds.size(); j++) {
                Element td = tds.get(j - 1);
                ContentValues values = new ContentValues();
                String value = td.toString();
                value = value.substring(value.indexOf(">") + 1, value.indexOf("</td>"));
                if (value.contains("&nbsp;")) {

                } else if (value.contains("节课")) {

                } else {
                    while (value.contains("<br />")) {
                        if (value.substring(0, value.indexOf("<br />")).contains("单")) {
                            values.put("flag", 1);
                            value = value.substring(value.indexOf("<br />") + 6, value.length());
                        } else if (value.substring(0, value.indexOf("<br />")).contains("双")) {
                            values.put("flag", 2);
                            value = value.substring(value.indexOf("<br />") + 6, value.length());
                        } else {
                            values.put("flag", 0);
                        }
                        values.put("hour", i - 2);
                        values.put("weekday", j - 1);
                        value = value.substring(value.indexOf("<br />") + 6, value.length());

                        String time = value.substring(0, value.indexOf("<br />"));
                        time = time.replace(" ", "");
                        startWeek = Integer.parseInt(time.substring(0, time.indexOf("--")));
                        endWeek = Integer.parseInt(time.substring(time.indexOf("--") + 2, time.indexOf("周")));
                        values.put("startweek", startWeek);
                        values.put("endWeek", endWeek);

                        value = value.substring(value.indexOf("<br />") + 6, value.length());
                        values.put("teacher", value.substring(0, value.indexOf("<br />")));
                        value = value.substring(value.indexOf("<br />") + 6, value.length());
                        if (value.contains("<br />")) {
                            values.put("classRoom", value.substring(0, value.indexOf("<br />")));
                            value = value.substring(value.indexOf("<br />") + 6, value.length());
                        } else {
                            values.put("classRoom", value.substring(0, value.length()));
                        }
                        db.insert(values, DBHelper.COURSE_TBL_NAME);
                    }

                }

            }
        }
    }
}
