package com.hps.level.mgr;

import com.newsoft.common.mybatis.Table;

@Table(name = "SCORELEVEL", pk = "LEVELKEY")
public class Scorelevel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public static final String REG = "REG";
	public static final String LOGIN = "LOGIN";
	public static final String SHARE = "SHARE";
	public static final String BAOLIAO = "BAOLIAO";
	public static final String CMS = "CMS";

	/** default constructor */
	public Scorelevel() {
	}

	private String levelkey;

	private String levelDes;

	private Integer score;
	private String memo;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getLevelDes() {
		return levelDes;
	}

	public void setLevelDes(String levelDes) {
		this.levelDes = levelDes;
	}
	
	
}