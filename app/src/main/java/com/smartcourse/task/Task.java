package com.smartcourse.task;

public abstract class Task implements Runnable
{
	private TaskListenner taskListenner;

	@Override
	public abstract void run();

	public void setTaskListenner(TaskListenner listenner)
	{
		this.taskListenner = listenner;
	}

}
