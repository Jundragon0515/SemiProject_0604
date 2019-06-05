package kh.semi.dto;

import java.util.Date;

public class UpgradeDTO {

	private int up_seq;
	private String up_id;
	private String up_nickname;
	private Date up_applydate;
	
	
	public UpgradeDTO() {
		super();
	}
	public UpgradeDTO(int up_seq, String up_id, String up_nickname, Date up_applydate) {
		super();
		this.up_seq = up_seq;
		this.up_id = up_id;
		this.up_nickname = up_nickname;
		this.up_applydate = up_applydate;
	}
	public int getUp_seq() {
		return up_seq;
	}
	public void setUp_seq(int up_seq) {
		this.up_seq = up_seq;
	}
	public String getUp_id() {
		return up_id;
	}
	public void setUp_id(String up_id) {
		this.up_id = up_id;
	}
	public String getUp_nickname() {
		return up_nickname;
	}
	public void setUp_nickname(String up_nickname) {
		this.up_nickname = up_nickname;
	}
	public Date getUp_applydate() {
		return up_applydate;
	}
	public void setUp_applydate(Date up_applydate) {
		this.up_applydate = up_applydate;
	}	
	
}
