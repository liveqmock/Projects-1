/**
 * 
 */
package com.ltkj.highway.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author wanggp
 * 
 *         2014年10月9日 下午3:09:03
 * 
 */
@SuppressWarnings("serial")
@Table(name = "station")
public class Station implements Serializable {

	@Id(column = "roadid")
	private int roadid;
	private int stationid;
	private String stationname;
	private int version;
	private Date starttime;
	private int stationtype;
	private int stationindex;
	private int status;
	private double xcode;
	private double ycode;

	public int getRoadid() {
		return roadid;
	}

	public void setRoadid(int roadid) {
		this.roadid = roadid;
	}

	public int getStationid() {
		return stationid;
	}

	public void setStationid(int stationid) {
		this.stationid = stationid;
	}

	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public int getStationtype() {
		return stationtype;
	}

	public void setStationtype(int stationtype) {
		this.stationtype = stationtype;
	}

	public int getStationindex() {
		return stationindex;
	}

	public void setStationindex(int stationindex) {
		this.stationindex = stationindex;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getXcode() {
		return xcode;
	}

	public void setXcode(double xcode) {
		this.xcode = xcode;
	}

	public double getYcode() {
		return ycode;
	}

	public void setYcode(double ycode) {
		this.ycode = ycode;
	}

	public void jsonToStation(JSONObject jsonObject) {
		if (jsonObject == null)
			return;

		try {
			this.roadid = jsonObject.getInt("ROADID");
			this.stationid = jsonObject.getInt("STATIONID");
			this.stationname = jsonObject.getString("STATIONNAME");
			this.xcode = jsonObject.getDouble("XCODE");
			this.ycode = jsonObject.getDouble("YCODE");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	public void mapToStation(Map<String,Object> map) {
		if (map == null)
			return;

		this.roadid = Integer.parseInt(map.get("ROADID").toString());
		this.stationid = Integer.parseInt(map.get("STATIONID").toString());
		this.stationname = map.get("STATIONNAME").toString();
		this.xcode = Double.valueOf(map.get("XCODE").toString()) ;
		this.ycode = Double.valueOf(map.get("YCODE").toString()) ;
		

	}

}
