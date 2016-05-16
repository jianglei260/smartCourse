package com.smartcourse.bean;

public class CloudSaveBean
{
	private String userClass;//
	private String courseName;
	private String userPro;
	private String teacher;
	private String info;

	public CloudSaveBean(String userClass, String courseName, String userPro, String teacher, String info)
	{
		super();
		this.userClass = userClass;
		this.courseName = courseName;
		this.userPro = userPro;
		this.teacher = teacher;
		this.info = info;
	}

	public CloudSaveBean()
	{
		// TODO Auto-generated constructor stub
	}

	public String getUserClass()
	{
		return userClass;
	}

	public void setUserClass(String userClass)
	{
		this.userClass = userClass;
	}

	public String getCourseName()
	{
		return courseName;
	}

	public void setCourseName(String courseName)
	{
		this.courseName = courseName;
	}

	public String getUserPro()
	{
		return userPro;
	}

	public void setUserPro(String userPro)
	{
		this.userPro = userPro;
	}

	public String getTeacher()
	{
		return teacher;
	}

	public void setTeacher(String teacher)
	{
		this.teacher = teacher;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}
}
