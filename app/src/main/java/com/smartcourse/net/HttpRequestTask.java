package com.smartcourse.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.smartcourse.task.Task;

public class HttpRequestTask extends Task
{
	private HttpTaskListenner httpTaskListenner;
	private String url;
	private List<NameValuePair> params;

	public HttpRequestTask(String url, HttpTaskListenner listenner)
	{
		this.url = url;
		this.httpTaskListenner = listenner;
		params = new ArrayList<NameValuePair>();
	}

	public void addParams(String name, String value)
	{
		NameValuePair param = new BasicNameValuePair(name, value);
		params.add(param);
	}

	public void addNameValuePair(NameValuePair nameValuePair)
	{
		params.add(nameValuePair);
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		HttpRequest httpRequest = HttpRequest.getInstance();
		String result = null;
		if (params.size() == 0)
		{
			result = httpRequest.doGet(url, "gb2312");
		} else
		{
			result = httpRequest.doPost(url, params, "gb2312");
		}
		if (result == null)
		{
			httpTaskListenner.onFailed();
		} else
		{
			httpTaskListenner.onSuccess(result);
		}
	}
}
