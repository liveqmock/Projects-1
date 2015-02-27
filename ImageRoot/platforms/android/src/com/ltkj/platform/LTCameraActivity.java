package com.ltkj.platform;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class LTCameraActivity extends Activity {
	// 声明拍照界面组件SurfaceView
	private SurfaceView surfaceView;
	private TextView tipText;
	// 声明界面控制组件SurfaceHolder
	private SurfaceHolder surfaceHolder;
	
	private boolean cameraReady = false;
	// 声明照相机
	private Camera camera;
	private Camera.PictureCallback pictureCallback;

	private List<Map<String, String>> data;

	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置当前Activity界面布局
		setContentView(R.layout.camera);

		data = new ArrayList<Map<String, String>>();
		tipText = (TextView) findViewById(R.id.camera_tip);
		// 实例化拍照界面组件
		surfaceView = (SurfaceView) findViewById(R.id.preview);
		// 从SurfaceView中获得SurfaceHolder
		surfaceHolder = surfaceView.getHolder();

		SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// 停止预览
				camera.stopPreview();
				// 释放相机资源
				camera.release();
				camera = null;
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// 打开相机
				camera = Camera.open();
				try {
					// 设置预览
					camera.setPreviewDisplay(holder);
					cameraReady = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// 获得相机参数
				Camera.Parameters parameters = camera.getParameters();
				// 设置相片大小
				parameters.setPictureSize(width, height);
				// 设置照片格式
				parameters.setPictureFormat(ImageFormat.JPEG);
				// 开始预览
				setCameraDisplayOrientation();
				camera.startPreview();
			}
		};

		surfaceHolder.addCallback(surfaceCallback);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// android2.4以下的手机必须加上这句

		pictureCallback = new Camera.PictureCallback() {

			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 2;
				Bitmap bMap = BitmapFactory.decodeByteArray(data, 0,
						data.length, options);

				new SavePictureTask().execute(bMap);
			}
		};
	}

	public void doCamera(View btn) {
		if (null == camera || !cameraReady) {
			return;
		}
		// camera.setDisplayOrientation(90);
		//camera.
		cameraReady = false;
		camera.takePicture(null, null, pictureCallback);
	}

	public void doSave(View btn) {
		buildResult();
		finish();
	}

	public void buildResult() {

		String[] allPath = new String[data.size()];
		String[] allTime = new String[data.size()];
		String[] allShowTime = new String [data.size()];
		for (int i = 0; i < allPath.length; i++) {
			allPath[i] = data.get(i).get("ImgPath");
			allTime[i] = data.get(i).get("TakeTime");
			allShowTime[i]=data.get(i).get("showTime");
		}
		Intent intent = new Intent();
		intent.putExtra("all_path", allPath);
		intent.putExtra("all_time", allTime);
		intent.putExtra("all_showTime", allShowTime);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			buildResult();
		}
		return false;

	}

	public void setCameraDisplayOrientation() {

		android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
		android.hardware.Camera.getCameraInfo(
				Camera.CameraInfo.CAMERA_FACING_BACK, info);
		int rotation = getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			result = (info.orientation + degrees) % 360;
			result = (360 - result) % 360; // compensate the mirror
		} else { // back-facing
			result = (info.orientation - degrees + 360) % 360;
		}
		camera.setDisplayOrientation(result);
	}

	public class SavePictureTask extends AsyncTask<Bitmap, String, String> {
		@Override
		protected String doInBackground(Bitmap... params) {
			// TODO Auto-generated method stub
			// 创建文件
			File pictureDir = new File(
					Environment.getExternalStorageDirectory(), "ImageSys");
			if (!pictureDir.exists() || !pictureDir.isDirectory()) {
				pictureDir.mkdir();
			}

			Date now = new Date();
			SimpleDateFormat formater = new SimpleDateFormat(
					"yyyyMMddHHmmssSSS", Locale.US);
			String nowStr = formater.format(now);
			File picture = new File(pictureDir, nowStr + ".jpg");

			Log.i("file name:", picture.getAbsolutePath());
			// 如果文件存在，删除现存文件
			if (picture.exists()) {
				picture.delete();
			}
			try {
				// 获取文件输出流
				FileOutputStream fos = new FileOutputStream(picture);

				Bitmap bMap = params[0];
				Bitmap bMapRotate;

				int rotation = getWindowManager().getDefaultDisplay()
						.getRotation();
				int degrees = 0;
				switch (rotation) {
				case Surface.ROTATION_0:
					degrees = 90;
					break;
				case Surface.ROTATION_90:
					degrees = 0;
					break;
				case Surface.ROTATION_180:
					degrees = 270;
					break;
				case Surface.ROTATION_270:
					degrees = 180;
					break;
				}
				Matrix matrix = new Matrix();
				matrix.reset();
				matrix.postRotate(degrees);
				bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(),
						bMap.getHeight(), matrix, true);
				bMap = bMapRotate;

				// 写到该文件
				bMap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 将图片压缩到流中
				fos.flush();
				// 关闭文件流
				fos.close();

				formater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
				String takeTime = formater.format(now);
				SimpleDateFormat formater1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
				String showTime = formater1.format(now);
				Map<String, String> map = new HashMap<String, String>();
				map.put("ImgPath", picture.getAbsolutePath());
				map.put("TakeTime", takeTime);
				map.put("showTime", showTime);
				data.add(map);
				amountHandler.sendEmptyMessage(0);

				setCameraDisplayOrientation();
				camera.startPreview();
				cameraReady = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler amountHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			tipText.setText("已拍照" + data.size() + "张");
		}
	};
}
