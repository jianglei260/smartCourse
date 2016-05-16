package com.smartcourse.net;

import com.smartcourse.task.Task;
import com.smartcourse.task.TaskExecutor;

public class HttpRequestManager
{
	public static void request(Task task)
	{
		TaskExecutor.getInstance().addTask(task);
	}
}
