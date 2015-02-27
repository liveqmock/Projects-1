package com.newsoft.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newsoft.common.attach.po.Attach;

/**
 * @author fengmq
 * 
 */
public class FileTools {
	final static Logger log = Logger.getLogger(FileTools.class);

	/**
	 * 将指定文件拷贝到指定目录下
	 * 
	 * @param filePath
	 * @param targetDirectory
	 * @return
	 */
	public static boolean copyFileToSpecialDirectory(String filePath,
			String targetFile) {
		FileInputStream in = null;
		FileOutputStream out = null;
		byte[] buffer = new byte[1024];
		try {
			in = new FileInputStream(filePath);
			File dest = new File(targetFile);
			if (!dest.exists()) {// 目标文件对应的目录不存在，创建新的目录
				int index = targetFile.lastIndexOf("/");
				if (index > 0) {
					String path = targetFile.substring(0, index);
					new File(path).mkdirs();
				}
			}
			out = new FileOutputStream(targetFile);
			int num = 0;
			while ((num = in.read(buffer)) != -1) {
				out.write(buffer, 0, num);
			}
		} catch (FileNotFoundException ex) {
			log.error(ex.getMessage());
			return false;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException ex) {
				log.error(ex.getMessage());
			}
		}
		return true;
	}

	/**
	 * 获取指定文件的内容，返回字符串
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public static String getFileContentByPath(String filePath) {
		StringBuffer content = new StringBuffer();
		try {
			File file = new File(filePath);
			InputStreamReader is = new InputStreamReader(new FileInputStream(
					file), "UTF-8");
			BufferedReader reader = new BufferedReader(is);
			String line = "";
			while ((line = reader.readLine()) != null) {
				content.append(line + "\n");
			}
			reader.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			return "";
		}
		return content.toString();
	}

	/**
	 * 根据字符串创建文件到指定目录
	 * 
	 * @param filePath
	 *            文件存放路径和名称
	 * @param fileContent
	 *            文件内容
	 * @return
	 */
	public static boolean createFileToDirectoryByString(String filePath,
			String fileContent) {
		File javaFile = new File(filePath);
		try {
			OutputStream ous = new FileOutputStream(javaFile);
			BufferedWriter rd = new BufferedWriter(new OutputStreamWriter(ous,
					"utf-8"));
			rd.write(fileContent);
			rd.close();
			ous.close();
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param attach
	 * @throws IOException
	 */
	public static void downloadFile(HttpServletResponse response, Attach attach)
			throws IOException {
		OutputStream out = response.getOutputStream();
		String sExt = attach.getContentType();
		// 文件格式为pdf、doc、xml格式的直接用浏览器打开。其他提示下载或者打开
		if (sExt.equals("application/pdf") || sExt.equals("image/pjpeg")
				|| sExt.equals("image/bmp")
				|| sExt.equals("application/vnd.ms-powerpoint")
				|| sExt.equals("text/plain")
				|| sExt.equals("application/msword")
				|| sExt.equals("text/plain")
				|| sExt.equals("application/vnd.ms-excel")) {
			if (sExt.equals("application/pdf"))
				response.setContentType(sExt);
			else
				response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "inline;filename="
					+ new String(attach.getFileName().getBytes("GBK"),
							"ISO8859_1"));
		} else {
			response.reset();
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(attach.getFileName().getBytes("GBK"),
							"ISO8859_1"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-transform, max-age=0");
            response.setHeader("Accept-Ranges", "none");

		}
		
		InputStream is = null;
		String fileDir = attach.getFileDir();
		if (StringTools.isEmpty(fileDir)) {
			is = new ByteArrayInputStream(attach.getContent());
		} else {
			File file = new File(fileDir + attach.getFileName());
			is = new FileInputStream(file);
		}
		
		outputAttachFileStream(out, is);
	}

	/**
	 * 输出文件
	 * 
	 * @param out
	 * @param inputStream
	 * @throws IOException
	 */
	public static void outputAttachFileStream(OutputStream out,
			InputStream inputStream) throws IOException {
		byte[] buffer = new byte[102400];
		try {
			int len;
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	// 获取文件类型
	public static String getFileExtention(String sExt) {
		sExt = sExt.toLowerCase();
		if (sExt.indexOf("/") > 0)
			return sExt;
		else if (sExt.equals("jpg") || sExt.equals("jpeg"))
			return "image/pjpeg";
		else if (sExt.equals("bmp"))
			return "image/bmp";
		else if (sExt.equals("doc") || sExt.equals("rtf"))
			return "application/msword";
		else if (sExt.equals("pdf"))
			return "application/pdf";
		else if (sExt.equals("ppt"))
			return "application/vnd.ms-powerpoint";
		else if (sExt.equals("txt") || sExt.equals("pas") || sExt.equals("cpp")
				|| sExt.equals("c") || sExt.equals("java")
				|| sExt.equals("xml") || sExt.equals("ini"))
			return "text/plain";
		else if (sExt.equals("xls") || sExt.equals("cvs"))
			return "application/vnd.ms-excel";
		else if (sExt.equals("mp3"))
			return "audio/mpeg";
		else if (sExt.equals("wma") || sExt.equals("avi"))
			return "audio/x-ms-wma";
		else if (sExt.equals("wmv"))
			return "video/x-ms-wmv";
		else if (sExt.equals("zip"))
			return "application/x-zip-compressed";
		else
			return "application/octet-stream";
	}

	// 获取文件类型
	public static String getFileExtentionByOriginalFileName(String fileName) {
		if (fileName.indexOf(".") != -1) {
			return getFileExtention(fileName.substring(fileName.lastIndexOf(".")+1));
		} else {
			return "无扩展名";
		}
	}

}
