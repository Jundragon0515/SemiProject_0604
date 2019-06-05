package kh.semi.dto;

import java.sql.Date;

public class ClassDoingDTO {
	private int do_seq;
	private int do_classid;
	private String do_userid;
	private String do_status;
	private String do_reviewok;
	private String do_date; //해당 수업을 수강할 날짜
	
	
	public ClassDoingDTO() {
		super();
	}
	public ClassDoingDTO(int do_seq, int do_classid, String do_userid, String do_status, String do_reviewok,
			String do_date) {
		super();
		this.do_seq = do_seq;
		this.do_classid = do_classid;
		this.do_userid = do_userid;
		this.do_status = do_status;
		this.do_reviewok = do_reviewok;
		this.do_date = do_date;
	}
	public int getDo_seq() {
		return do_seq;
	}
	public void setDo_seq(int do_seq) {
		this.do_seq = do_seq;
	}
	public int getDo_classid() {
		return do_classid;
	}
	public void setDo_classid(int do_classid) {
		this.do_classid = do_classid;
	}
	public String getDo_userid() {
		return do_userid;
	}
	public void setDo_userid(String do_userid) {
		this.do_userid = do_userid;
	}
	public String getDo_status() {
		return do_status;
	}
	public void setDo_status(String do_status) {
		this.do_status = do_status;
	}
	public String getDo_reviewok() {
		return do_reviewok;
	}
	public void setDo_reviewok(String do_reviewok) {
		this.do_reviewok = do_reviewok;
	}
	public String getDo_date() {
		return do_date;
	}
	public void setDo_date(String do_date) {
		this.do_date = do_date;
	}
	
	
}
