package com.ltkj.platform;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

public class TakePicPlugin extends CordovaPlugin {
	private CallbackContext callbackContext;

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		if ("seriesTakePic".equals(action)) {
			// Toast.makeText(cordova.getActivity(), "123", 0).show();
			// final JSONObject arg_object = args.getJSONObject(0);
			// this.
			this.callbackContext = callbackContext;
			Intent i = new Intent(cordova.getActivity(), LTCameraActivity.class);
			cordova.startActivityForResult((CordovaPlugin) this, i, 201);
			return true;
		}
		return false;
	}
	
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 201 && resultCode == Activity.RESULT_OK) {
			String[] all_path = data.getStringArrayExtra("all_path");
			String[] all_time = data.getStringArrayExtra("all_time");
			String[] all_showTime= data.getStringArrayExtra("all_showTime");
			
			JSONObject json = new JSONObject();
			JSONArray imgArr = new JSONArray();
			try {
				for (int i = 0; i < all_path.length && i < all_time.length; i++) {
					String path = all_path[i];
					String time = all_time[i];
					String showTime = all_showTime[i];
					//Log.i("imagePath get", path);
					JSONObject obj = new JSONObject();
					obj.put("ImgPath", "file://" + path);
					obj.put("TakeTime", time);
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
