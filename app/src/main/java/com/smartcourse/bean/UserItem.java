package com.smartcourse.bean;

public class UserItem
{
	private String depart;// 学院
	private String pro;// 专业
	private String grade;// 年级
	private String userClass;// 班级

	public UserItem(String depart, String pro, String grade, String userClass)
	{
		super();
		this.depart = depart;
		this.pro = pro;
		this.grade = grade;
		this.userClass = userClass;
	}

	public String getDepart()
	{
		return depart;
	}

	public void setDepart(String depart)
	{
		this.depart = depart;
	}

	public String getPro()
	{
		return pro;
	}

	public void setPro(String pro)
	{
		this.pro = pro;
	}

	public String getGrade()
	{
		return grade;
	}

	public void setGrade(String grade)
	{
		this.grade = grade;
	}

	public String getUserClass()
	{
		return userClass;
	}

	public void setUserClass(String userClass)
	{
		this.userClass = userClass;
	}
}
