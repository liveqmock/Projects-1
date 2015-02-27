package com.ltkj.highway.common;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.ltkj.highway.config.AppConfig;
import com.ltkj.highway.po.WeatherInfo;
import com.ltkj.highway.utils.FileUtil;
import com.ltkj.highway.utils.JsonUtil;
import com.ltkj.highway.widget.CustomToast;

/**
 * @author wanggp
 * 
 */
public class HighwayApp extends Application {

	private double latitude, longitude;
	private String city = "广州"; // 当前所在城市
	private String address = null; // 当前所在的地理位置
	private boolean isCityChange = false; //城市改变了
	private static FinalDb finalDb;

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化sqlite
		initSqlite();
		// 初始化百度地图
		SDKInitializer.initialize(getApplicationContext());
		
	}


	private void initSqlite() {

		finalDb = FinalDb.create(this, AppConfig.dbName);

		// // 表station不存在
		// if (!finalDb.tableIsExist(TableInfo.get(Station.class))) {
		// Log.i("初始化数据库", "表station不存在，初始化");
		// SQLiteDatabase db = finalDb.getDb();
		// String stationSql = FileUtil.getFromAssets(this, "sql/station.sql");
		// Log.i("stationSql", stationSql);
		// db.execSQL(stationSql);
		// } else {
		// Log.i("初始化数据库", "表station已存在");
		// }
	}

	/**
	 * 单例模式
	 * 
	 * @return
	 */
	public FinalDb getFinalDb() {
		if (finalDb == null) {
			initSqlite();
		}
		return finalDb;
	}




	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isCityChange() {
		return isCityChange;
	}

	public void setCityChange(boolean isCityChange) {
		this.isCityChange = isCityChange;
	}
}
