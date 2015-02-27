package com.ltkj.highway.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneCallUtil {

	public static void makePhoneCall(final Activity activity,
			final String title, final String phone) {

		if (((TelephonyManager) activity
				.getSystemService(Activity.TELEPHONY_SERVICE)).getPhoneType() == TelephonyManager.PHONE_TYPE_NONE
				&& ((TelephonyManager) activity
						.getSystemService(Activity.TELEPHONY_SERVICE))
						.getLine1Number() == null) {
			// no phone
			Toast.makeText(activity, "设备不支持电话功能！", Toast.LENGTH_SHORT).show();
			return;
		}
		// if (isPhoneNumberValid(phone)) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		if (StringUtils.isNotEmpty(title))
			builder.setTitle(title);
		builder.setMessage(phone);
		builder.setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent myIntentDial = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + phone));
				activity.startActivity(myIntentDial);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
		// } else {
		// Toast.makeText(activity, "电话格式不对！", Toast.LENGTH_SHORT).show();
		// }
	}

	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
		String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;

		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		Pattern pattern2 = Pattern.compile(expression2);
		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;

	}
}
