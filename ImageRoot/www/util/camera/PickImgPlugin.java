package com.ltkj.platform;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.luminous.pick.Action;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class PickImgPlugin extends CordovaPlugin {
	private CallbackContext callbackContext;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if ("pickMultiplePhotos".equals(action)) {
			// Toast.makeText(cordova.getActivity(), "123", 0).show();
			// final JSONObject arg_object = args.getJSONObject(0);
			// this.
			this.callbackContext = callbackContext;
			Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
			cordova.startActivityForResult((CordovaPlugin) this, i, 200);
			return true;
		}
		return false;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			String[] all_path = data.getStringArrayExtra("all_path");
			
			JSONObject json = new JSONObject();
			JSONArray imgArr = new JSONArray();
			Date now = new Date();
			SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			String nowStr = formater.format(now);
			SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
			String showTime = formater1.format(now);
			try {
				for (String string : all_path) {
					Log.i("imagePath get", string);
					JSONObject obj = new JSONObject();
					obj.put("ImgPath", "file://" + string);
					obj.put("TakeTime", nowStr);
					obj.put("showTime", showTime);
					imgArr.put(obj);
				}
				json.put("imgArr", imgArr);
				callbackContext.success(json);
			} catch (JSONException e) {
				callbackContext.error(0);
			}

		}
	}
}
