package com.hps.userexchange.mgr;

import com.newsoft.common.mybatis.Table;

@Table(name="highwaylife", pk="highwaylife_id")
public class Highwaylife implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** default constructor */
	public Highwaylife() {
	}

	   private String id;
	   private Integer type;
	   private String title;
	   private String userid;
	   private java.util.Date createtime;
	   private String detail;
		
		public String getId () {
		return id;
	}

	public void  setId(String id) {
		this.id = id;
	}
	
		public Integer getType () {
		return type;
	}

	public void  setType(Integer type) {
		this.type = type;
	}
	
		public String getTitle () {
		return title;
	}

	public void  setTitle(String title) {
		this.title = title;
	}
	
		public String getUserid () {
		return userid;
	}

	public void  setUserid(String userid) {
		this.userid = userid;
	}
	
		public java.util.Date getCreatetime () {
		return createtime;
	}

	public void  setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	
		public String getDetail () {
		return detail;
	}

	public void  setDetail(String detail) {
		this.detail = detail;
	}
	
	}