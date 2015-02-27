package com.hps.userscore.mgr;

import com.newsoft.common.mybatis.Table;

@Table(name = "userscoredetail", pk = "id")
public class Userscoredetail implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public Userscoredetail() {
	}

	private String id;
	private String userid;
	private String levelkey;
	private Integer score;
	private java.util.Date cratetime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLevelkey() {
		return levelkey;
	}

	public void setLevelkey(String levelkey) {
		this.levelkey = levelkey;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public java.util.Date getCratetime() {
		return cratetime;
	}

	public void setCratetime(java.util.Date cratetime) {
		this.cratetime = cratetime;
	}

}