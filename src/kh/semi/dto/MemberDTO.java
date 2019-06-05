package kh.semi.dto;

public class MemberDTO
{
	private String m_id; //pk
    private String m_password; //not null
    private String m_nickname; //not null
    private String m_gender;
    private String m_agerange; //not null
    private String m_monthday; 
    private String m_phone;
    private String m_photo;
    private String m_type;
    private String m_attention;
	
    public MemberDTO()
	{
		super();
	}
    
	public MemberDTO(
		String m_id, String m_password, String m_nickname, String m_gender, String m_agerange, String m_monthday
	)
	{
		super();
		this.m_id = m_id;
		this.m_password = m_password;
		this.m_nickname = m_nickname;
		this.m_gender = m_gender;
		this.m_agerange = m_agerange;
		this.m_monthday = m_monthday;
	}

	

	public MemberDTO(
		String m_id, String m_password, String m_nickname, String m_gender, String m_agerange, String m_monthday, String m_phone
	)
	{
		super();
		this.m_id = m_id;
		this.m_password = m_password;
		this.m_nickname = m_nickname;
		this.m_gender = m_gender;
		this.m_agerange = m_agerange;
		this.m_monthday = m_monthday;
		this.m_phone = m_phone;
	}

	public MemberDTO(
		String m_id, String m_password, String m_nickname, String m_gender, String m_agerange, String m_monthday, String m_phone, String m_photo, String m_type, String m_attention
	)
	{
		super();
		this.m_id = m_id;
		this.m_password = m_password;
		this.m_nickname = m_nickname;
		this.m_gender = m_gender;
		this.m_agerange = m_agerange;
		this.m_monthday = m_monthday;
		this.m_phone = m_phone;
		this.m_photo = m_photo;
		this.m_type = m_type;
		this.m_attention = m_attention;
	}
	
	
	
	public String getM_id()
	{
		return m_id;
	}

	public void setM_id(String m_id)
	{
		this.m_id = m_id;
	}

	public String getM_password()
	{
		return m_password;
	}

	public void setM_password(String m_password)
	{
		this.m_password = m_password;
	}

	public String getM_nickname()
	{
		return m_nickname;
	}

	public void setM_nickname(String m_nickname)
	{
		this.m_nickname = m_nickname;
	}

	public String getM_gender()
	{
		return m_gender;
	}

	public void setM_gender(String m_gender)
	{
		this.m_gender = m_gender;
	}

	public String getM_agerange()
	{
		return m_agerange;
	}

	public void setM_agerange(String m_agerange)
	{
		this.m_agerange = m_agerange;
	}

	public String getM_monthday()
	{
		return m_monthday;
	}

	public void setM_monthday(String m_monthday)
	{
		this.m_monthday = m_monthday;
	}

	public String getM_phone()
	{
		return m_phone;
	}

	public void setM_phone(String m_phone)
	{
		this.m_phone = m_phone;
	}

	public String getM_photo()
	{
		return m_photo;
	}

	public void setM_photo(String m_photo)
	{
		this.m_photo = m_photo;
	}

	public String getM_type()
	{
		return m_type;
	}

	public void setM_type(String m_type)
	{
		this.m_type = m_type;
	}

	public String getM_attention()
	{
		return m_attention;
	}

	public void setM_attention(String m_attention)
	{
		this.m_attention = m_attention;
	}
    
    
}
