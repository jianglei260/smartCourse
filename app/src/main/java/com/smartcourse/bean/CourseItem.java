package com.smartcourse.bean;

public class CourseItem
{
	private int id;
	private String name;// 课程名
	private String teacher;// 教师
	private String classRoom;// 教室编号
	private String hour;// 上课节数
	private int satrtWeek;// 开始周数
	private int endWeek;// 结束周数
	private int weekday;// 星期几
	private int flag;// 单双周 标记

	public int getFlag()
	{
		return flag;
	}

	public void setFlag(int flag)
	{
		this.flag = flag;
	}


	public CourseItem(String name, String teacher, String classRoom, String hour, int satrtWeek, int endWeek, int weekday, int flag)
	{
		super();
		this.name = name;
		this.teacher = teacher;
		this.classRoom = classRoom;
		this.hour = hour;
		this.satrtWeek = satrtWeek;
		this.endWeek = endWeek;
		this.weekday = weekday;
		this.flag = flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CourseItem()
	{

	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTeacher()
	{
		return teacher;
	}

	public void setTeacher(String teacher)
	{
		this.teacher = teacher;
	}

	public String getClassRoom()
	{
		return classRoom;
	}

	public void setClassRoom(String classRoom)
	{
		this.classRoom = classRoom;
	}

	public String getHour()
	{
		return hour;
	}

	public void setHour(String hour)
	{
		this.hour = hour;
	}

	public int getSatrtWeek()
	{
		return satrtWeek;
	}

	public void setSatrtWeek(int satrtWeek)
	{
		this.satrtWeek = satrtWeek;
	}

	public int getEndWeek()
	{
		return endWeek;
	}

	public void setEndWeek(int endWeek)
	{
		this.endWeek = endWeek;
	}

	public int getWeekday()
	{
		return weekday;
	}

	public void setWeekday(int weekday)
	{
		this.weekday = weekday;
	}
}
