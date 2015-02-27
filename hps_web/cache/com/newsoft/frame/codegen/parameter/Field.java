package com.newsoft.frame.codegen.parameter;

import org.apache.commons.lang.StringUtils;

/**
 * The property field of the entity.
 * 
 * @author guohb
 * 
 */
public class Field {

	/**
	 * The field name.
	 */
	private String fieldName;

	/**
	 * The class name of the field type, e.g. 'java.lang.String'.
	 */
	private String fieldType;

	/**
	 * The table column name in database
	 */
	private String columnName;

	private String gotterName;

	private String setterName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;

		// Set the getter and setter method name
		String capitalizedFieldName = StringUtils.capitalize(fieldName);
		this.gotterName = "get" + capitalizedFieldName;
		this.setterName = "set" + capitalizedFieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName
	 *            the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the setterName
	 */
	public String getSetterName() {
		return setterName;
	}

	/**
	 * @param setterName
	 *            the setterName to set
	 */
	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}

	/**
	 * @return the gotterName
	 */
	public String getGotterName() {
		return gotterName;
	}

	/**
	 * @param gotterName
	 *            the gotterName to set
	 */
	public void setGotterName(String gotterName) {
		this.gotterName = gotterName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Field [fieldName=" + fieldName + ", fieldType=" + fieldType + ", columnName=" + columnName + "]";
	}

}
