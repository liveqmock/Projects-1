package com.hps.userexchange.mgr;

import com.newsoft.common.mybatis.Table;

@Table(name="userexchnage", pk="userexchnage_id")
public class Userexchnage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	/** default constructor */
	public Userexchnage() {
	}

	   private String id;
	   private String userid;
	   private String productid;
	   private String producttitle;
	   private Integer status;
	   private Integer score;
	   private String handler;
	   private String handlername;
	   private String reicaddress;
	   private String reicphone;
	   private java.util.Date createtime;
		
		public String getId () {
		return id;
	}

	public void  setId(String id) {
		this.id = id;
	}
	
		public String getUserid () {
		return userid;
	}

	public void  setUserid(String userid) {
		this.userid = userid;
	}
	
		public String getProductid () {
		return productid;
	}

	public void  setProductid(String productid) {
		this.productid = productid;
	}
	
		public String getProducttitle () {
		return producttitle;
	}

	public void  setProducttitle(String producttitle) {
		this.producttitle = producttitle;
	}
	
		public Integer getStatus () {
		return status;
	}

	public void  setStatus(Integer status) {
		this.status = status;
	}
	
		public Integer getScore () {
		return score;
	}

	public void  setScore(Integer score) {
		this.score = score;
	}
	
		public String getHandler () {
		return handler;
	}

	public void  setHandler(String handler) {
		this.handler = handler;
	}
	
		public String getHandlername () {
		return handlername;
	}

	public void  setHandlername(String handlername) {
		this.handlername = handlername;
	}
	
		public String getReicaddress () {
		return reicaddress;
	}

	public void  setReicaddress(String reicaddress) {
		this.reicaddress = reicaddress;
	}
	
		public String getReicphone () {
		return reicphone;
	}

	public void  setReicphone(String reicphone) {
		this.reicphone = reicphone;
	}
	
		public java.util.Date getCreatetime () {
		return createtime;
	}

	public void  setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	
	}