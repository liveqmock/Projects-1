package com.hps.cms.mgr;

import com.newsoft.common.mybatis.Table;

@Table(name="cmscontent", pk="cmscontent_id")
public class Cmscontent implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** default constructor */
	public Cmscontent() {
	}

	   private String id;
	   private String categoryid;
	   private String title;
	   private String content;
	   private String authorid;
	   private String authorname;
	   private java.util.Date cratetime;
		
		public String getId () {
		return id;
	}

	public void  setId(String id) {
		this.id = id;
	}
	
		public String getCategoryid () {
		return categoryid;
	}

	public void  setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
		public String getTitle () {
		return title;
	}

	public void  setTitle(String title) {
		this.title = title;
	}
	
		public String getContent () {
		return content;
	}

	public void  setContent(String content) {
		this.content = content;
	}
	
		public String getAuthorid () {
		return authorid;
	}

	public void  setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	
		public String getAuthorname () {
		return authorname;
	}

	public void  setAuthorname(String authorname) {
		this.authorname = authorname;
	}
	
		public java.util.Date getCratetime () {
		return cratetime;
	}

	public void  setCratetime(java.util.Date cratetime) {
		this.cratetime = cratetime;
	}
	
	}