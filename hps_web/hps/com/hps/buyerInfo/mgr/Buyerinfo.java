package com.hps.buyerInfo.mgr;

public class Buyerinfo {
	   private String id;
	   private String userid;
	   private String goodsid;
	   private String realname;
	   private String address;
	   private String phone;
	   private String postcode;
	   private Integer score;
	   private Integer type;
	   private String imagePath;
	   private String imageName;
		
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
	
		public String getGoodsid () {
		return goodsid;
	}

	public void  setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	
		public String getRealname () {
		return realname;
	}

	public void  setRealname(String realname) {
		this.realname = realname;
	}
	
		public String getAddress () {
		return address;
	}

	public void  setAddress(String address) {
		this.address = address;
	}
	
		public String getPhone () {
		return phone;
	}

	public void  setPhone(String phone) {
		this.phone = phone;
	}
	
		public String getPostcode () {
		return postcode;
	}

	public void  setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
}