package com.smartcourse.view;

import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.smartcourse.R;
import com.smartcourse.bean.CloudSaveBean;
import com.smartcourse.bean.CourseItem;
import com.smartcourse.db.DBHelper;
import com.smartcourse.servelet.CloudManager;
import com.smartcourse.widget.StyleButton;
import com.smartcourse.widget.StyleEditText;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CourseDetialView extends RelativeLayout implements TextWatcher, View.OnClickListener
{

	private Context context;
	private CourseItem course;
	private StyleButton save;
	private android.widget.ListView infoList;
	private List<CloudSaveBean> saveLists;
	private InfoListAdapter adapter;
	private LinearLayout userSend;
	private EditText userEdit;
	private ImageView sendImage;
	private static int COUSE_NAME_EDIT_ID = 0x01;
	private static int CLSSROOM_EDIT_ID = 0x02;
	private static int TEACHER_EDIT_ID = 0x03;
	private static int HOUR_EDIT_ID = 0x04;
	private static int LIST_EDIT_ID = 0x05;
	private static int SAVE_ID = 0x06;
	private static int TITLE_BAR_ID = 0x07;
	private static int USER_SEND_ID = 0x08;

	public CourseDetialView(Context context, CourseItem course)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.course = course;
		initUI();
	}

	private void initUI()
	{
		// TODO Auto-generated method stub
		setBackgroundResource(R.drawable.week_bg);
		StyleEditText courseNameEdit = new StyleEditText(context, false);
		LayoutParams courseNameEditParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		courseNameEditParams.addRule(RelativeLayout.BELOW, TITLE_BAR_ID);
		courseNameEdit.setHint("课程名称");
		courseNameEdit.setText(course.getName());
		courseNameEdit.setId(COUSE_NAME_EDIT_ID);

		StyleEditText classRoomEdit = new StyleEditText(context, false);
		LayoutParams classRoomEditParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		classRoomEditParams.addRule(RelativeLayout.BELOW, COUSE_NAME_EDIT_ID);
		courseNameEditParams.addRule(RelativeLayout.BELOW, TITLE_BAR_ID);
		classRoomEdit.setHint("教室");
		classRoomEdit.setText(course.getClassRoom());
		classRoomEdit.setId(CLSSROOM_EDIT_ID);

		StyleEditText teacherEdit = new StyleEditText(context, false);
		LayoutParams teacherEditParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		teacherEditParams.addRule(RelativeLayout.BELOW, CLSSROOM_EDIT_ID);
		teacherEdit.setHint("教师");
		teacherEdit.setText(course.getTeacher());
		teacherEdit.setId(TEACHER_EDIT_ID);

		StyleEditText hourEdit = new StyleEditText(context, false);
		LayoutParams hourEditParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		hourEditParams.addRule(RelativeLayout.BELOW, TEACHER_EDIT_ID);
		hourEdit.setHint("上课时间");
		hourEdit.setText(course.getHour());
		hourEdit.setId(HOUR_EDIT_ID);

		save = new StyleButton(context);
		LayoutParams saveParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		saveParams.addRule(RelativeLayout.BELOW, HOUR_EDIT_ID);
		save.setText("保存修改");
		save.setVisibility(GONE);
		save.setId(SAVE_ID);

		TitleBarView barView = new TitleBarView(context,null);
		barView.setTitle("课程详情");
		barView.setId(TITLE_BAR_ID);
		barView.setRightImageVisible(false);

		courseNameEdit.addTextWatcher(this);
		classRoomEdit.addTextWatcher(this);
		teacherEdit.addTextWatcher(this);
		hourEdit.addTextWatcher(this);

		ScrollView scrollList = new ScrollView(context);
		LayoutParams scrollListParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		scrollListParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_item_top_margin);
		scrollListParams.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.week_view_toip_margin);
		scrollListParams.addRule(RelativeLayout.ABOVE, USER_SEND_ID);
		scrollListParams.addRule(RelativeLayout.BELOW, SAVE_ID);
		saveLists = new ArrayList<CloudSaveBean>();

		userSend = (LinearLayout) LinearLayout.inflate(context, R.layout.user_send, null);
		LayoutParams userSendParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		userSend.setId(USER_SEND_ID);
		userSendParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		userEdit = (EditText) userSend.findViewById(R.id.user_edit);
		sendImage = (ImageView) userSend.findViewById(R.id.user_send);
		sendImage.setOnClickListener(this);

		addView(barView);
		addView(courseNameEdit, courseNameEditParams);
		addView(classRoomEdit, classRoomEditParams);
		addView(teacherEdit, teacherEditParams);
		addView(hourEdit, hourEditParams);
		addView(save, saveParams);
		barView.setLeftImageOnClick(new OnClickListener()
		{

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				((Activity) context).onBackPressed();
			}

		});

		infoList = new ListView(context);
		LayoutParams infoListParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		infoListParams.addRule(RelativeLayout.BELOW, SAVE_ID);
		infoListParams.addRule(RelativeLayout.ABOVE, USER_SEND_ID);
		infoListParams.bottomMargin = infoListParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.today_list_top_margin);

		adapter = new InfoListAdapter<CloudSaveBean>(saveLists);
		catchList();
		infoList.setAdapter(adapter);
		infoList.setBackgroundColor(Color.WHITE);
		// scrollList.addView(infoList, infoListParams);
		addView(infoList, infoListParams);
		addView(userSend, userSendParams);
	}

	public void catchList()
	{
		Cursor c = DBHelper.getInstance(context).query(DBHelper.USER_TBL_NAME);
		c.moveToFirst();
		String pro = c.getString(c.getColumnIndex("pro"));
		String grade = c.getString(c.getColumnIndex("grade"));
		String mClass = c.getString(c.getColumnIndex("class"));
		mClass = grade + mClass;
		String courseName = course.getName();
		String teacher = course.getTeacher();
		CloudSaveBean cloudSaveBean = new CloudSaveBean(mClass, courseName, pro, teacher, "");
		CloudManager cloudManager = CloudManager.getInstance();
		cloudManager.getInfoFromCloud(cloudSaveBean, new FindCallback<AVObject>()
		{

			@Override
			public void done(List<AVObject> list, AVException arg1)
			{
				// TODO Auto-generated method stub
				if (arg1 == null)
				{
					if (list.size() == 0)
					{
						CloudSaveBean bean = new com.smartcourse.bean.CloudSaveBean();
						bean.setInfo("没有任何评论");
						saveLists.add(bean);
					}
					for (int i = 0; i < list.size(); i++)
					{
						CloudSaveBean bean = new com.smartcourse.bean.CloudSaveBean();
						bean.setInfo(list.get(i).getString("info"));
						saveLists.add(bean);
						Log.d("cloud get", "success");
					}

				} else
				{
					CloudSaveBean bean = new com.smartcourse.bean.CloudSaveBean();
					bean.setInfo("获取评论失败");
					saveLists.add(bean);
				}
				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		// TODO Auto-generated method stub
		save.setVisibility(VISIBLE);
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		// TODO Auto-generated method stub

	}

	public class InfoListAdapter<CloudSaveBean> extends ViewBaseAdapter<CloudSaveBean>
	{
		private ViewHolder viewHolder;

		public InfoListAdapter(List<CloudSaveBean> lists)
		{
			super(lists);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// TODO Auto-generated method stub
			if (convertView == null)
			{
				convertView = LinearLayout.inflate(context, R.layout.user_com_item, null);
				viewHolder = new ViewHolder();
				viewHolder.userText = (TextView) convertView.findViewById(R.id.user_com_text);
				convertView.setTag(viewHolder);
			} else
			{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			CloudSaveBean bean = (CloudSaveBean) saveLists.get(position);
			String info = ((com.smartcourse.bean.CloudSaveBean) bean).getInfo();
			viewHolder.userText.setText(info);
			return convertView;
		}

	}

	public class ViewHolder
	{
		TextView userText;
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		String info = userEdit.getText().toString();
		if (TextUtils.isEmpty(info))
		{
			Toast.makeText(context, "发送信息不能为空", Toast.LENGTH_SHORT).show();
			return;
		}

		Cursor c = DBHelper.getInstance(context).query(DBHelper.USER_TBL_NAME);
		c.moveToFirst();
		String pro = c.getString(c.getColumnIndex("pro"));
		String grade = c.getString(c.getColumnIndex("grade"));
		String mClass = c.getString(c.getColumnIndex("class"));
		mClass = grade + mClass;
		String courseName = course.getName();
		String teacher = course.getTeacher();
		final CloudSaveBean cloudSaveBean = new CloudSaveBean(mClass, courseName, pro, teacher, info);
		CloudManager.getInstance().saveToCloud(cloudSaveBean, new SaveCallback()
		{

			@Override
			public void done(AVException arg0)
			{
				// TODO Auto-generated method stub
				if (arg0 == null)
				{
					saveLists.add(cloudSaveBean);
					Log.d("list", saveLists.get(saveLists.size() - 1).getInfo());
					((Activity) context).runOnUiThread(new Runnable()
					{

						@SuppressLint("NewApi")
						@Override
						public void run()
						{
							// TODO Auto-generated method stub
							userEdit.setText("");
							adapter.notifyDataSetChanged();
							infoList.scrollListBy(infoList.getChildCount());
						}
					});

				}
			}
		});

	}
}
