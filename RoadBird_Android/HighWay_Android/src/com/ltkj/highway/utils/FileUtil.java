package com.ltkj.highway.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;

public class FileUtil {

	public static String SDCardRoot = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator;
	public static String ATTACH_DIRPATH = "NSOAtemp" + File.separator;

	/**
	 * 从asset读取文件
	 * 
	 * @param activity
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Object object, String fileName) {
		StringBuilder stringBuffer = new StringBuilder();
		InputStreamReader inputReader = null;
		BufferedReader bufReader = null;
		
		try {

			if (object instanceof Activity) {
				Activity activity = (Activity) object;
				inputReader = new InputStreamReader(activity.getResources()
						.getAssets().open(fileName));
			} else if (object instanceof Application) {
				Application application = (Application) object;
				inputReader = new InputStreamReader(application.getResources()
						.getAssets().open(fileName));
			}
			bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				stringBuffer.append(line);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufReader != null)
				try {
					bufReader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (inputReader != null)
				try {
					inputReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return stringBuffer.toString();
	}

	/**
	 * 从raw文件读取文件
	 * 
	 * @param activity
	 * @param rawId
	 * @return
	 */
	public static String getFromRaw(Activity activity, int rawId) {
		StringBuffer stringBuffer = new StringBuffer();
		InputStreamReader inputReader = null;
		BufferedReader bufReader = null;
		try {
			inputReader = new InputStreamReader(activity.getResources()
					.openRawResource(rawId));
			bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null)
				stringBuffer.append(line);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufReader != null)
				try {
					bufReader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if (inputReader != null)
				try {
					inputReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return stringBuffer.toString();
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dir
	 *            目录路径
	 * @return
	 */
	public static void createDri(String dir) {
		try {
			String path = SDCardRoot + dir;
			File dirpath = new File(path);
			if (!dirpath.exists() && !dirpath.isDirectory()) {
				dirpath.mkdirs();
			} else {

			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public final static String encoding = "GBK";

	public static void getZip(String srcPathname, String zipFilepath) {
		try {
			File file = new File(srcPathname);
			if (!file.exists())
				throw new RuntimeException("source file or directory "
						+ srcPathname + " does not exist.");
			Project proj = new Project();
			FileSet fileSet = new FileSet();
			fileSet.setProject(proj);
			// 判断是目录还是文件
			if (file.isDirectory()) {
				fileSet.setDir(file);
			} else {
				fileSet.setFile(file);
			}
			Zip zip = new Zip();
			zip.setProject(proj);
			zip.setDestFile(new File(zipFilepath));
			zip.addFileset(fileSet);
			zip.setEncoding(encoding);
			zip.execute();
		} catch (BuildException e) {

		} catch (RuntimeException e) {
			// TODO: handle exception
		}
	}

	public static String getFileType(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";
		String fileType = "";
		String[] fileNameAry = fileName.split("\\.");
		if (0 < fileNameAry.length)
			fileType = fileNameAry[fileNameAry.length - 1];
		return StringUtils.lowerCase(fileType);
	}

	/**
	 * 获取主文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getPrefix(String fileName) {
		String mainFileName = "";
		if (StringUtils.isNotEmpty(fileName)) {
			int endIndex = fileName.lastIndexOf(".");
			if (endIndex != -1) {
				mainFileName = fileName.substring(0, endIndex);
			}
		}
		return mainFileName;

	}

	public static boolean inputstreamtofile(InputStream is, String dirPath,
			String fileName) {
		boolean ret = false;
		OutputStream os = null;
		try {
			File file = new File(SDCardRoot + dirPath, fileName);
			if (!file.exists()) {
				file = FileUtil.createFileInSDCard(dirPath, fileName);
			}
			os = new FileOutputStream(file);

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.flush();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param dir
	 * @param fileName
	 * @return
	 * @throws IOException
	 */

	public static File createFileInSDCard(String dir, String fileName)
			throws IOException {
		createDri(dir);
		File file = new File(SDCardRoot + dir + fileName);
		if (isFileExist(dir, fileName))
			file.createNewFile();
		return file;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param dir
	 *            目录路径
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static boolean isFileExist(String dir, String fileName) {
		File file = new File(SDCardRoot + dir + File.separator + fileName);
		return file.exists();
	}

	public static void openFile(Activity activity, String filePath) {
		Intent intent = null;
		try {
			if (StringUtils.lowerCase(filePath).endsWith("pdf")) {
				intent = DocumentFileIntent.getPdfFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("docx")
					|| StringUtils.lowerCase(filePath).endsWith("doc")) {
				intent = DocumentFileIntent.getWordFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("xls")
					|| StringUtils.lowerCase(filePath).endsWith("xlsx")) {
				intent = DocumentFileIntent.getExcelFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("ppt")
					|| StringUtils.lowerCase(filePath).endsWith("pptx")) {
				intent = DocumentFileIntent.getPptFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("chm")) {
				intent = DocumentFileIntent.getChmFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("wps")) {
				intent = DocumentFileIntent.getWpsFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("et")
					|| StringUtils.lowerCase(filePath).endsWith("ett")) {
				intent = DocumentFileIntent.getEtFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("dps")
					|| StringUtils.lowerCase(filePath).endsWith("dpt")) {
				intent = DocumentFileIntent.getDpsFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("bmp")
					|| StringUtils.lowerCase(filePath).endsWith("dib")
					|| StringUtils.lowerCase(filePath).endsWith("gif")
					|| StringUtils.lowerCase(filePath).endsWith("jfif")
					|| StringUtils.lowerCase(filePath).endsWith("jpe")
					|| StringUtils.lowerCase(filePath).endsWith("jpeg")
					|| StringUtils.lowerCase(filePath).endsWith("jpg")
					|| StringUtils.lowerCase(filePath).endsWith("png")
					|| StringUtils.lowerCase(filePath).endsWith("tiff")
					|| StringUtils.lowerCase(filePath).endsWith("ico")) {
				intent = DocumentFileIntent.getImageFileIntent(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("apk")) {
				intent = DocumentFileIntent.getApkFile(filePath);
			} else if (StringUtils.lowerCase(filePath).endsWith("txt")) {
				intent = DocumentFileIntent.getTextFileIntent(filePath, false);
			} else {
				Toast toast = Toast.makeText(activity, "不支持打开该文件",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
			}

			if (intent != null)
				activity.startActivity(intent);

		} catch (Exception e) {
		} finally {
			// this.finish();
		}
	}

	/**
	 * 删除下载附件的目录
	 */
	public static void delFileAttach() {
		try {
			File file = new File(SDCardRoot + ATTACH_DIRPATH);
			deleteAllFiles(file);
		} catch (Exception e) {

		}
	}

	/**
	 * 删除目录下的文件
	 */
	private static void deleteAllFiles(File root) {
		File files[] = root.listFiles();
		if (files != null)
			for (File f : files) {
				if (f.isDirectory()) { // 判断是否为文件夹
					deleteAllFiles(f);
					try {
						f.delete();
					} catch (Exception e) {
					}
				} else {
					if (f.exists()) { // 判断是否存在
						deleteAllFiles(f);
						try {
							f.delete();
						} catch (Exception e) {
						}
					}
				}
			}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}
			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	public static String getFileName(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";
		return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
	}

	public static String getFileFormat(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

}
