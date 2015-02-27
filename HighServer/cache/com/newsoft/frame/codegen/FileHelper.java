package com.newsoft.frame.codegen;

import java.io.File;

import org.apache.commons.lang.StringUtils;

/**
 * The file utility class.
 * 
 * @author guohb
 * 
 */
public class FileHelper {
	/**
	 * Fetch the last package name.
	 * 
	 * @param className
	 *            The class name such as 'com.newsoft.frame.user'
	 * @return Return 'user' in the above case.
	 */
	public static String getLastPackageName(String className) {
		int index = className.lastIndexOf(".");
		if (index < 0) {
			return className.toLowerCase();
		}
		return className.substring(index + 1).toLowerCase();
	}

	/**
	 * Get java file prefix according to module name or last package name
	 * 
	 * @param moduleName
	 *            Such as 'user'
	 * @return Return 'User' in the above case.
	 */
	public static String getJavaFilePrefix(String moduleName) {
		return StringUtils.capitalize(moduleName);
	}

	/**
	 * 
	 * @param parent
	 *            The parent such as '/mnt/frame/generated'
	 * @param packageName
	 *            The package name such as 'com.newsoft.frame.user'
	 * @return Return '/mnt/frame/generated/com/newsoft/frame/user' in the above
	 *         case.
	 */
	public static String getFilePath(String parent, String packageName) {
		return parent.trim() + "/" + packageName.replaceAll("\\.", "/").trim();
	}

	/**
	 * To create file or directory recursively.
	 * 
	 * @param filePath
	 *            Such as '/mnt/frame/test.txt' or '/mnt/test'
	 * @return
	 */
	public static boolean createFileIfNotExist(String filePath) {
		if (filePath == null || filePath.trim().equals("")) {
			return false;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		}

		filePath = filePath.replaceAll("\\\\", "/");

		boolean isPathDirectory = filePath.indexOf(".") > 0 ? false : true;
		if (isPathDirectory) {
			return createFileExplicitly(filePath, true);
		}

		// Create parent directory
		int index = filePath.lastIndexOf("/");
		String directoryPath = filePath.substring(0, index);
		boolean result = createFileExplicitly(directoryPath, true);
		if (!result) {
			return false;
		}
		// Create the file finally
		return createFileExplicitly(filePath, false);
	}

	private static boolean createFileExplicitly(String filePath, boolean isDir) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				if (isDir) {
					return file.mkdirs();
				} else {
					return file.createNewFile();
				}
			} catch (Exception e) {
				System.err.println("Error when create new file with path: " + filePath);
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String className = "com.newsoft.frame.user";
		String packageName = FileHelper.getLastPackageName(className);
		System.out.println(packageName);
		String parent = "/mnt/frame/generated";
		System.out.println(FileHelper.getFilePath(parent, packageName));
		String filePath = "c:/mnt/code_gen/test333/456.txt";
		//filePath = "c:\\mnt\\code_gen\\test333";
		FileHelper.createFileIfNotExist(filePath);
	}
}
