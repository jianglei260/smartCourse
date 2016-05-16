package com.smartcourse.widget;

import com.example.smartcourse.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class StyleEditText extends RelativeLayout implements View.OnClickListener
{
	Context context;
	LinearLayout layout;
	EditText editText;
	FontTextView fontText;
	String hint;
	boolean isPassWord;
	boolean isNumber;
	private View bottomBorder;
	private View topBorder;
	public StyleEditText(Context context,boolean isPwd)
	{
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		isPassWord=isPwd;
		init();
	}
	@SuppressLint({ "ResourceAsColor", "NewApi" })
	private void init()
	{ 
		// TODO Auto-generated method stub
		layout=(LinearLayout) LinearLayout.inflate(context, R.layout.style_edit, null);
		editText=(EditText) layout.findViewById(R.id.styleEditText);
		bottomBorder=(View)layout.findViewById(R.id.editTextBottomBorder);
		topBorder=(View)layout.findViewById(R.id.editTextTopBorder);
		
		if(isPassWord)
		{
			editText.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}
		fontText=(FontTextView) layout.findViewById(R.id.styleEditDelete);
		fontText.setOnClickListener(this);
		fontText.setVisibility(View.GONE);
		editText.setOnFocusChangeListener(new OnFocusChangeListener()
		{

			@Override public void onFocusChange(View v, boolean hasFocus)
			{
				// TODO Auto-generated method stub
				if (hasFocus)
					fontText.setVisibility(View.VISIBLE);
				else
					fontText.setVisibility(View.GONE);
			}
		});
		setBackgroundColor(R.color.background);
		LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,context.getResources().getDimensionPixelSize(R.dimen.style_spinner_heght));
		setLayoutParams(params);
		addView(layout, params);
	}
	public void setRightText(String text)
	{
		fontText.setText(text);
	}
	
	public void setBottomBorderVisible(boolean visible)
	{
		if(visible==false)
		{
			bottomBorder.setVisibility(View.GONE);
		}
	}
	public void setTopBorderVisible(boolean visible)
	{
		if(visible==false)
		{
			topBorder.setVisibility(View.GONE);
		}
	}
	@SuppressLint("NewApi")
	public void setNumeric(boolean isNumeric)
	{
		editText.setInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_TEXT);
	}
	public void setHint(String hint)
	{
		editText.setHintTextColor(context.getResources().getColor(R.color.textColor));
		editText.setHint(hint);
	}
	public String getResult()
	{
		String value=editText.getText().toString();		
		return value;
		
	}
	public void onClick(View v)
	{
		editText.setText("");
	}
	public void setText(String string)
	{
		// TODO Auto-generated method stub
		editText.setText(string);
	}

	public void addTextWatcher(TextWatcher myWatcher)
	{
		editText.addTextChangedListener(myWatcher);
	}
}
