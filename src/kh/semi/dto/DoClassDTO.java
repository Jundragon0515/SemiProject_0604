package kh.semi.dto;

import java.util.Date;

public class DoClassDTO
{
	private int classId;
	private String tutorId;
	private String title;
	private String img;
	private Date date;
	
	public DoClassDTO()
	{
		super();
	}

	public DoClassDTO(int classId, String tutorId, String title, String img, Date date)
	{
		super();
		this.classId = classId;
		this.tutorId = tutorId;
		this.title = title;
		this.img = img;
		this.date = date;
	}

	public int getClassId()
	{
		return classId;
	}

	public void setClassId(int classId)
	{
		this.classId = classId;
	}

	public String getTutorId()
	{
		return tutorId;
	}

	public void setTutorId(String tutorId)
	{
		this.tutorId = tutorId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getImg()
	{
		return img;
	}

	public void setImg(String img)
	{
		this.img = img;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
	
}
