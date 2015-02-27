package com.hps.report.mgr;

public class ReportVo {
	
	private String id;
	private String userid;
	private String xcode;
	private String ycode;
	private java.util.Date occtime;
	private String type;
	private double xup;
	private double xlow;
	private double yup;
	private double ylow;
	
	public double getXup() {
		return xup;
	}
	public void setXup(double xup) {
		this.xup = xup;
	}
	public double getXlow() {
		return xlow;
	}
	public void setXlow(double xlow) {
		this.xlow = xlow;
	}
	public double getYup() {
		return yup;
	}
	public void setYup(double yup) {
		this.yup = yup;
	}
	public double getYlow() {
		return ylow;
	}
	public void setYlow(double ylow) {
		this.ylow = ylow;
	}
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
	public String getXcode() {
		return xcode;
	}
	public void setXcode(String xcode) {
		this.xcode = xcode;
	}
	public String getYcode() {
		return ycode;
	}
	public void setYcode(String ycode) {
		this.ycode = ycode;
	}
	public java.util.Date getOcctime() {
		return occtime;
	}
	public void setOcctime(java.util.Date occtime) {
		this.occtime = occtime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
