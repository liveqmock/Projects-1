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
					System.out.println("��" + width + "��" + hight);
					Bitmap icon = Bitmap.createBitmap(width, hight,
							Bitmap.Config.RGB_565); // ����һ���յ�BItMap
					Canvas canvas = new Canvas(icon);// ��ʼ���������Ƶ�ͼ��icon��

					Paint photoPaint = new Paint(); // ��������
					photoPaint.setDither(true); // ��ȡ��������ͼ�����
					photoPaint.setFilterBitmap(true);// ����һЩ

					Rect src = new Rect(0, 0, width, hight);// ����һ��ָ�����¾��ε�����
					Rect dst = new Rect(0, 0, width, hight);// ����һ��ָ�����¾��ε�����
					canvas.drawBitmap(imageBitmap, src, dst, photoPaint);// ��photo
																			// ���Ż�������
																			// dstʹ�õ������photoPaint

					Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
							| Paint.DEV_KERN_TEXT_FLAG);// ���û���
					textPaint.setTextSize(50.0f);// �����С
					textPaint.setTypeface(Typeface.DEFAULT_BOLD);// ����Ĭ�ϵĿ��
					textPaint.setColor(Color.RED);// ���õ���ɫ
					// textPaint.setShadowLayer(3f, 1,
					// 1,this.getResources().getColor(android.R.color.background_dark));//Ӱ��������
					canvas.drawText(waterStr, width - 20 - 300,
							hight - 30 - 20, textPaint);// ������ȥ�֣���ʼδ֪x,y������ֻ�ʻ���
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
		Log.i("saveMyBitmap", "����ˮӡͼƬ��");
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
