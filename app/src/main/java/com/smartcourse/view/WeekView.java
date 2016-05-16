package com.smartcourse.view;

import java.util.List;

import com.example.smartcourse.R;
import com.smartcourse.bean.CourseItem;
import com.smartcourse.bean.ScreenBean;
import com.smartcourse.utils.TimeUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class WeekView extends LinearLayout {
    private Context context;
    private List<CourseItem> list;
    private Paint paint;
    private TimeUtil timeUtil;

    public WeekView(Context context, List<CourseItem> list) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Style.FILL);
        timeUtil = new TimeUtil(context);
        initUI();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.dispatchDraw(canvas);

        ScreenBean screenBean = timeUtil.getLocation();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);

        int index = timeUtil.getIndex();


        canvas.drawBitmap(bitmap, screenBean.getX(), screenBean.getY(), paint);
    }


    private void initUI() {
        // TODO Auto-generated method stub

        setOrientation(VERTICAL);
        TitleBarView titleBarView = new TitleBarView(context);
        titleBarView.setTitle("所有课程");
        titleBarView.setLeftImageVisible(false);
        titleBarView.setRightImageVisible(false);

        LayoutParams tilteBarLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.title_bar_height));
        addView(titleBarView, tilteBarLayoutParams);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.week_view_toip_margin);
        WeekListView listView = new WeekListView(context);
        listView.setAdapter(list);


        listView.setPassedCourse(timeUtil.getPassedIndex());
        listView.setNowCourse(timeUtil.getIndex());
        addView(listView, layoutParams);
    }

}
