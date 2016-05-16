package com.smartcourse.task;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor
{
	private ExecutorService executorService;
	private static TaskExecutor takExecutor;
	public static int threadNum = 5;

	public static TaskExecutor getInstance()
	{
		if(takExecutor==null)
			takExecutor=new TaskExecutor();
		return takExecutor;
	}
	public TaskExecutor()
	{
		executorService=Executors.newFixedThreadPool(threadNum);
	}
	public void addTask(Task task)
	{
		executorService.execute(task);
	}
}
