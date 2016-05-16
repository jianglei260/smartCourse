package com.smartcourse.view;

import com.example.smartcourse.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class TitleBarView extends RelativeLayout {
    private Context context;
    private RelativeLayout layout;
    private TextView centerTitle;
    private ImageView leftImage, rightImage;

    public TitleBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // TODO Auto-generated constructor stub
        this.context = context;
        initUI();
    }

    public TitleBarView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        initUI();
    }

    private void initUI() {
        // TODO Auto-generated method stub
        layout = (RelativeLayout) RelativeLayout.inflate(context, R.layout.titlebarview, null);
        centerTitle = (TextView) layout.findViewById(R.id.title);
        leftImage = (ImageView) layout.findViewById(R.id.leftImage);
        rightImage = (ImageView) layout.findViewById(R.id.rightImage);
        LayoutParams tilteBarLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, context.getResources().getDimensionPixelSize(R.dimen.title_bar_height));
        addView(layout, tilteBarLayoutParams);
    }

    public void setTitle(String title) {
        centerTitle.setText(title);
    }

    public void setLeftImage(int id) {
        leftImage.setImageResource(id);
    }

    public void setRightImage(int id) {
        rightImage.setImageResource(id);
    }

    public void setLeftImageVisible(boolean visible) {
        if (visible == false)
            leftImage.setVisibility(GONE);
        else
            leftImage.setVisibility(VISIBLE);
    }

    public void setRightImageVisible(boolean visible) {
        if (visible == false)
            rightImage.setVisibility(GONE);
        else
            rightImage.setVisibility(VISIBLE);
    }

    public void setLeftImageOnClick(OnClickListener listenner) {
        leftImage.setOnClickListener(listenner);
    }

    public void setRightImageOnClick(OnClickListener listenner) {
        rightImage.setOnClickListener(listenner);
    }

}
