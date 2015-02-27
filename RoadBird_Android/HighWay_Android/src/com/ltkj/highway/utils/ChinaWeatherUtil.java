/**
 * 
 */
package com.ltkj.highway.utils;

import org.apache.commons.lang3.StringUtils;

import com.ltkj.highway.R;

/**
 * @author wanggp
 * 
 *         2014年10月8日 下午3:22:00
 * 
 */
public class ChinaWeatherUtil {

	public static int getWeatherImage(String weatherInfo) {

		int imageName = R.drawable.weather_default;
		if (StringUtils.isNotEmpty(weatherInfo)) {
			if (weatherInfo.indexOf("雷电") != -1)
				return R.drawable.weather_lightning;
			else if (weatherInfo.indexOf("台风") != -1)
				return R.drawable.weather_typhoon;
			else if (weatherInfo.indexOf("晴") != -1)
				return R.drawable.weather_fine;
			else if (weatherInfo.indexOf("雾") != -1
					|| weatherInfo.indexOf("霾") != -1)
				return R.drawable.weather_haze;
			else if (weatherInfo.indexOf("小雨") != -1
					|| weatherInfo.indexOf("阵雨") != -1
					|| weatherInfo.indexOf("中雨") != -1)
				return R.drawable.weather_lightrain_moderaterain;
			else if (weatherInfo.indexOf("阴") != -1
					|| weatherInfo.indexOf("云") != -1)
				return R.drawable.weather_cloudy;
			else if (weatherInfo.indexOf("大雨") != -1
					|| weatherInfo.indexOf("暴雨") != -1)
				return R.drawable.weather_heavyain_stormy;

		}

		return imageName;

	}
}
