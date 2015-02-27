package com.ltkj.platform;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class LTUploadPlugin extends CordovaPlugin {
	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {
		if ("LTUploadImage".equals(action)) {
			final JSONObject arg_object = args.getJSONObject(0);
			// String r =
			// post("http://128.8.38.114:8080/TestUpload_Struts2/uploadFileByStruts2.action",
			// arg_object);
			cordova.getThreadPool().execute(new Runnable() {
				public void run() { 
					try {
//						String r = post(
//								"http://183.62.56.27:4011/ForImgUp.ashx",
//								arg_object);
						String r = post(
								"http://119.146.239.82:8082/ForImgUp.ashx",
								arg_object);
						Log.i("upload return---", r);
						JSONObject object = new JSONObject(r);
						// object.put("success", "ture");
						// object.put("msg", r);
						if ("true".equals(object.getString("success"))) {
							callbackContext.success(object);
						} else {
							callbackContext.error(object);
						}
					} catch (Exception e) {
						System.err.println("Exception: " + e.getMessage());
						callbackContext.error(e.getMessage());
					}
				}
			});
			return true;
		}
		return false;

	}

	private String post(String url, JSONObject json) {
		try {
			HttpPost post = new HttpPost(url);
			MultipartEntity multipart = new MultipartEntity();

			Iterator<?> it = json.keys();
			while (it.hasNext()) {// ±È¿˙JSONObject
				String key = (String) it.next().toString();
				// aa2 = aa2 + result.getString(bb2);
				if (!key.equalsIgnoreCase("images")) {
					multipart.addPart(key, new StringBody(json.getString(key),
							Charset.forName("UTF-8")));
				}
			}
			/*
			 * multipart.addPart("lotImgStrucId", new
			 * StringBody(json.getString("lotImgStrucId"),
			 * Charset.forName("UTF-8"))); multipart.addPart("location", new
			 * StringBody(json.getString("location"),
			 * Charset.forName("UTF-8"))); multipart.addPart("photoCount", new
			 * StringBody(json.getString("photoCount"),
			 * Charset.forName("UTF-8"))); multipart.addPart("upUserId", new
			 * StringBody(json.getString("upUserId"),
			 * Charset.forName("UTF-8"))); multipart.addPart("lotimgUper", new
			 * StringBody(json.getString("lotimgUper"),
			 * Charset.forName("UTF-8"))); multipart.addPart("lotImgPhotoTime",
			 * new StringBody(json.getString("lotImgPhotoTime"),
			 * Charset.forName("UTF-8"))); multipart.addPart("cameraType", new
			 * StringBody(json.getString("cameraType"),
			 * Charset.forName("UTF-8")));
			 */
			// File file = new File(img_path);
			// Uri fileUri = Uri.fromFile(file);
			JSONArray jsonArr = json.getJSONArray("images");

			for (int i = 0; i < jsonArr.length(); i++) {

				String myImageUrl = jsonArr.getString(i);
				Log.i("url", myImageUrl);
				if (myImageUrl.startsWith("file:///")) {
					File file = new File(myImageUrl.substring(7));
					multipart.addPart("myFile", new FileBody(file));
				} else {
					Uri uri = Uri.parse(myImageUrl);

					String[] proj = { MediaStore.Images.Media.DATA };
					Cursor actualimagecursor = this.cordova.getActivity()
							.getContentResolver()
							.query(uri, proj, null, null, null);
					int actual_image_column_index = actualimagecursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					actualimagecursor.moveToFirst();
					String img_path = actualimagecursor
							.getString(actual_image_column_index);
					Log.i("url", img_path);
					File file = new File(img_path);
					multipart.addPart("myFile", new FileBody(file));
					actualimagecursor.close();
				}
			}

			HttpClient client = new DefaultHttpClient();
			Log.i("multipart", multipart.toString());
			post.setEntity(multipart);
			HttpResponse response = client.execute(post);
			Log.i("state", response.getStatusLine().toString());
			HttpEntity returnEntity = response.getEntity();
			String jsn = "";
			if (returnEntity != null) {
				jsn = EntityUtils.toString(returnEntity, HTTP.UTF_8);
			}
			return jsn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
