package com.newsoft.frame.codegen.parameter;

import org.apache.commons.lang.StringUtils;

/**
 * To support the parameter transformation.
 * 
 * @author guohb
 * 
 */
public class ParamHelper {

	/**
	 * Convert column name, such as 'user_name' to 'userName'
	 * 
	 * @param columnName
	 * @return
	 */
	public static String getFieldName(String columnName) {
		if (columnName == null) {
			return null;
		}
		columnName = columnName.toLowerCase();
		String[] names = columnName.split("_");
		StringBuilder filedName = new StringBuilder();
		for (int i = 0; i < names.length; i++) {
			if (names[i].trim().equals("")) {
				continue;
			}
			if (filedName.length() == 0) {
				filedName.append(names[i].trim());
				continue;
			}
			filedName.append(StringUtils.capitalize(names[i].trim()));
		}

		return filedName.toString();
	}

	/**
	 * Convert field name, such as 'userName' to 'user_name'
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String getColumnName(String fieldName) {
		if (fieldName == null) {
			return null;
		}
		StringBuilder columnName = new StringBuilder();
		for (int i = 0; i < fieldName.length(); i++) {
			char ch = fieldName.charAt(i);
			if (Character.isUpperCase(ch) && columnName.length() > 0) {
				columnName.append("_");
			}
			columnName.append(ch);
		}
		return columnName.toString().toLowerCase();
	}

	public static void main(String[] args) {
		System.out.println(ParamHelper.getFieldName("user"));
		System.out.println(ParamHelper.getFieldName("user_role"));
		System.out.println(ParamHelper.getFieldName("user_post_relation"));
		System.out.println(ParamHelper.getFieldName("_user_salary_1_"));

		System.out.println(ParamHelper.getColumnName("user"));
		System.out.println(ParamHelper.getColumnName("userRole"));
		System.out.println(ParamHelper.getColumnName("userPostRelation"));
		System.out.println(ParamHelper.getColumnName("userSalary1"));
	}
}
