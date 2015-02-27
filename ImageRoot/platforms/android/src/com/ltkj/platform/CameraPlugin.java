package com.ltkj.platform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class CameraPlugin extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args,
			final CallbackContext callbackContext) throws JSONException {

		if ("addWaterStrToImgWithImgPath".equals(action) && 2 <= args.length()) {
			final String filePath = args.getString(0);
			final String waterStr = args.getString(1);

			cordova.getThreadPool().execute(new Runnable() {
				public void run() {
					String path = null;
					if (filePath.startsWith("file:///")) {
						// file = new File(filePath.substring(7));
						path = filePath.substring(7);
					} else {
						Uri uri = Uri.parse(filePath);

						String[] proj = { MediaStore.Images.Media.DATA };
						Cursor actualimagecursor = CameraPlugin.this.cordova.getActivity()
								.getContentResolver()
								.query(uri, proj, null, null, null);
						int actual_image_column_index = actualimagecursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						actualimagecursor.moveToFirst();
						path = actualimagecursor
								.getString(actual_image_column_index);
						// Log.i("url", img_path);
						// file = new File(img_path);
						// path = img_path;
						actualimagecursor.close();
					}

					Bitmap imageBitmap = BitmapFactory.decodeFile(path);
					// FileOutputStream iStream = new FileOutputStream(fImage);
					// Bitmap oriBmp = Bitmap.;
					// oriBmp.compress(CompressFormat.JPEG, 100, iStream);
					int width = imageBitmap.getWidth(), hight = imageBitmap
							.getHeight();
					System.out.println("宽" + width + "高" + hight);
					Bitmap icon = Bitmap.createBitmap(width, hight,
							Bitmap.Config.RGB_565); // 建立一个空的BItMap
					Canvas canvas = new Canvas(icon);// 初始化画布绘制的图像到icon上

					Paint photoPaint = new Paint(); // 建立画笔
					photoPaint.setDither(true); // 获取跟清晰的图像采样
					photoPaint.setFilterBitmap(true);// 过滤一些

					Rect src = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
					Rect dst = new Rect(0, 0, width, hight);// 创建一个指定的新矩形的坐标
					canvas.drawBitmap(imageBitmap, src, dst, photoPaint);// 将photo
																			// 缩放或则扩大到
																			// dst使用的填充区photoPaint

					Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
							| Paint.DEV_KERN_TEXT_FLAG);// 设置画笔
					textPaint.setTextSize(50.0f);// 字体大小
					textPaint.setTypeface(Typeface.DEFAULT_BOLD);// 采用默认的宽度
					textPaint.setColor(Color.RED);// 采用的颜色
					// textPaint.setShadowLayer(3f, 1,
					// 1,this.getResources().getColor(android.R.color.background_dark));//影音的设置
					canvas.drawText(waterStr, width - 20 - 300,
							hight - 30 - 20, textPaint);// 绘制上去字，开始未知x,y采用那只笔绘制
					canvas.save(Canvas.ALL_SAVE_FLAG);
					canvas.restore();
					saveMyBitmap(icon, path);
					callbackContext.success();
				}
			});
		}
		return true;
	}

	public void saveMyBitmap(Bitmap bmp, String filePath) {
		Log.i("saveMyBitmap", "保存水印图片。");
		FileOutputStream fos = null;
		try {
			File fImage = new File(filePath);
			fos = new FileOutputStream(fImage);
			bmp.compress(Bitmap.CompressFormat.JPEG, 30, fos);
		} catch (FileNotFoundException e) {
		} finally {
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
				}
			}
		}

	}
}
